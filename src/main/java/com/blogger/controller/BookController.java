package com.blogger.controller;

import com.blogger.config.RestemplateConfig;

import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.payload.Appointmentdto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user/booking")
public class BookController {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestemplateConfig restTemplate;
    @Autowired
    private UserRepository userRepository;
 //http:localhost:8082/user/booking/
    @PostMapping("/{DoctorId}/{Availabledate}/{patientid}")
    public ResponseEntity<?> bookappointment(@PathVariable String DoctorId, @PathVariable String Availabledate,@PathVariable String patientid, @RequestBody Appointmentdto appointmentdto){
        restTemplate.getRestTemplate().postForEntity("http://localhost:8080/doctor/appointemnt/"+DoctorId+"/"+Availabledate+"/"+patientid,appointmentdto,String.class);
        Optional<User> byId = userRepository.findById(patientid);
        Role roles = roleRepository.findByName("ROLE_PATIENT").get();
        byId.get().getRoles().add(roles);
        userRepository.save(byId.get());
        return new ResponseEntity<>("Appointment booked successfully", HttpStatus.OK);
    }
    @GetMapping("/{patientId}")
  public ResponseEntity<?> getallpointment(@PathVariable String patientId  ) {
        Appointmentdto[] allappointment = restTemplate.getRestTemplate().getForObject("http://localhost:8080/doctor/appointemnt/patient/"+patientId, Appointmentdto[].class);
  if(allappointment == null){
      return new ResponseEntity<>("No Active Apppointments",HttpStatus.OK);
  }
   return new ResponseEntity<>(allappointment,HttpStatus.OK);
    }

//    @DeleteMapping()
}
