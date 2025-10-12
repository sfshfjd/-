package com.example.loginregister.controller;

import com.example.loginregister.dto.ApiResponse;
import com.example.loginregister.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    /**
     * 公共接口，无需认证
     * @return 响应
     */
    @GetMapping("/public")
    public ResponseEntity<?> allAccess() {
        return ResponseEntity.ok(ApiResponse.success("这是一个公共接口，无需认证即可访问"));
    }
    
    /**
     * 需要认证的接口
     * @return 当前用户信息
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> userAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        return ResponseEntity.ok(ApiResponse.success(
            "欢迎 " + user.getUsername() + "！这是需要认证的用户接口", 
            user
        ));
    }
    
    /**
     * 管理员专用接口
     * @return 响应
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok(ApiResponse.success("这是管理员专用接口"));
    }
    
    /**
     * 获取当前用户信息
     * @return 当前用户信息
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        // 创建用户信息响应，不包含敏感信息
        UserProfileResponse profile = new UserProfileResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
        
        return ResponseEntity.ok(ApiResponse.success("获取用户信息成功", profile));
    }
    
    // 内部类：用户信息响应
    public static class UserProfileResponse {
        private Long id;
        private String username;
        private String email;
        private String role;
        private java.time.LocalDateTime createdAt;
        
        public UserProfileResponse(Long id, String username, String email, 
                                 String role, java.time.LocalDateTime createdAt) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.role = role;
            this.createdAt = createdAt;
        }
        
        // Getters
        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    }
}

