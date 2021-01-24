package flashcardApplication.user.model;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public interface UserDetailsInterface extends org.springframework.security.core.userdetails.UserDetails{

    public String getUserId();

    @Override
    Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    String getPassword();

    @Override
    String getUsername();

    @Override
    boolean isAccountNonExpired();

    @Override
    boolean isAccountNonLocked();

    @Override
    boolean isCredentialsNonExpired();

    @Override
    boolean isEnabled();
}
