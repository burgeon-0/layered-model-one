package org.burgeon.sbd.domain.product.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.DomainEvent;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteProductEvent implements DomainEvent {

    private String productNo;

}
