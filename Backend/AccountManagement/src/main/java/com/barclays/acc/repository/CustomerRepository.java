package com.barclays.acc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barclays.acc.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	public List<Customer> findByPanno(String panno); 
	public List<Customer> findByUserid(int userid);
}
