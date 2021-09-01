package com.milewczyk.orderservice.service;

import com.milewczyk.orderservice.mapper.ShipmentMapper;
import com.milewczyk.orderservice.model.Shipment;
import com.milewczyk.orderservice.model.dto.ShipmentDTO;
import com.milewczyk.orderservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    public Page<ShipmentDTO> getAllShipments(Pageable pageable) {
        var shipments = shipmentRepository.findAll(pageable).stream()
                .map(shipmentMapper::mapShipmentToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(shipments);
    }

    public ShipmentDTO getShipmentByName(String name) {
        return shipmentMapper.mapShipmentToDTO(
                shipmentRepository.findByNameContains(name));
    }

    public ShipmentDTO addNewShipment(Shipment shipment) {
        var newShipment = shipmentRepository.save(shipment);
        return shipmentMapper.mapShipmentToDTO(newShipment);
    }

    public void deleteShipmentById(Long id) {
        shipmentRepository.deleteById(id);
    }
}
