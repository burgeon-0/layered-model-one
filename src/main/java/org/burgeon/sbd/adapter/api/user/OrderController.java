package org.burgeon.sbd.adapter.api.user;

import org.burgeon.sbd.adapter.model.req.PageQuery;
import org.burgeon.sbd.adapter.model.req.order.PlaceOrderForm;
import org.burgeon.sbd.adapter.model.res.MultiResponse;
import org.burgeon.sbd.adapter.model.res.PageResult;
import org.burgeon.sbd.adapter.model.res.Response;
import org.burgeon.sbd.adapter.model.res.SingleResponse;
import org.burgeon.sbd.adapter.model.res.order.OrderVO;
import org.burgeon.sbd.app.OrderService;
import org.burgeon.sbd.app.model.OrderDTO;
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
@RequestMapping(Constants.API_USER + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/actions/place")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResponse<String> placeOrder(@Valid @RequestBody PlaceOrderForm placeOrderForm) {
        OrderDTO orderDTO = placeOrderForm.to(OrderDTO.class);
        String orderNo = orderService.placeOrder(orderDTO);
        return SingleResponse.ok(orderNo);
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
    public MultiResponse<PageResult<OrderVO>> listOrders(@Valid @ModelAttribute PageQuery pageQuery) {
        return null;
    }

    @GetMapping("/{id}")
    public SingleResponse<OrderVO> getOrder(@PathVariable("id") String id) {
        return null;
    }

}
