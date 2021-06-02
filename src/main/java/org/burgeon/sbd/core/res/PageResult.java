package org.burgeon.sbd.core.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.burgeon.sbd.core.base.BaseModel;

import java.util.Collection;

/**
 * @author Sam Lu
 * @date 2021/5/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageResult<T> extends BaseModel {

    private int pageNo;
    private int pageSize;
    private long total;
    private Collection<T> results;

}
