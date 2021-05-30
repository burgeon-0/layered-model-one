package org.burgeon.sbd.adapter.api.admin;

import org.burgeon.sbd.adapter.model.req.PageQuery;
import org.burgeon.sbd.adapter.model.req.product.CreateProductForm;
import org.burgeon.sbd.adapter.model.req.product.UpdateProductForm;
import org.burgeon.sbd.adapter.model.res.MultiResponse;
import org.burgeon.sbd.adapter.model.res.PageResult;
import org.burgeon.sbd.adapter.model.res.Response;
import org.burgeon.sbd.adapter.model.res.SingleResponse;
import org.burgeon.sbd.adapter.model.res.product.ProductVO;
import org.burgeon.sbd.infra.Constants;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResponse<String> createProduct(@Valid @RequestBody CreateProductForm createProductForm) {
        return SingleResponse.ok("");
    }

    @PutMapping
    public Response updateProduct(@Valid @RequestBody UpdateProductForm updateProductForm) {
        return Response.ok();
    }

    @DeleteMapping("/{productNo}")
    public Response deleteProduct(@PathVariable("productNo") String productNo) {
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
