package com.bsm.projectTest.excel.domain;

import lombok.Data;

@Data
public class OrderDto {
	private String orderPlatform;
	private String orderDate;
	private String orderNumber;
	private String productCode;
	private String productName;
	private String option;
	private int quantity;
	private String buyer;
	private String buyerNumber;
	private String recipient;
	private String recipientNumber;
	private String address;
	private String postalCode;
	private String message;
	private String personalCustomsNumber;
	private int productPaymentAmount;
	private double customerPaymentShippingFee;
	private double settlementAmount;
}
