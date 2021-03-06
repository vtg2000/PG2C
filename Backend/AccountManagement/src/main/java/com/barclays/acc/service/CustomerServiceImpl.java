package com.barclays.acc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.acc.model.Account;
import com.barclays.acc.repository.AccountRepository;
import com.barclays.acc.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	public static Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	
	@Override
	public List<Account> viewAccounts(int customerid) {
		logger.info("Retrieving User Accounts for customer " + customerid );
		return accountRepository.findByCustomerid(customerid);
	}

}
