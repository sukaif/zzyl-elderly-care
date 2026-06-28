package com.zzyl.nursing.domain.vo;

import java.util.List;

/**
 * 树形结构VO
 * 用于楼栋-房间-床位的树形数据展示
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class TreeVo {

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 节点类型（floor：楼栋，room：房间，bed：床位） */
    private String type;

    /** 状态（仅床位有效：0空闲 1已入住 2维修） */
    private String status;

    /** 床位价格（仅床位有效） */
    private String price;

    /** 子节点列表 */
    private List<TreeVo> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }
}
