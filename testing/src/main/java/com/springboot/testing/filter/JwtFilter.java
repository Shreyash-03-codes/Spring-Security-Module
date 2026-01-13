package com.springboot.testing.filter;

import com.springboot.testing.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

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

           if(username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtService.validateAccessToken(token)){
               UserDetails userDetails=userDetailsService.loadUserByUsername(username);
               UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }
       }
       catch (Exception e){
           log.debug("got a exception",e.getMessage());
       }
       finally {
           filterChain.doFilter(request,response);

       }

    }
}
