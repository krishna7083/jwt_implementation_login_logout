package com.krishna.jwt.jwtimplementation.repository;

import com.krishna.jwt.jwtimplementation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String userName);

    @Query("UPDATE User u SET u.failedAttempt =?1 WHERE u.username=?2")
    @Modifying
    public void updateFailedAttempts(int failedAttempts, String username);
}
