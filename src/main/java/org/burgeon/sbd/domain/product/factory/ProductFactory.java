package org.burgeon.sbd.domain.product.factory;

import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class ProductFactory {

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public ProductFactory getInstance() {
        return new ProductFactory();
    }

    public ProductAggregate load(String productNo) {
        return productRepository.load(productNo);
    }

}
