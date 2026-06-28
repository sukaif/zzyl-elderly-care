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
    
    cursor.execute("SELECT menu_id, menu_name, path, parent_id FROM sys_menu WHERE menu_name = '小智健康'")
    results = cursor.fetchall()
    
    print("所有小智健康菜单:")
    for row in results:
        print(f"ID: {row[0]}, Name: {row[1]}, Path: {row[2]}, ParentID: {row[3]}")
    
    for row in results:
        menu_id = row[0]
        parent_id = row[3]
        
        if parent_id == 0 or parent_id is None:
            print(f"\n删除无效的小智健康菜单(id={menu_id})")
            cursor.execute("DELETE FROM sys_role_menu WHERE menu_id = %s", (menu_id,))
            cursor.execute("DELETE FROM sys_menu WHERE menu_id = %s", (menu_id,))
    
    conn.commit()
    print("\n清理完成")
    
    cursor.execute("SELECT menu_id, menu_name, path, parent_id FROM sys_menu WHERE path = 'aiconsult' OR menu_name = '小智健康'")
    results = cursor.fetchall()
    print("\n当前菜单状态:")
    for row in results:
        print(f"ID: {row[0]}, Name: {row[1]}, Path: {row[2]}, ParentID: {row[3]}")
    
    cursor.close()
    conn.close()

except Exception as e:
    print(f"错误: {e}")