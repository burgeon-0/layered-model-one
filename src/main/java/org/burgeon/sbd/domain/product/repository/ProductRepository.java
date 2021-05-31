package org.burgeon.sbd.domain.product.repository;

import org.burgeon.sbd.domain.product.ProductAggregate;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public interface ProductRepository {

    /**
     * 加载产品
     *
     * @param orderNo
     * @return
     */
    ProductAggregate load(String orderNo);

    /**
     * 保存产品
     *
     * @param order
     */
    void save(ProductAggregate order);

}
