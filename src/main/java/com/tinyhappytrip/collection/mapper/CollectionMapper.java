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
    List<Collection> selectUserCollections(Long userId);
    List<Collection> selectCollections();
    List<Collection> selectCollectionsBySearchKeyword(String searchKeyword);
    Collection selectCollectionByCollectionId(Long collectionId);

}