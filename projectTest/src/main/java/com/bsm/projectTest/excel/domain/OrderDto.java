package com.bsm.projectTest.excel.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {
	private String orderPlatform;	//7
	private Date orderDate;			//57
	private String orderNumber;		//1
	private String productCode;		//37:19
	private String productName;		//16
	private String option;			//18
	private int quantity;			//20
	private String buyer;			//8
	private String buyerNumber;		//43
	private String recipient;		//10
	private String recipientNumber;	//40
	private String address;			//42
	private String postalCode;		//44
	private String message;			//45
	private String personalCustomsNumber;	//56
	private int productPaymentAmount;		//25
	private int customerPaymentShippingFee;	//34+35
	private int settlementAmount;	//53
}
