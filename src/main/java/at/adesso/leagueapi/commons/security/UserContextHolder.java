package at.adesso.leagueapi.commons.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextHolder {
    public static String getUserId() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();
    }

    public static String getRole() {
        final GrantedAuthority authority = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith("ROLE_"))
                .findFirst()
                .orElse(null);

        return authority == null ? null : authority.getAuthority();
    }
}
