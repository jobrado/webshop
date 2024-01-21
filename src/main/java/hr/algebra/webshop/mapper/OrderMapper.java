package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.OrderDTO;

import hr.algebra.webshop.entity.Order;

public class OrderMapper {
    public static OrderDTO mapToOrderDTO (Order order) {
        return new OrderDTO(
                order.getId(),
                CartMapper.mapToCartItemDTO(order.getCart()),
                order.getDelivery(),
                order.getPaymentMethod(),
                order.getDate()

        );
    }
    public static Order mapToOrder(OrderDTO order) {
        return new Order(
                order.getId(),
                CartMapper.mapToCartItem(order.getCart()),
                order.getDelivery(),
                order.getPaymentMethod(),
                order.getDate()

        );
    }
}
