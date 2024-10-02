package org.example.project_6_paymybuddy.Proxies;

import jakarta.transaction.Transactional;
import org.example.project_6_paymybuddy.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserProxy extends JpaRepository<User,Long> {


    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User authenticateUser(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.token = :token")
    User findUserWithToken(@Param("token") String token);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserWithEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findUsersFromIdArray(@Param("ids") List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.token = :token WHERE u.id = :id")
    void setToken(int id, String token);

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.username = :username")
    User findUserByMailOrUsername(@Param("username") String username, @Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (username, email, password) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void createUser(String username, String email, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.username = :username, u.email = :email, u.password = :password WHERE u.id = :id")
    void updateUserProfile(int id, String username, String email, String password);
}
