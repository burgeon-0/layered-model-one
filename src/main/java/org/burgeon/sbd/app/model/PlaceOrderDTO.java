package org.burgeon.sbd.app.model;

import lombok.Data;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class PlaceOrderDTO {

    private List<Item> items;

    @Data
    public static class Item {

        private String productNo;
        private Integer count;

    }

}
