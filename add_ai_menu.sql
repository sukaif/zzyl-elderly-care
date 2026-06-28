SET @aiParentId := (SELECT menu_id FROM sys_menu WHERE path = 'aiconsult' AND menu_type = 'M' LIMIT 1);

INSERT INTO sys_menu (
  menu_name, parent_id, order_num, path, component, query, route_name,
  is_frame, is_cache, menu_type, visible, status, perms, icon,
  create_by, create_time, remark
) VALUES (
  '小智健康',
  @aiParentId,
  1,
  'virtualagent',
  'aiconsult/virtualagent',
  '',
  'VirtualAgent',
  1,
  0,
  'C',
  '0',
  '0',
  'aiconsult:virtualagent:view',
  'user',
  'admin',
  SYSDATE(),
  '小智健康（AI健康咨询）'
);

SET @newMenuId := LAST_INSERT_ID();

INSERT INTO sys_role_menu(role_id, menu_id) VALUES (1, @newMenuId);
INSERT INTO sys_role_menu(role_id, menu_id) VALUES (2, @newMenuId);