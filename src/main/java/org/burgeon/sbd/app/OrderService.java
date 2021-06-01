package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.OrderDTO;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.OrderAggregateFactory;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.ProductAggregateFactory;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.core.exception.ParamException;
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
    private ProductAggregateFactory productAggregateFactory;
    @Autowired
    private OrderAggregateFactory orderAggregateFactory;

    public String placeOrder(OrderDTO orderDTO) {
        if (CollectionUtils.isEmpty(orderDTO.getItems())) {
            throw new ParamException(ErrorCode.ORDER_ITEM_NULL);
        }

        List<PlaceOrderCommand.Item> orderItems = new ArrayList<>(orderDTO.getItems().size());
        for (OrderDTO.Item item : orderDTO.getItems()) {
            ProductAggregate productAggregate = productAggregateFactory.load(item.getProductNo());
            if (productAggregate == null) {
                throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND,
                        String.format("Product Not Found: %s", item.getProductNo()));
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
        OrderAggregate orderAggregate = orderAggregateFactory.load(orderNo);
        if (orderAggregate == null) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderAggregate.pay();
    }

    public void cancelOrder(String orderNo) {
        OrderAggregate orderAggregate = orderAggregateFactory.load(orderNo);
        if (orderAggregate == null) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderAggregate.cancel();
    }

    public void deleteOrder(String orderNo) {
        OrderAggregate orderAggregate = orderAggregateFactory.load(orderNo);
        if (orderAggregate == null) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderAggregate.delete();
    }

}
