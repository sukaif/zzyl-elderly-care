-- =============================================
-- 慧享养老系统 - 新增表结构迁移脚本（安全版）
-- 仅添加不存在的表，不会影响已有数据
-- =============================================

-- ----------------------------
-- 1、护理项目表
-- ----------------------------
CREATE TABLE IF NOT EXISTS nursing_project (
  id                bigint(20)    not null auto_increment    comment '编号',
  name              varchar(100)  default null               comment '名称',
  order_no          bigint(20)    default null               comment '排序号',
  unit              varchar(20)   default null               comment '单位',
  price             decimal(10,2) default 0.00              comment '价格',
  image             varchar(200)  default null               comment '图片',
  nursing_requirement varchar(500) default null              comment '护理要求',
  status            char(1)       default '1'                comment '状态（0禁用 1启用）',
  create_by         varchar(64)   default ''                 comment '创建者',
  create_time       datetime                                 comment '创建时间',
  update_by         varchar(64)   default ''                 comment '更新者',
  update_time       datetime                                 comment '更新时间',
  remark            varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '护理项目表';

-- ----------------------------
-- 2、护理计划表
-- ----------------------------
CREATE TABLE IF NOT EXISTS nursing_plan (
  id                bigint(20)    not null auto_increment    comment '编号',
  sort_no           bigint(20)    default null               comment '排序号',
  plan_name         varchar(100)  default null               comment '名称',
  status            char(1)       default '1'                comment '状态（0禁用 1启用）',
  create_by         varchar(64)   default ''                 comment '创建者',
  create_time       datetime                                 comment '创建时间',
  update_by         varchar(64)   default ''                 comment '更新者',
  update_time       datetime                                 comment '更新时间',
  remark            varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '护理计划表';

-- ----------------------------
-- 3、护理计划项目关联表
-- ----------------------------
CREATE TABLE IF NOT EXISTS nursing_project_plan (
  id                bigint(20)    not null auto_increment    comment '编号',
  plan_id           bigint(20)    default null               comment '计划id',
  project_id        bigint(20)    default null               comment '项目id',
  execute_time      varchar(20)   default null               comment '计划执行时间',
  execute_cycle     char(1)       default '0'                comment '执行周期（0天 1周 2月）',
  execute_frequency bigint(20)    default 1                  comment '执行频次(次)',
  create_by         varchar(64)   default ''                 comment '创建者',
  create_time       datetime                                 comment '创建时间',
  update_by         varchar(64)   default ''                 comment '更新者',
  update_time       datetime                                 comment '更新时间',
  remark            varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '护理计划项目关联表';

-- ----------------------------
-- 4、护理等级表
-- ----------------------------
CREATE TABLE IF NOT EXISTS nursing_level (
  id                bigint(20)    not null auto_increment    comment '主键ID',
  name              varchar(50)   default null               comment '等级名称',
  plan_id           bigint(20)    default null               comment '护理计划ID',
  fee               decimal(10,2) default 0.00              comment '护理费用',
  status            char(1)       default '1'                comment '状态（0禁用 1启用）',
  description       varchar(500)  default null               comment '等级说明',
  create_by         varchar(64)   default ''                 comment '创建者',
  create_time       datetime                                 comment '创建时间',
  update_by         varchar(64)   default ''                 comment '更新者',
  update_time       datetime                                 comment '更新时间',
  remark            varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '护理等级表';

-- ----------------------------
-- 5、退住记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS check_out (
  id                bigint(20)    not null auto_increment    comment '主键ID',
  check_in_id       bigint(20)    not null                   comment '入住ID',
  elder_id          bigint(20)    not null                   comment '老人ID',
  checkout_type     char(1)       default '0'                comment '退住类型（0个人原因 1病情转院 2去世 3其他）',
  checkout_date     date          default null               comment '申请退住日期',
  actual_leave_date date         default null               comment '实际离院日期',
  reason            varchar(500)  default null               comment '退住原因',
  total_charged     decimal(10,2) default 0.00              comment '已缴费用合计',
  total_deducted    decimal(10,2) default 0.00              comment '应扣费用合计',
  refund_amount     decimal(10,2) default 0.00              comment '应退金额',
  arrears           decimal(10,2) default 0.00              comment '欠款金额',
  status            char(1)       default '0'                comment '状态（0待结算 1已结算 2已完成）',
  create_by         varchar(64)   default ''                 comment '创建者',
  create_time       datetime                                 comment '创建时间',
  update_by         varchar(64)   default ''                 comment '更新者',
  update_time       datetime                                 comment '更新时间',
  remark            varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '退住记录表';

-- ----------------------------
-- 6、请假记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS leave_record (
  id                    bigint(20)    not null auto_increment    comment '主键ID',
  elder_id              bigint(20)    not null                   comment '老人ID',
  check_in_id           bigint(20)    not null                   comment '入住ID',
  leave_type            char(1)       default '0'                comment '请假类型（0外出探亲 1就医 2回家 3其他）',
  start_time            datetime      default null               comment '请假开始时间',
  expected_return_time  datetime      default null               comment '预计返回时间',
  actual_return_time    datetime      default null               comment '实际返回时间',
  leave_days            int(4)        default 0                  comment '请假天数',
  emergency_contact     varchar(30)   default null               comment '紧急联系人',
  emergency_phone       varchar(11)   default null               comment '紧急联系人电话',
  reason                varchar(500)  default null               comment '请假事由',
  status                char(1)       default '0'                comment '状态（0待审批 1已审批 2已销假 3超时未归）',
  approver              varchar(64)   default null               comment '审批人',
  approve_time          datetime      default null               comment '审批时间',
  create_by             varchar(64)   default ''                 comment '创建者',
  create_time           datetime                                 comment '创建时间',
  update_by             varchar(64)   default ''                 comment '更新者',
  update_time           datetime                                 comment '更新时间',
  remark                varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '请假记录表';

-- ----------------------------
-- 7、知情告示记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS notice_record (
  id                    bigint(20)    not null auto_increment    comment '主键ID',
  elder_id              bigint(20)    not null                   comment '老人ID',
  family_name           varchar(30)   default null               comment '家属姓名',
  communication_type    char(1)       default '0'                comment '沟通方式（0电话 1面谈 2微信 3其他）',
  communication_time    datetime      default null               comment '沟通时间',
  reason                varchar(500)  default null               comment '沟通原因',
  result                varchar(500)  default null               comment '沟通结果',
  communicator          varchar(64)   default ''                 comment '沟通人',
  create_by             varchar(64)   default ''                 comment '创建者',
  create_time           datetime                                 comment '创建时间',
  update_by             varchar(64)   default ''                 comment '更新者',
  update_time           datetime                                 comment '更新时间',
  remark                varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '知情告示记录表';

-- ----------------------------
-- 示例数据（使用 INSERT IGNORE 避免重复）
-- ----------------------------
INSERT IGNORE INTO nursing_project (id, name, order_no, unit, price, nursing_requirement, status, create_by, create_time) VALUES
(1, '测量血压', 1, '次', 15.00, '测量并记录血压值', '1', 'admin', NOW()),
(2, '测量体温', 2, '次', 5.00, '测量并记录体温', '1', 'admin', NOW()),
(3, '翻身拍背', 3, '次', 20.00, '协助翻身并拍背', '1', 'admin', NOW()),
(4, '口腔护理', 4, '次', 10.00, '进行口腔清洁护理', '1', 'admin', NOW()),
(5, '喂药服务', 5, '次', 8.00, '按时喂药并记录', '1', 'admin', NOW());

INSERT IGNORE INTO nursing_plan (id, sort_no, plan_name, status, create_by, create_time) VALUES
(1, 1, '一级护理计划', '1', 'admin', NOW()),
(2, 2, '二级护理计划', '1', 'admin', NOW());

INSERT IGNORE INTO nursing_project_plan (id, plan_id, project_id, execute_time, execute_cycle, execute_frequency, create_by, create_time) VALUES
(1, 1, 1, '08:00:00', '0', 2, 'admin', NOW()),
(2, 1, 2, '06:00:00', '0', 3, 'admin', NOW()),
(3, 1, 3, '10:00:00', '0', 4, 'admin', NOW()),
(4, 2, 4, '09:00:00', '0', 1, 'admin', NOW()),
(5, 2, 5, '12:00:00', '0', 1, 'admin', NOW());

INSERT IGNORE INTO nursing_level (id, name, plan_id, fee, status, description, create_by, create_time) VALUES
(1, '一级护理', 1, 3000.00, '1', '适用于完全自理老人', 'admin', NOW()),
(2, '二级护理', 2, 5000.00, '1', '适用于半自理老人', 'admin', NOW()),
(3, '三级护理', 1, 8000.00, '1', '适用于完全不能自理老人', 'admin', NOW());

-- ----------------------------
-- 菜单数据（使用 INSERT IGNORE 避免重复）
-- ----------------------------
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) VALUES
(1066, '入退管理', 5, 1, 'checkin', 'nursing/checkin/index', 1, 0, 'C', '0', '0', 'zzyl:checkin:list', 'edit', 'admin', NOW()),
(1067, '退住管理', 5, 2, 'checkout', 'nursing/checkout/index', 1, 0, 'C', '0', '0', 'zzyl:checkout:list', 'remove', 'admin', NOW()),
(1068, '请假管理', 5, 3, 'leave', 'nursing/leave/index', 1, 0, 'C', '0', '0', 'zzyl:leave:list', 'date', 'admin', NOW()),
(1069, '在住管理', 5, 4, 'stay', 'nursing/stay/index', 1, 0, 'C', '0', '0', 'zzyl:stay:list', 'monitor', 'admin', NOW()),
(1070, '床位管理', 5, 5, 'bed', 'nursing/bed/index', 1, 0, 'C', '0', '0', 'zzyl:bed:list', 'table', 'admin', NOW()),
(1071, '护理等级', 5, 6, 'level', 'nursing/level/index', 1, 0, 'C', '0', '0', 'zzyl:level:list', 'list', 'admin', NOW()),
(1072, '护理计划', 5, 7, 'plan', 'nursing/plan/index', 1, 0, 'C', '0', '0', 'zzyl:plan:list', 'star', 'admin', NOW());

-- 按钮权限
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) VALUES
(2001, '入住查询', 1066, 1, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:query', '#', 'admin', NOW()),
(2002, '入住新增', 1066, 2, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:add', '#', 'admin', NOW()),
(2003, '入住修改', 1066, 3, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:edit', '#', 'admin', NOW()),
(2004, '入住删除', 1066, 4, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:remove', '#', 'admin', NOW()),
(2010, '退住查询', 1067, 1, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:query', '#', 'admin', NOW()),
(2011, '退住新增', 1067, 2, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:add', '#', 'admin', NOW()),
(2012, '退住修改', 1067, 3, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:edit', '#', 'admin', NOW()),
(2013, '退住删除', 1067, 4, '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:remove', '#', 'admin', NOW()),
(2020, '请假查询', 1068, 1, '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:query', '#', 'admin', NOW()),
(2021, '请假新增', 1068, 2, '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:add', '#', 'admin', NOW()),
(2022, '请假修改', 1068, 3, '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:edit', '#', 'admin', NOW()),
(2023, '请假删除', 1068, 4, '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:remove', '#', 'admin', NOW()),
(2024, '请假审批', 1068, 5, '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:approve', '#', 'admin', NOW()),
(2025, '请假销假', 1068, 6, '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:complete', '#', 'admin', NOW());

SELECT '✅ 迁移完成！共创建7张新表，插入示例数据和菜单记录。' AS result;
