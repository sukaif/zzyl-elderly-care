package com.zzyl.nursing.mapper;

import com.zzyl.nursing.domain.HealthAssessment;
import java.util.List;

public interface HealthAssessmentMapper
{
    HealthAssessment selectHealthAssessmentById(Long id);
    List<HealthAssessment> selectHealthAssessmentList(HealthAssessment ha);
    int insertHealthAssessment(HealthAssessment ha);
    int updateHealthAssessment(HealthAssessment ha);
    int deleteHealthAssessmentByIds(Long[] ids);
}
