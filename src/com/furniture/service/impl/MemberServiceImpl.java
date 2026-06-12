package com.furniture.service.impl;


import com.furniture.dao.MemberDao;
import com.furniture.dao.impl.MemberDaoImpl;
import com.furniture.entity.Member;
import com.furniture.service.MemberService;
import com.furniture.utils.PasswordUtils;

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
        Member member = memberDao.getMemberByName(username);
        if (member == null) {
            return null;
        }
        if (PasswordUtils.verify(password, member.getPassword())) {
            // 旧 MD5 密码自动升级为新格式
            if (PasswordUtils.isMd5Hex(member.getPassword())) {
                memberDao.updatePassword(username, PasswordUtils.hashPassword(password));
            }
            return member;
        }
        return null;
    }

    @Override
    public boolean isExitUsername(String username) {
        Member member = memberDao.getMemberByName(username);
        return member != null;
    }
}
