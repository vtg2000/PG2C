package com.barclays.acc.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="transactions")
@Entity
public class AccountTransaction {
	@Id
//	@SequenceGenerator(name = "transactionID",schema = "transactionID",initialValue = 1)
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionid;
	private int transactoraccountno;
	private int transacteeaccountno;
//	@SequenceGenerator(name = "trasactionID",schema = "transactionID",initialValue = 1,allocationSize = 1)
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int trn;
	private LocalDate transactiondate;
	private String transactiontype;
	private float amount;
	
}
