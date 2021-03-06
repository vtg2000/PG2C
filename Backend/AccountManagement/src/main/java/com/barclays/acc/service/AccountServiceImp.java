package com.barclays.acc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barclays.acc.model.Account;
import com.barclays.acc.model.AccountTransaction;
import com.barclays.acc.repository.AccountRepository;
@Service
public class AccountServiceImp implements AccountService{

	@Autowired
	AccountTransactionService accountTransactionService;

	@Autowired
	AccountRepository accountrepository;
	
	public static Logger logger = Logger.getLogger(AccountServiceImp.class);
	public boolean checkamount(int amount) {
		if(amount < 0) {
			logger.error("Encountered an error.. Amount is Invalid");
			return false;
		}
		else
			return true;
	}
	
	@Override
	@Transactional
	@Modifying
	public void fundTransfer(int a1,int a2, int amount)throws ArithmeticException{
		if(!checkamount(amount)) {
			throw new ArithmeticException("Amount invalid");
		}
		
		logger.info("Transfering amount "+amount+" from account "+a1+" to account "+a2);
		Account acc1 =  accountrepository.findById(a1).get();
		Account acc2 =  accountrepository.findById(a2).get();
		if(acc1.getBalance() > amount) {
			acc1.setBalance(acc1.getBalance()-amount);
			acc2.setBalance(acc2.getBalance()+amount);
			accountrepository.save(acc1);
			accountrepository.save(acc2);
			logger.debug("Account tables successfully updated");
			accountTransactionService.addTransaction(a1, a2,LocalDateTime.now(), "debit", (float)amount);
		}
		else
		{
			logger.error("Encountered an error.. Unable to proceed with the transaction due to insufficient balance");
			throw new ArithmeticException();
		}
	}

	@Override
	public int checkBalance(int accountno)throws NullPointerException {
		logger.info("Retreiving account balance for account no "+accountno);
		Account acc =  accountrepository.findById(accountno).get();
		return acc.getBalance();
	}

	@Override
	@Transactional
	@Modifying
	public void addMoney(int accountno,int amount)throws ArithmeticException {

		if(!checkamount(amount)) {
			throw new ArithmeticException("Amount invalid");
		}
		Account acc = accountrepository.findById(accountno).get();
		acc.setBalance(acc.getBalance()+amount);
		accountrepository.save(acc);
		accountTransactionService.addTransaction(accountno, accountno,LocalDateTime.now(), "credit", (float)amount);
		logger.info("Successfully deposited amount "+amount+" in account no "+accountno);
	}

	@Override
	@Transactional
	@Modifying
	public void withdrawMoney(int accountno,int amount) throws ArithmeticException{

		if(!checkamount(amount)) {
			throw new ArithmeticException("Amount invalid");
		}
		Account acc = accountrepository.findById(accountno).get();
			int totalwithdrawned = accountTransactionService.totalAmountWithdrawned(accountno, LocalDate.now());
			if((totalwithdrawned < 10000) && ((totalwithdrawned+amount)<10000)) {
			if(acc.getBalance() > amount) {
			acc.setBalance(acc.getBalance() - amount);
			accountrepository.save(acc);
			accountTransactionService.addTransaction(accountno, accountno,LocalDateTime.now(), "debit", (float)amount);
			logger.info("Successfully withdrew amount "+amount+" from account no "+accountno);
			}
			else {
				logger.error("Cannot withdraw amount "+amount +" from account no "+accountno+" due to insufficient balance");
				throw new ArithmeticException("Cannot withdraw amount "+amount +" from account no "+accountno+" due to insufficient balance");
		} 
			}
			else {
				logger.error("Cannot withdraw amount "+amount +" from account no "+accountno+" due to Limit Reached");
				throw new ArithmeticException("Cannot withdraw amount "+amount +" from account no "+accountno+" due to Limit Reached");
			}
	}

	@Override
	public List<AccountTransaction> viewTransactions(int acc)throws ArithmeticException {
		logger.info("Retrieving recent transactions from the user account "+acc);
	 List<AccountTransaction> accountTransactions = accountTransactionService.viewTransaction(acc);
	return accountTransactions;
		
	}

	@Override
	public List<AccountTransaction> exportTransactions(int acc,LocalDate startdate,LocalDate enddate)throws ArithmeticException {
		logger.info("Exporting transactions for the user account "+acc+" from "+startdate+" to "+enddate);
		 List<AccountTransaction> accountTransactions = accountTransactionService.exportTransaction(acc,startdate,enddate);
		 return accountTransactions;
	
	}
	
}

