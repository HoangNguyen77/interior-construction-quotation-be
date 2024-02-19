package com.swp.spring.interiorconstructionquotation.service.user;

import com.swp.spring.interiorconstructionquotation.dao.IRoleRepository;
import com.swp.spring.interiorconstructionquotation.dao.IUserRepository;
import com.swp.spring.interiorconstructionquotation.entity.Notification;
import com.swp.spring.interiorconstructionquotation.entity.Role;
import com.swp.spring.interiorconstructionquotation.entity.User;
import com.swp.spring.interiorconstructionquotation.service.email.EmailService;
import com.swp.spring.interiorconstructionquotation.service.email.IEmailSerivce;
import com.swp.spring.interiorconstructionquotation.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private BCryptPasswordEncoder passwordEncoder;
    private IUserRepository iUserRepository;
    private IRoleRepository iRoleRepository;
    private IEmailSerivce iEmailService;
    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, IUserRepository iUserRepository, IRoleRepository iRoleRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
        this.iEmailService = emailService;
    }

    @Override
    public ResponseEntity<?> register(User user) {
        if (iUserRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body(new Notification("Tên đăng nhập đã tồn tại!"));
        }
        if (iUserRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body(new Notification("Email đã tồn tại!"));
        }

        // Mã hóa mật khẩu
        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);

        //gui thong tin kich hoat
        user.setEnableCode(generateEnableCode());
        user.setEnabled(false);

        //default role
        Role defaultRole = iRoleRepository.findByName("CUSTOMER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);


        User registeredUser = iUserRepository.save(user);
        //gui email
        sendEnableEmail(user.getEmail(), user.getEnableCode());

        return ResponseEntity.ok("Đăng ký thành công!");
    }
    private String generateEnableCode(){
        return UUID.randomUUID().toString();
    }
    private void sendEnableEmail(String email, String enableCode){
        String subject = "Kích hoạt tài khoản của bạn tại VivaDecor";
        String text = "Vui lòng sử dụng mã sau để kich hoạt cho tài khoản <"+email+">:<html><body><br/><h1>"+enableCode+"</h1></body></html>";
        text+="<br/> Click vào đường link để kích hoạt tài khoản: ";
        String url = "http://localhost:5173/enable/"+email+"/"+enableCode;
        text+=("<br/> <a href="+url+">"+url+"</a> ");

        iEmailService.sendEmail("hoangnhse173098@fpt.edu.vn", email, subject, text);
    }
    @Override
    public ResponseEntity<?> enableAccount(String email, String enableCode) {
        User user = iUserRepository.findByEmail(email);
        if (user==null){
            return ResponseEntity.badRequest().body(new Notification("Người dùng không tồn tại"));
        }
        if (user.isEnabled()){
            return ResponseEntity.badRequest().body(new Notification("Tài khoản đã được kích hoạt!"));
        }
        if (enableCode.equals(user.getEnableCode())){
            user.setEnabled(true);
            iUserRepository.save(user);
            return ResponseEntity.ok("Kích hoạt tài khoản thành công!");
        } else {
            return ResponseEntity.badRequest().body(new Notification("Mã kích hoạt không chính xác"));
        }
    }
}
