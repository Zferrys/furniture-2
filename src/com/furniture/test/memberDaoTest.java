package com.furniture.test;


import com.furniture.dao.MemberDao;
import com.furniture.dao.impl.MemberDaoImpl;
import org.junit.Test;

/**
 * @author zph
 * @version 1.0
 */
public class memberDaoTest {
    private final MemberDao memberDao = new MemberDaoImpl();
    @Test
    public void testGetMemberByName(){
        System.out.println(memberDao.getMemberByName("wenzhilin"));
    }
    @Test
    public void testQueryMemberByName(){
        System.out.println(memberDao.queryMemberByName("wenzhilin")!=-1?"存在用户":"不存在用户");
        System.out.println(memberDao.queryMemberByName("linhuahhh"));
    }
}
