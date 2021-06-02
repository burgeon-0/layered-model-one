package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.order.OrderDTO;
import org.burgeon.sbd.app.model.order.PlaceOrderDTO;
import org.burgeon.sbd.core.res.PageResult;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.OrderAggregateFactory;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.ProductAggregateFactory;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.core.exception.ParamException;
import org.burgeon.sbd.infra.repository.OrderEntityRepository;
import org.burgeon.sbd.infra.repository.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private OrderEntityRepository orderEntityRepository;

    public String placeOrder(PlaceOrderDTO placeOrderDTO) {
        if (CollectionUtils.isEmpty(placeOrderDTO.getItems())) {
            throw new ParamException(ErrorCode.ORDER_ITEM_NULL);
        }

        List<PlaceOrderCommand.Item> orderItems = new ArrayList<>(placeOrderDTO.getItems().size());
        for (PlaceOrderDTO.Item item : placeOrderDTO.getItems()) {
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

    public PageResult<OrderDTO> pageOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<OrderEntity> page = orderEntityRepository.findAll(pageable);
        PageResult<OrderDTO> pageResult = new PageResult();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(page.getTotalElements());
        List<OrderDTO> orderDTOList = page.getContent().stream().map(
                orderEntity -> orderEntity.to(OrderDTO.class)).collect(Collectors.toList());
        pageResult.setResults(orderDTOList);
        return pageResult;
    }

    public OrderDTO getOrder(String orderNo) {
        Optional<OrderEntity> optional = orderEntityRepository.findById(orderNo);
        if (!optional.isPresent()) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        OrderDTO orderDTO = optional.get().to(OrderDTO.class);
        return orderDTO;
    }

}
