package com.sat.data.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.sat.bean.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
