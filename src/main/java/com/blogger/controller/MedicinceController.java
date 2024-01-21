package com.blogger.controller;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.payload.Medicinedto;
import com.blogger.payload.medicineview;
import com.blogger.payload.orderdot;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{userId}")
    public ResponseEntity<?> getallmedicines(@PathVariable String userId) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId == null) {
            return new ResponseEntity<>("byid is emppty", HttpStatus.OK);
        }
        Medicinedto[] medicinelist = restTemplate.getRestTemplate().getForObject("http://localhost:8089/medicine/api/med", Medicinedto[].class);
        List<Medicinedto> listofmedicine = Arrays.stream(medicinelist).collect(Collectors.toList());
        medicineview medicineview = new medicineview();
        for (Medicinedto medicinedto : medicinelist) {
            medicinedto.setOrder(medicineview.getOrder() + medicinedto.getMedicineId() + "/" + byId.get().getId());
        }
        return new ResponseEntity<>(listofmedicine, HttpStatus.OK);
    }

    //http://lcoalhost:8082/user/medicine/
    @GetMapping("/{medicineId}/{userId}")
    public ResponseEntity<?> ordermedicine(@PathVariable String medicineId, @PathVariable String userId, @RequestBody orderdot orderdot) {
        Optional<User> byId = userRepository.findById(userId);

        restTemplate.getRestTemplate().postForEntity("http://localhost:8089/order/api/"+medicineId+"/"+ userId, orderdot, String.class);

        if (byId.get() != null) {
            User byUsername = byId.get();

            Role roles = roleRepository.findByName("ROLE_CUSTOMER").get();
            byUsername.getRoles().add(roles);
            userRepository.save(byUsername);
            return new ResponseEntity<>("Order placed", HttpStatus.OK);
        } else {
            throw  new RuntimeException("something went wrong");
        }

    }

}
