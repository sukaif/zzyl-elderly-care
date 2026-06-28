-- 入住表新增字段迁移脚本
-- 为 check_in 表添加入住办理所需的新字段

ALTER TABLE check_in
ADD COLUMN bed_id bigint(20) DEFAULT NULL COMMENT '床位ID',
ADD COLUMN deposit decimal(10,2) DEFAULT NULL COMMENT '押金',
ADD COLUMN nursing_fee decimal(10,2) DEFAULT NULL COMMENT '护理费用',
ADD COLUMN bed_price decimal(10,2) DEFAULT NULL COMMENT '床位价格',
ADD COLUMN other_fee decimal(10,2) DEFAULT NULL COMMENT '其他费用',
ADD COLUMN medical_fee decimal(10,2) DEFAULT NULL COMMENT '医保费用',
ADD COLUMN subsidy decimal(10,2) DEFAULT NULL COMMENT '政府补贴',
ADD COLUMN fee_date date DEFAULT NULL COMMENT '费用期限',
ADD COLUMN sign_date date DEFAULT NULL COMMENT '签约日期',
ADD COLUMN contract_name varchar(100) DEFAULT NULL COMMENT '合同名称',
ADD COLUMN third_party_name varchar(50) DEFAULT NULL COMMENT '丙方姓名',
ADD COLUMN third_party_phone varchar(20) DEFAULT NULL COMMENT '丙方联系方式',
ADD COLUMN contract_file varchar(255) DEFAULT NULL COMMENT '入住合同文件',
ADD COLUMN start_date date DEFAULT NULL COMMENT '合同开始日期',
ADD COLUMN end_date date DEFAULT NULL COMMENT '合同结束日期';
