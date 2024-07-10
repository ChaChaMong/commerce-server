package com.unknown.commerce.global.init;

import com.unknown.commerce.domain.item.dao.ItemProductRepository;
import com.unknown.commerce.domain.item.dao.ItemRepository;
import com.unknown.commerce.domain.item.entity.Item;
import com.unknown.commerce.domain.item.entity.ItemProduct;
import com.unknown.commerce.domain.product.dao.ProductRepository;
import com.unknown.commerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final ItemProductRepository itemProductRepository;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            if (!itemRepository.findAll().isEmpty()) {
                return;
            }

            initProduct();
            initItem();
            initItemProduct();
        };
    }

    private void initProduct() {
        productRepository.save(Product.builder().name("신라면").price(BigDecimal.valueOf(2500)).stock(100L).build());
        productRepository.save(Product.builder().name("삼다수").price(BigDecimal.valueOf(6000)).stock(2L).build());
        productRepository.save(Product.builder().name("포카칩").price(BigDecimal.valueOf(1500)).stock(0L).build());
        productRepository.save(Product.builder().name("빼빼로").price(BigDecimal.valueOf(1000)).stock(100L).build());
        productRepository.save(Product.builder().name("스윙칩").price(BigDecimal.valueOf(1500)).stock(100L).build());
    }

    private void initItem() {
        itemRepository.save(Item.builder().name("신라면 5개입").price(BigDecimal.valueOf(10000)).build());
        itemRepository.save(Item.builder().name("삼다수 6개입").price(BigDecimal.valueOf(30000)).build());
        itemRepository.save(Item.builder().name("과자 세트").price(BigDecimal.valueOf(15000)).build());
    }

    private void initItemProduct() {
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(1L).get()).product(productRepository.findById(1L).get()).quantity(5L).build());
        itemProductRepository.save(ItemProduct.builder().item(itemRepository.findById(2L).get()).product(productRepository.findById(2L).get()).quantity(6L).build());

        Item item = itemRepository.findById(3L).get();
        itemProductRepository.save(ItemProduct.builder().item(item).product(productRepository.findById(3L).get()).quantity(5L).build());
        itemProductRepository.save(ItemProduct.builder().item(item).product(productRepository.findById(4L).get()).quantity(5L).build());
        itemProductRepository.save(ItemProduct.builder().item(item).product(productRepository.findById(5L).get()).quantity(5L).build());
    }
}
