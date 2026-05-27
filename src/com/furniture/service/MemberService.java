package com.furniture.service;


import com.furniture.entity.Member;

/**
 * @author zph
 * @version 1.0
 */
public interface MemberService {
    public boolean register(Member member);
    public Member login(String username, String password);
    public boolean isExitUsername(String username);
}
