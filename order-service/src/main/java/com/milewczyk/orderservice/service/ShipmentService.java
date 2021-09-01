package com.milewczyk.orderservice.service;

import com.milewczyk.orderservice.mapper.ShipmentMapper;
import com.milewczyk.orderservice.model.Shipment;
import com.milewczyk.orderservice.model.dto.ShipmentDTO;
import com.milewczyk.orderservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.stream.Collectors;

/**
 * @author Mateusz Milewczyk, github: agiklo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    private final Principal principal;

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
        log.info("User " + principal.getName() + " created a new shipment " + newShipment.getShipmentId());
        return shipmentMapper.mapShipmentToDTO(newShipment);
    }

    public void deleteShipmentById(Long id) {
        try {
            log.info("User " + principal.getName() + " deleted shipment " + id);
            shipmentRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            log.info("User " + principal.getName() + " failed to delete shipment " + id);
            throw new IllegalArgumentException("Shipment " + id + "does not exist", e);
        }
    }
}
