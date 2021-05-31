package org.burgeon.sbd.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.domain.MagicObject;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDTO extends MagicObject {

    private String productName;
    private Integer price;
    private Integer stock;

}
