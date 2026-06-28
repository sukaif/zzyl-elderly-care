import pymysql

try:
    conn = pymysql.connect(
        host='localhost',
        port=3306,
        user='root',
        password='123',
        database='zzyl',
        charset='utf8mb4'
    )
    
    cursor = conn.cursor()
    
    cursor.execute("SELECT menu_id, menu_name, path, parent_id FROM sys_menu WHERE path = 'aiconsult' OR menu_name = '中州智询'")
    result = cursor.fetchone()
    
    if result:
        print(f"父菜单存在: id={result[0]}, name={result[1]}, path={result[2]}, parent_id={result[3]}")
    else:
        print("父菜单不存在，需要创建")
        cursor.execute("""
            INSERT INTO sys_menu (
                menu_name, parent_id, order_num, path, component, query, route_name,
                is_frame, is_cache, menu_type, visible, status, perms, icon,
                create_by, create_time, remark
            ) VALUES (
                '中州智询',
                0,
                10,
                'aiconsult',
                '',
                '',
                '',
                1,
                0,
                'M',
                '0',
                '0',
                '',
                'chat-line-square',
                'admin',
                SYSDATE(),
                '中州智询AI助手'
            )
        """)
        conn.commit()
        parent_id = cursor.lastrowid
        print(f"父菜单创建成功，id={parent_id}")
        
        cursor.execute("""
            INSERT INTO sys_menu (
                menu_name, parent_id, order_num, path, component, query, route_name,
                is_frame, is_cache, menu_type, visible, status, perms, icon,
                create_by, create_time, remark
            ) VALUES (
                '小智健康',
                %s,
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
            )
        """, (parent_id,))
        conn.commit()
        child_id = cursor.lastrowid
        print(f"子菜单创建成功，id={child_id}")
        
        cursor.execute("INSERT INTO sys_role_menu(role_id, menu_id) VALUES (1, %s)", (child_id,))
        cursor.execute("INSERT INTO sys_role_menu(role_id, menu_id) VALUES (2, %s)", (child_id,))
        conn.commit()
        print("角色权限已分配")
    
    cursor.close()
    conn.close()
    print("操作完成")

except Exception as e:
    print(f"错误: {e}")