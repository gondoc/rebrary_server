package com.gondoc.rebrary.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // header 에서 Jwt 추출
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        // 유효 토큰 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰 유효시 토큰으로부터 유저 정보 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // Security Context 에 유저 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
