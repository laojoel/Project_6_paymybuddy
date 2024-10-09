package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CookieFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("COOKIE_path = " + request.getRequestURI());
        if (request.getRequestURI().equals("/signin") || request.getRequestURI().equals("/signup")) {
            filterChain.doFilter(request, response);
        }
        else {
            Cookie[] cookies = request.getCookies();
            boolean cleared = false;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        request.setAttribute("token", cookie.getValue());

                        User user = userService.getUserWithToken((String)request.getAttribute("token"));
                        request.setAttribute("user", user);
                        if (user!=null) {cleared = true;}
                        break;
                    }
                }
            }
            if (!cleared){response.sendRedirect("signin");}
            else {filterChain.doFilter(request, response);}
        }
    }
}
