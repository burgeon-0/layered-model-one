package org.bg181.sbd.app.model.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bg181.sbd.core.base.ProductBaseModel;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDTO extends ProductBaseModel {

    private String productNo;

}
