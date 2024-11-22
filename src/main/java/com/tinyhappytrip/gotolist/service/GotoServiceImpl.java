package com.tinyhappytrip.gotolist.service;

import com.tinyhappytrip.gotolist.domain.GotoItem;
import com.tinyhappytrip.gotolist.domain.GotoList;
import com.tinyhappytrip.gotolist.dto.GotoRequest;
import com.tinyhappytrip.gotolist.dto.GotoResponse;
import com.tinyhappytrip.gotolist.mapper.GotoMapper;
import com.tinyhappytrip.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GotoServiceImpl implements GotoService {
    private final GotoMapper gotoMapper;

    @Override
    public void createGotoList(String title) {
        gotoMapper.insertList(title, SecurityUtil.getCurrentUserId());
    }

    @Override
    public void createGotoItem(String basePath, Long gotoListId, GotoRequest.CreateItemDto createItemDto) throws IOException {
        String placeImage = saveFile(basePath, createItemDto.getImageFile());
        createItemDto.setPlaceImage(placeImage);
        createItemDto.setGotoListId(gotoListId);
        GotoItem gotoItem = createItemDto.toEntity();
        gotoMapper.insertItem(gotoItem);
    }

    @Override
    public GotoResponse.GotoListDto getGotoList() {
        GotoList gotoList = gotoMapper.selectGotoList(SecurityUtil.getCurrentUserId());
        Long count = gotoMapper.selectCountGotoList(gotoList.getGotoListId());
        return GotoResponse.GotoListDto.toResponseDto(gotoList, count);
    }

    @Override
    public List<GotoResponse.GotoItemDto> getGotoItems(Long gotoListId) {
        String title = gotoMapper.selectGotoTitleByGotoListId(gotoListId);
        List<GotoItem> gotoItems = gotoMapper.selectGotoItemsByGotoListId(gotoListId);
        List<GotoResponse.GotoItemDto> list = new ArrayList<>();
        for (GotoItem item : gotoItems) {
            list.add(GotoResponse.GotoItemDto.toResponseDto(item, title));
        }
        return list;
    }

    @Override
    public void deleteGotoItem(Long gotoItemId, Long gotoListId) {
        gotoMapper.deleteGotoItem(gotoItemId, gotoListId);
    }

    @Override
    public void deleteGotoList(Long gotoListId) {
        gotoMapper.deleteGotoList(gotoListId);
    }

    public String saveFile(String basePath, MultipartFile imageFile) throws IOException {
        String yyyyMm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String uploadPath = basePath + '/' + yyyyMm;
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String storedFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String storyImage = uploadPath + '/' + storedFileName;
        File dest = new File(storyImage);
        imageFile.transferTo(dest);

        return storyImage;
    }
}
