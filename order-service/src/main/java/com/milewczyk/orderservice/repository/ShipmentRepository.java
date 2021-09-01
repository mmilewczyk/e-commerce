package com.milewczyk.orderservice.repository;

import com.milewczyk.orderservice.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Shipment findByNameContains(String name);
}
