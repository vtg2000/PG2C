package com.barclays.acc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.acc.model.Account;
import com.barclays.acc.repository.AccountRepository;
import com.barclays.acc.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public List<Account> viewAccounts(int customerID) {
		return accountRepository.findByCustomerID(customerID);
	}

}