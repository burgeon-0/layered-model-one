package org.bg181.sbd.adapter.api.admin;

import org.bg181.sbd.adapter.common.resolver.CamelCaseToSnakeCase;
import org.bg181.sbd.adapter.model.res.order.OrderVO;
import org.bg181.sbd.app.OrderService;
import org.bg181.sbd.app.model.order.OrderDTO;
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
@RestController("AdminOrderController")
@RequestMapping(Constants.API_ADMIN + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public SingleResponse<PageResult<OrderVO>> pageOrders(@Valid @CamelCaseToSnakeCase PageQuery pageQuery) {
        PageResult<OrderDTO> pageResult = orderService.pageOrders(pageQuery.getPageNo(),
                pageQuery.getPageSize());
        PageResult<OrderVO> voPageResult = pageResult.to(PageResult.class);
        return SingleResponse.ok(voPageResult);
    }

    @GetMapping("/{order_no}")
    public SingleResponse<OrderVO> getOrder(@PathVariable("order_no") String orderNo) {
        OrderDTO orderDTO = orderService.getOrder(orderNo);
        OrderVO orderVO = orderDTO.to(OrderVO.class);
        return SingleResponse.ok(orderVO);
    }

}
