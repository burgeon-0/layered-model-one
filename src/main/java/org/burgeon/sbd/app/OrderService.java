package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.OrderDTO;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.order.factory.OrderFactory;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.factory.ProductFactory;
import org.burgeon.sbd.infra.exception.BizException;
import org.burgeon.sbd.infra.exception.ErrorCode;
import org.burgeon.sbd.infra.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Service
public class OrderService {

    @Autowired
    private ProductFactory productFactory;
    @Autowired
    private OrderFactory orderFactory;

    public String placeOrder(OrderDTO orderDTO) {
        if (CollectionUtils.isEmpty(orderDTO.getItems())) {
            throw new ParamException(ErrorCode.ORDER_ITEM_NULL);
        }

        List<PlaceOrderCommand.Item> orderItems = new ArrayList<>(orderDTO.getItems().size());
        for (OrderDTO.Item item : orderDTO.getItems()) {
            ProductAggregate productAggregate = productFactory.load(item.getProductNo());
            if (productAggregate == null) {
                throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
            }
            if (!productAggregate.stockEnough(item.getCount())) {
                throw new BizException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
            }
            PlaceOrderCommand.Item orderItem = new PlaceOrderCommand.Item();
            orderItem.setProductAggregate(productAggregate);
            orderItem.setCount(item.getCount());
            orderItems.add(orderItem);
        }

        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand();
        placeOrderCommand.setItems(orderItems);
        OrderAggregate orderAggregate = new OrderAggregate(placeOrderCommand);
        return orderAggregate.getOrderNo();
    }

    public void payOrder(String orderNo) {
        OrderAggregate orderAggregate = orderFactory.load(orderNo);
        if (orderAggregate == null) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderAggregate.pay();
    }

    public void cancelOrder(String orderNo) {
        OrderAggregate orderAggregate = orderFactory.load(orderNo);
        if (orderAggregate == null) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderAggregate.cancel();
    }

    public void deleteOrder(String orderNo) {
        OrderAggregate orderAggregate = orderFactory.load(orderNo);
        if (orderAggregate == null) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderAggregate.delete();
    }

}
