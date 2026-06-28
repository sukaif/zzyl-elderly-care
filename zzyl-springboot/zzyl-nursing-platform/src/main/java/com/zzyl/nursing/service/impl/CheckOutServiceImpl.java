package com.zzyl.nursing.service.impl;

import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.domain.CheckOut;
import com.zzyl.nursing.mapper.CheckOutMapper;
import com.zzyl.nursing.service.ICheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退住记录Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-26
 */
@Service
public class CheckOutServiceImpl implements ICheckOutService
{
    @Autowired
    private CheckOutMapper checkOutMapper;

    @Override
    public CheckOut selectCheckOutById(Long id)
    {
        return checkOutMapper.selectCheckOutById(id);
    }

    @Override
    public List<CheckOut> selectCheckOutList(CheckOut checkOut)
    {
        return checkOutMapper.selectCheckOutList(checkOut);
    }

    @Override
    @Transactional
    public int insertCheckOut(CheckOut checkOut)
    {
        checkOut.setCreateTime(DateUtils.getNowDate());
        checkOut.setStatus("0"); // 待结算
        return checkOutMapper.insertCheckOut(checkOut);
    }

    @Override
    @Transactional
    public int updateCheckOut(CheckOut checkOut)
    {
        checkOut.setUpdateTime(DateUtils.getNowDate());
        return checkOutMapper.updateCheckOut(checkOut);
    }

    @Override
    @Transactional
    public int deleteCheckOutByIds(Long[] ids)
    {
        return checkOutMapper.deleteCheckOutByIds(ids);
    }
}
