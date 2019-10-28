package com.cursoservicesweb.curso.dto;

import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.entities.Payment;

import java.io.Serializable;
import java.time.Instant;

public class PaymentDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    private Long id;
    private Instant moment;
    private Long  orderId;

    public PaymentDTO(){

    }

    public PaymentDTO(Long id, Instant moment, Long orderId) {
        this.id = id;
        this.moment = moment;
        this.orderId = orderId;
    }

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.moment = payment.getMoment();
        this.orderId = payment.getOrder().getId();
    }

    public Payment toEntity(){
        Order order = new Order(orderId,null,null,null);

      return new Payment(id,moment,order);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
