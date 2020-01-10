package com.sid.digishopheroku.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTauthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With," +
                "Content-Type, Access-Control-Request-Method," +
                "Access-Control-RequestHeaders,Authorization");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials,Authorization");
        if(httpServletRequest.getMethod().equals("OPTIONS")){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }else {
            String jwt=httpServletRequest.getHeader(SecurityConstant.HEADER_STRING);
            if(jwt==null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)){
                filterChain.doFilter(httpServletRequest,httpServletResponse);return;
            }


            Claims claims= Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX,""))
                    .getBody();

            System.out.println("voici jwt: "+jwt);
            String username=claims.getSubject();
            ArrayList<Map<String,String>> roles= (ArrayList<Map<String, String>>) claims.get("roles");
            Collection<GrantedAuthority> authorities=new ArrayList<>();

            roles.forEach(r->{
                System.out.println(r.get("authority"));
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
            });

            UsernamePasswordAuthenticationToken authenticationUser=new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationUser); //on accerde au context de spring: on dit a spring voila l'utilisateur qui s'est authentifier
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }





    }
}
