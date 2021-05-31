package org.burgeon.sbd.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.MagicObject;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterDTO extends MagicObject {

    private String username;
    private String password;

}
