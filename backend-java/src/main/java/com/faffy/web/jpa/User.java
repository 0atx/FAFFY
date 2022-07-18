package com.faffy.web.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class User {
    @Id @GeneratedValue
    int no;

    String email;
    String name;
    String nickname;
    String password;
    Date birthday;
    Boolean gender;

    int thumbnail;




}
