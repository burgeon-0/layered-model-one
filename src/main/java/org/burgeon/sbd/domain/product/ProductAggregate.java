package org.burgeon.sbd.domain.product;

import lombok.Getter;
import org.burgeon.sbd.domain.SNKeeper;
import org.burgeon.sbd.domain.product.command.CreateProductCommand;
import org.burgeon.sbd.domain.product.command.UpdateProductCommand;
import org.burgeon.sbd.domain.product.event.CreateProductEvent;
import org.burgeon.sbd.domain.product.event.DeleteProductEvent;
import org.burgeon.sbd.domain.product.event.UpdateProductEvent;
import org.burgeon.sbd.domain.product.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class ProductAggregate {

    @Getter
    private String productNo;
    @Getter
    private String productName;
    @Getter
    private int price;
    private int stock;
    private boolean deleted;

    @Autowired
    private ProductRepository productRepository;

    public ProductAggregate(CreateProductCommand createProductCommand) {
        productNo = generateProductNo();
        BeanUtils.copyProperties(createProductCommand, this);
        productRepository.save(this);

        CreateProductEvent createProductEvent = createProductCommand.to(CreateProductEvent.class);
        createProductEvent.setProductNo(productNo);
        // TODO publish event
    }

    public void update(UpdateProductCommand updateProductCommand) {
        BeanUtils.copyProperties(updateProductCommand, this);
        productRepository.save(this);

        UpdateProductEvent updateProductEvent = updateProductCommand.to(UpdateProductEvent.class);
        updateProductEvent.setProductNo(productNo);
        // TODO publish event
    }

    public void delete() {
        deleted = true;
        productRepository.save(this);

        DeleteProductEvent deleteProductEvent = new DeleteProductEvent();
        deleteProductEvent.setProductNo(productNo);
        // TODO publish event
    }

    public boolean stockEnough(int count) {
        return stock >= count;
    }

    private String generateProductNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String prefix = sdf.format(new Date());
        String sn = SNKeeper.get("Product:" + prefix);
        return prefix + sn;
    }

}
