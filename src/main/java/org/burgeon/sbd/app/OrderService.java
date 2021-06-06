package org.burgeon.sbd.app;

import org.burgeon.sbd.app.model.order.OrderDTO;
import org.burgeon.sbd.app.model.order.PlaceOrderDTO;
import org.burgeon.sbd.core.base.OrderItem;
import org.burgeon.sbd.core.res.PageResult;
import org.burgeon.sbd.domain.order.OrderAggregate;
import org.burgeon.sbd.domain.order.OrderAggregateFactory;
import org.burgeon.sbd.domain.order.command.PlaceOrderCommand;
import org.burgeon.sbd.domain.product.ProductAggregate;
import org.burgeon.sbd.domain.product.ProductAggregateFactory;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.core.exception.ParamException;
import org.burgeon.sbd.infra.repository.OrderEntityRepository;
import org.burgeon.sbd.infra.repository.OrderItemEntityRepository;
import org.burgeon.sbd.infra.repository.entity.OrderEntity;
import org.burgeon.sbd.infra.repository.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
    @Autowired
    private OrderItemEntityRepository orderItemEntityRepository;

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
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<OrderEntity> page = orderEntityRepository.findAll(pageable);
        PageResult<OrderDTO> pageResult = new PageResult();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(page.getTotalElements());
        List<OrderDTO> orderDTOList = page.getContent().stream().map(
                orderEntity -> orderEntity.to(OrderDTO.class)).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(orderDTOList)) {
            List<String> orderNos = collectOrderNos(page.getContent());
            Map<String, List<OrderItem>> orderItemsMap = getOrderItemsMap(orderNos);
            for (OrderDTO orderDTO : orderDTOList) {
                orderDTO.setItems(orderItemsMap.get(orderDTO.getOrderNo()));
            }
        }

        pageResult.setResults(orderDTOList);
        return pageResult;
    }

    private List<String> collectOrderNos(List<OrderEntity> orderEntities) {
        List<String> orderNos = orderEntities.stream().map(
                orderEntity -> orderEntity.getOrderNo()).collect(Collectors.toList());
        return orderNos;
    }

    private Map<String, List<OrderItem>> getOrderItemsMap(List<String> orderNos) {
        Map<String, List<OrderItem>> orderItemsMap = new HashMap<>();
        List<OrderItemEntity> orderItemEntities = orderItemEntityRepository.findAllByOrderNoIn(orderNos);
        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            String orderNo = orderItemEntity.getOrderNo();
            List<OrderItem> orderItems = orderItemsMap.get(orderNo);
            if (orderItems == null) {
                orderItems = new ArrayList<>();
                orderItemsMap.put(orderNo, orderItems);
            }
            orderItems.add(orderItemEntity.to(OrderItem.class));
        }
        return orderItemsMap;
    }

    public OrderDTO getOrder(String orderNo) {
        Optional<OrderEntity> optional = orderEntityRepository.findById(orderNo);
        if (!optional.isPresent()) {
            throw new ParamException(ErrorCode.ORDER_NOT_FOUND);
        }
        OrderDTO orderDTO = optional.get().to(OrderDTO.class);
        orderDTO.setItems(getOrderItems(orderNo));
        return orderDTO;
    }

    private List<OrderItem> getOrderItems(String orderNo) {
        List<OrderItemEntity> orderItemEntities = orderItemEntityRepository.findAllByOrderNo(orderNo);
        if (CollectionUtils.isEmpty(orderItemEntities)) {
            return Collections.EMPTY_LIST;
        }
        List<OrderItem> orderItems = new ArrayList<>(orderItemEntities.size());
        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            orderItems.add(orderItemEntity.to(OrderItem.class));
        }
        return orderItems;
    }

}
