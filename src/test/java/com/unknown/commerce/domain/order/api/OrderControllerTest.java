package com.unknown.commerce.domain.order.api;

import com.unknown.commerce.domain.product.dao.ProductRepository;
import com.unknown.commerce.domain.product.entity.Product;
import com.unknown.commerce.global.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("동시에 11개 요청")
    public void t1() throws Exception {
        int threadCount = 11;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        List<ResultActions> results = Collections.synchronizedList(new ArrayList<>());

        for(int i = 0; i < threadCount; i++){
            executorService.submit(() -> {
                try {
                    ResultActions resultActions = mvc
                            .perform(post("/v1/orders")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                                "orderItems": [
                                                    {
                                                    "itemId": 1,
                                                    "name": "신라면 5개입",
                                                    "price": 10000,
                                                    "quantity": 2,
                                                    "productRequests": [
                                                        {
                                                        "productId": 1,
                                                        "name": "신라면",
                                                        "price": 2500,
                                                        "quantity": 5
                                                        }
                                                    ]
                                                    }
                                                ],
                                                "status": "COMPLETED",
                                                "address": "서울시",
                                                "addressDetail": "강남구",
                                                "postcode": "01234",
                                                "phone": 1012345678,
                                                "ordererName": "TEST1"
                                            }
                                            """)
                            )
                            .andDo(print());
                    results.add(resultActions);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // 제품의 재고가 0이어야 함
        Product product = productRepository.findById(1L).orElseThrow();
        assertEquals(0, product.getStock());

        // 10개의 요청은 정상적으로 처리되어야 함
        for (int i = 0; i < 10; i++) {
            ResultActions result = results.get(i);
            result.andExpect(status().isCreated());
        }

        // 마지막 11번째 요청은 재고 부족 메시지를 반환해야 함
        ResultActions lastResult = results.get(10);
        lastResult.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(HttpResponse.Fail.OUT_OF_STOCK_PRODUCT.getMessage()));
    }
}