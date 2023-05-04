package at.adesso.leagueapi.commons.security;

import at.adesso.leagueapi.commons.util.jwt.JwtTokenUtil;
import at.adesso.leagueapi.commons.util.jwt.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    public final static String ACCESS_TOKEN_NAME = "leagueapi_access_token";
    private final JwtTokenValidator jwtTokenValidator;

    private final JwtTokenUtil jwtTokenUtil;

    public JwtSecurityFilter(JwtTokenValidator jwtTokenValidator, JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenValidator = jwtTokenValidator;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final Optional<Cookie> accessTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_NAME))
                .findFirst();

        String token = null;
        if (accessTokenCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        } else {
            token = accessTokenCookie.get().getValue();
        }

        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtTokenValidator.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String userId = jwtTokenUtil.getUserIdFromToken(token);

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId, null,
                        userId == null ? List.of() : getAuthorities(jwtTokenUtil.getRoleFromToken(token))
                );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
