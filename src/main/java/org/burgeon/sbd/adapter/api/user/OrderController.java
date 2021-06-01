package org.burgeon.sbd.adapter.api.user;

import org.burgeon.sbd.adapter.model.req.order.PlaceOrderForm;
import org.burgeon.sbd.app.OrderService;
import org.burgeon.sbd.app.model.OrderDTO;
import org.burgeon.sbd.app.model.PlaceOrderDTO;
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
        return SingleResponse.createSuccess(orderNo);
    }

    @PostMapping("/{orderNo}/actions/pay")
    public Response payOrder(@PathVariable("orderNo") String orderNo) {
        orderService.payOrder(orderNo);
        return Response.ok();
    }

    @PostMapping("/{orderNo}/actions/cancel")
    public Response cancelOrder(@PathVariable("orderNo") String orderNo) {
        orderService.cancelOrder(orderNo);
        return Response.ok();
    }

    @PostMapping("/{orderNo}/actions/delete")
    public Response deleteOrder(@PathVariable("orderNo") String orderNo) {
        orderService.deleteOrder(orderNo);
        return Response.ok();
    }

    @GetMapping
    public SingleResponse<PageResult<OrderDTO>> pageOrders(@Valid @ModelAttribute PageQuery pageQuery) {
        PageResult<OrderDTO> pageResult = orderService.pageOrders(pageQuery.getPageNo(),
                pageQuery.getPageSize());
        return SingleResponse.ok(pageResult);
    }

    @GetMapping("/{orderNo}")
    public SingleResponse<OrderDTO> getOrder(@PathVariable("orderNo") String orderNo) {
        OrderDTO orderDTO = orderService.getOrder(orderNo);
        return SingleResponse.ok(orderDTO);
    }

}
