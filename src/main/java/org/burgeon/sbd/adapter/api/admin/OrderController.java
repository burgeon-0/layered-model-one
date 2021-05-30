package org.burgeon.sbd.adapter.api.admin;

import org.burgeon.sbd.adapter.model.req.PageQuery;
import org.burgeon.sbd.adapter.model.res.MultiResponse;
import org.burgeon.sbd.adapter.model.res.PageResult;
import org.burgeon.sbd.adapter.model.res.SingleResponse;
import org.burgeon.sbd.adapter.model.res.order.OrderVO;
import org.burgeon.sbd.infra.Constants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@RestController
@RequestMapping(Constants.API_ADMIN + "/orders")
public class OrderController {

    @GetMapping
    public MultiResponse<PageResult<OrderVO>> listOrders(@Valid @ModelAttribute PageQuery pageQuery) {
        return null;
    }

    @GetMapping("/{id}")
    public SingleResponse<OrderVO> getOrder(@PathVariable("id") String id) {
        return null;
    }

}
