package com.wt.springboot.excel;

import java.lang.reflect.Field;

public class ValidRequest {
    private Field field;
    private CellBean cellBean;

    public Field getField() {
        return field;
    }

    public ValidRequest setField(Field field) {
        this.field = field;
        return this;
    }

    public CellBean getCellBean() {
        return cellBean;
    }

    public ValidRequest setCellBean(CellBean cellBean) {
        this.cellBean = cellBean;
        return this;
    }
}
