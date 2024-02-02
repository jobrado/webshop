package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.OrderDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    void createOrder(OrderDTO OrderDTO);
    void updateOrder(String id, OrderDTO orderDTO);
    void deleteOrder(String id);

    List<OrderDTO> getAllOrders();
    List<OrderDTO> getAllOrdersByUserId(String id);
    List<OrderDTO> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);
}
