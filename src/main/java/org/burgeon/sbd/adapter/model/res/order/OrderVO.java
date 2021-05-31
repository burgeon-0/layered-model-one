package org.burgeon.sbd.adapter.model.res.order;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class OrderVO {

    private String orderNo;
    private List<Item> items;
    private Integer totalPrice;
    private Date placeTime;
    private Date payTime;
    private Date cancelTime;
    private Date deleteTime;
    private Integer status;

    @Data
    public static class Item {

        private String productNo;
        private String productName;
        private Integer totalCount;
        private Integer totalPrice;

    }

}
