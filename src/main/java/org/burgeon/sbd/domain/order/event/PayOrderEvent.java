package org.burgeon.sbd.domain.order.event;

import lombok.Data;
import org.burgeon.sbd.domain.DomainEvent;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class PayOrderEvent extends DomainEvent {

    private String orderNo;
    private Date payTime;

}
