package com.example.maybhojan_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name="delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long deliveryPartnerId;

    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getDeliveryPartnerId() {
		return deliveryPartnerId;
	}

	public void setDeliveryPartnerId(Long deliveryPartnerId) {
		this.deliveryPartnerId = deliveryPartnerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}