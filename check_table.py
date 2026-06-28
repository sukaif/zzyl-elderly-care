import pymysql
db = pymysql.connect(host='localhost', user='root', password='123', database='zzyl', charset='utf8mb4')
cursor = db.cursor()
cursor.execute("DESCRIBE health_assessment")
result = cursor.fetchall()
for row in result:
    print(row)
db.close()