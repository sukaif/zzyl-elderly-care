import pymysql
db = pymysql.connect(host='localhost', user='root', password='123', database='zzyl', charset='utf8mb4')
cursor = db.cursor()
cursor.execute("SELECT menu_id, menu_name, path, component FROM sys_menu WHERE menu_name LIKE '%小智%' OR menu_name LIKE '%中州%'")
result = cursor.fetchall()
for row in result:
    print(row)
db.close()