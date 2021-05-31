package org.burgeon.sbd.domain.product.event;

import lombok.Data;
import org.burgeon.sbd.domain.DomainEvent;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class DeleteProductEvent extends DomainEvent {

    private String productNo;

}
