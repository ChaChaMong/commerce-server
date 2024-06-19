package com.unknown.commerce.global.init;

import com.unknown.commerce.domain.item.dao.ItemRepository;
import com.unknown.commerce.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@Profile("!test")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ItemRepository itemRepository;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            if (!itemRepository.findAll().isEmpty()) {
                return;
            }

            initItem();
        };
    }

    private void initItem() {
        itemRepository.save(Item.builder().name("상품1").price(BigDecimal.valueOf(10000)).build());
        itemRepository.save(Item.builder().name("상품2").price(BigDecimal.valueOf(20000)).build());
        itemRepository.save(Item.builder().name("상품3").price(BigDecimal.valueOf(30000)).build());
    }
}
