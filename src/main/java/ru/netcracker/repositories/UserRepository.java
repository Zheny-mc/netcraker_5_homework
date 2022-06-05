package ru.netcracker.repositories;

import ru.netcracker.entities.User;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByFirNameAndMidlName(String firName, String midlName);
    
}
