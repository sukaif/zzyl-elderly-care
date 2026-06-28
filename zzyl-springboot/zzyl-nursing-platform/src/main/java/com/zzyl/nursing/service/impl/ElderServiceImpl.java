package com.zzyl.nursing.service.impl;

import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.ElderMapper;
import com.zzyl.nursing.domain.Elder;
import com.zzyl.nursing.service.IElderService;

/**
 * 老人Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-25
 */
@Service
public class ElderServiceImpl implements IElderService
{
    @Autowired
    private ElderMapper elderMapper;

    @Override
    public Elder selectElderById(Long id)
    {
        return elderMapper.selectElderById(id);
    }

    @Override
    public List<Elder> selectElderList(Elder elder)
    {
        return elderMapper.selectElderList(elder);
    }

    @Override
    public int insertElder(Elder elder)
    {
        elder.setCreateTime(DateUtils.getNowDate());
        return elderMapper.insertElder(elder);
    }

    @Override
    public int updateElder(Elder elder)
    {
        elder.setUpdateTime(DateUtils.getNowDate());
        return elderMapper.updateElder(elder);
    }

    @Override
    public int deleteElderByIds(Long[] ids)
    {
        return elderMapper.deleteElderByIds(ids);
    }

    @Override
    public int deleteElderById(Long id)
    {
        return elderMapper.deleteElderById(id);
    }
}
