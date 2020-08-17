package com.wt.springboot.validator.example;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020-04-09 下午 1:15
 * description
 */
public class FormData {

    @NotEmpty(message = "tableName不能为空、表名联系后端获取")
    private String tableName;
    @NotEmpty(message = "ssbh 不能为空、ssbh为任务表主键")
    private String ssbh;
    @Valid
    private BdYzVo bdYzVo;
    private String id; //保存的单表的主键
    private Map<String, Object> data;//保存的单表的数据  id、ssbh 不需要 日期格式以rq结尾

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSsbh() {
        return ssbh;
    }

    public void setSsbh(String ssbh) {
        this.ssbh = ssbh;
    }

    public BdYzVo getBdYzVo() {
        return bdYzVo;
    }

    public void setBdYzVo(BdYzVo bdYzVo) {
        this.bdYzVo = bdYzVo;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

