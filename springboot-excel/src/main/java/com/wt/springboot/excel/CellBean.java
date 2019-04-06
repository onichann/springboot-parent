package com.wt.springboot.excel;

import org.apache.poi.ss.usermodel.CellType;

public class CellBean {

    private int cellRow;
    private int cellColumn;
    private Object cellValue;
    private CellType cellType;
    private String cellCommont;//存放备注
    private boolean isError;//存放cell的值是否检验通过
    private String errorMessage;//存放错误信息
    private String fieldName;//存放当前数据对应数据库的列名称

    CellBean() {
    }

    public int getCellRow() {
        return cellRow;
    }

    public CellBean setCellRow(int cellRow) {
        this.cellRow = cellRow;
        return this;
    }

    public int getCellColumn() {
        return cellColumn;
    }

    public CellBean setCellColumn(int cellColumn) {
        this.cellColumn = cellColumn;
        return this;
    }

    public Object getCellValue() {
        return cellValue;
    }

    public CellBean setCellValue(Object cellValue) {
        this.cellValue = cellValue;
        return this;
    }

    public String getCellCommont() {
        return cellCommont;
    }

    public CellBean setCellCommont(String cellCommont) {
        this.cellCommont = cellCommont;
        return this;
    }

    public boolean isError() {
        return isError;
    }

    public CellBean setError(boolean error) {
        isError = error;
        return this;
    }

    public Object getCellType() {
        return cellType;
    }

    public CellBean setCellType(CellType cellType) {
        this.cellType = cellType;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CellBean setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public CellBean setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    @Override
    public String toString() {
        return "CellBean{" +
                "cellRow=" + cellRow +
                ", cellColumn=" + cellColumn +
                ", cellValue=" + cellValue +
                ", cellType=" + cellType +
                ", cellCommont='" + cellCommont + '\'' +
                ", isError=" + isError +
                ", errorMessage='" + errorMessage + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }

    public static CellBean empty(){
        return new CellBean();
    }
}
