package com.furniture.web;

import com.furniture.entity.Member;
import com.furniture.service.MemberService;
import com.furniture.service.impl.MemberServiceImpl;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author zph
 * @version 1.0
 */
public class memberServlet extends Basic_Servlet {
    private final MemberService memberService = new MemberServiceImpl();
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("memberServlet.....");
//        String action = req.getParameter("action");
//        if ("memberLogin".equals(action)) {
//            memberLogin(req, resp);
//        } else if ("memberRegister".equals(action)) {
//            memberRegister(req, resp);
//        } else {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
//    }

    protected void memberLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Member member = new Member(null, username, password, null);
        Member realmember = memberService.login(member.getUsername(), member.getPassword());
        if (realmember != null) {
            // Session固定攻击防护：先销毁旧session再创建新session
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("member", realmember);
            req.getRequestDispatcher("/views/member/login_ok.jsp").forward(req, resp);
        } else {
            req.setAttribute("username", username);
            req.setAttribute("msg", "用户名或密码错误");
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            System.out.println("登录失败");
        }
    }

    protected void memberRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        if (username == null || password == null || repassword == null || email == null || code == null ||
                username.isEmpty() || password.isEmpty() || repassword.isEmpty() || email.isEmpty() || code.isEmpty()) {
            request.setAttribute("msg_register", "请完整填写注册信息");
            request.setAttribute("active", "register");
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
            return;
        }
        Member member = new Member(null, username, password, email);
        if (token != null && token.equalsIgnoreCase(code)) {
            // 验证通过后清除验证码，防止重复使用
            session.removeAttribute(KAPTCHA_SESSION_KEY);

            if (memberService.register(member)) {
                request.getRequestDispatcher("/views/member/register_ok.html").forward(request, response);
            } else {
                request.getRequestDispatcher("/views/member/register_fail.html").forward(request, response);
            }
        } else {
            request.setAttribute("msg_register", "验证码错误");
            request.setAttribute("active", "register");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        }
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }


    protected void exitUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        boolean exists = memberService.isExitUsername(username);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("isExist", exists);
        String resJson = new Gson().toJson(resultMap);
        resp.getWriter().write(resJson);
    }


    protected void exitCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        Map<String, Object> resultMap = new HashMap<>();
        if (token != null && token.equalsIgnoreCase(code)) {
            resultMap.put("codeIsExist", true);
        } else {
            resultMap.put("codeIsExist", false);
        }
        String resJson = new Gson().toJson(resultMap);
        resp.getWriter().write(resJson);
    }
}
