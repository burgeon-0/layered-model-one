package org.burgeon.sbd.adapter.api.admin;

import org.burgeon.sbd.adapter.model.res.order.OrderVO;
import org.burgeon.sbd.app.OrderService;
import org.burgeon.sbd.app.model.order.OrderDTO;
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
@RestController("AdminOrderController")
@RequestMapping(Constants.API_ADMIN + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public SingleResponse<PageResult<OrderVO>> pageOrders(@Valid @ModelAttribute PageQuery pageQuery) {
        PageResult<OrderDTO> pageResult = orderService.pageOrders(pageQuery.getPageNo(),
                pageQuery.getPageSize());
        PageResult<OrderVO> voPageResult = pageResult.to(PageResult.class);
        return SingleResponse.ok(voPageResult);
    }

    @GetMapping("/{orderNo}")
    public SingleResponse<OrderVO> getOrder(@PathVariable("orderNo") String orderNo) {
        OrderDTO orderDTO = orderService.getOrder(orderNo);
        OrderVO orderVO = orderDTO.to(OrderVO.class);
        return SingleResponse.ok(orderVO);
    }

}
