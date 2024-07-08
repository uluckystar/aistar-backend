package com.localaihub.aistar.configuration.filter;

import com.localaihub.aistar.common.enums.JwtError;
import com.localaihub.aistar.common.util.JwtExceptionUtil;
import com.localaihub.aistar.common.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器，用于验证请求中的JWT令牌
 *
 * @apiNote 此过滤器会在每次请求时执行，检查JWT令牌的有效性并在有效时设置安全上下文
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
                logger.debug("从JWT令牌中提取的用户名: {}", username);
            } catch (Exception e) {
                logger.error("JWT令牌提取错误: {}", e.getMessage());
                JwtError jwtError = JwtExceptionUtil.extractJwtError(e);
                if (jwtError != null) {
                    request.setAttribute("jwtError", jwtError);
                }
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("已认证用户: {}", username);
            } else {
                logger.debug("JWT令牌验证失败: {}", token);
            }
        }

        chain.doFilter(request, response);
    }
}
