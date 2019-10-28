package com.cursoservicesweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.cursoservicesweb.curso.dto.OrderItemDTO;
import com.cursoservicesweb.curso.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursoservicesweb.curso.dto.OrderDTO;
import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.entities.User;
import com.cursoservicesweb.curso.repositories.OrderRepository;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AuthService authService;

    public List<OrderDTO> findAll() {
        List<Order> list = repository.findAll();
        return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Optional<Order> obj = repository.findById(id);
        Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        authService.validateOwrOrderAdmin(entity);
        return new OrderDTO(entity);
    }

    public List<OrderDTO> findByClient() {
        User user = authService.authenticad();
        List<Order> listOrder = repository.findByClient(user);
        return listOrder.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderItemDTO> findItems(Long id) {
        Order order = repository.getOne(id);
        authService.validateOwrOrderAdmin(order);

        Set<OrderItem> set = order.getOrderItems();

        return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());

    }
}
