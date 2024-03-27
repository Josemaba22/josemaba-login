package com.josemaba.security.config.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.josemaba.security.exception.ObjectNotFoundException;
import com.josemaba.security.persistence.entity.security.User;
import com.josemaba.security.service.UserService;
import com.josemaba.security.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                System.out.println("ETRO EN EL FILTRO JWT AUTHENTICATION FILTER");
                //1. Obtener encabezado http llamado Authorization
                String authorizationHeader = request.getHeader("Authorization"); //Bearer jwt
                if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request, response);
                    return;
                }

                //2. Obtener token JWT desde el encabezado
                String jwt = authorizationHeader.split(" ")[1];

                //3. Obtener el subject/username desde el token
                //   esta accion a su vez valida el formato del token, firma y fecha de expiracion
                String username = jwtService.extractUsername(jwt);

                //4. Setear onjeto authentication dentro de security context holder
                User user = userService.findOneByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                //5. Ejecutar el registro de filtros
                filterChain.doFilter(request, response);

    }
    
}
