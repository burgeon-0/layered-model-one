package org.burgeon.sbd.domain.order;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.burgeon.sbd.domain.ApplicationContextHolder;
import org.burgeon.sbd.domain.DomainEventBus;
import org.burgeon.sbd.domain.SNKeeper;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.order.event.CancelOrderEvent;
import org.burgeon.sbd.domain.order.event.DeleteOrderEvent;
import org.burgeon.sbd.domain.order.event.PayOrderEvent;
import org.burgeon.sbd.domain.order.event.PlaceOrderEvent;
import org.burgeon.sbd.domain.order.repository.OrderItemRepository;
import org.burgeon.sbd.domain.order.repository.OrderRepository;
import org.burgeon.sbd.domain.product.ProductAggregate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Data
public class OrderAggregate {

    private String orderNo;
    private int totalPrice;
    private Date placeTime;
    private Date payTime;
    private Date cancelTime;
    private Date deleteTime;
    private int status;

    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private List<OrderItem> items;

    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private OrderRepository orderRepository = ApplicationContextHolder.getBean(OrderRepository.class);
    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private OrderItemRepository orderItemRepository = ApplicationContextHolder.getBean(OrderItemRepository.class);
    @Getter(value = AccessLevel.PRIVATE)
    @Setter(value = AccessLevel.PRIVATE)
    private DomainEventBus domainEventBus = ApplicationContextHolder.getBean(DomainEventBus.class);

    public OrderAggregate(PlaceOrderCommand placeOrderCommand) {
        orderNo = generateOrderNo();
        placeTime = new Date();
        status = Status.UNPAID.ordinal();

        items = new ArrayList<>(placeOrderCommand.getItems().size());
        List<PlaceOrderEvent.Item> eventOrderItems = new ArrayList<>(placeOrderCommand.getItems().size());
        for (PlaceOrderCommand.Item item : placeOrderCommand.getItems()) {
            ProductAggregate productAggregate = item.getProductAggregate();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNo(orderNo);
            orderItem.setProductNo(productAggregate.getProductNo());
            orderItem.setProductName(productAggregate.getProductName());
            orderItem.setTotalCount(item.getCount());
            orderItem.setTotalPrice(productAggregate.getPrice() * item.getCount());
            totalPrice += orderItem.getTotalPrice();
            items.add(orderItem);

            PlaceOrderEvent.Item eventOrderItem = orderItem.to(PlaceOrderEvent.Item.class);
            eventOrderItems.add(eventOrderItem);
        }
        orderItemRepository.save(items);
        orderRepository.save(this);

        PlaceOrderEvent placeOrderEvent = new PlaceOrderEvent();
        placeOrderEvent.setOrderNo(orderNo);
        placeOrderEvent.setItems(eventOrderItems);
        placeOrderEvent.setPlaceTime(placeTime);
        domainEventBus.publishEvent(placeOrderEvent);
    }

    public void pay() {
        payTime = new Date();
        status = Status.PAID.ordinal();
        orderRepository.save(this);

        PayOrderEvent payOrderEvent = new PayOrderEvent();
        payOrderEvent.setOrderNo(orderNo);
        payOrderEvent.setPayTime(payTime);
        domainEventBus.publishEvent(payOrderEvent);
    }

    public void cancel() {
        cancelTime = new Date();
        status = Status.CANCELLED.ordinal();
        orderRepository.save(this);

        CancelOrderEvent cancelOrderEvent = new CancelOrderEvent();
        cancelOrderEvent.setOrderNo(orderNo);
        cancelOrderEvent.setCancelTime(cancelTime);
        domainEventBus.publishEvent(cancelOrderEvent);
    }

    public void delete() {
        deleteTime = new Date();
        status = Status.DELETED.ordinal();
        orderRepository.save(this);

        DeleteOrderEvent deleteOrderEvent = new DeleteOrderEvent();
        deleteOrderEvent.setOrderNo(orderNo);
        deleteOrderEvent.setDeleteTime(deleteTime);
        domainEventBus.publishEvent(deleteOrderEvent);
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
