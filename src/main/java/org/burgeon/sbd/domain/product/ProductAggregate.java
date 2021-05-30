package org.burgeon.sbd.domain.product;

import lombok.Getter;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class ProductAggregate {

    private String productNo;
    @Getter
    private String name;
    @Getter
    private int price;
    private int stock;

    public static ProductAggregate load(String productNo) {
        return null;
    }

    public boolean stockEnough(int count) {
        return stock >= count;
    }

}
