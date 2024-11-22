package com.tinyhappytrip.gotolist.service;

import com.tinyhappytrip.gotolist.dto.GotoRequest;
import com.tinyhappytrip.gotolist.dto.GotoResponse;

import java.io.IOException;
import java.util.List;

public interface GotoService {
    void createGotoList(String title);

    void createGotoItem(String basePath, Long gotoListId, GotoRequest.CreateItemDto createItemDto) throws IOException;

    GotoResponse.GotoListDto getGotoList();

    List<GotoResponse.GotoItemDto> getGotoItems(Long gotoListId);

    void deleteGotoItem(Long gotoItemId, Long gotoListId);

    void deleteGotoList(Long gotoListId);
}
