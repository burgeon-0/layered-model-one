package org.burgeon.sbd.adapter.api.user;

import org.burgeon.sbd.adapter.common.resolver.CamelCaseToSnakeCase;
import org.burgeon.sbd.adapter.model.req.order.PlaceOrderForm;
import org.burgeon.sbd.adapter.model.res.order.OrderVO;
import org.burgeon.sbd.app.OrderService;
import org.burgeon.sbd.app.model.order.OrderDTO;
import org.burgeon.sbd.app.model.order.PlaceOrderDTO;
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
@RestController("UserOrderController")
@RequestMapping(Constants.API_USER + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/actions/place")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResponse<String> placeOrder(@Valid @RequestBody PlaceOrderForm placeOrderForm) {
        PlaceOrderDTO placeOrderDTO = placeOrderForm.to(PlaceOrderDTO.class);
        String orderNo = orderService.placeOrder(placeOrderDTO);
        return SingleResponse.created(orderNo);
    }

    @PostMapping("/{order_no}/actions/pay")
    public Response payOrder(@PathVariable("order_no") String orderNo) {
        orderService.payOrder(orderNo);
        return Response.ok();
    }

    @PostMapping("/{order_no}/actions/cancel")
    public Response cancelOrder(@PathVariable("order_no") String orderNo) {
        orderService.cancelOrder(orderNo);
        return Response.ok();
    }

    @PostMapping("/{order_no}/actions/delete")
    public Response deleteOrder(@PathVariable("order_no") String orderNo) {
        orderService.deleteOrder(orderNo);
        return Response.ok();
    }

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
