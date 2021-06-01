package org.burgeon.sbd.adapter.model.req.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.Base;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlaceOrderForm extends Base {

    @NotNull
    private List<Item> items;

    @Data
    public static class Item {

        @NotNull
        private String productNo;
        @NotNull
        private Integer count;

    }

}
