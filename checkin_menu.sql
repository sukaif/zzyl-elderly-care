-- 入住管理菜单 SQL
-- 父菜单ID: 5（服务管理/护理模块）

-- 入住管理菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入住管理', '5', '3', 'checkin', 'nursing/checkin/index', 1, 0, 'C', '0', '0', 'zzyl:checkin:list', '#', 'admin', sysdate(), '', null, '入住管理菜单');

SELECT @parentId := LAST_INSERT_ID();

-- 入住管理子按钮
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入住查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入住新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入住修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入住删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:checkin:remove',       '#', 'admin', sysdate(), '', null, '');

-- 楼栋管理菜单（资源管理）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('床位管理', '5', '4', 'bed', 'nursing/bed/index', 1, 0, 'C', '0', '0', 'zzyl:bed:list', '#', 'admin', sysdate(), '', null, '床位管理菜单');

SELECT @bedParentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('床位查询', @bedParentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:bed:query',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('床位新增', @bedParentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:bed:add',            '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('床位修改', @bedParentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:bed:edit',           '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('床位删除', @bedParentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'zzyl:bed:remove',         '#', 'admin', sysdate(), '', null, '');

-- 分配菜单给管理员角色（role_id=1）
INSERT IGNORE INTO sys_role_menu(role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms IN (
  'zzyl:checkin:list','zzyl:checkin:query','zzyl:checkin:add',
  'zzyl:checkin:edit','zzyl:checkin:remove',
  'zzyl:bed:list','zzyl:bed:query','zzyl:bed:add',
  'zzyl:bed:edit','zzyl:bed:remove'
);
