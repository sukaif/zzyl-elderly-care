import pymysql
db = pymysql.connect(host='localhost', user='root', password='123', database='zzyl', charset='utf8mb4')
cursor = db.cursor()
cursor.execute("UPDATE sys_menu SET component = 'aiconsult/virtualagent/index' WHERE menu_id = 2125")
db.commit()
cursor.execute("SELECT menu_id, menu_name, path, component FROM sys_menu WHERE menu_id = 2125")
result = cursor.fetchone()
print("修改后:", result)
db.close()