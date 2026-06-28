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
    
    with open('add_ai_menu.sql', 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    sql_statements = sql_content.split(';')
    
    for sql in sql_statements:
        sql = sql.strip()
        if sql:
            try:
                cursor.execute(sql)
                conn.commit()
                print(f"执行成功: {sql[:50]}...")
            except Exception as e:
                print(f"执行失败: {sql[:50]}...")
                print(f"错误: {e}")
                conn.rollback()
    
    cursor.close()
    conn.close()
    print("所有SQL执行完成")

except Exception as e:
    print(f"连接数据库失败: {e}")