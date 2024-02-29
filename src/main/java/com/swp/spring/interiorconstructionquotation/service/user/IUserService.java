package com.swp.spring.interiorconstructionquotation.service.user;


import com.swp.spring.interiorconstructionquotation.entity.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public ResponseEntity<?> register(User user);
    public ResponseEntity<?> enableAccount(String email, String enableCode);
    public void changePassword(String username, String newPassword);
    public void forgetPassword(String username, String email);
    public ResponseEntity<?> updateUserEnabledStatus(int userId, boolean enabled);

}
