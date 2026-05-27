package com.furniture.dao.impl;


import com.furniture.dao.BasicDAO;
import com.furniture.dao.MemberDao;
import com.furniture.entity.Member;

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
        String sql = "insert into member(username,password,email) values(?,md5(?),?)";
        return update(sql, member.getUsername(), member.getPassword(), member.getEmail()) == 1;
    }

    @Override
    public Member queryMemberByNameAndPassword(String username, String password) {
        String sql = "select id,username,password,email from member where username = ? and password = md5(?)";
        return querySingle(sql, Member.class, username, password);
    }


}
