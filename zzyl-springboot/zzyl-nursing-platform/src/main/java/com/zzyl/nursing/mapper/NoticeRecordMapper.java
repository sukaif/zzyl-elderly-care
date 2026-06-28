package com.zzyl.nursing.mapper;

import com.zzyl.nursing.domain.NoticeRecord;
import java.util.List;

/**
 * 知情告示记录Mapper接口
 *
 * @author ruoyi
 * @date 2026-06-26
 */
public interface NoticeRecordMapper
{
    NoticeRecord selectNoticeRecordById(Long id);

    List<NoticeRecord> selectNoticeRecordList(NoticeRecord noticeRecord);

    int insertNoticeRecord(NoticeRecord noticeRecord);

    int updateNoticeRecord(NoticeRecord noticeRecord);

    int deleteNoticeRecordById(Long id);

    int deleteNoticeRecordByIds(Long[] ids);
}
