package com.zzyl.nursing.mapper;

import com.zzyl.nursing.domain.CheckOut;
import java.util.List;

/**
 * 退住记录Mapper接口
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public interface CheckOutMapper
{
    CheckOut selectCheckOutById(Long id);

    List<CheckOut> selectCheckOutList(CheckOut checkOut);

    int insertCheckOut(CheckOut checkOut);

    int updateCheckOut(CheckOut checkOut);

    int deleteCheckOutById(Long id);

    int deleteCheckOutByIds(Long[] ids);
}
