package com.barclays.acc.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclays.acc.model.AccountTransaction;
import com.barclays.acc.repository.AccountTransactionRepository;

@Service
public class AccountTransactionServiceImp implements AccountTransactionService {
	
	@Autowired
	AccountTransactionRepository accountTransactionRepository;
	
	@Override
	public void addTransaction(int acc1, int acc2, LocalDate transaction_date,String type, float amount) {
		AccountTransaction accountTransaction = AccountTransaction.builder()
				.transactor_account_no(acc1)
				.transactee_account_no(acc2)
				.trn(123254)									//make Trn unique
				.transaction_date(transaction_date)
				.transaction_type(type)
				.amount(amount)
				.build();
		accountTransactionRepository.save(accountTransaction);
		
	}
	@Override
	public List<AccountTransaction> viewTransaction(int acc) {
		List<AccountTransaction> accountTransactions = accountTransactionRepository.findAllTransaction(acc);
		return accountTransactions;
		
	}

}
