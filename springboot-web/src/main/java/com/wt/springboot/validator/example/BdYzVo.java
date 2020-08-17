package com.wt.springboot.validator.example;

import javax.validation.constraints.NotEmpty;

/**
 * @author Administrator
 * @date 2020-04-09 下午 1:36
 * description
 */
public class BdYzVo {
    @NotEmpty(message = "tableId[点击的表单Id]不能为空")
    private String tableId;
    @NotEmpty(message = "hjId(环节Id)不能为空")
    private String hjId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getHjId() {
        return hjId;
    }

    public void setHjId(String hjId) {
        this.hjId = hjId;
    }
}
