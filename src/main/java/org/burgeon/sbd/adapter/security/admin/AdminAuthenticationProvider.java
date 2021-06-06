package org.burgeon.sbd.adapter.security.admin;

import lombok.extern.slf4j.Slf4j;
import org.burgeon.sbd.adapter.security.SecurityForbiddenException;
import org.burgeon.sbd.adapter.security.SecurityUnauthorizedException;
import org.burgeon.sbd.domain.user.UserAggregate;
import org.burgeon.sbd.domain.user.UserAggregateFactory;
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
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserAggregateFactory userAggregateFactory;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        UserAggregate userAggregate = userAggregateFactory.loadByToken(token);
        if (userAggregate == null) {
            throw new SecurityUnauthorizedException("Token Invalid: " + token);
        }

        if (!userAggregate.getIsAdmin()) {
            throw new SecurityForbiddenException("The User Is Not An Admin");
        }

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                userAggregate.getUsername(),
                userAggregate.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
