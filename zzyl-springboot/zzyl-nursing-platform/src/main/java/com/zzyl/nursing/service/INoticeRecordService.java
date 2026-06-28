package com.zzyl.nursing.service;

import com.zzyl.nursing.domain.NoticeRecord;
import java.util.List;

/**
 * 知情告示记录Service接口
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public interface INoticeRecordService
{
    NoticeRecord selectNoticeRecordById(Long id);

    List<NoticeRecord> selectNoticeRecordList(NoticeRecord noticeRecord);

    int insertNoticeRecord(NoticeRecord noticeRecord);

    int updateNoticeRecord(NoticeRecord noticeRecord);

    int deleteNoticeRecordByIds(Long[] ids);
}
