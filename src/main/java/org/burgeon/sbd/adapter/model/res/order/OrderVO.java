package org.burgeon.sbd.adapter.model.res.order;

import lombok.Data;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class OrderVO {

    private String orderNo;
    private String productName;
    private Integer totalCount;
    private Integer totalPrice;
    private Date placeTime;
    private Date payTime;
    private Date cancelTime;
    private Date deleteTime;
    private Integer status;

}
