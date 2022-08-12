package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.jpa.type.LoginType;
import com.faffy.web.jpa.type.UserNoAndNicknameMask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>  findByNo(int no);

    Optional<User> findByNickname(String nickname);

    Optional<User>  findByEmail(String email);

    Optional<User> findByEmailAndLoginType(String email, LoginType loginType);

    List<UserNoAndNicknameMask> findAllBy();

    Optional<UserNoAndNicknameMask> findByEmailAndPassword(String email, String password);

    @Query("select u from User u where u.email = :email and u.birthday = :birthday")
    Optional<User> findByEmailBirthday(String email, LocalDate birthday);

    @Query("select distinct u from User u join fetch u.categories uc " +
            "where u.nickname like concat('%',:keyword,'%') " +
            "or uc.userCategoryMapper.category.name like concat('%',:keyword,'%')")
    List<User> findByKeyword(String keyword) throws SQLException;
}
