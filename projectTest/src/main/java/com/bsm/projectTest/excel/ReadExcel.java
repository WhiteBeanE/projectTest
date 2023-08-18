package com.bsm.projectTest.excel;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bsm.projectTest.excel.domain.OrderDto;

public class ReadExcel {
	public static void main(String[] args) {
		try {
//			FileInputStream file = new FileInputStream("C:/Users/SeongMin/Desktop/0601 주문건 모음/" + "스마트스토어_선택주문발주발송관리_20210602_1859.xlsx");
			FileInputStream file = new FileInputStream("C:/Users/SeongMin/Desktop/0601 주문건 모음/" + "NewOrder_2021-06-02 19_00.xls");
			String orderPlatform = "지옥션";
			// Workbook workbook = new HSSFWorkbook(new FileInputStream("파일경로.xls"));
			// POI 라이브러리의 IOUtils 클래스를 사용하여 최대 바이트 배열 크기를 설정
			IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
			
			Workbook workbook = new HSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);
			
//			// XSSFWorkbook 클래스를 사용하여 엑셀 워크북을 생성
//			XSSFWorkbook workbook = new XSSFWorkbook(file);
//			// 워크북에서 첫 번째 시트를 가져옴
//			XSSFSheet sheet = workbook.getSheetAt(0);
			
			// 각 행을 반복하여 데이터 추출
//          for (Row row : sheet) {
//              Cell cell = row.getCell(targetColumnIndex);
//
//              if (cell != null) {
//                  String cellValue = cell.toString();
//                  System.out.println("Extracted Value: " + cellValue);
//              }
//			}
			int startRow = 1;
			for(int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if(orderPlatform.equals("스마트스토어")) {
					OrderDto orderDto = new OrderDto();
					orderDto.setOrderPlatform(row.getCell(7).toString());
					orderDto.setOrderDate((row.getCell(57).getDateCellValue()));
					orderDto.setOrderNumber(row.getCell(1).toString());
					orderDto.setProductCode(row.getCell(37).toString() + ":" + row.getCell(19).toString());
					orderDto.setProductName(row.getCell(16).toString());
					orderDto.setOption(row.getCell(18).toString());
					orderDto.setQuantity((int)row.getCell(20).getNumericCellValue());
					orderDto.setBuyer(row.getCell(8).toString());
					orderDto.setBuyerNumber(row.getCell(43).toString());
					orderDto.setRecipient(row.getCell(10).toString());
					orderDto.setRecipientNumber(row.getCell(40).toString());
					orderDto.setAddress(row.getCell(42).toString());
					orderDto.setPostalCode(row.getCell(44).toString());
					orderDto.setMessage(row.getCell(45) == null ? "" : row.getCell(45).getStringCellValue());
					orderDto.setPersonalCustomsNumber(row.getCell(56).toString());
					orderDto.setProductPaymentAmount((int)row.getCell(25).getNumericCellValue());
					orderDto.setCustomerPaymentShippingFee((int)row.getCell(34).getNumericCellValue() + (int)row.getCell(35).getNumericCellValue());
					orderDto.setSettlementAmount((int)row.getCell(53).getNumericCellValue());
					System.out.println(orderDto);
				}else if(orderPlatform.equals("지옥션")) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					OrderDto orderDto = new OrderDto();
					orderDto.setOrderPlatform(row.getCell(0).toString().substring(0, row.getCell(0).toString().indexOf("(")));
					orderDto.setOrderDate(dateFormat.parse(row.getCell(36).toString()));
					orderDto.setOrderNumber(row.getCell(2).toString());
					orderDto.setProductCode(row.getCell(16).toString());
					orderDto.setProductName(row.getCell(6).toString());
					orderDto.setOption(row.getCell(8) == null ? "" : row.getCell(8).getStringCellValue());
					orderDto.setQuantity((int)row.getCell(7).getNumericCellValue());
					orderDto.setBuyer(row.getCell(3).toString());
					orderDto.setBuyerNumber(row.getCell(18).toString());
					orderDto.setRecipient(row.getCell(20).toString());
					orderDto.setRecipientNumber(row.getCell(21).toString());
					orderDto.setAddress(row.getCell(25).toString());
					orderDto.setPostalCode(row.getCell(24).toString());
					orderDto.setMessage(row.getCell(26) == null ? "" : row.getCell(26).getStringCellValue());
					orderDto.setPersonalCustomsNumber(row.getCell(23).toString());
					orderDto.setProductPaymentAmount(Integer.parseInt(row.getCell(15).toString().replaceAll(",", "")));
					orderDto.setCustomerPaymentShippingFee(Integer.parseInt(row.getCell(28).toString().replaceAll(",", "")));
					orderDto.setSettlementAmount(Integer.parseInt(row.getCell(38).toString().replaceAll(",", "")));
					System.out.println(orderDto);
				}
//				Iterator<Cell> cellIterator = row.cellIterator();
//				while(cellIterator.hasNext()) {
//					Cell cell = cellIterator.next();
//					
//					switch (cell.getCellType()) {
//					case NUMERIC:	// 숫자 타입의 셀일 경우, 숫자 값을 출력
//						System.out.print(cell.getNumericCellValue() + "\t");
//						break;
//					case STRING:	// 문자열 타입의 셀일 경우, 문자열 값을 출력
//						System.out.print(cell.getStringCellValue() + "\t");
//						break;
//					case BLANK:
//						System.out.print("null \t");
//						break;
//					default:
//						throw new IllegalStateException("Unexpected value: " + cell.getCellType());
//					}
//				}
				System.out.println();
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
