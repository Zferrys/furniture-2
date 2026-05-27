package com.furniture.service.impl;


import com.furniture.dao.MemberDao;
import com.furniture.dao.impl.MemberDaoImpl;
import com.furniture.entity.Member;
import com.furniture.service.MemberService;

/**
 * @author zph
 * @version 1.0
 */
public class MemberServiceImpl implements MemberService {
    private final MemberDao memberDao = new MemberDaoImpl();


    @Override
    public boolean register(Member member) {
        if (memberDao.queryMemberByName(member.getUsername()) == 1) {
            System.out.println("用户已存在，不能添加");
            return false;
        }
        if (memberDao.addMember(member)) {
            System.out.println("注册成功");
            return true;
        }
        return false;
    }

    @Override
    public Member login(String username, String password) {
        return memberDao.queryMemberByNameAndPassword(username, password);
    }

    @Override
    public boolean isExitUsername(String username) {
        Member member = memberDao.getMemberByName(username);
        return member != null;
    }
}
