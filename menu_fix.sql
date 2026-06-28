﻿﻿﻿﻿﻿SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;
SET collation_connection = utf8mb4_unicode_ci;

DELETE FROM sys_menu WHERE menu_id >= 2000;

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) VALUES
('护理管理', 0, 5, 'nursing', NULL, 1, 0, 'M', '0', '0', '', 'fa-solid fa-heart-pulse', 'admin', NOW());

SET @parentId = LAST_INSERT_ID();

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time) VALUES
('护理项目', @parentId, 1, 'project', 'nursing/project/index', 1, 0, 'C', '0', '0', 'nursing:project:list', '', 'admin', NOW()),
('护理计划', @parentId, 2, 'plan', 'nursing/plan/index', 1, 0, 'C', '0', '0', 'nursing:plan:list', '', 'admin', NOW()),
('护理等级', @parentId, 3, 'level', 'nursing/level/index', 1, 0, 'C', '0', '0', 'nursing:level:list', '', 'admin', NOW());