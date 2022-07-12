package org.bg181.sbd.adapter.security.user;

import lombok.extern.slf4j.Slf4j;
import org.bg181.sbd.adapter.security.SecurityUnauthorizedException;
import org.bg181.sbd.domain.user.UserAggregate;
import org.bg181.sbd.domain.user.UserAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/6/5
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserAggregateFactory userAggregateFactory;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        UserAggregate userAggregate = userAggregateFactory.loadByToken(token);
        if (userAggregate == null) {
            throw new SecurityUnauthorizedException("Token Invalid: " + token);
        }

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                userAggregate.getUsername(),
                userAggregate.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
