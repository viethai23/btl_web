package com.btl_web.btl_web.repository;

import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    List<User> findByIdentifier(String identifier);
}
