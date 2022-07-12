package org.bg181.sbd.adapter.api.admin;

import org.bg181.sbd.adapter.common.resolver.CamelCaseToSnakeCase;
import org.bg181.sbd.adapter.model.req.product.CreateProductForm;
import org.bg181.sbd.adapter.model.req.product.UpdateProductForm;
import org.bg181.sbd.adapter.model.res.product.ProductVO;
import org.bg181.sbd.app.ProductService;
import org.bg181.sbd.app.model.product.CreateProductDTO;
import org.bg181.sbd.app.model.product.ProductDTO;
import org.bg181.sbd.app.model.product.UpdateProductDTO;
import org.bg181.sbd.core.req.PageQuery;
import org.bg181.sbd.core.res.PageResult;
import org.bg181.sbd.core.res.Response;
import org.bg181.sbd.core.res.SingleResponse;
import org.bg181.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController("AdminProductController")
@RequestMapping(Constants.API_ADMIN + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResponse<String> createProduct(@Valid @RequestBody CreateProductForm createProductForm) {
        CreateProductDTO createProductDTO = createProductForm.to(CreateProductDTO.class);
        String productNo = productService.createProduct(createProductDTO);
        return SingleResponse.created(productNo);
    }

    @PutMapping("/{product_no}")
    public Response updateProduct(@PathVariable("product_no") String productNo,
                                  @Valid @RequestBody UpdateProductForm updateProductForm) {
        UpdateProductDTO updateProductDTO = updateProductForm.to(UpdateProductDTO.class);
        productService.updateProduct(productNo, updateProductDTO);
        return Response.ok();
    }

    @DeleteMapping("/{product_no}")
    public Response deleteProduct(@PathVariable("product_no") String productNo) {
        productService.deleteProduct(productNo);
        return Response.ok();
    }

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
