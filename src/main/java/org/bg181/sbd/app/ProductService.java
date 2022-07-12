package org.bg181.sbd.app;

import org.bg181.sbd.app.model.product.CreateProductDTO;
import org.bg181.sbd.app.model.product.ProductDTO;
import org.bg181.sbd.app.model.product.UpdateProductDTO;
import org.bg181.sbd.core.exception.ErrorCode;
import org.bg181.sbd.core.exception.ParamException;
import org.bg181.sbd.core.res.PageResult;
import org.bg181.sbd.domain.product.ProductAggregate;
import org.bg181.sbd.domain.product.ProductAggregateFactory;
import org.bg181.sbd.domain.product.command.CreateProductCommand;
import org.bg181.sbd.domain.product.command.UpdateProductCommand;
import org.bg181.sbd.infra.repository.ProductEntityRepository;
import org.bg181.sbd.infra.repository.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Service
public class ProductService {

    @Autowired
    private ProductAggregateFactory productAggregateFactory;
    @Autowired
    private ProductEntityRepository productEntityRepository;

    public String createProduct(CreateProductDTO createProductDTO) {
        CreateProductCommand createProductCommand = createProductDTO.to(CreateProductCommand.class);
        ProductAggregate productAggregate = new ProductAggregate(createProductCommand);
        return productAggregate.getProductNo();
    }

    public void updateProduct(String productNo, UpdateProductDTO updateProductDTO) {
        ProductAggregate productAggregate = productAggregateFactory.load(productNo);
        if (productAggregate == null) {
            throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        UpdateProductCommand updateProductCommand = updateProductDTO.to(UpdateProductCommand.class);
        productAggregate.update(updateProductCommand);
    }

    public void deleteProduct(String productNo) {
        ProductAggregate productAggregate = productAggregateFactory.load(productNo);
        if (productAggregate == null) {
            throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productAggregate.delete();
    }

    public PageResult<ProductDTO> pageProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<ProductEntity> page = productEntityRepository.findAllByDeleted(pageable, false);
        PageResult<ProductDTO> pageResult = new PageResult();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(page.getTotalElements());
        List<ProductDTO> productDTOList = page.getContent().stream().map(
                productEntity -> productEntity.to(ProductDTO.class)).collect(Collectors.toList());
        pageResult.setResults(productDTOList);
        return pageResult;
    }

    public ProductDTO getProduct(String productNo) {
        ProductEntity productEntity = productEntityRepository.findByProductNoAndDeleted(productNo, false);
        if (productEntity == null) {
            throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        ProductDTO productDTO = productEntity.to(ProductDTO.class);
        return productDTO;
    }

}
