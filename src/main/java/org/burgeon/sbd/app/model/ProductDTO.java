package org.burgeon.sbd.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.ProductBase;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDTO extends ProductBase {

    private String productNo;

}
