package com.wt.springboot.validator.example;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
  *@author Administrator
  *@date 2020-03-30 下午 4:52
  *description 
 */
    
@TableName(value = "gg_lzyj")
public class GgLzyj implements Serializable {
    /**
     * 编号(主键)
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 任务编号
     */
    @TableField(value = "rwbh")
    @NotBlank(message = "流转意见中 rwbh不能为空")
    private String rwbh;

    /**
     * 流程编号
     */
    @TableField(value = "lcbh")
    @NotBlank(message = "流转意见中 lcbh不能为空")
    private String lcbh;

    /**
     * 流转意见
     */
    @TableField(value = "lzyj")
    private String lzyj;

    /**
     * 发送人员
     */
    @TableField(value = "fsry")
    private String fsry;

    /**
     * 发送日期
     */
    @TableField(value = "fsrq")
    private Date fsrq;

    /**
     * 发送环节
     */
    @TableField(value = "fshj")
    @NotBlank(message = "流转意见中 fshj不能为空")
    private String fshj;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_RWBH = "rwbh";

    public static final String COL_LCBH = "lcbh";

    public static final String COL_LZYJ = "lzyj";

    public static final String COL_FSRY = "fsry";

    public static final String COL_FSRQ = "fsrq";

    public static final String COL_FSHJ = "fshj";

    /**
     * 获取编号(主键)
     *
     * @return id - 编号(主键)
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号(主键)
     *
     * @param id 编号(主键)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取任务编号
     *
     * @return rwbh - 任务编号
     */
    public String getRwbh() {
        return rwbh;
    }

    /**
     * 设置任务编号
     *
     * @param rwbh 任务编号
     */
    public void setRwbh(String rwbh) {
        this.rwbh = rwbh;
    }

    /**
     * 获取流程编号
     *
     * @return lcbh - 流程编号
     */
    public String getLcbh() {
        return lcbh;
    }

    /**
     * 设置流程编号
     *
     * @param lcbh 流程编号
     */
    public void setLcbh(String lcbh) {
        this.lcbh = lcbh;
    }

    /**
     * 获取流转意见
     *
     * @return lzyj - 流转意见
     */
    public String getLzyj() {
        return lzyj;
    }

    /**
     * 设置流转意见
     *
     * @param lzyj 流转意见
     */
    public void setLzyj(String lzyj) {
        this.lzyj = lzyj;
    }

    /**
     * 获取发送人员
     *
     * @return fsry - 发送人员
     */
    public String getFsry() {
        return fsry;
    }

    /**
     * 设置发送人员
     *
     * @param fsry 发送人员
     */
    public void setFsry(String fsry) {
        this.fsry = fsry;
    }

    /**
     * 获取发送日期
     *
     * @return fsrq - 发送日期
     */
    public Date getFsrq() {
        return fsrq;
    }

    /**
     * 设置发送日期
     *
     * @param fsrq 发送日期
     */
    public void setFsrq(Date fsrq) {
        this.fsrq = fsrq;
    }

    /**
     * 获取发送环节
     *
     * @return fshj - 发送环节
     */
    public String getFshj() {
        return fshj;
    }

    /**
     * 设置发送环节
     *
     * @param fshj 发送环节
     */
    public void setFshj(String fshj) {
        this.fshj = fshj;
    }
}