package com.tinyhappytrip.collection.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionLikeMapper {
    int selectCountByCollectionIdAndUserId(Long collectionId, Long userId);
    int insert(Long collectionId, Long userId);
    int delete(Long collectionId, Long userId);
    int selectCountByCollectionId(Long collectionId);
    List<Long> selectUserLikeCollection(Long userId);
    List<Long> selectTopThreeCollection();
}


