package com.wt.springboot.mybatis.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  *@author Administrator
  *@date 2020-07-17 下午 2:24
  *introduction 
 */

/**
    * 编制管理_附件目录
    */
@TableName(value = "T_BZGL_FJML")
public class TBzglFjml implements Serializable {
    /**
     * 附件目录唯一标识
     */
    @TableId(value = "FJML_GUID", type = IdType.INPUT)
    private String fjmlGuid;

    /**
     * 附件文件夹名称
     */
    @TableField(value = "FJMLMC")
    private String fjmlmc;

    /**
     * 显示顺序
     */
    @TableField(value = "DISPLAY_ORDER")
    private BigDecimal displayOrder;

    /**
     * 上级目录唯一标识
     */
    @TableField(value = "PARENT_ID")
    private String parentId;

    /**
     * 项目状态
     */
    @TableField(value = "XMZT")
    private String xmzt;

    /**
     * 是否必备
     */
    @TableField(value = "SFBB")
    private BigDecimal sfbb;

    /**
     * 文件夹类型
     */
    @TableField(value = "TYPE")
    private String type;

    /**
     * 子文件夹
     */
    @TableField(exist = false, select = false)
    private List<TBzglFjml> children;

    public List<TBzglFjml> getChildren() {
        return children;
    }

    public void setChildren(List<TBzglFjml> children) {
        this.children = children;
    }

    /**
     * 当前目录文件
     */
    @TableField(exist = false, select = false)
    private List<TBzglXmfj> fjs;

    public List<TBzglXmfj> getFjs() {
        return fjs;
    }

    public void setFjs(List<TBzglXmfj> fjs) {
        this.fjs = fjs;
    }

    @TableField(exist = false, select = false)
    private Map<String, Object> attributes;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addFjs(List<TBzglXmfj> fjs) {
        if(this.fjs == null) {
            this.fjs = fjs;
        }else{
            if (fjs != null) {
                this.fjs.addAll(fjs);
            }
        }
    }

    public void addFj(TBzglXmfj fj) {
        if (this.fjs == null) {
            this.fjs = new ArrayList<>();
        }
        if (fj != null) {
            this.fjs.add(fj);
        }
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_FJML_GUID = "FJML_GUID";

    public static final String COL_FJMLMC = "FJMLMC";

    public static final String COL_DISPLAY_ORDER = "DISPLAY_ORDER";

    public static final String COL_PARENT_ID = "PARENT_ID";

    public static final String COL_XMZT = "XMZT";

    public static final String COL_SFBB = "SFBB";

    public static final String COL_TYPE = "TYPE";

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
        this.fjmlGuid = fjmlGuid;
    }

    /**
     * 获取附件文件夹名称
     *
     * @return FJMLMC - 附件文件夹名称
     */
    public String getFjmlmc() {
        return fjmlmc;
    }

    /**
     * 设置附件文件夹名称
     *
     * @param fjmlmc 附件文件夹名称
     */
    public void setFjmlmc(String fjmlmc) {
        this.fjmlmc = fjmlmc == null ? null : fjmlmc.trim();
    }

    /**
     * 获取显示顺序
     *
     * @return DISPLAY_ORDER - 显示顺序
     */
    public BigDecimal getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 设置显示顺序
     *
     * @param displayOrder 显示顺序
     */
    public void setDisplayOrder(BigDecimal displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 获取上级目录唯一标识
     *
     * @return PARENT_ID - 上级目录唯一标识
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置上级目录唯一标识
     *
     * @param parentId 上级目录唯一标识
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * 获取项目状态
     *
     * @return XMZT - 项目状态
     */
    public String getXmzt() {
        return xmzt;
    }

    /**
     * 设置项目状态
     *
     * @param xmzt 项目状态
     */
    public void setXmzt(String xmzt) {
        this.xmzt = xmzt == null ? null : xmzt.trim();
    }

    /**
     * 获取是否必备
     *
     * @return SFBB - 是否必备
     */
    public BigDecimal getSfbb() {
        return sfbb;
    }

    /**
     * 设置是否必备
     *
     * @param sfbb 是否必备
     */
    public void setSfbb(BigDecimal sfbb) {
        this.sfbb = sfbb;
    }

    /**
     * 获取文件夹类型
     *
     * @return TYPE - 文件夹类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置文件夹类型
     *
     * @param type 文件夹类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fjmlGuid=").append(fjmlGuid);
        sb.append(", fjmlmc=").append(fjmlmc);
        sb.append(", displayOrder=").append(displayOrder);
        sb.append(", parentId=").append(parentId);
        sb.append(", xmzt=").append(xmzt);
        sb.append(", sfbb=").append(sfbb);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }

    public static TBzglFjml empty() {
        return new TBzglFjml();
    }
}