package com.wt.springboot.validator.example;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author Administrator
 * @date 2020-07-01 上午 10:08
 * introduction
 */
public class TableRecordVo {
    @NotEmpty(message = "ssbh 不能为空、ssbh为任务表主键")
    private String ssbh;
    @Valid
    private BdYzVo bdYzVo;

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
}
