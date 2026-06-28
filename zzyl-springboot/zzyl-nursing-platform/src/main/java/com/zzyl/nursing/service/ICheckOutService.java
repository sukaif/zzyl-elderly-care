package com.zzyl.nursing.service;

import com.zzyl.nursing.domain.CheckOut;
import java.util.List;

/**
 * 退住记录Service接口
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public interface ICheckOutService
{
    CheckOut selectCheckOutById(Long id);

    List<CheckOut> selectCheckOutList(CheckOut checkOut);

    int insertCheckOut(CheckOut checkOut);

    int updateCheckOut(CheckOut checkOut);

    int deleteCheckOutByIds(Long[] ids);
}
