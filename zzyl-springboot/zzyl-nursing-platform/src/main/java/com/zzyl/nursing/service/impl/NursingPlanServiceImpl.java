package com.zzyl.nursing.service.impl;

import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zzyl.nursing.mapper.NursingPlanMapper;
import com.zzyl.nursing.mapper.NursingProjectPlanMapper;
import com.zzyl.nursing.domain.NursingPlan;
import com.zzyl.nursing.domain.NursingProjectPlan;
import com.zzyl.nursing.domain.dto.NursingPlanDto;
import com.zzyl.nursing.domain.vo.NursingPlanVo;
import com.zzyl.nursing.domain.vo.NursingProjectPlanVo;
import com.zzyl.nursing.service.INursingPlanService;

/**
 * 护理计划Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-06-22
 */
@Transactional
@Service
public class NursingPlanServiceImpl implements INursingPlanService 
{
    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    @Autowired
    private NursingProjectPlanMapper nursingProjectPlanMapper;

    /**
     * 查询护理计划
     *
     * @param id 护理计划主键
     * @return 护理计划
     */
    @Override
    public NursingPlanVo selectNursingPlanById(Long id)
    {
        // 1.根据id查询护理计划
        NursingPlan nursingPlan = nursingPlanMapper.selectNursingPlanById(id);

        // 2.根据护理计划id查询关联的所有护理项目
        List<NursingProjectPlanVo> list = nursingProjectPlanMapper.selectByPlanId(id);

        // 3.封装结果并返回
        NursingPlanVo nursingPlanVo = new NursingPlanVo();
        nursingPlanVo.setId(nursingPlan.getId());
        nursingPlanVo.setSortNo(nursingPlan.getSortNo().intValue());
        nursingPlanVo.setPlanName(nursingPlan.getPlanName());
        nursingPlanVo.setStatus(nursingPlan.getStatus().intValue());
        nursingPlanVo.setProjectPlans(list);
        return nursingPlanVo;
    }

    /**
     * 查询护理计划列表
     * 
     * @param nursingPlan 护理计划
     * @return 护理计划
     */
    @Override
    public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan)
    {
        return nursingPlanMapper.selectNursingPlanList(nursingPlan);
    }

    /**
     * 查询所有启用的护理计划
     * 
     * @return 护理计划集合
     */
    @Override
    public List<NursingPlan> selectAllEnablePlan()
    {
        NursingPlan nursingPlan = new NursingPlan();
        nursingPlan.setStatus(1L);
        return nursingPlanMapper.selectNursingPlanList(nursingPlan);
    }

    /**
     * 新增护理计划（带事务，同步保存关联项目）
     * @param dto 护理计划
     * @return 结果
     */
    @Override
    public int insertNursingPlan(NursingPlanDto dto)
    {
        // 保存护理计划
        NursingPlan nursingPlan = new NursingPlan();
        nursingPlan.setSortNo(dto.getSortNo().longValue());
        nursingPlan.setStatus(dto.getStatus().longValue());
        nursingPlan.setPlanName(dto.getPlanName());
        nursingPlan.setCreateTime(DateUtils.getNowDate());
        nursingPlanMapper.insertNursingPlan(nursingPlan);

        // 批量保存护理项目计划关系（仅当项目列表不为空时）
        if (dto.getProjectPlans() != null && !dto.getProjectPlans().isEmpty()) {
            nursingProjectPlanMapper.batchInsert(dto.getProjectPlans(), nursingPlan.getId());
        }
        return 1;
    }

    /**
     * 修改护理计划
     *
     * @param dto 护理计划
     * @return 结果
     */
    @Override
    public int updateNursingPlan(NursingPlanDto dto)
    {
        // 判断dto中的项目列表为空，如果不为空，则先删除护理计划与护理项目的关系，然后重新批量添加
        if (dto.getProjectPlans() != null && !dto.getProjectPlans().isEmpty()) {
            // 删除护理计划对应的护理项目列表
            nursingProjectPlanMapper.deleteByPlanId(dto.getId());

            // 批量保存护理计划对应的护理项目列表
            nursingProjectPlanMapper.batchInsert(dto.getProjectPlans(), dto.getId());
        }

        // 属性拷贝
        NursingPlan nursingPlan = new NursingPlan();
        nursingPlan.setId(dto.getId());
        nursingPlan.setSortNo(dto.getSortNo().longValue());
        nursingPlan.setPlanName(dto.getPlanName());
        nursingPlan.setStatus(dto.getStatus().longValue());
        nursingPlan.setUpdateTime(DateUtils.getNowDate());

        // 不管项目列表是否为空，都要修改护理计划
        return nursingPlanMapper.updateNursingPlan(nursingPlan);
    }

    /**
     * 批量删除护理计划（带事务，级联删除关联记录）
     * 
     * @param ids 需要删除的护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanByIds(Long[] ids)
    {
        for(Long id : ids){
            nursingProjectPlanMapper.deleteByPlanId(id);
        }
        return nursingPlanMapper.deleteNursingPlanByIds(ids);
    }

    /**
     * 删除护理计划信息
     * 
     * @param id 护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanById(Long id)
    {
        nursingProjectPlanMapper.deleteByPlanId(id);
        return nursingPlanMapper.deleteNursingPlanById(id);
    }
}
