package com.blogger.controller;

import com.blogger.entity.User;
import com.blogger.exception.ResourceNotFoundExcecption;

import com.blogger.payload.Doctorview;
import com.blogger.payload.LoginDto;

import com.blogger.payload.SignUpDto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;

import com.blogger.service.impl.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
    @RequestMapping("user/api")
    public class AuthController {
//    @Autowired
//    private AuthenticationManager authenticationManager;

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
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtTokenProvider tokenProvider;
//http://localhost:8081/user/log/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(signUpDto == null){
            return  new
                    ResponseEntity<>("Please Enter Your Details to register an account",HttpStatus.OK);
        }
        userService.signup(signUpDto);
        return new ResponseEntity<>("User Registered Successfully",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String login = userService.login(loginDto);
        return new ResponseEntity<>(login,HttpStatus.OK);
    }
    @GetMapping("/all/users")
    public ResponseEntity<?> getalluser(){
        List<User> getalluser = userService.getalluserrecentsignupsorted();
        return new ResponseEntity<>(getalluser,HttpStatus.OK);
    }
    @GetMapping("/search/doctor/{userId}")
    public ResponseEntity<?> searchdoctos(@PathVariable String userId){
        Doctorview searchdoctors = userService.searchdoctors(userId);
        return new ResponseEntity<>(searchdoctors,HttpStatus.OK);

    }
//http://localhost:8082/user/api/password/
    @PutMapping("/password/{userId}")
    public ResponseEntity<?> updatepassword(@PathVariable String userId, @RequestBody LoginDto loginDto){
        String updaatpassword = userService.updaatpassword(userId, loginDto);
        return new ResponseEntity<>(updaatpassword,HttpStatus.OK);
    }

//    @PostMapping("/signin")
//    ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.",
//                HttpStatus.OK);
//    }
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
        saveduser.setUsername(signUpDto.getEmail());
        saveduser.setPassword(signUpDto.getPassword());
                 userRepository.save(saveduser);
                 return new ResponseEntity<>("Account Credentials Upated successfully",HttpStatus.OK);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<String> logoutaccount() {
//        Authentication authentiaction = SecurityContextHolder.getContext().getAuthentication();
//        if (authentiaction != null) {// means account logged in
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        return new ResponseEntity<>("Logged Out Successfully", HttpStatus.OK);
//    }
}

