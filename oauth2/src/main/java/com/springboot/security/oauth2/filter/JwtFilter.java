package com.springboot.security.oauth2.filter;

import com.springboot.security.oauth2.entity.User;
import com.springboot.security.oauth2.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       try{
           String header=request.getHeader("Authorization");
           String token=null;
           String username=null;

           if(header!=null && header.startsWith("Bearer ")){
               token=header.substring(7);
               username=jwtService.extractUsername(token);
           }

           if(username != null && jwtService.validateAccessToken(token) && SecurityContextHolder.getContext().getAuthentication()==null){
               User user=(User)userDetailsService.loadUserByUsername(username);
               UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
       }
       catch (Exception e){

       }

        filterChain.doFilter(request,response);

    }
}
