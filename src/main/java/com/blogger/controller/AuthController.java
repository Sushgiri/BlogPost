package com.blogger.controller;

import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.exception.ResourceNotFoundExcecption;
import com.blogger.payload.JWTAuthResponse;
import com.blogger.payload.LoginDto;
import com.blogger.payload.SignUpDto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import com.blogger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


        @Autowired
        private JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already exists",
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already exists",
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully",
                HttpStatus.OK);
    }
//     Authenticationmanager is a interface which aunthencticate the authenctication object
//    securitycontextholder holds the roles and info of current thread(account)

//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.",
//                HttpStatus.OK);
//    }

            ///LOGIN USING JWT TOKEN

        @PostMapping("/signin")
        public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                        loginDto){
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get token form tokenProvider
            String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JWTAuthResponse(token));
        }
















    @PutMapping("/{userId}")
    public ResponseEntity<String> udpate(@PathVariable long userId, @Valid @RequestBody SignUpDto signUpDto,BindingResult bindingResult){
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

