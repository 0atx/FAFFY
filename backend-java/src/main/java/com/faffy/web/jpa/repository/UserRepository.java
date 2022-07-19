package com.faffy.web.jpa.repository;

import com.faffy.web.jpa.entity.User;
import com.faffy.web.mapping.PublicUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>  findByNo(int no);

    Optional<PublicUserInfo> findByNickname(String nickname);

    Optional<PublicUserInfo>  findByEmail(String email);

    List<PublicUserInfo> findAllBy();

    Optional<PublicUserInfo> findByEmailAndPassword(String email,String password);

}
