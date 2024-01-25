package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.OrderDTO;
import hr.algebra.webshop.entity.Order;
import hr.algebra.webshop.mapper.OrderMapper;
import hr.algebra.webshop.repository.OrderRepository;
import hr.algebra.webshop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        orderRepository.save(OrderMapper.mapToOrder(orderDTO));
    }

    @Override
    public void updateOrder(String id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("order does not exist with a given id"));
        order.setCart(orderDTO.getCart());
        order.setDelivery(orderDTO.getDelivery());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setDate(orderDTO.getDate());
        orderRepository.save(order);
    }

    @Override
    public void deleteCategory(String id) {
        orderRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("order does not exist with a given id"));
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDTO getOrderById(String id) {
        Optional<Order> order = orderRepository.findById(id);

        return order.map(OrderMapper::mapToOrderDTO).orElseThrow(()
                -> new ResourceNotFoundException("order does not exist with a given id"));
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(OrderMapper::mapToOrderDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrdersByUserId(String email) {
        return orderRepository.findAll().stream()
                .filter(order -> email.equals(order.getCart().getUser().getEmail()))
                .map(OrderMapper::mapToOrderDTO)
                .collect(Collectors.toList());
    }
    public List<OrderDTO> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> allOrders = orderRepository.findAll();

        if (startDate != null && endDate != null) {
            return  allOrders.stream()
                    .filter(order -> order.getDate().isAfter(startDate.minusDays(1)) && order.getDate().isBefore(endDate.plusDays(1)))
                    .map(OrderMapper::mapToOrderDTO)
                    .collect(Collectors.toList());
        } else {
            return allOrders.stream().map(OrderMapper::mapToOrderDTO)
                    .collect(Collectors.toList());
        }
    }

}
