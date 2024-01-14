package hr.algebra.webshop.service;


import hr.algebra.webshop.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDTO OrderDTO);
    void updateOrder(String id, OrderDTO orderDTO);
    void deleteCategory(String id);
    OrderDTO getOrderById(String id);
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getAllOrdersByUserId(String id);
}
