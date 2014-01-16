package com.isales.print;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.alibaba.fastjson.JSON;
import com.isales.print.model.Boutique;
import com.isales.print.model.CarInfo;
import com.isales.print.model.ClientInfo;
import com.isales.print.model.Mortgage;
import com.isales.print.model.Replacement;
import com.isales.print.model.ShopInfo;

public class IsalesPrintUtils {

	public static String writeXls(PrintData printData) {
		try {
			String fileName = UUID.randomUUID().toString();
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dir = Config.getPrintDir();
			String path = sdf.format(date);
			path = String.format("%s/%s/%s.xls", dir, path, fileName);
			File file = new File(path);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			com.isales.print.model.PrintBean printBean = printData.getData();
			FileOutputStream out = new FileOutputStream(file);
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet  = workbook.createSheet("Isales商谈备忘");
			
			HSSFCellStyle borderStyle = workbook.createCellStyle();  
			borderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
			borderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
			borderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
			borderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			sheet.setDefaultColumnStyle(0, borderStyle);
			sheet.setDefaultColumnStyle(1, borderStyle);
			sheet.setDefaultColumnStyle(2, borderStyle);
			sheet.setDefaultColumnStyle(3, borderStyle);
			sheet.setDefaultColumnStyle(4, borderStyle);
			sheet.setDefaultColumnStyle(5, borderStyle);
			sheet.setColumnWidth(0, 15*256);
			sheet.setColumnWidth(1, 15*256);
			sheet.setColumnWidth(2, 15*256);
			sheet.setColumnWidth(3, 15*256);
			sheet.setColumnWidth(4, 15*256);
			sheet.setColumnWidth(5, 15*256);
			//字体样式  
	        HSSFFont font = workbook.createFont();  
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	          
	        //表格样式  
	        HSSFCellStyle cellStyle = workbook.createCellStyle();  
	        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//指定单元格垂直居中对齐  
	        cellStyle.setWrapText(true);//指定单元格自动换行  
	        cellStyle.setFont(font);
	        
	        //表格样式  
	        HSSFCellStyle hCellStyle = workbook.createCellStyle();
	        hCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
	        hCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        hCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//指定单元格垂直居中对齐  
	        hCellStyle.setWrapText(true);//指定单元格自动换行  
	        hCellStyle.setFont(font);
	        
	        //第一行：标题
	        HSSFRow titleRow = createRow(sheet, 0, 0, 5, "东风日产" + printData.getData().getShopinfo().getShopname() + "专营店商谈备忘", cellStyle);
	        titleRow.setHeightInPoints(25f);//设置行高度  
	        
	        //第二行：空行
			createNullRow(sheet, 1, 1, 0, 5);
			
			//第三行：客户信息
	        createRow(sheet, 2, 0, 5, "客户信息", hCellStyle);
	        
	        ClientInfo clientInfo = null;
	        if(printData.getData() != null && printData.getData().getClientinfo() != null ) {
	        	clientInfo = printData.getData().getClientinfo();
	        } else {
	        	clientInfo = new ClientInfo();
	        }
	        
	        ShopInfo shopinfo = null;
	        if(printData != null && printData.getData().getShopinfo() != null) {
	        	shopinfo = printData.getData().getShopinfo();
	        } else {
	        	shopinfo = new ShopInfo();
	        }
	        
	        //第四行：客户信息
	        HSSFRow row3 = sheet.createRow(3);
			createCell(row3, 0, "姓名");
			createCell(row3, 1, clientInfo.getClientname());
			createCell(row3, 2, "手机");
			createCell(row3, 3, clientInfo.getClientphone());
			createCell(row3, 4, "来店日期");
			createCell(row3, 5, shopinfo.getDate());
			
			//第五行：空行
			createNullRow(sheet, 4, 4, 0, 5);
			
			CarInfo carinfo = null;
	        if(printData != null && printData.getData().getCarinfo() != null) {
	        	carinfo = printData.getData().getCarinfo();
	        } else {
	        	carinfo = new CarInfo();
	        }
			//第6行：购车意向
			createRow(sheet, 5, 0, 5, "购车意向", hCellStyle);
			HSSFRow row6 = sheet.createRow(6);
			createCell(row6, 0, "意向车型");
			createCell(row6, 1, carinfo.getCartype());
			createCell(row6, 2, "用车需求");
			createCell(row6, 3, carinfo.getCarrequire());
			createCell(row6, 4, "计划买车时间");
			createCell(row6, 5, carinfo.getCardate());
			
			HSSFRow row7 = sheet.createRow(7);
			createCell(row7, 0, "全包价");
			createCell(row7, 1, carinfo.getAllprice());
			createCell(row7, 2, "指导价");
			createCell(row7, 3, carinfo.getGuideprice());
			createCell(row7, 4, "净车价");
			createCell(row7, 5, carinfo.getNoprice());
			
			HSSFRow row8 = sheet.createRow(8);
			createCell(row8, 0, "保险");
			createCell(row8, 1, carinfo.getSafeprice());
			createCell(row8, 2, "购置税");
			createCell(row8, 3, carinfo.getDuty());
			createCell(row8, 4, "上牌费");
			createCell(row8, 5, carinfo.getCost());
			
	        //第10行：空行
			createNullRow(sheet, 9, 9, 0, 5);
			
	        //第11行：精品选购
			createRow(sheet, 10, 0, 5, "精品选购", hCellStyle);
	        
	        HSSFRow th11Row = sheet.createRow(11);
	        createCell(th11Row, 0, "类别");
	        createCell(th11Row, 1, "名称");
	        createCell(th11Row, 2, "单价");
	        createCell(th11Row, 3, "数量");
	        createCell(th11Row, 4, "金额");
	        
	        int bCount = printBean.getBoutique().size();
	        List<Boutique> list = printBean.getBoutique();
	        int cRowNum = 12;
	        for(int i = 0; i < bCount; i ++) {
	        	HSSFRow row = sheet.createRow(cRowNum ++);
	        	createCell(row, 0, list.get(i).getType());
		        createCell(row, 1, list.get(i).getName());
		        createCell(row, 2, list.get(i).getPrice());
		        createCell(row, 3, list.get(i).getNumber());
		        createCell(row, 4, list.get(i).getMoney());
	        }
	        
	        //空行
			createNullRow(sheet, cRowNum, cRowNum, 0, 5);
			
			Mortgage mortgage = null;
			if(printData != null && printData.getMortgage() != null) {
				mortgage = printData.getMortgage();
			} else {
				mortgage = new Mortgage();
			}
			//按揭
			cRowNum = cRowNum + 1;
			createRow(sheet, cRowNum, 0, 5, "按揭", hCellStyle);
	        
	        cRowNum = cRowNum + 1;
	        HSSFRow th_n_plus_1_Row = sheet.createRow(cRowNum);
	        createCell(th_n_plus_1_Row, 0, "贷款金额");
	        mergeCell(sheet, th_n_plus_1_Row, cRowNum, 1, 2, mortgage.getA_money());
	        createCell(th_n_plus_1_Row, 3, "贷款时间");
	        mergeCell(sheet, th_n_plus_1_Row, cRowNum, 4, 5, mortgage.getA_date());
			
			cRowNum = cRowNum + 1;
			HSSFRow th_n_plus_2_Row = sheet.createRow(cRowNum);
			createCell(th_n_plus_2_Row, 0, "首付金额");
	        mergeCell(sheet, th_n_plus_2_Row, cRowNum, 1, 2, mortgage.getB_money());
	        createCell(th_n_plus_2_Row, 3, "月供金额");
	        mergeCell(sheet, th_n_plus_2_Row, cRowNum, 4, 5, mortgage.getC_money());
	        
			//空行
			cRowNum = cRowNum + 1;
			createNullRow(sheet, cRowNum, cRowNum, 0, 5);
			
			Replacement replacement = null;
			if(printData != null && printData.getReplacement() != null) {
				replacement = printData.getReplacement();
			} else {
				replacement = new Replacement();
			}
			//置换
			cRowNum = cRowNum + 1;
			createRow(sheet, cRowNum, 0, 5, "置换", hCellStyle);
			
	        cRowNum = cRowNum + 1;
	        HSSFRow th_n_plus_4_Row = sheet.createRow(cRowNum);
	        createCell(th_n_plus_4_Row, 0, "车型名称");
	        createCell(th_n_plus_4_Row, 1, replacement.getCartype());
	        createCell(th_n_plus_4_Row, 2, "评估价格");
	        createCell(th_n_plus_4_Row, 3, replacement.getPrice());
	        createCell(th_n_plus_4_Row, 4, "抵新车款");
	        createCell(th_n_plus_4_Row, 5, replacement.getCars());
	        
	        //空行
			cRowNum = cRowNum + 1;
			createNullRow(sheet, cRowNum, cRowNum, 0, 5);
			
			cRowNum = cRowNum + 1;
			createRow(sheet, cRowNum, 0, 5, "专营店信息", hCellStyle);
			
			cRowNum = cRowNum + 1;
			HSSFRow row = sheet.createRow(cRowNum);
			createCell(row, 0, "销售代表");
			mergeCell(sheet, row, cRowNum, 1, 2, shopinfo.getRepresentative());
			createCell(row, 3, "手机");
			mergeCell(sheet, row, cRowNum, 4, 5, shopinfo.getClientphone());
			
			cRowNum = cRowNum + 1;
			row = sheet.createRow(cRowNum);
			createCell(row, 0, "销售热线");
			mergeCell(sheet, row, cRowNum, 1, 2, shopinfo.getRepresentative());
			createCell(row, 3, "地址");
			mergeCell(sheet, row, cRowNum, 4, 5, shopinfo.getShopaddress());
			
			//空行
			cRowNum = cRowNum + 1;
			createNullRow(sheet, cRowNum, cRowNum, 0, 5);
			
			cRowNum = cRowNum + 1;
			createRow(sheet, cRowNum, 0, 5, "优惠/备注", hCellStyle);
			
			HSSFCellStyle rCellStyle = workbook.createCellStyle();
			rCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
			rCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
			rCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
			rCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			rCellStyle.setWrapText(true);//指定单元格自动换行
			cRowNum = cRowNum + 1;
			
			createRow(sheet, cRowNum, 0, 5, printData.getRemark(), rCellStyle);
			
			cRowNum = cRowNum + 1;
			row = sheet.createRow(cRowNum);
			row.setHeightInPoints(25f);//设置行高度
			createCell(row, 0, "确认签字");
			mergeCell(sheet, row, cRowNum, 1, 2, "");
			createCell(row, 3, "日期");
			mergeCell(sheet, row, cRowNum, 4, 5, "");
			
			workbook.setPrintArea(0, 0, 5, 0, cRowNum);
			
	        workbook.write(out);
	        out.close();
	        return path;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HSSFCell createCell(HSSFRow row, int column, String value) {
		HSSFCell cell = row.createCell(column);
		cell.setCellValue(value);
		return cell;
	}
	
	private static void createNullRow(HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress nullRow = new CellRangeAddress(firstRow,lastRow,0,5);
		sheet.addMergedRegion(nullRow);
	}
	
	private static HSSFRow createRow(HSSFSheet sheet, int rownum, int firstCol, int lastCol, String value, HSSFCellStyle cellStyle) {
		HSSFRow row = sheet.createRow(rownum);  
        CellRangeAddress cellAddress = new CellRangeAddress(rownum, rownum, firstCol, lastCol);
		sheet.addMergedRegion(cellAddress);
        HSSFCell cell = row.createCell(firstCol);  
        cell.setCellValue(value);
        if(cellStyle != null) {
        	cell.setCellStyle(cellStyle);
        }
        return row;
	}
	
	private static void mergeCell(HSSFSheet sheet, HSSFRow row, int rownum, int firstCol, int lastCol, String value) {
        CellRangeAddress cellAddress = new CellRangeAddress(rownum, rownum, firstCol, lastCol);
		sheet.addMergedRegion(cellAddress);
        HSSFCell cell = row.createCell(firstCol);  
        cell.setCellValue(value);
	}
	
	public static void main(String[] args) {
		String data = "{\"data\":{\"shopinfo\":{\"shopname\":\"专营店名称\",\"shopaddress\":\"专营店地址\",\"shopPhone\":\"销售热线\",\"representative\":\"销售代表\",\"clientphone\":\"业代电话\",\"date\":\"来店日期\"},\"clientinfo\":{\"clientname\":\"姓名\",\"clientphone\":\"电话\"},\"carinfo\":{\"cartype\":\"意向车型\",\"carrequire\":\"用车需求\",\"cardate\":\"计划买车时间\",\"allprice\":\"全包价\",\"guideprice\":\"指导价\",\"noprice\":\"净车价\",\"safeprice\":\"保险\",\"duty\":\"购置税\",\"cost\":\"上牌费\"},\"boutique\":[{\"type\":\"类别\",\"name\":\"名称\",\"price\":\"价格\",\"number\":\"数量\",\"money\":\"金额\"},{\"type\":\"类别\",\"name\":\"名称\",\"price\":\"价格\",\"number\":\"数量\",\"money\":\"金额\"}]},\"mortgage\":{\"a_money\":\"贷款金额\",\"a_date\":\"贷款时间\",\"b_money\":\"首付金额\",\"c_money\":\"月供金额\"},\"replacement\":{\"cartype\":\"车型名称\",\"price\":\"评估价格\",\"cars\":\"低新车款\"},\"remark\":\"备注\"}";
		PrintData printData = JSON.parseObject(data, PrintData.class);
		writeXls(printData);
	}
}
