package com.faffy.web.jpa.type;

/**
 * JPA Repository로 검색한 Entity의 회원 번호, 닉네임만 포함하도록 마스킹하는 인터페이스
 */
public interface UserNoAndNicknameMask {
    int getNo();
    String getNickname();
}
