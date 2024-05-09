package com.tinyhappytrip.user.controller;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.dto.UserResponse;
import com.tinyhappytrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody UserRequest.Login login) {
        JwtToken jwtToken = userService.login(login);
        return ResponseEntity.status(jwtToken != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).body(jwtToken);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Integer> join(@RequestBody UserRequest.Join join) {
        int res = userService.join(join);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    // 중복 검증 false -> 중복된 value 없음 (통과)
    @GetMapping("/validate")
    public boolean validate(@RequestParam String type, @RequestParam String value) {
        return userService.validate(type, value);
    }

    // 회원 정보 수정
    @PatchMapping
    public ResponseEntity<Integer> editUserInfo(@RequestBody UserRequest.Edit edit) {
        return ResponseEntity.status(userService.editUserInfo(edit) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 회원 삭제 -> 삭제 되었으면 200
    @DeleteMapping
    public ResponseEntity<Integer> withdrawal() {
        return ResponseEntity.status(userService.withdrawal() == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 회원 정보 가져오기
    @GetMapping("/{userId}")
    public UserResponse.UserInfo getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    // 팔로우 추가
    @PostMapping("/follow/{followerId}")
    public ResponseEntity<Integer> addFollow(@PathVariable Long followerId) {
        return ResponseEntity.status(userService.addFollow(followerId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 팔로우 취소
    @DeleteMapping("/follow/{followerId}")
    public ResponseEntity<Integer> removeFollow(@PathVariable Long followerId) {
        return ResponseEntity.status(userService.removeFollow(followerId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 팔로워 / 팔로잉 목록 가져오기
    @GetMapping("/follow/{userId}")
    public List<Long> getFollowList(@RequestParam String type, @PathVariable Long userId) {
        return userService.getFollowList(type, userId);
    }

//    @GetMapping("/follow/{userId}")
//    public ResponseEntity<UserResponse> followeeList(@PathVariable Long userId) {
//    }
//
//
//    @PostMapping("/follows/{userId}")
//    public ResponseEntity<Void> followUser(@PathVariable Long userId) {
//        // userId를 사용하여 특정 사용자를 팔로우하는 작업 수행
//    }
//
//    @DeleteMapping("/follows/{userId}")
//    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId) {
//        // userId를 사용하여 특정 사용자를 언팔로우하는 작업 수행
//    }


    /**
     * 1. 유저 이미지 수정 API
     * 2. 소
     */

//    @PostMapping("/duplication")
//    public ResponseEntity<Integer> duplication(@RequestBody UserRequest.Duplcation duplcation) {
//        int res = userService.duplicateId();
//        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).body(res);
//    }
    @PostMapping("/members/test")
    public Long member() {
        Long currentUsername = SecurityUtil.getCurrentUserId();
        System.out.println("currentUsername = " + currentUsername);
        return currentUsername;
    }

    @PostMapping("/admin/test")
    public Long admin() {
        Long currentUsername = SecurityUtil.getCurrentUserId();
        System.out.println("currentUsername = " + currentUsername);
        return currentUsername;
    }
}
