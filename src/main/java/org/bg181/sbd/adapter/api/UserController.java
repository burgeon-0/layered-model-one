package org.bg181.sbd.adapter.api;

import org.bg181.sbd.adapter.model.req.user.LoginForm;
import org.bg181.sbd.adapter.model.req.user.RegisterForm;
import org.bg181.sbd.core.res.Response;
import org.bg181.sbd.core.res.SingleResponse;
import org.bg181.sbd.app.UserService;
import org.bg181.sbd.app.model.user.LoginDTO;
import org.bg181.sbd.app.model.user.RegisterDTO;
import org.bg181.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController("UserController")
@RequestMapping(Constants.API + "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/actions/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Response register(@Valid @RequestBody RegisterForm registerForm) {
        RegisterDTO registerDTO = registerForm.to(RegisterDTO.class);
        userService.register(registerDTO);
        return Response.created();
    }

    @PostMapping("/actions/login")
    public Response login(@Valid @RequestBody LoginForm loginForm) {
        LoginDTO loginDTO = loginForm.to(LoginDTO.class);
        String token = userService.login(loginDTO);
        return SingleResponse.ok(token);
    }

}
