package com.tinyhappytrip.gotolist.controller;

import com.tinyhappytrip.gotolist.dto.GotoRequest;
import com.tinyhappytrip.gotolist.dto.GotoResponse;
import com.tinyhappytrip.gotolist.service.GotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/goto-list")
@RequiredArgsConstructor
public class GotoController {
    private final GotoService gotoService;

    @PostMapping
    public void createGotoList(@RequestParam String title) {
        gotoService.createGotoList(title);
    }

    @PostMapping("/{gotoListId}")
    public void createGotoItem(@Value("${image.goto-list}") String basePath, @PathVariable Long gotoListId, @ModelAttribute GotoRequest.CreateItemDto createItemDto) throws IOException {
        gotoService.createGotoItem(basePath, gotoListId, createItemDto);
    }

    @GetMapping
    public GotoResponse.GotoListDto getGotoList() {
        return gotoService.getGotoList();
    }

    @GetMapping("/{gotoListId}")
    public List<GotoResponse.GotoItemDto> getGotoItems(@PathVariable Long gotoListId) {
        return gotoService.getGotoItems(gotoListId);
    }

    @DeleteMapping("/{gotoListId}")
    public void deleteGotoList(@PathVariable Long gotoListId) {
        gotoService.deleteGotoList(gotoListId);
    }

    @DeleteMapping("/{gotoListId}/{gotoItemId}")
    public void deleteGotoItem(@PathVariable Long gotoItemId, @PathVariable Long gotoListId) {
        gotoService.deleteGotoItem(gotoItemId, gotoListId);
    }
}
