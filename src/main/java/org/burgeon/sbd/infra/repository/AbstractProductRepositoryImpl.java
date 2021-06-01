package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.core.DomainRepository;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public abstract class AbstractProductRepositoryImpl implements DomainRepository<ProductAggregate, String> {

    private Map<String, ProductAggregate> productMap = new HashMap<>();

    @Override
    public ProductAggregate load(String productNo) {
        return productMap.get(productNo);
    }

    @Override
    public void save(ProductAggregate product) {
        productMap.put(product.getProductNo(), product);
    }

}
