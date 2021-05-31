package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.domain.DomainRepository;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class ProductRepositoryImpl implements DomainRepository<ProductAggregate, String> {

    @Override
    public ProductAggregate load(String orderNo) {
        return null;
    }

    @Override
    public void save(ProductAggregate order) {

    }

    @Override
    public void save(List<ProductAggregate> productAggregates) {

    }

}
