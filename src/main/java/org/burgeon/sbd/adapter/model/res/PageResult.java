package org.burgeon.sbd.adapter.model.res;

import lombok.Data;

import java.util.Collection;

/**
 * @author Sam Lu
 * @date 2021/5/29
 */
@Data
public class PageResult<T> {

    private int pageNo;
    private int pageSize;
    private int total;
    private Collection<T> results;

}