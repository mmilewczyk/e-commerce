package com.milewczyk.orderservice.mapper;

import com.milewczyk.orderservice.model.Shipment;
import com.milewczyk.orderservice.model.dto.ShipmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    ShipmentDTO mapShipmentToDTO(Shipment shipment);
}
