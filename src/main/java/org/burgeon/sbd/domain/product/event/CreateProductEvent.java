package org.burgeon.sbd.domain.product.event;

import lombok.Data;
import org.burgeon.sbd.domain.DomainEvent;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class CreateProductEvent extends DomainEvent {

    private String productNo;
    private String productName;
    private Integer price;
    private Integer stock;

}
