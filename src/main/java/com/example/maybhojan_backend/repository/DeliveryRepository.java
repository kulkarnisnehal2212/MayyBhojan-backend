package com.example.maybhojan_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.maybhojan_backend.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

    List<Delivery> findByDeliveryPartnerIdAndStatusNot(Long partnerId,String status);

    Delivery findByOrderId(Long orderId);

	List<Delivery> findByDeliveryPartnerId(Long partnerId);

}