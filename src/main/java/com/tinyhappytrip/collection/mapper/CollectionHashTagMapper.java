package com.tinyhappytrip.collection.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionHashTagMapper {
    int insert(Long collectionId, List<String> hashtags);
    int delete(Long collectionId);
    List<String> selectHashTagsByCollectionId(Long collectionId);
    List<Long> selectCollectionIdsBySearchKeyword(String searchKeyword);
}
