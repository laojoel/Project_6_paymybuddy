package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CookieFilterTest {
    @InjectMocks
    private CookieFilter cookieFilter;
    @Mock
    private UserService userService;

    MockHttpServletRequest request;
    HttpServletResponse response;
    FilterChain filterChain;

    @BeforeEach
    void setup() {
        request = new MockHttpServletRequest();
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }


    // let http connexion from the Public Area of the Website (signin Page)
    @Test
    void doFilterInternalTest_signin() throws ServletException, IOException {
        request.setRequestURI("/signin");

        cookieFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    // let http connexion from the public area of the Website (signup Page)
    @Test
    void doFilterInternalTest_signup() throws ServletException, IOException {
        request.setRequestURI("/signup");

        cookieFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    // block http connexion from the private area of the Website with invalid cookie
    // and redirect to signin page
    @Test
    void doFilterInternalTest_invalidCookie() throws ServletException, IOException {
        when(userService.getUserWithToken(anyString())).thenReturn(null);
        request.setRequestURI("/transaction");
        Cookie[] cook= {new Cookie("token", "abc")};
        request.setCookies(cook);

        cookieFilter.doFilterInternal(request, response, filterChain);

        verify(response, times(1)).sendRedirect("signin");
    }

    // allow http connexion from the private area of the Website with valid cookie
    @Test
    void doFilterInternalTest_validCookie() throws ServletException, IOException {
        when(userService.getUserWithToken(anyString())).thenReturn(new User());
        request.setRequestURI("/transaction");
        Cookie[] cook= {new Cookie("token", "validToken")};
        request.setCookies(cook);

        cookieFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }




}
