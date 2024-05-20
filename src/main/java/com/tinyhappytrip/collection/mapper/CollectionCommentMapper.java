package com.tinyhappytrip.collection.mapper;

import com.tinyhappytrip.collection.domain.CollectionComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionCommentMapper {
    int insert(Long collectionId, Long userId, String content);

    int delete(Long collectionCommentId, Long userId);

    List<CollectionComment> selectCommentBycollectionId(Long collectionId);
}
