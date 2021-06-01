package org.burgeon.sbd.domain.order.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.Base;
import org.burgeon.sbd.domain.product.ProductAggregate;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlaceOrderCommand extends Base {

    private List<Item> items;

    @Data
    public static class Item {

        private ProductAggregate productAggregate;
        private Integer count;

    }

}
