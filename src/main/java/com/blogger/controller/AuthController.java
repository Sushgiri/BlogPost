package com.blogger.controller;

import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.exception.ResourceNotFoundExcecption;
import com.blogger.payload.JWTAuthResponse;
import com.blogger.payload.LoginDto;
import com.blogger.payload.SignUpDto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;

import com.blogger.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
    @RequestMapping("user/log")
    public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

//http://localhost:8080/user/log/signup
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


//    @Autowired
//    private JwtTokenProvider tokenProvider;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String signup = userService.signup(signUpDto);
        return new ResponseEntity<>(signup,HttpStatus.OK);


    }

    @GetMapping("all")
    public ResponseEntity<?> getalluser(){
        List<User> getalluser = userService.getalluser();
        return new ResponseEntity<>(getalluser,HttpStatus.OK);
    }
//     Authenticationmanager is a interface which aunthencticate the authenctication object
//    securitycontextholder holds the roles and info of current thread(account)

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.",
                HttpStatus.OK);
    }

            ///LOGIN USING JWT TOKEN



//        @PostMapping("/signin")
//        public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
//                                                                        loginDto){
//            Authentication authentication = authenticationManager.authenticate(new
//                    UsernamePasswordAuthenticationToken(
//                    loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            // get token form tokenProvider
//            String token = tokenProvider.generateToken(authentication);
//            return ResponseEntity.ok(new JWTAuthResponse(token));
//        }
















    @PutMapping("/{userId}")
    public ResponseEntity<String> udpate(@PathVariable String userId, @Valid @RequestBody SignUpDto signUpDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundExcecption("Account Not Found with id:" + userId)
        );
        User saveduser = new User();
        saveduser.setId(user.getId());
        saveduser.setName(signUpDto.getName());
        saveduser.setEmail(signUpDto.getUsername());
        saveduser.setUsername(signUpDto.getUsername());
        saveduser.setPassword(signUpDto.getPassword());
                 userRepository.save(saveduser);
                 return new ResponseEntity<>("Account Credentials Upated successfully",HttpStatus.OK);
    }


    @GetMapping("/allusers")
    public  ResponseEntity<List<User>> getall(){
        List<User> all = userRepository.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }




    @GetMapping("/logout")
    public ResponseEntity<String> logoutaccount() {
        Authentication authentiaction = SecurityContextHolder.getContext().getAuthentication();
        if (authentiaction != null) {// means account logged in
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ResponseEntity<>("Logged Out Successfully", HttpStatus.OK);
    }
}

