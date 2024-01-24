package com.blogger.service.impl;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.exception.DataAlreadyExists;
import com.blogger.exception.ResourceNotFoundExcecption;
import com.blogger.payload.*;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.print.Doc;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.websocket.servlet.UndertowWebSocketServletWebServerCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestemplateConfig restTemplate;


//    private final UserDetailsService userDetailsService;
//
//    public UserService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    //    @Autowired
//    private AuthenticationManager authenticationManager;
    public void signup(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new DataAlreadyExists("Username is Already Registered");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new DataAlreadyExists("Email is Already Registered");
        }
        LocalDateTime signupdatetime = LocalDateTime.now();
        String userid = UUID.randomUUID().toString();
        User user = new User();
        user.setId(userid);
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setLocation(signUpDto.getLocation());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setSignupdatetime(signupdatetime.toString());
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
    }

    public String login(LoginDto loginDto) {
        User byUsername = userRepository.findByUsername(loginDto.getUsernameOrEmail());
        if (byUsername == null) {
            return "There is no account with username  " + loginDto.getUsernameOrEmail() + "\n" + "Signup here  " + "http://localhost:8082/user/api/signup";
        }
        if (passwordEncoder.matches(loginDto.getPassword(), byUsername.getPassword())) {
            return "welcome   " + byUsername.getName() + "\n" + "Search Doctors  " + "http://localhost:8082/user/api/search/doctor/" + byUsername.getId() + "\n" + "WRITE BLOG  " + "http://localhost:8082/user/blog/"+ byUsername.getUsername()+ "\n" +"READ BLOGS     " +"http://localhost:8082/user/blog/readallblogs/"+byUsername.getUsername()+ "\n" +"APPOINTMENTS    :"+"http://localhost:8082/user/booking/"+byUsername.getId()+ "\n" + "Medicines     :"+"http://localhost:8082/user/medicine/"+byUsername.getId() + "\n" + "LOGOUT:http://localhost:8082/user/api/logout";

        }
        return "You have Entered Wrong Password" + "\n" + "FORGOT PASSWORD   RESET HERE  " + "http://localhost:8082/user/api/password/"+byUsername.getId() ;
    }
    public List<User> getallusers() {
        List<User> all = userRepository.findAll();
       // List<User> sorted = all.stream().sorted(Comparator.comparing(User::getSignupdatetime).reversed()).collect(Collectors.toList());
        return all;
    }
    public Doctorview searchdoctors(String userId) {
        Optional<User> byId = userRepository.findById(userId);
        Doctordto[] doctorlist = restTemplate.getRestTemplate().getForObject("http://localhost:8081/doctor/api/all", Doctordto[].class);
        List<Doctordto> fetchedlist = Arrays.stream(doctorlist).collect(Collectors.toList());
        List<String> bookingLinks = new ArrayList<>();
        for (Doctordto doctordtolist : doctorlist) {
            doctordtolist.setReview("http://localhost:8082/review/api/" + doctordtolist.getEmail() + "/" + userId);
             doctordtolist.setReadreview("http://localhost:8082/review/api/"+doctordtolist.getDoctorId());
            for(AvailableSlots availableSlots:doctordtolist.getAvailableSlots()){
                availableSlots.setBook("http://localhost:8082/user/booking/"+doctordtolist.getDoctorId()+"/"+availableSlots.getDate()+"/"+byId.get().getId());
            }
            fetchedlist.add(doctordtolist);
        }
        Set<Doctordto> returnset = new HashSet<>(fetchedlist);
        Doctorview doctorview = new Doctorview();
        doctorview.setDoctordtoList(returnset);
        return doctorview;
    }
    public String resetpassword(String userId, SignUpDto signUpDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundExcecption("Account Not Found with id:" + userId)
        );
        User saveduser = new User();
        saveduser.setId(user.getId());
        saveduser.setName(user.getName());
        saveduser.setEmail(user.getUsername());
        saveduser.setUsername(user.getEmail());
        saveduser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User save = userRepository.save(saveduser);
        if (save != null) {
            return " Your password has been updated";
        }
        return "something went wrong password is not updated";
    }
    public String updaatpassword(String userId, LoginDto loginDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundExcecption("Account Not Found with id:" + userId)
        );
        User saveduser = new User();
        saveduser.setId(user.getId());
        saveduser.setName(user.getName());
        saveduser.setEmail(user.getUsername());
        saveduser.setUsername(user.getUsername());
        saveduser.setSignupdatetime(user.getSignupdatetime());
        saveduser.setReviews(user.getReviews());
        saveduser.setRoles(user.getRoles());
        saveduser.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        User save = userRepository.save(saveduser);
        boolean matches = passwordEncoder.matches(save.getPassword(),  loginDto.getPassword());
        return "password udpated";
    }
}
//        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
//        byUsername.setRoles(Collections.singleton(roles));
//        userRepository.save(byUsername);



