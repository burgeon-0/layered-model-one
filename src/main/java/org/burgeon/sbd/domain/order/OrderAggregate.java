package org.burgeon.sbd.domain.order;

import lombok.Getter;
import org.burgeon.sbd.domain.SNKeeper;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.order.event.CancelOrderEvent;
import org.burgeon.sbd.domain.order.event.DeleteOrderEvent;
import org.burgeon.sbd.domain.order.event.PayOrderEvent;
import org.burgeon.sbd.domain.order.event.PlaceOrderEvent;
import org.burgeon.sbd.domain.order.repository.OrderRepository;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class OrderAggregate {

    @Getter
    private String orderNo;
    private List<OrderItem> items;
    private int totalPrice;
    private Date placeTime;
    private Date payTime;
    private Date cancelTime;
    private Date deleteTime;
    private int status;

    @Autowired
    private OrderRepository orderRepository;

    public OrderAggregate(PlaceOrderCommand placeOrderCommand) {
        orderNo = generateOrderNo();
        placeTime = new Date();
        status = Status.UNPAID.ordinal();

        items = new ArrayList<>(placeOrderCommand.getItems().size());
        List<PlaceOrderEvent.Item> eventOrderItems = new ArrayList<>(placeOrderCommand.getItems().size());
        for (PlaceOrderCommand.Item item : placeOrderCommand.getItems()) {
            ProductAggregate productAggregate = item.getProductAggregate();
            OrderItem orderItem = new OrderItem();
            orderItem.setProductNo(productAggregate.getProductNo());
            orderItem.setProductName(productAggregate.getProductName());
            orderItem.setTotalCount(item.getCount());
            orderItem.setTotalPrice(productAggregate.getPrice() * item.getCount());
            totalPrice += orderItem.getTotalPrice();
            items.add(orderItem);

            PlaceOrderEvent.Item eventOrderItem = orderItem.to(PlaceOrderEvent.Item.class);
            eventOrderItems.add(eventOrderItem);
        }
        orderRepository.save(this);

        PlaceOrderEvent placeOrderEvent = new PlaceOrderEvent();
        placeOrderEvent.setOrderNo(orderNo);
        placeOrderEvent.setItems(eventOrderItems);
        placeOrderEvent.setPlaceTime(placeTime);
        // TODO publish event
    }

    public void pay() {
        payTime = new Date();
        status = Status.PAID.ordinal();
        orderRepository.save(this);

        PayOrderEvent payOrderEvent = new PayOrderEvent();
        payOrderEvent.setOrderNo(orderNo);
        payOrderEvent.setPayTime(payTime);
        // TODO publish event
    }

    public void cancel() {
        cancelTime = new Date();
        status = Status.CANCELLED.ordinal();
        orderRepository.save(this);

        CancelOrderEvent cancelOrderEvent = new CancelOrderEvent();
        cancelOrderEvent.setOrderNo(orderNo);
        cancelOrderEvent.setCancelTime(cancelTime);
        // TODO publish event
    }

    public void delete() {
        deleteTime = new Date();
        status = Status.DELETED.ordinal();
        orderRepository.save(this);

        DeleteOrderEvent deleteOrderEvent = new DeleteOrderEvent();
        deleteOrderEvent.setOrderNo(orderNo);
        deleteOrderEvent.setDeleteTime(deleteTime);
        // TODO publish event
    }

    private String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String prefix = sdf.format(new Date());
        String sn = SNKeeper.get("Order:" + prefix);
        return prefix + sn;
    }

    enum Status {
        /**
         * 未支付
         */
        UNPAID,
        /**
         * 已支付
         */
        PAID,
        /**
         * 已取消
         */
        CANCELLED,
        /**
         * 已删除
         */
        DELETED
    }

}
