package com.furniture.dao;


import com.furniture.entity.Member;

/**
 * @author zph
 * @version 1.0
 */
public interface MemberDao {
    public Member getMemberByName(String name);
    public int queryMemberByName(String name);
    public boolean addMember(Member member);
    public Member queryMemberByNameAndPassword(String username,String password);
    public int updatePassword(String username, String newPassword);
}
