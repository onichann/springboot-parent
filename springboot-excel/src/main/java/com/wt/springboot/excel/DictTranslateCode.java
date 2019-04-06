package com.wt.springboot.excel;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DictTranslateCode {

    private List<DictType> dictTypeList = new ArrayList<>();

    /**
     * codekey->codeValue
     *
     * @param typeCode
     * @param codeKey
     * @return
     */
    public Object translateCodeKey2CodeValue(String typeCode, String codeKey) {
        if (Strings.isBlank(typeCode)) {
            return codeKey;
        }
        Optional<DictType> first = this.dictTypeList.stream().filter(v -> Objects.equals(typeCode.toUpperCase(), v.getTypeCode())).findFirst();
        if (first.isPresent()) {
            DictCode dict = first.get().getDictByKey(codeKey);
            return dict.getCodeValue();
        } else {
            return codeKey;
        }
    }

    /**
     * codeValue->key
     *
     * @param typeCode
     * @param codeValue
     * @return
     */
    public Object translateCodeValue2CodeKey(String typeCode, String codeValue) {
        if (Strings.isBlank(typeCode)) {
            return codeValue;
        }
        Optional<DictType> first = this.dictTypeList.stream().filter(v -> Objects.equals(typeCode.toUpperCase(), v.getTypeCode())).findFirst();
        if (first.isPresent()) {
            DictCode dict = first.get().getDictByValue(codeValue);
            return dict.getCodeKey();
        } else {
            return codeValue;
        }
    }

    public DictType setDict(String typeName, String typeCode) {
        Assert.notNull(typeName, "TYPENAME IS NULL");
        Assert.notNull(typeCode, "TYPECODE IS NULL");
        Optional<DictType> dictType = dictTypeList.stream().filter(v -> v.getTypeCode().equalsIgnoreCase(typeCode)).findFirst();
        if (dictType.isPresent()) {
            return dictType.get().setTypeName(typeName);
        } else {
            DictType dictTypeNew = new DictType(typeName, typeCode.toUpperCase());
            dictTypeList.add(dictTypeNew);
            return dictTypeNew;
        }
    }

    public class DictType {
        private String typeName;
        private String typeCode;
        private List<DictCode> dictCodeLict;

        public DictType(String typeName, String typeCode) {
            this.typeName = typeName;
            this.typeCode = typeCode;
        }

        public DictType() {

        }

        public DictType addDict(String codeKey, String codeValue) {
            if (this.dictCodeLict == null) {
                this.dictCodeLict = new ArrayList<>();
            }
            Optional<DictCode> first = this.dictCodeLict.stream().filter(v -> v.getCodeKey().equals(codeKey)).findFirst();
            if (first.isPresent()) {
                first.get().setCodeValue(codeValue);
            } else {
                DictCode dictCodeNew = new DictCode(codeKey, codeValue);
                this.dictCodeLict.add(dictCodeNew);
            }
            return this;
        }

        public DictCode getDictByKey(String codeKey) {
            if (this.dictCodeLict == null) {
                return new DictCode(codeKey, codeKey);
            }
            Optional<DictCode> first = this.dictCodeLict.stream().filter(v -> v.getCodeKey().equals(codeKey)).findFirst();
            return first.orElseGet(() -> new DictCode(codeKey, codeKey));
        }

        /**
         * 无法找到对应的字典，将原值返回
         * @param codeValue
         * @return
         */
        public DictCode getDictByValue(String codeValue) {
            if (this.dictCodeLict == null) {
                return new DictCode(codeValue, codeValue);
            }
            Optional<DictCode> first = this.dictCodeLict.stream().filter(v -> v.getCodeValue().equals(codeValue)).findFirst();
            return first.orElseGet(() -> new DictCode(codeValue, codeValue));
        }

        public String getTypeName() {
            return typeName;
        }

        public DictType setTypeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public List<DictCode> getDictCodeLict() {
            return dictCodeLict;
        }

        @Override
        public String toString() {
            return "DictType{" +
                    "typeName='" + typeName + '\'' +
                    ", typeCode='" + typeCode + '\'' +
                    ", dictCodeLict=" + dictCodeLict +
                    '}';
        }
    }

    class DictCode {
        private String codeKey; //字典值
        private String codeValue;//字典值翻译

        public DictCode(String codeKey, String codeValue) {
            this.codeKey = codeKey;
            this.codeValue = codeValue;
        }

        public String getCodeKey() {
            return codeKey;
        }

        public void setCodeKey(String codeKey) {
            this.codeKey = codeKey;
        }

        public String getCodeValue() {
            return codeValue;
        }

        public void setCodeValue(String codeValue) {
            this.codeValue = codeValue;
        }

        @Override
        public String toString() {
            return "DictCode{" +
                    "codeKey='" + codeKey + '\'' +
                    ", codeValue='" + codeValue + '\'' +
                    '}';
        }
    }
}
