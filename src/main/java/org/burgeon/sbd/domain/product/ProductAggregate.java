package org.burgeon.sbd.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.burgeon.sbd.core.*;
import org.burgeon.sbd.core.base.ProductBaseModel;
import org.burgeon.sbd.domain.product.command.CreateProductCommand;
import org.burgeon.sbd.domain.product.command.UpdateProductCommand;
import org.burgeon.sbd.domain.product.event.CreateProductEvent;
import org.burgeon.sbd.domain.product.event.DeleteProductEvent;
import org.burgeon.sbd.domain.product.event.UpdateProductEvent;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@NoArgsConstructor
public class ProductAggregate extends ProductBaseModel {

    private static final long NODE_ID = 50;

    @Setter
    @Getter
    private String productNo;
    @Setter
    @Getter
    private boolean deleted;

    private SnGenerator snGenerator = SpringBeanFactory.getBean(SnGenerator.class);
    private PropertyManager propertyManager = SpringBeanFactory.getBean(PropertyManager.class);
    private DomainRepository<ProductAggregate, String> productRepository = SpringBeanFactory.getDomainRepository(
            ProductAggregate.class, String.class);
    private DomainEventBus domainEventBus = SpringBeanFactory.getBean(DomainEventBus.class);

    public ProductAggregate(CreateProductCommand createProductCommand) {
        productNo = generateProductNo();
        propertyManager.copy(createProductCommand, this);
        productRepository.save(this);

        CreateProductEvent createProductEvent = createProductCommand.to(CreateProductEvent.class);
        createProductEvent.setProductNo(productNo);
        domainEventBus.publishEvent(createProductEvent);
    }

    public void update(UpdateProductCommand updateProductCommand) {
        propertyManager.copy(updateProductCommand, this);
        productRepository.save(this);

        UpdateProductEvent updateProductEvent = updateProductCommand.to(UpdateProductEvent.class);
        updateProductEvent.setProductNo(productNo);
        domainEventBus.publishEvent(updateProductEvent);
    }

    public void delete() {
        deleted = true;
        productRepository.save(this);

        DeleteProductEvent deleteProductEvent = new DeleteProductEvent();
        deleteProductEvent.setProductNo(productNo);
        domainEventBus.publishEvent(deleteProductEvent);
    }

    public boolean stockEnough(int count) {
        return getStock() >= count;
    }

    private String generateProductNo() {
        return snGenerator.generateSn(NODE_ID);
    }

}
