package org.burgeon.sbd.domain.product.factory;

import org.burgeon.sbd.domain.ApplicationContextHolder;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.repository.ProductRepository;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public class ProductFactory {

    public static final ProductFactory INSTANCE = new ProductFactory();

    public static ProductFactory getInstance() {
        return INSTANCE;
    }

    private ProductRepository productRepository;

    public ProductFactory() {
        productRepository = ApplicationContextHolder.getBean(ProductRepository.class);
    }

    public ProductAggregate load(String orderNo) {
        return productRepository.load(orderNo);
    }

}
