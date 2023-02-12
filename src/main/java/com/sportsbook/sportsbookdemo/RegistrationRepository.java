package com.sportsbook.sportsbookdemo;

import com.sportsbook.sportsbookdemo.entity.Registration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface RegistrationRepository extends CrudRepository<Registration, Long> {
    Registration findByPhone(String phone);
    Registration findByUsernameAndPassword(String username, String password);

}


