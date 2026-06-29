-- =============================================
-- 慧享养老服务平台 - 测试管理模块数据库脚本
-- =============================================

-- 测试用例表
CREATE TABLE IF NOT EXISTS zzyl_test_case (
    id              BIGINT AUTO_INCREMENT COMMENT '用例ID',
    case_no         VARCHAR(64)  NOT NULL COMMENT '用例编号',
    case_name       VARCHAR(200) NOT NULL COMMENT '用例名称',
    module          VARCHAR(100) DEFAULT NULL COMMENT '所属模块',
    case_type       VARCHAR(32)  DEFAULT '功能' COMMENT '用例类型(功能/接口/性能/安全)',
    precondition    TEXT         DEFAULT NULL COMMENT '前置条件',
    test_steps      TEXT         DEFAULT NULL COMMENT '测试步骤',
    expected_result TEXT         DEFAULT NULL COMMENT '预期结果',
    actual_result   TEXT         DEFAULT NULL COMMENT '实际结果',
    status          VARCHAR(16)  DEFAULT '新建' COMMENT '状态(新建/执行中/通过/失败/阻塞)',
    priority        VARCHAR(8)   DEFAULT 'P2' COMMENT '优先级(P0/P1/P2/P3)',
    executor        VARCHAR(64)  DEFAULT NULL COMMENT '执行人',
    requirement_id  VARCHAR(64)  DEFAULT NULL COMMENT '关联需求',
    bug_id          VARCHAR(64)  DEFAULT NULL COMMENT '关联缺陷',
    remark          VARCHAR(500) DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time     DATETIME     DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time     DATETIME     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_no (case_no),
    KEY idx_module (module),
    KEY idx_status (status),
    KEY idx_priority (priority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用例表';

-- 测试缺陷表
CREATE TABLE IF NOT EXISTS zzyl_test_bug (
    id                BIGINT AUTO_INCREMENT COMMENT '缺陷ID',
    bug_no            VARCHAR(64)  NOT NULL COMMENT '缺陷编号',
    bug_title         VARCHAR(200) NOT NULL COMMENT '缺陷标题',
    module            VARCHAR(100) DEFAULT NULL COMMENT '所属模块',
    severity          VARCHAR(16)  DEFAULT '一般' COMMENT '严重程度(致命/严重/一般/建议)',
    priority          VARCHAR(8)   DEFAULT 'P2' COMMENT '优先级(P0/P1/P2/P3)',
    status            VARCHAR(16)  DEFAULT '新建' COMMENT '状态(新建/已确认/处理中/已解决/已关闭/ reopened)',
    bug_description   TEXT         DEFAULT NULL COMMENT '缺陷描述',
    reproduce_steps   TEXT         DEFAULT NULL COMMENT '复现步骤',
    expected_result   TEXT         DEFAULT NULL COMMENT '预期结果',
    actual_result     TEXT         DEFAULT NULL COMMENT '实际结果',
    reporter          VARCHAR(64)  DEFAULT NULL COMMENT '提交人',
    assignee          VARCHAR(64)  DEFAULT NULL COMMENT '处理人',
    resolution        VARCHAR(500) DEFAULT NULL COMMENT '解决方案',
    case_id           VARCHAR(64)  DEFAULT NULL COMMENT '关联用例',
    remark            VARCHAR(500) DEFAULT NULL COMMENT '备注',
    create_by         VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time       DATETIME     DEFAULT NULL COMMENT '创建时间',
    update_by         VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time       DATETIME     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_bug_no (bug_no),
    KEY idx_bug_module (module),
    KEY idx_bug_status (status),
    KEY idx_severity (severity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试缺陷表';

-- 测试计划表
CREATE TABLE IF NOT EXISTS zzyl_test_plan (
    id              BIGINT AUTO_INCREMENT COMMENT '计划ID',
    plan_name       VARCHAR(200) NOT NULL COMMENT '计划名称',
    plan_version    VARCHAR(64)  DEFAULT NULL COMMENT '计划版本',
    module          VARCHAR(100) DEFAULT NULL COMMENT '测试模块',
    start_date      DATETIME     DEFAULT NULL COMMENT '开始日期',
    end_date        DATETIME     DEFAULT NULL COMMENT '结束日期',
    status          VARCHAR(16)  DEFAULT '未开始' COMMENT '状态(未开始/进行中/已完成/已暂停)',
    total_cases     INT          DEFAULT 0 COMMENT '用例总数',
    passed_cases    INT          DEFAULT 0 COMMENT '通过数',
    failed_cases    INT          DEFAULT 0 COMMENT '失败数',
    blocked_cases   INT          DEFAULT 0 COMMENT '阻塞数',
    executor        VARCHAR(64)  DEFAULT NULL COMMENT '执行人',
    description     TEXT         DEFAULT NULL COMMENT '计划描述',
    remark          VARCHAR(500) DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time     DATETIME     DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time     DATETIME     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_plan_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试计划表';

-- 插入示例测试用例
INSERT INTO zzyl_test_case (case_no, case_name, module, case_type, test_steps, expected_result, status, priority, executor)
VALUES
('TC-001', '用户登录-正确账号密码', '系统管理-登录', '功能',
 '1. 打开登录页面\n2. 输入正确用户名admin\n3. 输入正确密码admin123\n4. 点击登录按钮',
 '登录成功，跳转到系统首页', '通过', 'P0', '测试员'),

('TC-002', '用户登录-错误密码', '系统管理-登录', '功能',
 '1. 打开登录页面\n2. 输入正确用户名admin\n3. 输入错误密码wrong\n4. 点击登录按钮',
 '登录失败，提示"用户名或密码错误"', '通过', 'P0', '测试员'),

('TC-003', '入住登记-老人信息录入', '入住管理', '功能',
 '1. 登录系统\n2. 进入入住管理->入住登记\n3. 点击新增\n4. 填写老人基本信息\n5. 点击保存',
 '保存成功，列表显示新登记的老人信息', '通过', 'P1', '测试员'),

('TC-004', '护理等级-等级查询', '护理管理', '功能',
 '1. 登录系统\n2. 进入护理管理->护理等级\n3. 在搜索框输入等级名称\n4. 点击搜索',
 '页面正确显示匹配的护理等级数据', '通过', 'P2', '测试员'),

('TC-005', '床位管理-新增床位', '床位管理', '功能',
 '1. 登录系统\n2. 进入床位管理\n3. 选择楼层和房间\n4. 点击新增床位\n5. 填写床位信息\n6. 点击保存',
 '保存成功，床位列表显示新床位', '通过', 'P1', '测试员'),

('TC-006', '接口测试-获取用户列表', '系统管理', '接口',
 '1. 调用GET /system/user/list接口\n2. 传入分页参数pageNum=1, pageSize=10',
 '返回状态码200，数据格式正确，包含用户列表', '通过', 'P1', '测试员'),

('TC-007', '告警通知-设备异常触发告警', '智能监测', '功能',
 '1. 登录系统\n2. 模拟设备上报异常数据\n3. 查看告警记录',
 '正确记录告警信息并通知相关人员', '新建', 'P1', '测试员'),

('TC-008', '预约参观-后台记录查看', '预约管理', '功能',
 '1. 登录系统\n2. 进入预约管理模块\n3. 查看预约记录列表\n4. 查看预约详情',
 '正确显示参观预约记录及详情', '通过', 'P1', '测试员'),

('TC-009', '数据导出-用例导出Excel', '测试管理', '功能',
 '1. 登录系统\n2. 进入测试用例管理\n3. 选择用例\n4. 点击导出按钮',
 '成功下载Excel文件，内容与页面数据一致', '新建', 'P2', '测试员'),

('TC-010', 'AI体检报告-上传并分析', 'AI智能', '接口',
 '1. 上传PDF体检报告\n2. 调用AI分析接口\n3. 查看分析结果',
 'AI正确分析体检报告，返回结构化健康建议', '通过', 'P0', '测试员');

-- 插入菜单数据 (需根据实际菜单表结构调整)
-- 父菜单: 测试管理
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES ('测试管理', 0, 5, 'test', NULL, 1, 0, 'M', '0', '0', '', 'bug', 'admin', sysdate());

-- 获取测试管理菜单ID的变量方式不同数据库有差异，以下为简化写法
-- 子菜单: 测试用例管理
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '测试用例', menu_id, 1, 'testcase', 'test/case/index', 1, 0, 'C', '0', '0', 'test:case:list', 'documentation', 'admin', sysdate()
FROM sys_menu WHERE menu_name = '测试管理';

-- 子菜单: 缺陷管理
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '缺陷管理', menu_id, 2, 'bug', 'test/bug/index', 1, 0, 'C', '0', '0', 'test:bug:list', 'bug', 'admin', sysdate()
FROM sys_menu WHERE menu_name = '测试管理';

-- 子菜单: 测试计划
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '测试计划', menu_id, 3, 'plan', 'test/plan/index', 1, 0, 'C', '0', '0', 'test:plan:list', 'date', 'admin', sysdate()
FROM sys_menu WHERE menu_name = '测试管理';

-- 子菜单: 测试仪表盘
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '测试仪表盘', menu_id, 4, 'dashboard', 'test/dashboard/index', 1, 0, 'C', '0', '0', 'test:dashboard:list', 'chart', 'admin', sysdate()
FROM sys_menu WHERE menu_name = '测试管理';
