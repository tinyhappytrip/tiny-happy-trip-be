package com.tinyhappytrip.user.controller;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.dto.UserResponse;
import com.tinyhappytrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody UserRequest.LoginDto loginDto) {
        JwtToken token = userService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Integer> join(@Value("${image.user}") String basePath, @RequestBody UserRequest.JoinDto joinDto) {
        joinDto.setUserImage(basePath + "/default.jpg");
        int res = userService.join(joinDto);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    // 중복 검증 false -> 중복된 value 없음 (통과)
    @GetMapping("/validate")
    public String validate(@RequestParam String type, @RequestParam String value) {
        return userService.validate(type, value);
    }

    // 회원 정보 수정
    @PatchMapping
    public ResponseEntity<Integer> editUserInfo(@RequestBody UserRequest.EditDto editDto) {
        return ResponseEntity.status(userService.editUserInfo(editDto) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 회원 삭제 -> 삭제 되었으면 200
    @DeleteMapping
    public ResponseEntity<Integer> withdrawal() {
        return ResponseEntity.status(userService.withdrawal() == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 회원 정보 가져오기
    @GetMapping("/{userId}")
    public UserResponse.UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    // 비밀번호 초기화
    @GetMapping("/reset/{email}")
    public void resetPassword(@PathVariable String email) {
        userService.resetPassword(email);
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
    public ResponseEntity<List<UserResponse.FollowUserDto>> getFollowList(@RequestParam String type, @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFollowList(type, userId));
    }

    // 회원 이미지 수정
    @PatchMapping("/image")
    public ResponseEntity<String> uploadUserImage(@Value("${image.user}") String basePath, @RequestParam("userImageFile") MultipartFile userImageFile) throws IOException {
        userService.uploadUserImage(basePath, userImageFile);
        return ResponseEntity.ok("프로필 이미지가 성공적으로 변경되었습니다.");
    }
}
