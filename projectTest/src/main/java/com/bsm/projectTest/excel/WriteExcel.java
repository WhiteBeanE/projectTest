package com.bsm.projectTest.excel;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	public static void main(String[] args) {
		// 클래스를 사용하여 빈 워크북(엑셀 파일)을 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		// OrderData 이름의 빈 시트를 워크북에 생성
		XSSFSheet sheet = workbook.createSheet("OrderData");
		// Data 생성
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("1", new Object[] {"ID", "NAME", "PHONE", "DATE"});
		data.put("2", new Object[] {1, "White", "010-1234-1234", new Date()});
		data.put("3", new Object[] {2, "Bean", "010-1234-4321", new Date()});
		data.put("4", new Object[] {3, "WhiteBean", "010-1333-1333", new Date()});
		data.put("5", new Object[] {4, "John", "010-1224-1224", new Date()});
		// test
		Set<String> keySet = data.keySet();
		int rownum = 0;
		for(String key : keySet) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = (Object[]) data.get(key);
			int cellnum = 0;
			for(Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if(obj instanceof String) {
					cell.setCellValue((String) obj);
				}else if(obj instanceof Integer){
					cell.setCellValue((Integer) obj);
				}else if(obj instanceof Date) {
					cell.setCellValue((Date) obj);
				}
			}
		}
		try {
//			// 엑셀 파일을 저장하기 위해 URL 클래스를 이용하여 리소스 폴더 내의 OrderData 파일의 경로를 가져옴
//			URL url = ReadExcel.class.getClassLoader().getResource("OrderData");
			// 엑셀 파일을 저장할 경로 지정
		    String outputPath = "C:/Users/SeongMin/Desktop/0601 주문건 모음/OrderData.xlsx";
			// 파일 생성
			FileOutputStream out = new FileOutputStream(outputPath);
			// workbook 내용  파일 작성
			workbook.write(out);
			out.close();
			
			System.out.println("OrderData.xlsx written successfully on disk.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
