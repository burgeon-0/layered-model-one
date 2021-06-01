package org.burgeon.sbd.core.req;

import lombok.Data;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/28
 */
@Data
public class PageQuery {

    private int pageNo = 1;
    private int pageSize = 10;
    private boolean needTotal = true;
    private List<OrderDesc> orderDescs;

}
