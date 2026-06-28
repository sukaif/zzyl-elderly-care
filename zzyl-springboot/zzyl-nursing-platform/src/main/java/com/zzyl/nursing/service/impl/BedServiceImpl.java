package com.zzyl.nursing.service.impl;

import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.BedMapper;
import com.zzyl.nursing.domain.Bed;
import com.zzyl.nursing.service.IBedService;

/**
 * 床位Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@Service
public class BedServiceImpl implements IBedService
{
    @Autowired
    private BedMapper bedMapper;

    @Override
    public Bed selectBedById(Long id)
    {
        return bedMapper.selectBedById(id);
    }

    @Override
    public List<Bed> selectBedList(Bed bed)
    {
        return bedMapper.selectBedList(bed);
    }

    @Override
    public List<Bed> selectBedListByStatus(String status)
    {
        return bedMapper.selectBedListByStatus(status);
    }

    @Override
    public int insertBed(Bed bed)
    {
        bed.setCreateTime(DateUtils.getNowDate());
        return bedMapper.insertBed(bed);
    }

    @Override
    public int updateBed(Bed bed)
    {
        bed.setUpdateTime(DateUtils.getNowDate());
        return bedMapper.updateBed(bed);
    }

    @Override
    public int deleteBedByIds(Long[] ids)
    {
        return bedMapper.deleteBedByIds(ids);
    }

    @Override
    public int deleteBedById(Long id)
    {
        return bedMapper.deleteBedById(id);
    }
}
