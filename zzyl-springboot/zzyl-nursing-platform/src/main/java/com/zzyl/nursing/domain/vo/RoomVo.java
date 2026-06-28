package com.zzyl.nursing.domain.vo;

import java.math.BigDecimal;

/**
 * 房间数据VO
 * 根据床位ID查询房间信息（楼层、房间、价格）
 *
 * @author ruoyi
 * @date 2026-06-25
 */
public class RoomVo {

    /** 楼栋名称 */
    private String floorName;

    /** 房间号 */
    private String roomNumber;

    /** 床位号 */
    private String bedNumber;

    /** 床位价格 */
    private BigDecimal price;

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
