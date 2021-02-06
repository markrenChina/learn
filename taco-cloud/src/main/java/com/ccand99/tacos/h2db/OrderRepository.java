package com.ccand99.tacos.h2db;

import com.ccand99.tacos.model.Order;

public interface OrderRepository {
    
    Order save(Order order);
}
