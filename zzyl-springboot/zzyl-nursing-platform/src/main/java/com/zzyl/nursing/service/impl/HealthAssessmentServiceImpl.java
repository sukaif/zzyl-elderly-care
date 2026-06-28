package com.zzyl.nursing.service.impl;

import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.domain.HealthAssessment;
import com.zzyl.nursing.mapper.HealthAssessmentMapper;
import com.zzyl.nursing.service.IHealthAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthAssessmentServiceImpl implements IHealthAssessmentService
{
    @Autowired
    private HealthAssessmentMapper healthAssessmentMapper;

    @Override
    public HealthAssessment selectHealthAssessmentById(Long id)
    {
        return healthAssessmentMapper.selectHealthAssessmentById(id);
    }

    @Override
    public List<HealthAssessment> selectHealthAssessmentList(HealthAssessment ha)
    {
        return healthAssessmentMapper.selectHealthAssessmentList(ha);
    }

    @Override
    @Transactional
    public int insertHealthAssessment(HealthAssessment ha)
    {
        ha.setCreateTime(DateUtils.getNowDate());
        ha.setStatus("0"); // 待分析
        return healthAssessmentMapper.insertHealthAssessment(ha);
    }

    @Override
    @Transactional
    public int updateHealthAssessment(HealthAssessment ha)
    {
        ha.setUpdateTime(DateUtils.getNowDate());
        return healthAssessmentMapper.updateHealthAssessment(ha);
    }

    @Override
    @Transactional
    public int deleteHealthAssessmentByIds(Long[] ids)
    {
        return healthAssessmentMapper.deleteHealthAssessmentByIds(ids);
    }
}
