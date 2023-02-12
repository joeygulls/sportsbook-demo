package com.sportsbook.sportsbookdemo;

import com.sportsbook.sportsbookdemo.entity.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/sb-api/v1")
public class RegistrationController {

    @Autowired
    RegistrationRepository registrationRepository;

    @PostMapping("/register")
    public Registration createRegistration(@Valid @RequestBody Registration registration) {
        return registrationRepository.save(registration);
    }

    @GetMapping("/registration/{phone}")
    public ResponseEntity<Registration> getUserByPhone(@PathVariable(value = "phone") String phone)
            throws UserNotFoundException {
        log.info("in getUserByPhone - phone=" + phone);
        Registration registration = registrationRepository.findByPhone(phone);

        if (registration == null) {
            log.info("Phone" + phone + " not found");
            throw new UserNotFoundException("User not found for phone number :: " + phone);
        }
        return ResponseEntity.ok().body(registration);
    }



    @GetMapping("/login")
    public ResponseEntity<Registration> getUserByPhone(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {

        Registration registration = registrationRepository.findByUsernameAndPassword(username, password);

        if (registration == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(registration);
    }
}
