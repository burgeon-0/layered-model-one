package org.burgeon.sbd.domain.product.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.DomainEvent;
import org.burgeon.sbd.core.base.ProductBase;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateProductEvent extends ProductBase implements DomainEvent {

    private String productNo;

}
