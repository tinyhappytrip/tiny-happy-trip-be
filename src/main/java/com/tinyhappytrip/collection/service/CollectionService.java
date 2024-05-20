package com.tinyhappytrip.collection.service;

import com.tinyhappytrip.collection.dto.CollectionRequest;
import com.tinyhappytrip.collection.dto.CollectionResponse;

import java.util.List;

public interface CollectionService {
    int createCollection(CollectionRequest.CreateDto createDto);

    int deleteCollection(Long collectionId);

    int updateCollection(String basePath, Long collectionId, CollectionRequest.Update update);

    int setCollectionLike(Long collectionId);

    CollectionResponse.CollectionInfo getCollectionByCollectionId(Long collectionId);

    List<CollectionResponse.CollectionInfo> getUserCollection();

    List<CollectionResponse.CollectionInfo> getUserLikeCollection();

    List<CollectionResponse.CollectionInfo> getTopThreeCollection();

    // 댓글
    int addComment(Long collectionId, CollectionRequest.Comment comment);

    int deleteComment(Long collectionCommentId);
}

