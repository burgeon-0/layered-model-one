package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.ProductDTO;
import org.burgeon.sbd.domain.product.command.CreateProductCommand;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.command.UpdateProductCommand;
import org.burgeon.sbd.domain.product.ProductAggregateFactory;
import org.burgeon.sbd.domain.exception.ErrorCode;
import org.burgeon.sbd.domain.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Service
public class ProductService {

    @Autowired
    private ProductAggregateFactory productAggregateFactory;

    public String createProduct(ProductDTO productDTO) {
        CreateProductCommand createProductCommand = productDTO.to(CreateProductCommand.class);
        ProductAggregate productAggregate = new ProductAggregate(createProductCommand);
        return productAggregate.getProductNo();
    }

    public void updateProduct(String productNo, ProductDTO productDTO) {
        ProductAggregate productAggregate = productAggregateFactory.load(productNo);
        if (productAggregate == null) {
            throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        UpdateProductCommand updateProductCommand = productDTO.to(UpdateProductCommand.class);
        productAggregate.update(updateProductCommand);
    }

    public void deleteProduct(String productNo) {
        ProductAggregate productAggregate = productAggregateFactory.load(productNo);
        if (productAggregate == null) {
            throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productAggregate.delete();
    }

}
