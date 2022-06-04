package com.baeldung.crud.repositories;

import com.baeldung.crud.entities.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByFirNameAndMidlName(String firName, String midlName);
    
}
