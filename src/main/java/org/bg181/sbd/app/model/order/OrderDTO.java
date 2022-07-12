package org.bg181.sbd.app.model.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bg181.sbd.core.base.OrderBaseModel;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDTO extends OrderBaseModel {

    private String orderNo;

}
