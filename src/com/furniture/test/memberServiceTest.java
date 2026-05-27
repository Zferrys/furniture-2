package com.furniture.test;


import com.furniture.entity.Member;
import com.furniture.service.MemberService;
import com.furniture.service.impl.MemberServiceImpl;
import org.junit.Test;

/**
 * @author zph
 * @version 1.0
 */
public class memberServiceTest {
    private final MemberService memberService = new MemberServiceImpl();
    @Test
    public void testRegister(){
        System.out.println("注册用户");
        Member member = new Member(null,"xiaoming", "xiaoming", "xiaoming@abs.com");
        memberService.register(member);

    }


}
