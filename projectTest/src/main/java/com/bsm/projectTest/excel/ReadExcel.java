package com.bsm.projectTest.excel;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) {
		try {
			FileInputStream file = new FileInputStream("C:/Users/SeongMin/Desktop/0601 주문건 모음/" + "스마트스토어_선택주문발주발송관리_20210602_1859.xlsx");
			String orderPlatform = "스마트스토어";
			
			// POI 라이브러리의 IOUtils 클래스를 사용하여 최대 바이트 배열 크기를 설정
			IOUtils.setByteArrayMaxOverride(Integer.MAX_VALUE);
			// XSSFWorkbook 클래스를 사용하여 엑셀 워크북을 생성
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// 워크북에서 첫 번째 시트를 가져옴
			XSSFSheet sheet = workbook.getSheetAt(0);
			// 시트의 각 행을 반복하는 반복자를 생성
			Iterator<Row> rowIterator = sheet.iterator();
			
			if (rowIterator.hasNext()) {
                rowIterator.next();
                rowIterator.next();
            }
			while(rowIterator.hasNext()) {
				// 행을 하나씩 가져옴
				Row row = rowIterator.next();
				// 행의 각 셀을 반복하는 반복자를 생성
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					
//					System.out.println(cell.getCellType() + "\t");
					switch (cell.getCellType()) {
					case NUMERIC:	// 숫자 타입의 셀일 경우, 숫자 값을 출력
						System.out.print(cell.getNumericCellValue() + "\t");
						break;
					case STRING:	// 문자열 타입의 셀일 경우, 문자열 값을 출력
						System.out.print(cell.getStringCellValue() + "\t");
						break;
					case BLANK:
						System.out.print("null \t");
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + cell.getCellType());
					}
				}
				System.out.println();
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
