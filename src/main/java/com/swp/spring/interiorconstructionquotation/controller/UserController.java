package com.swp.spring.interiorconstructionquotation.controller;

import com.swp.spring.interiorconstructionquotation.entity.User;
import com.swp.spring.interiorconstructionquotation.security.ChangePasswordRequest;
import com.swp.spring.interiorconstructionquotation.security.ForgetPasswordRequest;
import com.swp.spring.interiorconstructionquotation.security.JwtResponse;
import com.swp.spring.interiorconstructionquotation.security.LoginRequest;
import com.swp.spring.interiorconstructionquotation.service.JWT.JwtService;
import com.swp.spring.interiorconstructionquotation.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;

    // Allow requests from 'http://localhost:3000'

    @GetMapping("/enable")
    public ResponseEntity<?> enableAccount(@RequestParam String email, @RequestParam String enableCode){
        ResponseEntity<?> response = iUserService.enableAccount(email, enableCode);
        return response;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody User user){
        ResponseEntity<?> response = iUserService.register(user);
        return response;
    }
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest){
        try {
            iUserService.forgetPassword(forgetPasswordRequest.getUsername(), forgetPasswordRequest.getEmail());
            return ResponseEntity.ok("Gửi email thành công!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Đã xảy ra lỗi trong quá trình xử lý");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        // Xác thực người dùng bằng tên đăng nhập và mật khẩu
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            // Nếu xác thực thành công, tạo token JWT
            if(authentication.isAuthenticated()){
                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                System.out.println(jwt);
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        }catch (AuthenticationException e){
            // Xác thực không thành công, trả về lỗi hoặc thông báo
            System.out.println("aaa");
            return ResponseEntity.badRequest().body("Tên đăng nhập hặc mật khẩu không chính xác.");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công.");
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        // Xác thực người dùng bằng tên đăng nhập và mật khẩu
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(changePasswordRequest.getUsername(), changePasswordRequest.getOldPassword())
            );
            if(authentication.isAuthenticated()){
                iUserService.changePassword(changePasswordRequest.getUsername(), changePasswordRequest.getNewPassword());
                return ResponseEntity.ok("Đổi mật khẩu thành công!");
            }
        }catch (AuthenticationException e){
            // Xác thực không thành công, trả về lỗi hoặc thông báo
            System.out.println("aaa");
            return ResponseEntity.badRequest().body("Tên đăng nhập hặc mật khẩu không chính xác.");
        }
        return ResponseEntity.badRequest().body("Mật khẩu không chính xác");
    }
}
