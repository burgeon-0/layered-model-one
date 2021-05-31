package org.burgeon.sbd.domain.product.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.MagicObject;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateProductCommand extends MagicObject {

    private String productName;
    private Integer price;
    private Integer stock;

}
