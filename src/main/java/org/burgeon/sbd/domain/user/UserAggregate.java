package org.burgeon.sbd.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.burgeon.sbd.domain.Copyable;
import org.burgeon.sbd.domain.DomainEventBus;
import org.burgeon.sbd.domain.DomainRepository;
import org.burgeon.sbd.domain.SpringBeanFactory;
import org.burgeon.sbd.domain.exception.BizException;
import org.burgeon.sbd.domain.exception.ErrorCode;
import org.burgeon.sbd.domain.user.command.LoginCommand;
import org.burgeon.sbd.domain.user.command.RegisterCommand;
import org.burgeon.sbd.domain.user.event.LoginEvent;
import org.burgeon.sbd.domain.user.event.RegisterEvent;

import java.util.UUID;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
public class UserAggregate {

    private String userId;
    private String username;
    private String password;

    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private Copyable copyable = SpringBeanFactory.getBean(Copyable.class);
    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private DomainRepository<UserAggregate, String> userRepository = SpringBeanFactory.getDomainRepository(
            UserAggregate.class, String.class);
    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
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
        return generateToken();
    }

    private boolean correctUsername(String username) {
        return this.username.equals(username);
    }

    private boolean correctPassword(String password) {
        return this.password.equals(password);
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
