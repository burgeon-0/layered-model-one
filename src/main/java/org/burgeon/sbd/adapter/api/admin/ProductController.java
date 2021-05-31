package org.burgeon.sbd.adapter.api.admin;

import org.burgeon.sbd.adapter.model.req.PageQuery;
import org.burgeon.sbd.adapter.model.req.product.CreateProductForm;
import org.burgeon.sbd.adapter.model.req.product.UpdateProductForm;
import org.burgeon.sbd.adapter.model.res.MultiResponse;
import org.burgeon.sbd.adapter.model.res.PageResult;
import org.burgeon.sbd.adapter.model.res.Response;
import org.burgeon.sbd.adapter.model.res.SingleResponse;
import org.burgeon.sbd.adapter.model.res.product.ProductVO;
import org.burgeon.sbd.app.ProductService;
import org.burgeon.sbd.app.model.ProductDTO;
import org.burgeon.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController
@RequestMapping(Constants.API_ADMIN + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResponse<String> createProduct(@Valid @RequestBody CreateProductForm createProductForm) {
        ProductDTO productDTO = createProductForm.to(ProductDTO.class);
        String productNo = productService.createProduct(productDTO);
        return SingleResponse.ok(productNo);
    }

    @PutMapping("/{productNo}")
    public Response updateProduct(@PathVariable("productNo") String productNo,
                                  @Valid @RequestBody UpdateProductForm updateProductForm) {
        ProductDTO productDTO = updateProductForm.to(ProductDTO.class);
        productService.updateProduct(productNo, productDTO);
        return Response.ok();
    }

    @DeleteMapping("/{productNo}")
    public Response deleteProduct(@PathVariable("productNo") String productNo) {
        productService.deleteProduct(productNo);
        return Response.ok();
    }

    @GetMapping
    public MultiResponse<PageResult<ProductVO>> listProducts(@Valid @ModelAttribute PageQuery pageQuery) {
        return null;
    }

    @GetMapping("/{productNo}")
    public SingleResponse<ProductVO> getProduct(@PathVariable("productNo") String productNo) {
        return null;
    }

}
