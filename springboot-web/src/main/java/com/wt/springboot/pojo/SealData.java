package com.wt.springboot.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Administrator
 * @date 2019-08-14 下午 4:12
 * PROJECT_NAME sand-demo
 */
@Configuration
@ConfigurationProperties(prefix = "seal.sealdata")
public class SealData {

    public SealData() {
    }

    /**
     * sourceId : 201908130001
     * businessCode : TD-ZF-JDS
     * departmentCode : 310000
     * fileName : tzgzs.pdf
     * sealDataList : [{"positionY":"0","page":"1","type":"keyword","keyword":"sign","positionX":"0","fillInText":""}]
     */


    private String sourceId;
    private String businessCode;
    private String departmentCode;
    private String fileName;

//    @NestedConfigurationProperty
    private List<SealDataListBean> sealDataList;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SealDataListBean> getSealDataList() {
        return sealDataList;
    }

    public void setSealDataList(List<SealDataListBean> sealDataList) {
        this.sealDataList = sealDataList;
    }

    public static class SealDataListBean {
        /**
         * positionY : 0
         * page : 1
         * type : keyword
         * keyword : sign
         * positionX : 0
         * fillInText :
         */

        private String positionY;
        private String page;
        private String type;
        private String keyword;
        private String positionX;
        private String fillInText;

        public String getPositionY() {
            return positionY;
        }

        public void setPositionY(String positionY) {
            this.positionY = positionY;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getPositionX() {
            return positionX;
        }

        public void setPositionX(String positionX) {
            this.positionX = positionX;
        }

        public String getFillInText() {
            return fillInText;
        }

        public void setFillInText(String fillInText) {
            this.fillInText = fillInText;
        }
    }
}
