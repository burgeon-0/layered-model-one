package org.burgeon.sbd.domain.user.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.DomainEvent;
import org.burgeon.sbd.core.base.UserBase;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterEvent extends UserBase implements DomainEvent {

    private String userId;

}
