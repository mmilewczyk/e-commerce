package com.milewczyk.cartservice.service;

import com.milewczyk.cartservice.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
}
