package com.tinyhappytrip.collection.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionLikeMapper {
    int selectCountBycollectionIdAndUserId(Long collectionId, Long userId);
    int insert(Long collectionId, Long userId);
    int delete(Long collectionId, Long userId);
    int selectCountBycollectionId(Long collectionId);
    List<Long> selectUserLikecollection(Long userId);
    List<Long> selectTopThreecollection();
}


