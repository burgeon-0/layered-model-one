package org.burgeon.sbd.adapter.api.user;

import org.burgeon.sbd.core.req.PageQuery;
import org.burgeon.sbd.core.res.MultiResponse;
import org.burgeon.sbd.core.res.PageResult;
import org.burgeon.sbd.core.res.SingleResponse;
import org.burgeon.sbd.adapter.model.res.product.ProductVO;
import org.burgeon.sbd.infra.Constants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController("UserProductController")
@RequestMapping(Constants.API_USER + "/products")
public class ProductController {

    @GetMapping
    public MultiResponse<PageResult<ProductVO>> listProducts(@Valid @ModelAttribute PageQuery pageQuery) {
        return MultiResponse.ok(null);
    }

    @GetMapping("/{productNo}")
    public SingleResponse<ProductVO> getProduct(@PathVariable("productNo") String productNo) {
        return null;
    }

}
