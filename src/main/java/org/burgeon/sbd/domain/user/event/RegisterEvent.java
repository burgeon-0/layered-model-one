package org.burgeon.sbd.domain.user.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.DomainEvent;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterEvent extends DomainEvent {

    private String userId;
    private String username;
    private String password;

}
