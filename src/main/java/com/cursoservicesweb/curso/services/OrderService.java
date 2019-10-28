package com.cursoservicesweb.curso.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.cursoservicesweb.curso.dto.CategoryDTO;
import com.cursoservicesweb.curso.dto.OrderItemDTO;
import com.cursoservicesweb.curso.entities.*;
import com.cursoservicesweb.curso.entities.enums.OrderStatus;
import com.cursoservicesweb.curso.repositories.OrderItemRepository;
import com.cursoservicesweb.curso.repositories.ProductRepository;
import com.cursoservicesweb.curso.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursoservicesweb.curso.dto.OrderDTO;
import com.cursoservicesweb.curso.repositories.OrderRepository;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

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

	@Transactional(readOnly = true)
	public List<OrderDTO> findByClientId(Long clientId) {
		User client = userRepository.getOne(clientId);
		List<Order> listOrder = repository.findByClient(client);
		return listOrder.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO placeOrder(List<OrderItemDTO> dto) {
        User client = authService.authenticad();
        Order order = new Order(null, Instant.now(),client, OrderStatus.WAITING_PAYMENT);

        for(OrderItemDTO itemDTO : dto){
            Product product = productRepository.getOne(itemDTO.getProductId()) ;
            OrderItem item = new OrderItem(order,product,itemDTO.getQuantity(),itemDTO.getPrice());

            order.getOrderItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        return new OrderDTO(order);

	}

    @Transactional
    public OrderDTO update(Long id , OrderDTO obj) {
        try {
            Order entity = repository.getOne(id);
            entity.setOrderStatus(obj.getOrderStatus());

            entity =  repository.save(entity);
            return new OrderDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

}
