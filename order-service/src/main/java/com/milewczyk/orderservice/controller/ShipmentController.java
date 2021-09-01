package com.milewczyk.orderservice.controller;

import com.milewczyk.orderservice.model.Shipment;
import com.milewczyk.orderservice.model.dto.ShipmentDTO;
import com.milewczyk.orderservice.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mateusz Milewczyk, github: agiklo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<Page<ShipmentDTO>> getAllShipments(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getAllShipments(pageable));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ShipmentDTO> getShipmentByName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getShipmentByName(name));
    }

    @PostMapping("/management")
    public ResponseEntity<ShipmentDTO> addNewShipment(@RequestBody Shipment shipment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.addNewShipment(shipment));
    }

    @DeleteMapping("/management/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShipmentById(@RequestParam Long id) {
        shipmentService.deleteShipmentById(id);
    }
}
