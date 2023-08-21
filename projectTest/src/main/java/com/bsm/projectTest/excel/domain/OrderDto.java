package com.bsm.projectTest.excel.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {				//네이버 				지옥션	쿠팡
	private String orderPlatform;	//7					0		x
	private Date orderDate;			//57				36		9
	private String orderNumber;		//1					2		2
	private String productCode;		//37:19				16		16
	private String productName;		//16				6		10
	private String option;			//18				8		11
	private int quantity;			//20				7		22
	private String buyer;			//8					3		24
	private String buyerNumber;		//43				18		25
	private String recipient;		//10				20		26
	private String recipientNumber;	//40				21		36
	private String address;			//42				25		29
	private String postalCode;		//44				24		28
	private String message;			//45				26		30
	private String personalCustomsNumber;	//56		23		35
	private int productPaymentAmount;		//25		15		18
	private int customerPaymentShippingFee;	//34+35		28		20+21
	private int settlementAmount;	//53				38		
}
