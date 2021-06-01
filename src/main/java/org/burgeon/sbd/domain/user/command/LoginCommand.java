package org.burgeon.sbd.domain.user.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.UserBase;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginCommand extends UserBase {

}
