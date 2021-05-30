package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.OrderDTO;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.PlaceOrderCommand;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.infra.exception.BizException;
import org.burgeon.sbd.infra.exception.ErrorCode;
import org.burgeon.sbd.infra.exception.ParamException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Service
public class OrderService {

    public String placeOrder(OrderDTO orderDTO) {
        ProductAggregate productAggregate = ProductAggregate.load(orderDTO.getProductNo());
        if (productAggregate == null) {
            throw new ParamException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        if (!productAggregate.stockEnough(orderDTO.getCount())) {
            throw new BizException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
        }
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand();
        placeOrderCommand.setProductNo(orderDTO.getProductNo());
        placeOrderCommand.setProductName(productAggregate.getName());
        placeOrderCommand.setTotalCount(orderDTO.getCount());
        placeOrderCommand.setTotalPrice(productAggregate.getPrice() * orderDTO.getCount());
        placeOrderCommand.setPlaceTime(new Date());
        OrderAggregate orderAggregate = new OrderAggregate(placeOrderCommand);
        return orderAggregate.getOrderNo();
    }

}
