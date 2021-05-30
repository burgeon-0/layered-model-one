package org.burgeon.sbd.adapter.model.req.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.BaseObject;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterForm extends BaseObject {

    private String username;
    private String password;

}
