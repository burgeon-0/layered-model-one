package org.bg181.sbd.adapter.model.req.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bg181.sbd.core.base.BaseModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateProductForm extends BaseModel {

    @NotNull
    private String productName;
    @NotNull
    @Min(1)
    private Integer price;
    @NotNull
    @Min(1)
    private Integer stock;

}
