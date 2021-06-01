package org.burgeon.sbd.adapter.api.admin;

import org.burgeon.sbd.adapter.model.req.product.CreateProductForm;
import org.burgeon.sbd.adapter.model.req.product.UpdateProductForm;
import org.burgeon.sbd.app.ProductService;
import org.burgeon.sbd.app.model.CreateProductDTO;
import org.burgeon.sbd.app.model.ProductDTO;
import org.burgeon.sbd.app.model.UpdateProductDTO;
import org.burgeon.sbd.core.req.PageQuery;
import org.burgeon.sbd.core.res.PageResult;
import org.burgeon.sbd.core.res.Response;
import org.burgeon.sbd.core.res.SingleResponse;
import org.burgeon.sbd.infra.Constants;
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
        return SingleResponse.createSuccess(productNo);
    }

    @PutMapping("/{productNo}")
    public Response updateProduct(@PathVariable("productNo") String productNo,
                                  @Valid @RequestBody UpdateProductForm updateProductForm) {
        UpdateProductDTO updateProductDTO = updateProductForm.to(UpdateProductDTO.class);
        productService.updateProduct(productNo, updateProductDTO);
        return Response.ok();
    }

    @DeleteMapping("/{productNo}")
    public Response deleteProduct(@PathVariable("productNo") String productNo) {
        productService.deleteProduct(productNo);
        return Response.ok();
    }

    @GetMapping
    public SingleResponse<PageResult<ProductDTO>> listProducts(@Valid @ModelAttribute PageQuery pageQuery) {
        PageResult<ProductDTO> pageResult = productService.pageProducts(pageQuery.getPageNo(),
                pageQuery.getPageSize());
        return SingleResponse.ok(pageResult);
    }

    @GetMapping("/{productNo}")
    public SingleResponse<ProductDTO> getProduct(@PathVariable("productNo") String productNo) {
        ProductDTO productDTO = productService.getProduct(productNo);
        return SingleResponse.ok(productDTO);
    }

}
