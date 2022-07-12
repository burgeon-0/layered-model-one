package org.bg181.sbd.adapter.api.user;

import org.bg181.sbd.adapter.common.resolver.CamelCaseToSnakeCase;
import org.bg181.sbd.adapter.model.res.product.ProductVO;
import org.bg181.sbd.app.ProductService;
import org.bg181.sbd.app.model.product.ProductDTO;
import org.bg181.sbd.core.req.PageQuery;
import org.bg181.sbd.core.res.PageResult;
import org.bg181.sbd.core.res.SingleResponse;
import org.bg181.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController("UserProductController")
@RequestMapping(Constants.API_USER + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public SingleResponse<PageResult<ProductVO>> pageProducts(@Valid @CamelCaseToSnakeCase PageQuery pageQuery) {
        PageResult<ProductDTO> pageResult = productService.pageProducts(pageQuery.getPageNo(),
                pageQuery.getPageSize());
        PageResult<ProductVO> voPageResult = pageResult.to(PageResult.class);
        return SingleResponse.ok(voPageResult);
    }

    @GetMapping("/{product_no}")
    public SingleResponse<ProductVO> getProduct(@PathVariable("product_no") String productNo) {
        ProductDTO productDTO = productService.getProduct(productNo);
        ProductVO productVO = productDTO.to(ProductVO.class);
        return SingleResponse.ok(productVO);
    }

}
