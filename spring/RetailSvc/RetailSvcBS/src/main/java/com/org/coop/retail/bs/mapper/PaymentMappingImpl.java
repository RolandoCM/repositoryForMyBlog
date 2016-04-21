package com.org.coop.retail.bs.mapper;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.coop.retail.bs.config.RetailDozerConfig;

@Component
public class PaymentMappingImpl {
	@Autowired
	private RetailDozerConfig retailDozerConfig;
	
	public void mapCashPaymentBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "cashPaymentMap");
	}
	public void mapCardPaymentBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "cardPaymentMap");
	}
	
	public void mapCreditPaymentBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "creditPaymentMap");
	}
	
	public void mapChequePaymentBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "chequePaymentMap");
	}
	
	public void mapAdvancePaymentBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "advancePaymentMap");
	}
	
	public void mapCashPaymentDtlBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "cashPaymentDtlMap");
	}
	
	public void mapCardPaymentDtlBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "cardPaymentDtlMap");
	}
	
	public void mapCreditPaymentDtlBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "creditPaymentDtlMap");
	}
	
	public void mapChequePaymentDtlBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "chequePaymentDtlMap");
	}
	
	public void mapAdvancePaymentDtlBean(Object src, Object dest) {
		Mapper dozerBeanMapper = (Mapper) retailDozerConfig.dozerBean();
		dozerBeanMapper.map(src, dest, "advancePaymentDtlMap");
	}
}