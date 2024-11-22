package com.tinyhappytrip.gotolist.mapper;

import com.tinyhappytrip.gotolist.domain.GotoItem;
import com.tinyhappytrip.gotolist.domain.GotoList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GotoMapper {
    void insertList(String title, Long userId);

    void insertItem(GotoItem gotoItem);

    GotoList selectGotoList(Long userId);

    Long selectCountGotoList(Long gotoListId);

    List<GotoItem> selectGotoItemsByGotoListId(Long gotoListId);

    String selectGotoTitleByGotoListId(Long gotoListId);

    void deleteGotoItem(Long gotoItemId, Long gotoListId);

    void deleteGotoList(Long gotoListId);
}
