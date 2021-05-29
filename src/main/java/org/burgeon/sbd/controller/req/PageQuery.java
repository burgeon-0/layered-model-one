package org.burgeon.sbd.controller.req;

import lombok.Data;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/28
 */
@Data
public class PageQuery extends Query {

    private int pageNo = 1;
    private int pageSize = 10;
    private boolean needTotal = true;
    private List<OrderDesc> orderDescs;

}
