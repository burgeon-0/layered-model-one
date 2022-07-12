package org.bg181.sbd.adapter.api.user;

import org.bg181.sbd.adapter.common.resolver.CamelCaseToSnakeCase;
import org.bg181.sbd.adapter.model.req.order.PlaceOrderForm;
import org.bg181.sbd.adapter.model.res.order.OrderVO;
import org.bg181.sbd.app.OrderService;
import org.bg181.sbd.app.model.order.OrderDTO;
import org.bg181.sbd.app.model.order.PlaceOrderDTO;
import org.bg181.sbd.core.req.PageQuery;
import org.bg181.sbd.core.res.PageResult;
import org.bg181.sbd.core.res.Response;
import org.bg181.sbd.core.res.SingleResponse;
import org.bg181.sbd.infra.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        List<PlaceOrderDTO.Item> dtoItems = new ArrayList<>();
        for (PlaceOrderForm.Item item : placeOrderForm.getItems()) {
            dtoItems.add(item.to(PlaceOrderDTO.Item.class));
        }
        placeOrderDTO.setItems(dtoItems);
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
