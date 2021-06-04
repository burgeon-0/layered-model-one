package org.burgeon.sbd.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.burgeon.sbd.core.DomainEventBus;
import org.burgeon.sbd.core.DomainRepository;
import org.burgeon.sbd.core.SnGenerator;
import org.burgeon.sbd.core.SpringBeanFactory;
import org.burgeon.sbd.core.base.OrderBaseModel;
import org.burgeon.sbd.core.base.OrderItem;
import org.burgeon.sbd.core.exception.BizException;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.order.event.CancelOrderEvent;
import org.burgeon.sbd.domain.order.event.DeleteOrderEvent;
import org.burgeon.sbd.domain.order.event.PayOrderEvent;
import org.burgeon.sbd.domain.order.event.PlaceOrderEvent;
import org.burgeon.sbd.domain.product.ProductAggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@NoArgsConstructor
public class OrderAggregate extends OrderBaseModel {

    private static final long NODE_ID = 51;

    @Setter
    @Getter
    private String orderNo;

    private SnGenerator snGenerator = SpringBeanFactory.getBean(SnGenerator.class);
    private DomainRepository<OrderAggregate, String> orderRepository = SpringBeanFactory.getDomainRepository(
            OrderAggregate.class, String.class);
    private DomainRepository<OrderItem, String> orderItemRepository = SpringBeanFactory.getDomainRepository(
            OrderItem.class, String.class);
    private DomainEventBus domainEventBus = SpringBeanFactory.getBean(DomainEventBus.class);

    public OrderAggregate(PlaceOrderCommand placeOrderCommand) {
        orderNo = generateOrderNo();
        setPlaceTime(new Date());
        setStatus(Status.UNPAID);

        setItems(new ArrayList<>(placeOrderCommand.getItems().size()));
        List<OrderItem> eventOrderItems = new ArrayList<>(placeOrderCommand.getItems().size());
        int totalPrice = 0;
        for (PlaceOrderCommand.Item item : placeOrderCommand.getItems()) {
            ProductAggregate productAggregate = item.getProductAggregate();
            if (!productAggregate.stockEnough(item.getCount())) {
                throw new BizException(ErrorCode.PRODUCT_STOCK_NOT_ENOUGH);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNo(orderNo);
            orderItem.setProductNo(productAggregate.getProductNo());
            orderItem.setProductName(productAggregate.getProductName());
            orderItem.setTotalCount(item.getCount());
            orderItem.setTotalPrice(productAggregate.getPrice() * item.getCount());
            totalPrice += orderItem.getTotalPrice();
            getItems().add(orderItem);

            OrderItem eventOrderItem = orderItem.to(OrderItem.class);
            eventOrderItems.add(eventOrderItem);
        }
        setTotalPrice(totalPrice);
        orderItemRepository.save(getItems());
        orderRepository.save(this);

        PlaceOrderEvent placeOrderEvent = new PlaceOrderEvent();
        placeOrderEvent.setOrderNo(orderNo);
        placeOrderEvent.setItems(eventOrderItems);
        placeOrderEvent.setTotalPrice(totalPrice);
        placeOrderEvent.setPlaceTime(getPlaceTime());
        domainEventBus.publishEvent(placeOrderEvent);
    }

    public void pay() {
        setPayTime(new Date());
        setStatus(Status.PAID);
        orderRepository.save(this);

        PayOrderEvent payOrderEvent = new PayOrderEvent();
        payOrderEvent.setOrderNo(orderNo);
        payOrderEvent.setPayTime(getPayTime());
        domainEventBus.publishEvent(payOrderEvent);
    }

    public void cancel() {
        setCancelTime(new Date());
        setStatus(Status.CANCELLED);
        orderRepository.save(this);

        CancelOrderEvent cancelOrderEvent = new CancelOrderEvent();
        cancelOrderEvent.setOrderNo(orderNo);
        cancelOrderEvent.setCancelTime(getCancelTime());
        domainEventBus.publishEvent(cancelOrderEvent);
    }

    public void delete() {
        setDeleteTime(new Date());
        setStatus(Status.DELETED);
        orderRepository.save(this);

        DeleteOrderEvent deleteOrderEvent = new DeleteOrderEvent();
        deleteOrderEvent.setOrderNo(orderNo);
        deleteOrderEvent.setDeleteTime(getDeleteTime());
        domainEventBus.publishEvent(deleteOrderEvent);
    }

    private String generateOrderNo() {
        return snGenerator.generateSn(NODE_ID);
    }

}
