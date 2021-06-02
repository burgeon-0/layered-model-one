package org.burgeon.sbd.adapter.model.req.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.BaseModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateProductForm extends BaseModel {

    @NotNull
    private String productName;
    @NotNull
    @Min(1)
    private Integer price;
    @NotNull
    @Min(1)
    private Integer stock;

}
