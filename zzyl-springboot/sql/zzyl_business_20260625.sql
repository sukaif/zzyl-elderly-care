-- ----------------------------
-- 入住办理业务模块表结构
-- 适用数据库：MySQL 8.0+
-- 基于第8章-入住办理-后端接口开发文档设计
-- ----------------------------

-- ----------------------------
-- 1、老人表 (elder)
-- 存储入住老人的基本信息
-- ----------------------------
drop table if exists elder;
create table elder (
  id              bigint(20)    not null auto_increment    comment '主键ID',
  name            varchar(30)   not null                   comment '老人姓名',
  age             int(3)        default null               comment '年龄',
  gender          char(1)       default '0'                comment '性别（0男 1女 2未知）',
  id_card_no      varchar(18)   default ''                 comment '身份证号',
  phone           varchar(11)   default ''                 comment '联系电话',
  address         varchar(100)  default ''                 comment '家庭住址',
  image           varchar(200)  default ''                 comment '老人照片',
  create_by       varchar(64)   default ''                 comment '创建者',
  create_time     datetime                                 comment '创建时间',
  update_by       varchar(64)   default ''                 comment '更新者',
  update_time     datetime                                 comment '更新时间',
  remark          varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '老人表';

-- ----------------------------
-- 2、楼栋表 (floor)
-- 存储养老院楼栋信息
-- ----------------------------
drop table if exists floor;
create table floor (
  id              bigint(20)    not null auto_increment    comment '主键ID',
  name            varchar(30)   not null                   comment '楼栋名称',
  sort_order      int(4)        default 0                  comment '显示顺序',
  status          char(1)       default '0'                comment '状态（0正常 1停用）',
  create_by       varchar(64)   default ''                 comment '创建者',
  create_time     datetime                                 comment '创建时间',
  update_by       varchar(64)   default ''                 comment '更新者',
  update_time     datetime                                 comment '更新时间',
  remark          varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '楼栋表';

-- ----------------------------
-- 3、房间表 (room)
-- 存储楼栋下的房间信息
-- ----------------------------
drop table if exists room;
create table room (
  id              bigint(20)    not null auto_increment    comment '主键ID',
  floor_id        bigint(20)    not null                   comment '所属楼栋ID',
  room_number     varchar(20)   not null                   comment '房间号',
  sort_order      int(4)        default 0                  comment '显示顺序',
  status          char(1)       default '0'                comment '状态（0正常 1停用）',
  create_by       varchar(64)   default ''                 comment '创建者',
  create_time     datetime                                 comment '创建时间',
  update_by       varchar(64)   default ''                 comment '更新者',
  update_time     datetime                                 comment '更新时间',
  remark          varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '房间表';

-- ----------------------------
-- 4、床位表 (bed)
-- 存储房间下的床位信息
-- ----------------------------
drop table if exists bed;
create table bed (
  id              bigint(20)    not null auto_increment    comment '主键ID',
  room_id         bigint(20)    not null                   comment '所属房间ID',
  bed_number      varchar(20)   not null                   comment '床位号',
  status          char(1)       default '0'                comment '状态（0空闲 1已入住 2维修）',
  price           decimal(10,2) default 0.00              comment '床位价格/月',
  sort_order      int(4)        default 0                  comment '显示顺序',
  create_by       varchar(64)   default ''                 comment '创建者',
  create_time     datetime                                 comment '创建时间',
  update_by       varchar(64)   default ''                 comment '更新者',
  update_time     datetime                                 comment '更新时间',
  remark          varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '床位表';

-- ----------------------------
-- 5、入住表 (check_in)
-- 存储老人的入住登记信息
-- ----------------------------
drop table if exists check_in;
create table check_in (
  id                bigint(20)    not null auto_increment    comment '主键ID',
  elder_id          bigint(20)    not null                   comment '老人ID',
  nursing_level_id  bigint(20)    default null               comment '护理等级ID',
  check_in_date     date          default null               comment '入住日期',
  status            char(1)       default '0'                comment '状态（0未入住 1已入住 2已退住）',
  family_info       text          default null               comment '家属信息（JSON格式）',
  create_by         varchar(64)   default ''                 comment '创建者',
  create_time       datetime                                 comment '创建时间',
  update_by         varchar(64)   default ''                 comment '更新者',
  update_time       datetime                                 comment '更新时间',
  remark            varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '入住表';

-- ----------------------------
-- 6、入住费用表 (check_in_cost)
-- 存储入住相关的费用和床位信息
-- ----------------------------
drop table if exists check_in_cost;
create table check_in_cost (
  id              bigint(20)    not null auto_increment    comment '主键ID',
  check_in_id     bigint(20)    not null                   comment '入住ID',
  bed_id          bigint(20)    default null               comment '床位ID',
  nursing_fee     decimal(10,2) default 0.00              comment '护理费用/月',
  bed_price       decimal(10,2) default 0.00              comment '床位价格/月',
  total_cost      decimal(10,2) default 0.00              comment '总费用/月',
  pre_pay         decimal(10,2) default 0.00              comment '预交费用',
  create_by       varchar(64)   default ''                 comment '创建者',
  create_time     datetime                                 comment '创建时间',
  update_by       varchar(64)   default ''                 comment '更新者',
  update_time     datetime                                 comment '更新时间',
  remark          varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '入住费用表';

-- ----------------------------
-- 7、合同表 (contract)
-- 存储入住签约合同信息
-- ----------------------------
drop table if exists contract;
create table contract (
  id              bigint(20)    not null auto_increment    comment '主键ID',
  contract_no     varchar(30)   not null                   comment '合同编号',
  elder_id        bigint(20)    not null                   comment '老人ID',
  check_in_id     bigint(20)    default null               comment '入住ID',
  sign_date       date          default null               comment '签约日期',
  start_date      date          default null               comment '合同开始日期',
  end_date        date          default null               comment '合同结束日期',
  content         text          default null               comment '合同内容',
  status          char(1)       default '0'                comment '状态（0待签署 1已签署 2已到期）',
  create_by       varchar(64)   default ''                 comment '创建者',
  create_time     datetime                                 comment '创建时间',
  update_by       varchar(64)   default ''                 comment '更新者',
  update_time     datetime                                 comment '更新时间',
  remark          varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '合同表';

-- ----------------------------
-- 初始化 - 示例数据
-- ----------------------------

-- 楼栋数据
insert into floor values(1, 'A栋',  1, '0', 'admin', sysdate(), '', null, '');
insert into floor values(2, 'B栋',  2, '0', 'admin', sysdate(), '', null, '');
insert into floor values(3, 'C栋',  3, '0', 'admin', sysdate(), '', null, '');

-- 房间数据（A栋）
insert into room values(1, 1, 'A101', 1, '0', 'admin', sysdate(), '', null, '');
insert into room values(2, 1, 'A102', 2, '0', 'admin', sysdate(), '', null, '');
insert into room values(3, 1, 'A103', 3, '0', 'admin', sysdate(), '', null, '');

-- 房间数据（B栋）
insert into room values(4, 2, 'B201', 1, '0', 'admin', sysdate(), '', null, '');
insert into room values(5, 2, 'B202', 2, '0', 'admin', sysdate(), '', null, '');
insert into room values(6, 2, 'B203', 3, '0', 'admin', sysdate(), '', null, '');

-- 房间数据（C栋）
insert into room values(7, 3, 'C301', 1, '0', 'admin', sysdate(), '', null, '');
insert into room values(8, 3, 'C302', 2, '0', 'admin', sysdate(), '', null, '');

-- 床位数据（A栋房间床位）
insert into bed values(1, 1, 'A101-1', '0', 2500.00, 1, 'admin', sysdate(), '', null, '');
insert into bed values(2, 1, 'A101-2', '0', 2500.00, 2, 'admin', sysdate(), '', null, '');
insert into bed values(3, 2, 'A102-1', '0', 2000.00, 1, 'admin', sysdate(), '', null, '');
insert into bed values(4, 2, 'A102-2', '0', 2000.00, 2, 'admin', sysdate(), '', null, '');
insert into bed values(5, 3, 'A103-1', '0', 3000.00, 1, 'admin', sysdate(), '', null, '');
insert into bed values(6, 3, 'A103-2', '0', 3000.00, 2, 'admin', sysdate(), '', null, '');

-- 床位数据（B栋房间床位）
insert into bed values(7, 4, 'B201-1', '0', 1800.00, 1, 'admin', sysdate(), '', null, '');
insert into bed values(8, 4, 'B201-2', '0', 1800.00, 2, 'admin', sysdate(), '', null, '');
insert into bed values(9, 5, 'B202-1', '0', 2200.00, 1, 'admin', sysdate(), '', null, '');
insert into bed values(10, 5, 'B202-2', '0', 2200.00, 2, 'admin', sysdate(), '', null, '');
insert into bed values(11, 6, 'B203-1', '0', 2800.00, 1, 'admin', sysdate(), '', null, '');

-- 床位数据（C栋房间床位）
insert into bed values(12, 7, 'C301-1', '0', 3500.00, 1, 'admin', sysdate(), '', null, '');
insert into bed values(13, 7, 'C301-2', '0', 3500.00, 2, 'admin', sysdate(), '', null, '');
insert into bed values(14, 8, 'C302-1', '0', 3200.00, 1, 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 8、护理项目表 (nursing_project)
-- 存储可执行的护理服务项目
-- ----------------------------
drop table if exists nursing_project;
create table nursing_project (
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
-- 9、护理计划表 (nursing_plan)
-- 存储护理计划方案
-- ----------------------------
drop table if exists nursing_plan;
create table nursing_plan (
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
-- 10、护理计划项目关联表 (nursing_project_plan)
-- 存储护理计划包含的护理项目及执行规则
-- ----------------------------
drop table if exists nursing_project_plan;
create table nursing_project_plan (
  id                bigint(20)    not null auto_increment    comment '编号',
  plan_id           bigint(20)    default null               comment '计划id',
  project_id        bigint(20)    default null               comment '项目id',
  execute_time      varchar(20)   default null               comment '计划执行时间(HH:mm:ss)',
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
-- 11、护理等级表 (nursing_level)
-- 存储护理等级定义
-- ----------------------------
drop table if exists nursing_level;
create table nursing_level (
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

-- 护理项目示例数据
insert into nursing_project values(1, '测量血压', 1, '次', 15.00, null, '测量并记录血压值', '1', 'admin', sysdate(), '', null, '');
insert into nursing_project values(2, '测量体温', 2, '次', 5.00, null, '测量并记录体温', '1', 'admin', sysdate(), '', null, '');
insert into nursing_project values(3, '翻身拍背', 3, '次', 20.00, null, '协助翻身并拍背', '1', 'admin', sysdate(), '', null, '');
insert into nursing_project values(4, '口腔护理', 4, '次', 10.00, null, '进行口腔清洁护理', '1', 'admin', sysdate(), '', null, '');
insert into nursing_project values(5, '喂药服务', 5, '次', 8.00, null, '按时喂药并记录', '1', 'admin', sysdate(), '', null, '');

-- 护理计划示例数据
insert into nursing_plan values(1, 1, '一级护理计划', '1', 'admin', sysdate(), '', null, '');
insert into nursing_plan values(2, 2, '二级护理计划', '1', 'admin', sysdate(), '', null, '');

-- 护理计划项目关联示例
insert into nursing_project_plan values(1, 1, 1, '08:00:00', '0', 2, 'admin', sysdate(), '', null, '');
insert into nursing_project_plan values(2, 1, 2, '06:00:00', '0', 3, 'admin', sysdate(), '', null, '');
insert into nursing_project_plan values(3, 1, 3, '10:00:00', '0', 4, 'admin', sysdate(), '', null, '');
insert into nursing_project_plan values(4, 2, 4, '09:00:00', '0', 1, 'admin', sysdate(), '', null, '');
insert into nursing_project_plan values(5, 2, 5, '12:00:00', '0', 1, 'admin', sysdate(), '', null, '');

-- 护理等级示例数据
insert into nursing_level values(1, '一级护理', 1, 3000.00, '1', '适用于完全自理老人', 'admin', sysdate(), '', null, '');
insert into nursing_level values(2, '二级护理', 2, 5000.00, '1', '适用于半自理老人', 'admin', sysdate(), '', null, '');
insert into nursing_level values(3, '三级护理', 1, 8000.00, '1', '适用于完全不能自理老人', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 菜单数据（sys_menu），挂载在 护理管理(id=5) 目录下
-- 使用前请确认 sys_menu 表已存在且 menu_id 不冲突
-- ----------------------------
-- 入退管理目录（放在护理管理下面作为二级目录）
insert into sys_menu values('1066', '入退管理', '5', '1', 'checkin', 'nursing/checkin/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:checkin:list', 'edit', 'admin', sysdate(), '', null, '入退管理菜单');
insert into sys_menu values('1067', '退住管理', '5', '2', 'checkout', 'nursing/checkout/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:checkout:list', 'remove', 'admin', sysdate(), '', null, '退住管理菜单');
insert into sys_menu values('1068', '请假管理', '5', '3', 'leave', 'nursing/leave/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:leave:list', 'date', 'admin', sysdate(), '', null, '请假管理菜单');
insert into sys_menu values('1069', '在住管理', '5', '4', 'stay', 'nursing/stay/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:stay:list', 'monitor', 'admin', sysdate(), '', null, '在住管理菜单');
insert into sys_menu values('1070', '床位管理', '5', '5', 'bed', 'nursing/bed/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:bed:list', 'table', 'admin', sysdate(), '', null, '床位管理菜单');
insert into sys_menu values('1071', '护理等级', '5', '6', 'level', 'nursing/level/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:level:list', 'list', 'admin', sysdate(), '', null, '护理等级菜单');
insert into sys_menu values('1072', '护理计划', '5', '7', 'plan', 'nursing/plan/index', '', '', 1, 0, 'C', '0', '0', 'zzyl:plan:list', 'star', 'admin', sysdate(), '', null, '护理计划菜单');
insert into sys_menu values('1073', 'AI智能体', '5', '8', 'aiconsult', 'aiconsult/virtualagent/index', '', '', 1, 0, 'C', '0', '0', '', 'guide', 'admin', sysdate(), '', null, 'AI智能体菜单');

-- 入住管理按钮权限
insert into sys_menu values('2001', '入住查询', '1066', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2002', '入住新增', '1066', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2003', '入住修改', '1066', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2004', '入住删除', '1066', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:remove', '#', 'admin', sysdate(), '', null, '');
-- 退住管理按钮权限
insert into sys_menu values('2010', '退住查询', '1067', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2011', '退住新增', '1067', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2012', '退住修改', '1067', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2013', '退住删除', '1067', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:checkout:remove', '#', 'admin', sysdate(), '', null, '');
-- 请假管理按钮权限
insert into sys_menu values('2020', '请假查询', '1068', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2021', '请假新增', '1068', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2022', '请假修改', '1068', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2023', '请假删除', '1068', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2024', '请假审批', '1068', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:approve', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2025', '请假销假', '1068', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'zzyl:leave:complete', '#', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 12、退住记录表 (check_out)
-- 存储老人的退住申请和结算信息
-- ----------------------------
drop table if exists check_out;
create table check_out (
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
-- 13、请假记录表 (leave_record)
-- 存储老人的请假申请和审批信息
-- ----------------------------
drop table if exists leave_record;
create table leave_record (
  id                    bigint(20)    not null auto_increment    comment '主键ID',
  elder_id              bigint(20)    not null                   comment '老人ID',
  check_in_id           bigint(20)    not null                   comment '入住ID',
  leave_type            char(1)       default '0'                comment '请假类型（0外出探亲 1就医 2回家 3其他）',
  start_time            datetime      default null               comment '请假开始时间',
  expected_return_time  datetime      default null               comment '预计返回时间',
  actual_return_time    datetime      default null               comment '实际返回时间（销假时填写）',
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
-- 14、知情告示记录表 (notice_record)
-- 存储与家属的沟通记录
-- ----------------------------
drop table if exists notice_record;
create table notice_record (
  id                    bigint(20)    not null auto_increment    comment '主键ID',
  elder_id              bigint(20)    not null                   comment '老人ID',
  family_name           varchar(30)   default null               comment '家属姓名',
  communication_type    char(1)       default '0'                comment '沟通方式（0电话 1面谈 2微信 3其他）',
  communication_time    datetime      default null               comment '沟通时间',
  reason                varchar(500)  default null               comment '沟通原因',
  result                varchar(500)  default null               comment '沟通结果',
  communicator          varchar(64)   default ''                 comment '沟通人（员工）',
  create_by             varchar(64)   default ''                 comment '创建者',
  create_time           datetime                                 comment '创建时间',
  update_by             varchar(64)   default ''                 comment '更新者',
  update_time           datetime                                 comment '更新时间',
  remark                varchar(500)  default null               comment '备注',
  primary key (id)
) engine=innodb auto_increment=100 comment = '知情告示记录表';
