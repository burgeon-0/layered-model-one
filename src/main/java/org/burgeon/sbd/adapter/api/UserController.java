package org.burgeon.sbd.adapter.api;

import org.burgeon.sbd.adapter.model.req.user.LoginForm;
import org.burgeon.sbd.adapter.model.req.user.RegisterForm;
import org.burgeon.sbd.adapter.model.res.Response;
import org.burgeon.sbd.infra.Constants;
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

    @PostMapping("/actions/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Response userRegister(@Valid @RequestBody RegisterForm registerForm) {
        return Response.ok();
    }

    @PostMapping("/actions/login")
    public Response userLogin(@Valid @RequestBody LoginForm loginForm) {
        return Response.ok();
    }

}
