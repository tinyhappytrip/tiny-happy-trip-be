package com.tinyhappytrip.collection.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionItemMapper {
    int insert(Long collectionId, List<Long> collectionItems);
    int delete(Long collectionId);
    List<Long> selectCollectionItems(Long collectionId);
}
