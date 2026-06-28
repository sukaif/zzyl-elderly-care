package com.zzyl.nursing.service;

import com.zzyl.nursing.domain.HealthAssessment;
import java.util.List;

public interface IHealthAssessmentService
{
    HealthAssessment selectHealthAssessmentById(Long id);
    List<HealthAssessment> selectHealthAssessmentList(HealthAssessment ha);
    int insertHealthAssessment(HealthAssessment ha);
    int updateHealthAssessment(HealthAssessment ha);
    int deleteHealthAssessmentByIds(Long[] ids);
}
