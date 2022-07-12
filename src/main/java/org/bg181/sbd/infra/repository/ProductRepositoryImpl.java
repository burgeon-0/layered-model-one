package org.bg181.sbd.infra.repository;

import org.bg181.sbd.core.AbstractDomainRepository;
import org.bg181.sbd.domain.product.ProductAggregate;
import org.bg181.sbd.infra.repository.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class ProductRepositoryImpl extends AbstractDomainRepository<ProductAggregate, String> {

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Override
    public ProductAggregate load(String productNo) {
        ProductEntity productEntity = productEntityRepository.findByProductNoAndDeleted(productNo, false);
        if (productEntity != null) {
            ProductAggregate productAggregate = productEntity.to(ProductAggregate.class);
            return productAggregate;
        }
        return null;
    }

    @Override
    public void save(ProductAggregate product) {
        ProductEntity productEntity = product.to(ProductEntity.class);
        productEntityRepository.save(productEntity);
    }

}
