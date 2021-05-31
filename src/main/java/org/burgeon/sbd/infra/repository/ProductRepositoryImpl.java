package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public ProductAggregate load(String orderNo) {
        return null;
    }

    @Override
    public void save(ProductAggregate order) {

    }

}
