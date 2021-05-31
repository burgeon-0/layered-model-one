package org.burgeon.sbd.adapter.api;

import org.burgeon.sbd.adapter.model.req.user.LoginForm;
import org.burgeon.sbd.adapter.model.req.user.RegisterForm;
import org.burgeon.sbd.adapter.model.res.Response;
import org.burgeon.sbd.adapter.model.res.SingleResponse;
import org.burgeon.sbd.app.UserService;
import org.burgeon.sbd.app.model.LoginDTO;
import org.burgeon.sbd.app.model.RegisterDTO;
import org.burgeon.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController
@RequestMapping(Constants.API + "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/actions/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Response register(@Valid @RequestBody RegisterForm registerForm) {
        RegisterDTO registerDTO = registerForm.to(RegisterDTO.class);
        userService.register(registerDTO);
        return Response.ok();
    }

    @PostMapping("/actions/login")
    public Response login(@Valid @RequestBody LoginForm loginForm) {
        LoginDTO loginDTO = loginForm.to(LoginDTO.class);
        String token = userService.login(loginDTO);
        return SingleResponse.ok(token);
    }

}
