package org.burgeon.sbd.adapter.api.user;

import org.burgeon.sbd.adapter.model.req.PageQuery;
import org.burgeon.sbd.adapter.model.res.MultiResponse;
import org.burgeon.sbd.adapter.model.res.PageResult;
import org.burgeon.sbd.adapter.model.res.SingleResponse;
import org.burgeon.sbd.adapter.model.res.product.ProductVO;
import org.burgeon.sbd.infra.Constants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController
@RequestMapping(Constants.API_USER + "/products")
public class ProductController {

    @GetMapping
    public MultiResponse<PageResult<ProductVO>> listProducts(@Valid @ModelAttribute PageQuery pageQuery) {
        return null;
    }

    @GetMapping("/{productNo}")
    public SingleResponse<ProductVO> getProduct(@PathVariable("productNo") String productNo) {
        return null;
    }

}
