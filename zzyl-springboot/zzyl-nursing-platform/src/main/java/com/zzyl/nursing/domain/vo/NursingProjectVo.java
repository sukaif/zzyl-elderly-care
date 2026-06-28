package com.zzyl.nursing.domain.vo;

/**
 * 护理项目值对象
 *
 * @author ruoyi
 * @date 2026-06-23
 */
public class NursingProjectVo {
    /**
     * 项目名称
     */
    private String label;

    /**
     * 项目ID
     */
    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}