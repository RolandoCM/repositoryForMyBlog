package com.org.coop.retail.servicehelper;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.coop.canonical.account.beans.AdvanceRegisterBean;
import com.org.coop.canonical.account.beans.CardRegisterBean;
import com.org.coop.canonical.account.beans.CashRegisterBean;
import com.org.coop.canonical.account.beans.ChequeRegisterBean;
import com.org.coop.canonical.account.beans.CreditRegisterBean;
import com.org.coop.canonical.account.beans.TransactionPaymentBean;
import com.org.coop.retail.bs.mapper.PaymentMappingImpl;
import com.org.coop.retail.entities.AdvanceRegister;
import com.org.coop.retail.entities.CardRegister;
import com.org.coop.retail.entities.CashRegister;
import com.org.coop.retail.entities.ChequeRegister;
import com.org.coop.retail.entities.CreditRegister;
import com.org.coop.retail.entities.TransactionPayment;
import com.org.coop.retail.repositories.AdvanceRegisterRepository;
import com.org.coop.retail.repositories.CardRegisterRepository;
import com.org.coop.retail.repositories.CashRegisterRepository;
import com.org.coop.retail.repositories.ChequeRegisterRepository;
import com.org.coop.retail.repositories.CreditRegisterRepository;
import com.org.coop.retail.repositories.TransactionPaymentRepository;

@Service
public class PaymentServiceHelperImpl {

	private static final Logger log = Logger.getLogger(PaymentServiceHelperImpl.class); 
	
	@Autowired
	private PaymentMappingImpl paymentMappingImpl;
	
	@Autowired
	private CashRegisterRepository cashRegisterRepository;
	
	@Autowired
	private CardRegisterRepository cardRegisterRepository;
	
	@Autowired
	private CreditRegisterRepository creditRegisterRepository;
	
	@Autowired
	private ChequeRegisterRepository chequeRegisterRepository;
	
	@Autowired
	private AdvanceRegisterRepository advanceRegisterRepository;
	
	@Autowired
	private TransactionPaymentRepository transactionPaymentRepository;
	
	/**
	 *
	/* Make an entry into transaction_payment table for all modes of payment (CASH/CARD/Advance) etc.
	 * Then enter card payment detail into corresponding table like cash_register, card_register,
	 * advance_register, cheque_register, credit_register
	 * @param paymentBean
	 */
	@Transactional(value="retailTransactionManager")
	public void makePayment(TransactionPaymentBean paymentBean) {
		//*********************************
		// CASH PAYMENT
		//*********************************
		if(paymentBean.getCashRegisters() != null && paymentBean.getCashRegisters().size() > 0) {
			if(log.isDebugEnabled()) {
				log.debug("Making payment by CASH");
			}
			for(CashRegisterBean cashRegisterBean : paymentBean.getCashRegisters()) {
				cashRegisterBean.setGlTranId(paymentBean.getGlTranId());
				
				TransactionPayment payment = null;
				if(cashRegisterBean.getPaymentId() == 0) {
					payment = new TransactionPayment(); 
				} else {
					payment = transactionPaymentRepository.findOne(cashRegisterBean.getPaymentId());
				}
				
				paymentMappingImpl.mapCashPaymentBean(cashRegisterBean, payment);
				transactionPaymentRepository.saveAndFlush(payment);
				
				cashRegisterBean.setPaymentId(payment.getPaymentId());
				
				int cashId = cashRegisterBean.getCashId();
				CashRegister cash = null;
				if(cashId == 0) {
					cash = new CashRegister();
				} else {
					cash = cashRegisterRepository.findOne(cashId);
				}
				
				paymentMappingImpl.mapCashPaymentDtlBean(cashRegisterBean, cash);
				cashRegisterRepository.saveAndFlush(cash);
				cashRegisterBean.setCashId(cash.getCashId());
			}
		}
		
		//*********************************
		// CARD PAYMENT
		//*********************************
		if(paymentBean.getCardRegisters() != null && paymentBean.getCardRegisters().size() > 0) {
			if(log.isDebugEnabled()) {
				log.debug("Making payment by CARD");
			}
			for(CardRegisterBean cardRegisterBean : paymentBean.getCardRegisters()) {
				cardRegisterBean.setGlTranId(paymentBean.getGlTranId());

				TransactionPayment payment = null;
				if(cardRegisterBean.getPaymentId() == 0) {
					payment = new TransactionPayment(); 
				} else {
					payment = transactionPaymentRepository.findOne(cardRegisterBean.getPaymentId());
				}
				paymentMappingImpl.mapCardPaymentBean(cardRegisterBean, payment);
				transactionPaymentRepository.saveAndFlush(payment);
				
				cardRegisterBean.setPaymentId(payment.getPaymentId());
				
				int cardId = cardRegisterBean.getCardId();
				CardRegister card = null;
				if(cardId == 0) {
					card = new CardRegister();
				} else {
					card = cardRegisterRepository.findOne(cardId);
				}
				paymentMappingImpl.mapCardPaymentDtlBean(cardRegisterBean, card);
				cardRegisterRepository.saveAndFlush(card);
				cardRegisterBean.setCardId(card.getCardId());
			}
		}
		
		//*********************************
		// CREDIT PAYMENT
		//*********************************
		if(paymentBean.getCreditRegisters() != null && paymentBean.getCreditRegisters().size() > 0) {
			if(log.isDebugEnabled()) {
				log.debug("Making payment by CREDIT");
			}
			for(CreditRegisterBean creditRegisterBean : paymentBean.getCreditRegisters()) {
				creditRegisterBean.setGlTranId(paymentBean.getGlTranId());
				TransactionPayment payment = null;
				if(creditRegisterBean.getPaymentId() == 0) {
					payment = new TransactionPayment(); 
				} else {
					payment = transactionPaymentRepository.findOne(creditRegisterBean.getPaymentId());
				}
				paymentMappingImpl.mapCreditPaymentBean(creditRegisterBean, payment);
				transactionPaymentRepository.saveAndFlush(payment);
				
				creditRegisterBean.setPaymentId(payment.getPaymentId());
				
				int creditPaymentId = creditRegisterBean.getCreditPaymentId();
				CreditRegister credit = null;
				if(creditPaymentId == 0) {
					credit = new CreditRegister();
				} else {
					credit = creditRegisterRepository.findOne(creditPaymentId);
				}
				paymentMappingImpl.mapCreditPaymentDtlBean(creditRegisterBean, credit);
				creditRegisterRepository.saveAndFlush(credit);
				creditRegisterBean.setCreditPaymentId(credit.getCreditId());
			}
		}
		
		//*********************************
		// CHEQUE PAYMENT
		//*********************************
		if(paymentBean.getChequeRegisters() != null && paymentBean.getChequeRegisters().size() > 0) {
			if(log.isDebugEnabled()) {
				log.debug("Making payment by CHEQUE");
			}
			for(ChequeRegisterBean chequeRegisterBean : paymentBean.getChequeRegisters()) {
				chequeRegisterBean.setGlTranId(paymentBean.getGlTranId());
				TransactionPayment payment = null;
				if(chequeRegisterBean.getPaymentId() == 0) {
					payment = new TransactionPayment(); 
				} else {
					payment = transactionPaymentRepository.findOne(chequeRegisterBean.getPaymentId());
				}
				paymentMappingImpl.mapChequePaymentBean(chequeRegisterBean, payment);
				transactionPaymentRepository.saveAndFlush(payment);
				chequeRegisterBean.setPaymentId(payment.getPaymentId());
				
				int chequeId = chequeRegisterBean.getChequeId();
				ChequeRegister cheque = null;
				if(chequeId == 0) {
					cheque = new ChequeRegister();
				} else {
					cheque = chequeRegisterRepository.findOne(chequeId);
				}
				paymentMappingImpl.mapChequePaymentDtlBean(chequeRegisterBean, cheque);
				chequeRegisterRepository.saveAndFlush(cheque);
				chequeRegisterBean.setChequeId(cheque.getChequeId());
			}
		}
		
		//*********************************
		// ADVANCE PAYMENT
		//*********************************
		if(paymentBean.getAdvanceRegisters() != null && paymentBean.getAdvanceRegisters().size() > 0) {
			if(log.isDebugEnabled()) {
				log.debug("Making payment by ADVANCE");
			}
			for(AdvanceRegisterBean advanceRegisterBean : paymentBean.getAdvanceRegisters()) {
				advanceRegisterBean.setGlTranId(paymentBean.getGlTranId());

				TransactionPayment payment = null;
				if(advanceRegisterBean.getPaymentId() == 0) {
					payment = new TransactionPayment(); 
				} else {
					payment = transactionPaymentRepository.findOne(advanceRegisterBean.getPaymentId());
				}
				
				paymentMappingImpl.mapAdvancePaymentBean(advanceRegisterBean, payment);
				transactionPaymentRepository.saveAndFlush(payment);
				
				advanceRegisterBean.setPaymentId(payment.getPaymentId());
				
				int advanceId = advanceRegisterBean.getAdvanceId();
				AdvanceRegister advance = null;
				if(advanceId == 0) {
					advance = new AdvanceRegister();
				} else {
					advance = advanceRegisterRepository.findOne(advanceId);
				}
				
				paymentMappingImpl.mapAdvancePaymentDtlBean(advanceRegisterBean, advance);
				advanceRegisterRepository.saveAndFlush(advance);
				advanceRegisterBean.setAdvanceId(advance.getAdvanceId());
			}
		}
	}
	
	@Transactional(value="retailTransactionManager")
	public void getPayment(int branchId, int glTranId) {
		List<TransactionPayment> payment = transactionPaymentRepository.findByBranchIdAndGlTranId(branchId, glTranId);
	}
}
