package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.user.LoginDTO;
import org.burgeon.sbd.app.model.user.RegisterDTO;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.core.exception.ParamException;
import org.burgeon.sbd.domain.user.UserAggregate;
import org.burgeon.sbd.domain.user.UserAggregateFactory;
import org.burgeon.sbd.domain.user.command.LoginCommand;
import org.burgeon.sbd.domain.user.command.RegisterCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Service
public class UserService {

    @Autowired
    private UserAggregateFactory userAggregateFactory;

    public void register(RegisterDTO registerDTO) {
        RegisterCommand registerCommand = registerDTO.to(RegisterCommand.class);
        new UserAggregate(registerCommand);
    }

    public String login(LoginDTO loginDTO) {
        LoginCommand loginCommand = loginDTO.to(LoginCommand.class);
        UserAggregate userAggregate = userAggregateFactory.load(loginCommand.getUsername());
        if (userAggregate == null) {
            throw new ParamException(ErrorCode.USER_NOT_FOUND);
        }
        String token = userAggregate.login(loginCommand);
        return token;
    }

}
