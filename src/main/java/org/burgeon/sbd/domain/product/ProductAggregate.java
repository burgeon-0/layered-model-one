package org.burgeon.sbd.domain.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.burgeon.sbd.domain.*;
import org.burgeon.sbd.domain.product.command.CreateProductCommand;
import org.burgeon.sbd.domain.product.command.UpdateProductCommand;
import org.burgeon.sbd.domain.product.event.CreateProductEvent;
import org.burgeon.sbd.domain.product.event.DeleteProductEvent;
import org.burgeon.sbd.domain.product.event.UpdateProductEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class ProductAggregate {

    private String productNo;
    private String productName;
    private int price;
    private int stock;
    private boolean deleted;

    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private Copyable copyable = SpringBeanFactory.getBean(Copyable.class);
    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private DomainRepository<ProductAggregate, String> productRepository = SpringBeanFactory.getDomainRepository(
            ProductAggregate.class, String.class);
    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private DomainEventBus domainEventBus = SpringBeanFactory.getBean(DomainEventBus.class);

    public ProductAggregate(CreateProductCommand createProductCommand) {
        productNo = generateProductNo();
        copyable.copy(createProductCommand, this);
        productRepository.save(this);

        CreateProductEvent createProductEvent = createProductCommand.to(CreateProductEvent.class);
        createProductEvent.setProductNo(productNo);
        domainEventBus.publishEvent(createProductEvent);
    }

    public void update(UpdateProductCommand updateProductCommand) {
        copyable.copy(updateProductCommand, this);
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
        return stock >= count;
    }

    private String generateProductNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String prefix = sdf.format(new Date());
        String sn = SnKeeper.get("Product:" + prefix);
        return prefix + sn;
    }

}
