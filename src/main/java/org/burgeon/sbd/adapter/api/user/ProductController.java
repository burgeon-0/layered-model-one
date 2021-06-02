package org.burgeon.sbd.adapter.api.user;

import org.burgeon.sbd.adapter.model.res.product.ProductVO;
import org.burgeon.sbd.app.ProductService;
import org.burgeon.sbd.app.model.product.ProductDTO;
import org.burgeon.sbd.core.req.PageQuery;
import org.burgeon.sbd.core.res.PageResult;
import org.burgeon.sbd.core.res.SingleResponse;
import org.burgeon.sbd.infra.Constants;
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
    public SingleResponse<PageResult<ProductVO>> pageProducts(@Valid @ModelAttribute PageQuery pageQuery) {
        PageResult<ProductDTO> pageResult = productService.pageProducts(pageQuery.getPageNo(),
                pageQuery.getPageSize());
        PageResult<ProductVO> voPageResult = pageResult.to(PageResult.class);
        return SingleResponse.ok(voPageResult);
    }

    @GetMapping("/{productNo}")
    public SingleResponse<ProductVO> getProduct(@PathVariable("productNo") String productNo) {
        ProductDTO productDTO = productService.getProduct(productNo);
        ProductVO productVO = productDTO.to(ProductVO.class);
        return SingleResponse.ok(productVO);
    }

}
