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
    
    cursor.execute("SELECT menu_id, menu_name, path, parent_id FROM sys_menu WHERE parent_id IS NULL")
    results = cursor.fetchall()
    
    if results:
        print("发现以下菜单的parent_id为NULL:")
        for row in results:
            print(f"ID: {row[0]}, Name: {row[1]}, Path: {row[2]}")
        
        print("\n尝试修复...")
        for row in results:
            menu_id = row[0]
            menu_name = row[1]
            
            cursor.execute("""
                SELECT menu_id FROM sys_menu 
                WHERE menu_name LIKE %s AND menu_type = 'M' AND menu_id != %s
                LIMIT 1
            """, (f'%{menu_name[:2]}%', menu_id))
            parent = cursor.fetchone()
            
            if parent:
                print(f"将 {menu_name}(id={menu_id}) 的parent_id设为 {parent[0]}")
                cursor.execute("UPDATE sys_menu SET parent_id = %s WHERE menu_id = %s", (parent[0], menu_id))
            else:
                print(f"未找到 {menu_name}(id={menu_id}) 的父菜单，设为0")
                cursor.execute("UPDATE sys_menu SET parent_id = 0 WHERE menu_id = %s", (menu_id,))
        
        conn.commit()
        print("\n修复完成")
    else:
        print("没有发现parent_id为NULL的菜单")
    
    cursor.close()
    conn.close()

except Exception as e:
    print(f"错误: {e}")