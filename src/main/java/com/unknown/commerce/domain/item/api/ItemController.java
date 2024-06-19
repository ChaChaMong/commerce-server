package com.unknown.commerce.domain.item.api;

import com.unknown.commerce.domain.item.application.ItemService;
import com.unknown.commerce.domain.item.dto.ItemDetailResponse;
import com.unknown.commerce.domain.item.dto.ItemResponse;
import com.unknown.commerce.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<PageResponse<ItemResponse>> getItems(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        PageResponse<ItemResponse> responses = PageResponse.of(this.itemService.getItems(page, size));
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDetailResponse> getItem(
            @PathVariable("id") Long id
    ) {
        ItemDetailResponse itemDetailResponse = itemService.getItem(id);
        return ResponseEntity.ok().body(itemDetailResponse);
    }
}
