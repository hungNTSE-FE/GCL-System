package com.gcl.crm.utils;

import com.gcl.crm.entity.Potential;
import com.gcl.crm.entity.TransactionHistory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public static final int COLUMN_INDEX_PHONE = 1;
    public static final int COLUMN_INDEX_DATE = 2;
    public static final int COLUMN_INDEX_NAME = 3;
    public static final int COLUMN_INDEX_EMAIL = 4;
    public static final int COLUMN_INDEX_SOURCE = 5;
    public static final int COLUMN_INDEX_ADDRESS = 6;
    public static final int COLUMN_INDEX_ID = 9 ;
    public static final int COLUMN_INDEX_ACCOUNT_NUMBER =4;
    public static final int COLUMN_INDEX_ACCOUNT_NAME = 6 ;

    public static final int COLUMN_INDEX_TYPE = 17 ;
    public static final int COLUMN_INDEX_LOT = 20 ;
    public static final int COLUMN_INDEX_INSERT_DATE = 26;
    public static final int COLUMN_INDEX_MONEY = 22 ;




    private Workbook getWorkbook(InputStream stream, String filename) throws IOException, NotOfficeXmlFileException {
        Workbook workbook = null;
        if (filename.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(stream);
        } else if (filename.endsWith("xls")) {
            workbook = new HSSFWorkbook(stream);
        }
        return workbook;
    }

    public List<Potential> getPotentialData(InputStream stream, String filename) throws IOException, IllegalStateException {
        List<Potential> potentialData = new ArrayList<>();
        Workbook workbook = this.getWorkbook(stream, filename);
        if (workbook == null) throw new IllegalStateException("Tập tin đã chọn không đúng định dạng");
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 3) {
                continue;
            }
            if (row.getLastCellNum() != 24){
                continue;
            }
            Iterator<Cell> cellIterator = row.cellIterator();
            Potential item = new Potential();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                Object cellValue = this.getCellValue(cell);
                if (cellValue == null) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_PHONE:
                        if (cellValue instanceof String) {
                            item.setPhoneNumber(cellValue.toString().trim().replace(" ", ""));
                        } else if (cellValue instanceof Double) {
                            Double phone = Double.valueOf(cellValue.toString());
                            NumberFormat numberFormat = NumberFormat.getInstance();
                            numberFormat.setMaximumFractionDigits(20);
                            numberFormat.setGroupingUsed(false);
                            if (phone != null) {
                                item.setPhoneNumber(numberFormat.format(phone));
                            }
                        }
                        break;
                    case COLUMN_INDEX_DATE:
                        Date date;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        if (cellValue instanceof Double) {
                            date = DateUtil.getJavaDate((Double) cellValue);
                        } else {
                            try {
                                date = simpleDateFormat.parse((String) cellValue);
                            } catch (ParseException e) {
                                item.setDate(null);
                                break;
                            }
                        }
                        item.setDate(simpleDateFormat.format(date));
                        break;
                    case COLUMN_INDEX_NAME:
                        String name = cellValue.toString();
                        item.setName(name);
                        break;
                    case COLUMN_INDEX_EMAIL:
                        String email = cellValue.toString();
                        item.setEmail(email);
                        break;
                    case COLUMN_INDEX_SOURCE:
                        String source = cellValue.toString();
                        item.setSourceName(source);
                        break;
                    case COLUMN_INDEX_ADDRESS:
                        String address = cellValue.toString();
                        item.setAddress(address);
                        break;
                    default:
                        break;
                }
            }
            if (item.getEmail() != null || item.getPhoneNumber() != null) {
                if (!potentialData.contains(item)){
                    potentialData.add(item);
                }
            }
        }
        return potentialData;
    }
    public List<TransactionHistory> getTransactionData(InputStream stream, String filename) throws IOException, IllegalStateException {
        List<TransactionHistory> transactionData = new ArrayList<>();
        Workbook workbook = this.getWorkbook(stream, filename);
        if (workbook == null) throw new IllegalStateException("Tập tin đã chọn không đúng định dạng");
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 7) {
                continue;
            }
            if (row.getLastCellNum() != 28){
                continue;
            }
            Iterator<Cell> cellIterator = row.cellIterator();
            TransactionHistory item = new TransactionHistory();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                Object cellValue = this.getCellValue(cell);
                if (cellValue == null) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                System.out.println("index " + columnIndex);
                System.out.println("value " + cellValue);

                switch (columnIndex) {
                    case COLUMN_INDEX_INSERT_DATE:
                        Date date;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        if (cellValue instanceof Double) {
                            date = DateUtil.getJavaDate((Double) cellValue);
                        } else {
                            try {
                                date = simpleDateFormat.parse((String) cellValue);
                            } catch (ParseException e) {
                                item.setCreateDate(null);
                                    break;
                            }
                        }
                        item.setCreateDate(date);
                        break;
                    case COLUMN_INDEX_ACCOUNT_NAME:
                        String name = cellValue.toString();
                        item.setAccountName(name);
                        System.out.println("name " + name );
                        break;

                    case COLUMN_INDEX_ACCOUNT_NUMBER:
                        String number = cellValue.toString();
                        item.setAccountNumber(number);
                        break;
                    case  COLUMN_INDEX_ID:
                        String id = cellValue.toString();
                        item.setTransactionID(id);
                        break;
                    case COLUMN_INDEX_TYPE:
                        String type = cellValue.toString();
                        item.setTransactionType(type);
                        break;
                    case COLUMN_INDEX_LOT:
                        if(!cellValue.toString().isEmpty()){
                            String lot = cellValue.toString();
                            item.setLot((int) Math.round(Double.parseDouble(lot)));


                        }else{
                            return transactionData;
                        }


                        break;
                    case COLUMN_INDEX_MONEY:
                        String money = cellValue.toString();
                        item.setMoney(money);
                        break;
                    default:
                        break;
                }
            }
            if(item.getTransactionID() != null){
                transactionData.add(item);
                System.out.println(item.toString());
            }


        }

        System.out.println(transactionData.get(transactionData.size()-1).getTransactionID());
        return transactionData;
    }

    private Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
            default:
                break;
        }
        if (cellValue != null && cellValue.toString().equals("0.0")){
            return null;
        }
        return cellValue;
    }
}
