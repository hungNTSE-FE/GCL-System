package com.gcl.crm.exporter;

import com.gcl.crm.entity.TradingAccount;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public class TradingAccountExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFSheet sheet1;
    private XSSFSheet sheet2;
    private XSSFSheet sheet3;
    XSSFFont font ;
    String title ;
    String month ;
    String sheetName;

    private List<TradingAccount> stopTradingAccountList;
    private List<TradingAccount> tradingAccountList;
    private List<TradingAccount> noneTradingAccountList;
    private List<TradingAccount> blockTradingAccountList;





    public TradingAccountExcelExporter(String title, String month, String sheetName, List<TradingAccount> stopTradingAccountList, List<TradingAccount> tradingAccountList, List<TradingAccount> noneTradingAccountList, List<TradingAccount> blockTradingAccountList) {

        this.title = title;
        this.month = month;
        this.sheetName = sheetName;
        workbook = new XSSFWorkbook();
        sheet =workbook.createSheet(sheetName+ " CHÁY");
        sheet1 =workbook.createSheet(sheetName+ " KHÓA");
        sheet2 =workbook.createSheet(sheetName+ " ĐANG GIAO DỊCH");
        sheet3 =workbook.createSheet(sheetName+ " CHƯA GIAO DỊCH");
        setSheetBlock(sheet);
        setSheetStop(sheet1);
        setSheet(sheet2);
        setSheet(sheet3);
        this.stopTradingAccountList = stopTradingAccountList;
        this.tradingAccountList = tradingAccountList;
        this.noneTradingAccountList = noneTradingAccountList;
        this.blockTradingAccountList = blockTradingAccountList;
    }

    private void writeHeaderRow(XSSFSheet sheet, String accountType){
        Row title = sheet.createRow(0);
        Row month = sheet.createRow(1);

        Row row = sheet.createRow(3);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setFontName("Times New Roman");
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setFont(font);

        //title style
        CellStyle styleTitle = workbook.createCellStyle();
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeight(18);
        fontTitle.setFontName("Times New Roman");
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setFont(fontTitle);


        Cell titleCell = title.createCell(0);

        titleCell.setCellValue(this.title + accountType);

        titleCell.setCellStyle(styleTitle);
        //month style
        CellStyle styleMonth = workbook.createCellStyle();
        XSSFFont fontMonth = workbook.createFont();
        fontMonth.setItalic(true);
        fontMonth.setFontHeight(16);
        fontMonth.setFontName("Times New Roman");
        styleMonth.setAlignment(HorizontalAlignment.CENTER);
        styleMonth.setFont(fontMonth);


        Cell monthCell = month.createCell(0);

        monthCell.setCellValue(this.month);

        monthCell.setCellStyle(styleMonth);



        Cell cell = row.createCell(0);
        cell.setCellValue("STT");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Mã TKGD");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Tên TKGD");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Mã môi giới");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Tên môi giới");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Số điện thoại");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Địa chỉ mail");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("Ghi chú");
        cell.setCellStyle(style);


    }
    private void writeDataRows(XSSFSheet sheet,List<TradingAccount> tradingAccountList){
        int rowCount = 4;
        int index = 1 ;
        for(TradingAccount tradingAccount : tradingAccountList){
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(setStyle());

            cell.setCellValue(index);
            cell = row.createCell(1);

            cell.setCellValue(tradingAccount.getAccountNumber());
            cell.setCellStyle(setStyle());
            cell = row.createCell(2);
            cell.setCellValue(tradingAccount.getAccountName());
            cell.setCellStyle(setStyle());

            cell = row.createCell(3);
            cell.setCellValue(tradingAccount.getBrokerCode());
            cell.setCellStyle(setStyle());

            cell = row.createCell(4);
            cell.setCellValue(tradingAccount.getBrokerName());
            cell.setCellStyle(setStyle());

            cell = row.createCell(5);
            cell.setCellValue(tradingAccount.getCustomer().getPhoneNumber());
            cell.setCellStyle(setStyle());

            cell = row.createCell(6);
            cell.setCellValue(tradingAccount.getCustomer().getEmail());
            cell.setCellStyle(setStyle());

            cell = row.createCell(7);
            cell.setCellValue(tradingAccount.getCustomer().getDescription());
            cell.setCellStyle(setStyle());

            index++ ;
            }
        }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRowBlockAccount(sheet," CHÁY");
        writeDataRowsBlockAccount(sheet,blockTradingAccountList);
        writeHeaderRowStopAccount(sheet1, " KHÓA");
        writeDataRowsStopAccount(sheet1,stopTradingAccountList);
        writeHeaderRow(sheet2," ĐANG GIAO DỊCH");
        writeDataRows(sheet2,tradingAccountList);
        writeHeaderRow(sheet3," CHƯA GIAO DỊCH");
        writeDataRows(sheet3,noneTradingAccountList);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    public CellStyle setStyle(){
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        font.setFontName("Times New Roman");
        style.setFont(font);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.DASHED);
        return style;
    }
    public void setSheet(XSSFSheet sheet){
        sheet.setColumnWidth(0,2000);
        sheet.setColumnWidth(1,6000);
        sheet.setColumnWidth(2,9000);
        sheet.setColumnWidth(3,6000);
        sheet.setColumnWidth(4,8000);
        sheet.setColumnWidth(5,6000);
        sheet.setColumnWidth(6,10000);
        sheet.setColumnWidth(7,14000);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,7));
    }
    private void writeHeaderRowStopAccount(XSSFSheet sheet, String accountType){
        Row title = sheet.createRow(0);
        Row month = sheet.createRow(1);

        Row row = sheet.createRow(3);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setFontName("Times New Roman");
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setFont(font);

        //title style
        CellStyle styleTitle = workbook.createCellStyle();
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeight(18);
        fontTitle.setFontName("Times New Roman");
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setFont(fontTitle);


        Cell titleCell = title.createCell(0);

        titleCell.setCellValue(this.title + accountType);

        titleCell.setCellStyle(styleTitle);
        //month style
        CellStyle styleMonth = workbook.createCellStyle();
        XSSFFont fontMonth = workbook.createFont();
        fontMonth.setItalic(true);
        fontMonth.setFontHeight(16);
        fontMonth.setFontName("Times New Roman");
        styleMonth.setAlignment(HorizontalAlignment.CENTER);
        styleMonth.setFont(fontMonth);


        Cell monthCell = month.createCell(0);

        monthCell.setCellValue(this.month);

        monthCell.setCellStyle(styleMonth);



        Cell cell = row.createCell(0);
        cell.setCellValue("STT");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Mã TKGD");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Tên TKGD");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Mã môi giới");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Tên môi giới");
        cell.setCellStyle(style);




    }
    private void writeDataRowsStopAccount(XSSFSheet sheet,List<TradingAccount> tradingAccountList){
        int rowCount = 4;
        int index = 1 ;
        for(TradingAccount tradingAccount : tradingAccountList){
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(setStyleStop());

            cell.setCellValue(index);
            cell = row.createCell(1);

            cell.setCellValue(tradingAccount.getAccountNumber());
            cell.setCellStyle(setStyleStop());
            cell = row.createCell(2);
            cell.setCellValue(tradingAccount.getAccountName());
            cell.setCellStyle(setStyleStop());

            cell = row.createCell(3);
            cell.setCellValue(tradingAccount.getBrokerCode());
            cell.setCellStyle(setStyleStop());

            cell = row.createCell(4);
            cell.setCellValue(tradingAccount.getBrokerName());
            cell.setCellStyle(setStyleStop());



            index++ ;
        }
    }
    public CellStyle setStyleStop(){
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        font.setFontName("Times New Roman");
        style.setFont(font);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.DASHED);
        return style;
    }
    public CellStyle setStyleBlock(){
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        font.setFontName("Times New Roman");
        style.setFont(font);

        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.DASHED);
        return style;
    }
    public void setSheetStop(XSSFSheet sheet){
        sheet.setColumnWidth(0,2000);
        sheet.setColumnWidth(1,6000);
        sheet.setColumnWidth(2,9000);
        sheet.setColumnWidth(3,6000);
        sheet.setColumnWidth(4,8000);
        sheet.setColumnWidth(5,6000);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,4));
    }
    public void setSheetBlock(XSSFSheet sheet)  {
        sheet.setColumnWidth(0,2000);
        sheet.setColumnWidth(1,6000);
        sheet.setColumnWidth(2,6000);
        sheet.setColumnWidth(3,4000);
        sheet.setColumnWidth(4,6000);
        sheet.setColumnWidth(5,3000);
        sheet.setColumnWidth(6,3500);
        sheet.setColumnWidth(7,4000);
        sheet.setColumnWidth(8,4000);
        sheet.setColumnWidth(9,4000);
        sheet.setColumnWidth(10,6000);
        sheet.setColumnWidth(11,5000);
        sheet.setColumnWidth(12,10000);
        sheet.setColumnWidth(13,10000);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,14));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,14));
    }
    private void writeHeaderRowBlockAccount(XSSFSheet sheet, String accountType){
        Row title = sheet.createRow(0);
        Row month = sheet.createRow(1);

        Row row = sheet.createRow(3);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setFontName("Times New Roman");
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setFont(font);

        //title style
        CellStyle styleTitle = workbook.createCellStyle();
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeight(18);
        fontTitle.setFontName("Times New Roman");
        styleTitle.setAlignment(HorizontalAlignment.CENTER);
        styleTitle.setFont(fontTitle);


        Cell titleCell = title.createCell(0);

        titleCell.setCellValue(this.title + accountType);

        titleCell.setCellStyle(styleTitle);
        //month style
        CellStyle styleMonth = workbook.createCellStyle();
        XSSFFont fontMonth = workbook.createFont();
        fontMonth.setItalic(true);
        fontMonth.setFontHeight(16);
        fontMonth.setFontName("Times New Roman");
        styleMonth.setAlignment(HorizontalAlignment.CENTER);
        styleMonth.setFont(fontMonth);


        Cell monthCell = month.createCell(0);

        monthCell.setCellValue(this.month);

        monthCell.setCellStyle(styleMonth);



        Cell cell = row.createCell(0);
        cell.setCellValue("STT");
        cell.setCellStyle(style);



        cell = row.createCell(1);
        cell.setCellValue("MÃ TKGD");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("TÊN TKGD");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("MÃ MÔI GIỚI");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("TÊN MÔI GIỚI");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Giới tính");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Số CMND");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("NGÀY SINH");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("NGÀY CẤP");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("NƠI CẤP");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("ĐỊA CHỈ");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("SDT");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("ĐỊA CHỈ MAIL");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("GHI CHÚ");
        cell.setCellStyle(style);

    }
    private void writeDataRowsBlockAccount(XSSFSheet sheet,List<TradingAccount> tradingAccountList){
        int rowCount = 4;
        int index = 1 ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for(TradingAccount tradingAccount : tradingAccountList){
            Row row = sheet.createRow(rowCount++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(setStyleBlock());

            cell.setCellValue(index);
            cell = row.createCell(1);

            cell.setCellValue(tradingAccount.getAccountNumber());
            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(2);

            cell.setCellValue(tradingAccount.getAccountName());
            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(3);
            cell.setCellValue(tradingAccount.getBrokerCode());
            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(4);
            cell.setCellValue(tradingAccount.getBrokerName());
            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(5);
            if(tradingAccount.getCustomer().getGender().getValue().equals("0")){
                cell.setCellValue("Nam");
                cell.setCellStyle(setStyleBlock());
            }else {
                cell.setCellValue("Nữ");
                cell.setCellStyle(setStyleBlock());
            }


            cell = row.createCell(6);
            cell.setCellValue(tradingAccount.getCustomer().getIdentification().getIdentityNumber());
            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(7);
            cell.setCellValue(tradingAccount.getCustomer().getIdentification().getBirthDate()+"");

            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(8);
            cell.setCellValue(tradingAccount.getCustomer().getIdentification().getIssueDate()+"");
            cell.setCellStyle(setStyleBlock());

            cell = row.createCell(9);
            cell.setCellValue(tradingAccount.getCustomer().getIdentification().getIssuePlace());
            cell.setCellStyle(setStyleBlock());
            cell = row.createCell(10);
            cell.setCellValue(tradingAccount.getCustomer().getAddress());
            cell.setCellStyle(setStyleBlock());
            cell = row.createCell(11);
            cell.setCellValue(tradingAccount.getCustomer().getPhoneNumber());
            cell.setCellStyle(setStyleBlock());
            cell = row.createCell(12);
            cell.setCellValue(tradingAccount.getCustomer().getEmail());
            cell.setCellStyle(setStyleBlock());
            cell = row.createCell(13);
            cell.setCellValue(tradingAccount.getCustomer().getDescription());
            cell.setCellStyle(setStyleBlock());
            index++ ;
        }
    }
}
