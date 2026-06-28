package com.zzyl.nursing.service.impl;

import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.domain.NoticeRecord;
import com.zzyl.nursing.mapper.NoticeRecordMapper;
import com.zzyl.nursing.service.INoticeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知情告示记录Service业务层处理
 *
 * @author ruoyi
 * @date 2026-06-26
 */
@Service
public class NoticeRecordServiceImpl implements INoticeRecordService
{
    @Autowired
    private NoticeRecordMapper noticeRecordMapper;

    @Override
    public NoticeRecord selectNoticeRecordById(Long id)
    {
        return noticeRecordMapper.selectNoticeRecordById(id);
    }

    @Override
    public List<NoticeRecord> selectNoticeRecordList(NoticeRecord noticeRecord)
    {
        return noticeRecordMapper.selectNoticeRecordList(noticeRecord);
    }

    @Override
    @Transactional
    public int insertNoticeRecord(NoticeRecord noticeRecord)
    {
        noticeRecord.setCreateTime(DateUtils.getNowDate());
        return noticeRecordMapper.insertNoticeRecord(noticeRecord);
    }

    @Override
    @Transactional
    public int updateNoticeRecord(NoticeRecord noticeRecord)
    {
        noticeRecord.setUpdateTime(DateUtils.getNowDate());
        return noticeRecordMapper.updateNoticeRecord(noticeRecord);
    }

    @Override
    @Transactional
    public int deleteNoticeRecordByIds(Long[] ids)
    {
        return noticeRecordMapper.deleteNoticeRecordByIds(ids);
    }
}
