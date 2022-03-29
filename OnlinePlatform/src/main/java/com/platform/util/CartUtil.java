package com.platform.util;

import com.platform.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartUtil {

    @Autowired
    private CartRepository cartRepository;

    public int newCartId(){
        return cartRepository.findMaxId() + 1;
    }
}