package com.furniture.dao.impl;


import com.furniture.dao.BasicDAO;
import com.furniture.dao.MemberDao;
import com.furniture.entity.Member;
import com.furniture.utils.PasswordUtils;

/**
 * @author zph
 * @version 1.0
 */
public class MemberDaoImpl extends BasicDAO<Member> implements MemberDao {

    @Override
    public Member getMemberByName(String name) {
        String sql = "select id,username,password,email from member where username = ?";
        return querySingle(sql, Member.class, name);
    }

    @Override
    public int queryMemberByName(String name) {
        /**
         * 返回-1表示不存在该用户，可以添加
         */
        String sql = "select id,username,password,email from member where username = ?";
        return querySingle(sql, Member.class, name) == null ? -1 : 1;
    }

    @Override
    public boolean addMember(Member member) {
        // 使用 SHA-256 + 盐值哈希替代 MySQL md5()
        String hashedPassword = PasswordUtils.hashPassword(member.getPassword());
        String sql = "insert into member(username,password,email) values(?,?,?)";
        return update(sql, member.getUsername(), hashedPassword, member.getEmail()) == 1;
    }

    @Override
    public Member queryMemberByNameAndPassword(String username, String password) {
        // Java 端校验密码，替代原来 SQL 端的 md5() 比较
        Member member = getMemberByName(username);
        if (member == null) {
            return null;
        }
        if (PasswordUtils.checkPassword(password, member.getPassword())) {
            return member;
        }
        return null;
    }


}
