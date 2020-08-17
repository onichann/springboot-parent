package com.wt.springboot.mybatis.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
  *@author Administrator
  *@date 2020-07-17 下午 2:35
  *introduction 
 */

/**
    * 编制管理_项目附件
    */
@TableName(value = "T_BZGL_XMFJ")
public class TBzglXmfj implements Serializable {
    /**
     * 项目附件唯一标识
     */
    @TableId(value = "XMFJ_GUID", type = IdType.INPUT)
    private String xmfjGuid;

    /**
     * 附件目录唯一标识
     */
    @TableField(value = "FJML_GUID")
    private String fjmlGuid;

    /**
     * 项目管理唯一标识
     */
    @TableField(value = "XMGL_GUID")
    private String xmglGuid;

    /**
     * 项目附件名称
     */
    @TableField(value = "FJMC")
    private String fjmc;

    /**
     * 文件路径
     */
    @TableField(value = "WJLJ")
    private String wjlj;

    /**
     * 文件大小
     */
    @TableField(value = "WJDX")
    private BigDecimal wjdx;

    /**
     * 上传时间
     */
    @TableField(value = "SCSJ")
    private Date scsj;

    /**
     * 上传用户
     */
    @TableField(value = "SCYH")
    private String scyh;

    private static final long serialVersionUID = 1L;

    public static final String COL_XMFJ_GUID = "XMFJ_GUID";

    public static final String COL_FJML_GUID = "FJML_GUID";

    public static final String COL_XMGL_GUID = "XMGL_GUID";

    public static final String COL_FJMC = "FJMC";

    public static final String COL_WJLJ = "WJLJ";

    public static final String COL_WJDX = "WJDX";

    public static final String COL_SCSJ = "SCSJ";

    public static final String COL_SCYH = "SCYH";

    /**
     * 获取项目附件唯一标识
     *
     * @return XMFJ_GUID - 项目附件唯一标识
     */
    public String getXmfjGuid() {
        return xmfjGuid;
    }

    /**
     * 设置项目附件唯一标识
     *
     * @param xmfjGuid 项目附件唯一标识
     */
    public void setXmfjGuid(String xmfjGuid) {
        this.xmfjGuid = xmfjGuid == null ? null : xmfjGuid.trim();
    }

    /**
     * 获取附件目录唯一标识
     *
     * @return FJML_GUID - 附件目录唯一标识
     */
    public String getFjmlGuid() {
        return fjmlGuid;
    }

    /**
     * 设置附件目录唯一标识
     *
     * @param fjmlGuid 附件目录唯一标识
     */
    public void setFjmlGuid(String fjmlGuid) {
        this.fjmlGuid = fjmlGuid == null ? null : fjmlGuid.trim();
    }

    /**
     * 获取项目管理唯一标识
     *
     * @return XMGL_GUID - 项目管理唯一标识
     */
    public String getXmglGuid() {
        return xmglGuid;
    }

    /**
     * 设置项目管理唯一标识
     *
     * @param xmglGuid 项目管理唯一标识
     */
    public void setXmglGuid(String xmglGuid) {
        this.xmglGuid = xmglGuid == null ? null : xmglGuid.trim();
    }

    /**
     * 获取项目附件名称
     *
     * @return FJMC - 项目附件名称
     */
    public String getFjmc() {
        return fjmc;
    }

    /**
     * 设置项目附件名称
     *
     * @param fjmc 项目附件名称
     */
    public void setFjmc(String fjmc) {
        this.fjmc = fjmc == null ? null : fjmc.trim();
    }

    /**
     * 获取文件路径
     *
     * @return WJLJ - 文件路径
     */
    public String getWjlj() {
        return wjlj;
    }

    /**
     * 设置文件路径
     *
     * @param wjlj 文件路径
     */
    public void setWjlj(String wjlj) {
        this.wjlj = wjlj == null ? null : wjlj.trim();
    }

    /**
     * 获取文件大小
     *
     * @return WJDX - 文件大小
     */
    public BigDecimal getWjdx() {
        return wjdx;
    }

    /**
     * 设置文件大小
     *
     * @param wjdx 文件大小
     */
    public void setWjdx(BigDecimal wjdx) {
        this.wjdx = wjdx;
    }

    /**
     * 获取上传时间
     *
     * @return SCSJ - 上传时间
     */
    public Date getScsj() {
        return scsj;
    }

    /**
     * 设置上传时间
     *
     * @param scsj 上传时间
     */
    public void setScsj(Date scsj) {
        this.scsj = scsj;
    }

    /**
     * 获取上传用户
     *
     * @return SCYH - 上传用户
     */
    public String getScyh() {
        return scyh;
    }

    /**
     * 设置上传用户
     *
     * @param scyh 上传用户
     */
    public void setScyh(String scyh) {
        this.scyh = scyh == null ? null : scyh.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", xmfjGuid=").append(xmfjGuid);
        sb.append(", fjmlGuid=").append(fjmlGuid);
        sb.append(", xmglGuid=").append(xmglGuid);
        sb.append(", fjmc=").append(fjmc);
        sb.append(", wjlj=").append(wjlj);
        sb.append(", wjdx=").append(wjdx);
        sb.append(", scsj=").append(scsj);
        sb.append(", scyh=").append(scyh);
        sb.append("]");
        return sb.toString();
    }

    public static TBzglXmfj empty() {
        return new TBzglXmfj();
    }
}