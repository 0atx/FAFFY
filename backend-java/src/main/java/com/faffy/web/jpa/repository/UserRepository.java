package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.UserNoAndNicknameMask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>  findByNo(int no);

    Optional<User> findByNickname(String nickname);

    Optional<User>  findByEmail(String email);

    List<UserNoAndNicknameMask> findAllBy();

    Optional<UserNoAndNicknameMask> findByEmailAndPassword(String email, String password);

    @Query("select u from User u where u.email = :email and u.birthday = :birthday")
    Optional<User> findByEmailBirthday(String email, LocalDate birthday);

    @Query("select distinct u from User u join fetch User_Category uc join fetch FashionCategory fc " +
            "where u.nickname like %:keyword% or fc.name like %:keyword%")
    List<User> findByKeyword(String keyword);
}
