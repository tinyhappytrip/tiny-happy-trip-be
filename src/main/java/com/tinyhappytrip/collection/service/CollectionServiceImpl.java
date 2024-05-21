package com.tinyhappytrip.collection.service;//package com.tinyhappytrip.collection.service;

import com.tinyhappytrip.collection.domain.Collection;
import com.tinyhappytrip.collection.domain.CollectionComment;
import com.tinyhappytrip.collection.domain.enums.Scope;
import com.tinyhappytrip.collection.dto.CollectionRequest;
import com.tinyhappytrip.collection.dto.CollectionResponse;
import com.tinyhappytrip.collection.mapper.*;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.story.mapper.StoryImageMapper;
import com.tinyhappytrip.story.mapper.StoryMapper;
import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    private final CollectionCommentMapper collectionCommentMapper;
    private final CollectionHashTagMapper collectionHashTagMapper;
    private final CollectionItemMapper collectionItemMapper;
    private final CollectionLikeMapper collectionLikeMapper;
    private final CollectionMapper collectionMapper;
    private final StoryImageMapper storyImageMapper;
    private final StoryMapper storyMapper;
    private final UserMapper userMapper;

    @Override
    public int createCollection(CollectionRequest.CreateDto createDto) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            System.out.println(createDto);
            Collection collection = Collection.builder()
                    .userId(userId)
                    .title(createDto.getTitle())
                    .description(createDto.getDescription())
                    .scope(createDto.getScope())
                    .musicKeyword(createDto.getMusicKeyword())
                    .build();
            createDto.setUserId(userId);
            collectionMapper.insert(createDto);

            // 스캔하여 hashtags 리스트에 단어 추가
            List<String> hashtags = new ArrayList<>();
            String content = createDto.getDescription();
            Pattern pattern = Pattern.compile("#\\S+");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                hashtags.add(matcher.group());
            }
            if (!hashtags.isEmpty()) {
                collectionHashTagMapper.insert(createDto.getCollectionId(), hashtags);
            }
            collectionItemMapper.insert(createDto.getCollectionId(), createDto.getCollectionItems());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteCollection(Long collectionId) {
        return collectionMapper.delete(SecurityUtil.getCurrentUserId(), collectionId);
    }

    @Override
    public int updateCollection(String basePath, Long collectionId, CollectionRequest.Update update) {
        Long userId = SecurityUtil.getCurrentUserId();
        System.out.println(userId);
        try {
            Collection collection = new Collection(
                    userId,
                    update.getTitle(),
                    update.getDescription(),
                    update.getScope(),
                    update.getMusicKeyword()
            );
            collectionMapper.update(userId, collectionId, collection);

            // 관련 collectionItem 삭제 및 삽입
            collectionItemMapper.delete(collectionId);
            collectionItemMapper.insert(collectionId, update.getCollectionItems());

            // 관련 collectionHashTag 삭제 및 삽입
            collectionHashTagMapper.delete(collectionId);
            // 스캔하여 hashtags 리스트에 단어 추가
            List<String> hashtags = new ArrayList<>();
            String content = update.getDescription();
            Pattern pattern = Pattern.compile("#\\S+");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                hashtags.add(matcher.group());
            }
            if (!hashtags.isEmpty()) {
                collectionHashTagMapper.insert(collectionId, hashtags);
            }
            collectionHashTagMapper.insert(collectionId, update.getHashtags());

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int setCollectionLike(Long collectionId) {
        Long userId = SecurityUtil.getCurrentUserId();
        // 좋아요가 눌려있다면,
        if (collectionLikeMapper.selectCountByCollectionIdAndUserId(collectionId, userId) == 1) {
            return collectionLikeMapper.delete(collectionId, userId);
        }
        return collectionLikeMapper.insert(collectionId, userId);
    }

    @Override
    public List<CollectionResponse.CollectionInfo> getUserCollection(Long userId) {
        List<CollectionResponse.CollectionInfo> collections = new ArrayList<>();
        for (Collection collection : collectionMapper.selectUserCollections(userId)) {
            System.out.println(collection);
            CollectionResponse.CollectionInfo collectionInfo = makecollectionInfo(collection);
            collections.add(collectionInfo);
        }
        return collections;
    }

    @Override
    public List<CollectionResponse.CollectionInfo> getUserLikeCollection() {
        List<CollectionResponse.CollectionInfo> collections = new ArrayList<>();
        Long userId = SecurityUtil.getCurrentUserId();
        for (Long collectionId : collectionLikeMapper.selectUserLikeCollection(userId)) {
            CollectionResponse.CollectionInfo collectionInfo = makecollectionInfo(collectionMapper.selectCollectionByCollectionId(collectionId));
            collections.add(collectionInfo);
        }
        return collections;
    }

    @Override
    public List<CollectionResponse.CollectionInfo> getCollectionsBySearchKeyword(String keyword) {
        Set<CollectionResponse.CollectionInfo> collectionSet = new HashSet<>();

        for (Long collectionId: collectionHashTagMapper.selectHashtagCollectionIdsBySearchKeyword(keyword)) {
            collectionSet.add(makecollectionInfo(collectionMapper.selectCollectionByCollectionId(collectionId)));
        }

        for (Collection collection: collectionMapper.selectCollectionsBySearchKeyword(keyword)) {
            collectionSet.add(makecollectionInfo(collection));
        }

        return new ArrayList<>(collectionSet);
    }

    @Override
    public List<CollectionResponse.CollectionInfo> getTopThreeCollection() {
        List<CollectionResponse.CollectionInfo> collections = new ArrayList<>();
        for (Long collectionId : collectionLikeMapper.selectTopThreeCollection()) {
            CollectionResponse.CollectionInfo collectionInfo = makecollectionInfo(collectionMapper.selectCollectionByCollectionId(collectionId));
            collections.add(collectionInfo);
        }

        for (Collection collection : collectionMapper.selectCollections()) {
            collections.add(makecollectionInfo(collection));
        }
        return collections;
    }

    @Override
    public CollectionResponse.CollectionInfo getCollectionByCollectionId(Long collectionId) {
        return makecollectionInfo(collectionMapper.selectCollectionByCollectionId(collectionId));
    }

    @Override
    public int addComment(Long collectionId, CollectionRequest.Comment comment) {
        return collectionCommentMapper.insert(collectionId, SecurityUtil.getCurrentUserId(), comment.getContent());
    }

    @Override
    public int deleteComment(Long collectionCommentId) {
        return collectionCommentMapper.delete(collectionCommentId, SecurityUtil.getCurrentUserId());
    }

    private String saveFile(String basePath, MultipartFile imageFile) throws IOException {
        String yyyyMm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String uploadPath = basePath + '/' + yyyyMm;
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String storedFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String fullPath = uploadPath + '/' + storedFileName;
        File dest = new File(fullPath);
        imageFile.transferTo(dest); // 파일 저장
        return fullPath;
    }

    private CollectionResponse.CollectionInfo makecollectionInfo(Collection collection) {
        User user = userMapper.selectByUserId(collection.getUserId()).get();
        CollectionResponse.CollectionInfo collectionInfo = CollectionResponse.CollectionInfo.from(user, collection);
        Long collectionId = collectionInfo.getCollectionId();
        List<CollectionResponse.collectionItem> collectionItems = new ArrayList<>();
        System.out.println(collectionId);
        // 플레이리스트 내 모든 스토리들을 collectionItem type으로 변경 (collectionItem 대표 사진은 story 사진들 중 가장 앞 사진)
        List<Long> items = collectionItemMapper.selectCollectionItems(collectionId);
        System.out.println("items.size() = " + items.size());
        for (Long storyId : items) {
            System.out.println("collection" + " " + collection);
            System.out.println(storyId);
            List<String> images = storyImageMapper.selectAllByStoryId(storyId);
            if (images.isEmpty()) continue;
            collectionItems.add(
                    CollectionResponse.collectionItem.from(
                            storyMapper.selectByStoryId(storyId),
                            storyImageMapper.selectAllByStoryId(storyId).get(0)
                    )
            );
        }
        collectionInfo.setCollectionItems(collectionItems);

        // 플레이리스트 해시태그 세팅
        List<String> hashtags = collectionHashTagMapper.selectHashTagsByCollectionId(collectionId);
        collectionInfo.setHashtags(hashtags);

        // 플레이리스트 댓글 세팅
        List<CollectionResponse.Comment> comments = new ArrayList<>();
        for (CollectionComment collectionComment : collectionCommentMapper.selectCommentByCollectionId(collectionId)) {
            comments.add(CollectionResponse.Comment.from(user, collectionComment));
        }
        collectionInfo.setComments(comments);

        // 플레이리스트 좋아요 수 세팅
        collectionInfo.setLikeCount(collectionLikeMapper.selectCountByCollectionId(collectionId));
        return collectionInfo;
    }
}