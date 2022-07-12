package org.bg181.sbd.domain.user.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bg181.sbd.core.DomainEvent;
import org.bg181.sbd.core.base.UserBaseModel;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginEvent extends UserBaseModel implements DomainEvent {

    private String userId;

}
