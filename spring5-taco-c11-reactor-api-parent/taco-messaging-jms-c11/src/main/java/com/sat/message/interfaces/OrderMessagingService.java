package com.sat.message.interfaces;

import com.sat.tacos.Order;

public interface OrderMessagingService {
	
	void sendOrder(Order order);
}
