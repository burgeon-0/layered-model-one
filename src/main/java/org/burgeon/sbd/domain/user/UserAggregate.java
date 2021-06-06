package org.burgeon.sbd.domain.user;

import lombok.*;
import org.burgeon.sbd.core.Copyable;
import org.burgeon.sbd.core.DomainEventBus;
import org.burgeon.sbd.core.DomainRepository;
import org.burgeon.sbd.core.SpringBeanFactory;
import org.burgeon.sbd.core.base.UserBaseModel;
import org.burgeon.sbd.core.exception.BizException;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.domain.user.command.LoginCommand;
import org.burgeon.sbd.domain.user.command.RegisterCommand;
import org.burgeon.sbd.domain.user.event.LoginEvent;
import org.burgeon.sbd.domain.user.event.RegisterEvent;

import java.util.UUID;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@NoArgsConstructor
public class UserAggregate extends UserBaseModel {

    @Setter
    @Getter
    private String userId;

    private Copyable copyable = SpringBeanFactory.getBean(Copyable.class);
    private DomainRepository<UserAggregate, String> userRepository = SpringBeanFactory.getDomainRepository(
            UserAggregate.class, String.class);
    private DomainEventBus domainEventBus = SpringBeanFactory.getBean(DomainEventBus.class);

    public UserAggregate(RegisterCommand registerCommand) {
        userId = generateUserId();
        copyable.copy(registerCommand, this);
        userRepository.save(this);

        RegisterEvent registerEvent = registerCommand.to(RegisterEvent.class);
        registerEvent.setUserId(userId);
        domainEventBus.publishEvent(registerEvent);
    }

    public String login(LoginCommand loginCommand) {
        if (!correctUsername(loginCommand.getUsername())) {
            throw new BizException(ErrorCode.LOGIN_USERNAME_INVALID);
        }
        if (!correctPassword(loginCommand.getPassword())) {
            throw new BizException(ErrorCode.LOGIN_PASSWORD_INVALID);
        }

        LoginEvent loginEvent = loginCommand.to(LoginEvent.class);
        loginEvent.setUserId(userId);
        domainEventBus.publishEvent(loginEvent);

        String token = generateToken();
        TokenFactory.put(token, getUsername());
        return token;
    }

    private boolean correctUsername(String username) {
        return getUsername().equals(username);
    }

    private boolean correctPassword(String password) {
        return getPassword().equals(password);
    }

    private String generateUserId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
