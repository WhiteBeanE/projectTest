package com.bsm.projectTest.excel.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {				//네이버 				지옥션
	private String orderPlatform;	//7					0
	private Date orderDate;			//57				36
	private String orderNumber;		//1					2
	private String productCode;		//37:19				16
	private String productName;		//16				6
	private String option;			//18				8
	private int quantity;			//20				7
	private String buyer;			//8					3
	private String buyerNumber;		//43				18
	private String recipient;		//10				20
	private String recipientNumber;	//40				21
	private String address;			//42				25
	private String postalCode;		//44				24
	private String message;			//45				26
	private String personalCustomsNumber;	//56		23
	private int productPaymentAmount;		//25		15
	private int customerPaymentShippingFee;	//34+35		28
	private int settlementAmount;	//53				38
}
