package org.example.u5w3d5compito.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.u5w3d5compito.entities.Utente;
import org.example.u5w3d5compito.exceptions.NonAutorizzatoException;
import org.example.u5w3d5compito.services.UtentiService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenTools tokenTools;
    private final UtentiService utentiService;

    public TokenFilter(TokenTools tokenTools, UtentiService utentiService) {
        this.tokenTools = tokenTools;
        this.utentiService = utentiService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer")){
            throw new NonAutorizzatoException("Inserire un token valido nell'header");
        }
        String accessToken = authorization.replace("Bearer ", "");
        this.tokenTools.verifyToken(accessToken);
        UUID utenteId = this.tokenTools.getIdByToken(accessToken);
        Utente utente = this.utentiService.findById(utenteId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}