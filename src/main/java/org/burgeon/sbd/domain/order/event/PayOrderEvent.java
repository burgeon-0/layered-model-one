package org.burgeon.sbd.domain.order.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.DomainEvent;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayOrderEvent implements DomainEvent {

    private String orderNo;
    private Date payTime;

}
