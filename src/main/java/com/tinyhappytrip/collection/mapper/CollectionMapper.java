package com.tinyhappytrip.collection.mapper;

import com.tinyhappytrip.collection.domain.Collection;
import com.tinyhappytrip.collection.dto.CollectionRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {
    int insert(CollectionRequest.CreateDto collection);

    int delete(Long userId, Long collectionId);

    int update(Long userId, Long collectionId, Collection collection);
    Collection selectCollectionByCollectionId(Long collectionId);
    List<Collection> selectUserCollection(Long userId);
    List<Collection> selectThreeCollection();
}