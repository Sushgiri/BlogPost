package com.blogger.controller;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.payload.Medicinedto;
import com.blogger.payload.medicineview;
import com.blogger.payload.orderdot;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Handler;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user/medicine")
public class MedicinceController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestemplateConfig restTemplate;

    //http://localhost:8082/user/medicine/
    @GetMapping("/{userId}/{useremail}")
    @CircuitBreaker(name = "medicineBreaker", fallbackMethod = "medicineFallback")
    public ResponseEntity<?> getallmedicines(@PathVariable String userId,@PathVariable String useremail ) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId == null) {
            return new ResponseEntity<>("byid is emppty", HttpStatus.OK);
        }
        Medicinedto[] medicinelist = restTemplate.getRestTemplate().getForObject("http://localhost:8089/medicine/api/med", Medicinedto[].class);
        List<Medicinedto> listofmedicine = Arrays.stream(medicinelist).collect(Collectors.toList());
        medicineview medicineview = new medicineview();
        for (Medicinedto medicinedto : medicinelist) {
            medicinedto.setOrder(medicineview.getOrder() + medicinedto.getMedicineId() + "/" + byId.get().getId() + "/" +useremail);
        }
        return new ResponseEntity<>(listofmedicine, HttpStatus.OK);
    }
    //http://lcoalhost:8082/user/medicine/
    @PostMapping("/{medicineId}/{userId}/{useremail}/{quantity}")
    @CircuitBreaker(name = "medicineBreaker", fallbackMethod = "medicineFallback")
    public ResponseEntity<?> ordermedicine(@PathVariable String medicineId, @PathVariable String userId,@PathVariable String useremail , @PathVariable String quantity) {
        Optional<User> byId = userRepository.findById(userId);
//        if (byId.isPresent()) {
//            User byUsername = byId.get();
//
//            Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
//                    .orElseThrow(() -> new RuntimeException("ROLE_CUSTOMER not found"));
//
//            if (!byUsername.getRoles().contains(customerRole)) {
//                byUsername.getRoles().add(customerRole);
//                userRepository.save(byUsername);
//            }
//        }else {
//            throw  new RuntimeException("something went wrong");
//        }
        String apiUrl = "http://localhost:8089/order/api/getpaymentpage/{medicineId}/{userId}/{userEmail}/{quantity}";
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("medicineId", medicineId);
        uriVariables.put("userId", userId);
        uriVariables.put("userEmail", useremail);
        uriVariables.put("quantity", quantity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(apiUrl, null, String.class, uriVariables);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public ResponseEntity<?>  medicineFallback(String userId,String useremail,Exception e){
        return new ResponseEntity<>("MEDICINE-SERVICE IS DOWN",HttpStatus.BAD_REQUEST);
    }
}
