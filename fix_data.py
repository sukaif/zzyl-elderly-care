
import requests, json

# Login 
r = requests.get('http://localhost:8080/captchaImage')
c = r.json()
uuid = c["uuid"]

import subprocess as sp
res = sp.run(["D:/fw/Redis/redis-cli.exe", "-p", "6380", "get", f"captcha_codes:{uuid}"], capture_output=True, text=True)
code = res.stdout.strip().strip(chr(34))

r = requests.post('http://localhost:8080/login', json={"username":"admin","password":"admin123","code":code,"uuid":uuid})
token = r.json().get("token","")
headers = {"Authorization":f"Bearer {token}","Content-Type":"application/json"}

# Create check-in
data = {
    "name":"张三",
    "age":75,"gender":"0",
    "idCardNo":"110101195001011234",
    "phone":"13800138000",
    "address":"北京市朝阳区幸福路1号",
    "familyInfo":'[{"name":"张伟","relation":"儿子","phone":"13900139000"}]',
    "nursingLevelId":76,"nursingFee":50.00,
    "bedId":1,"bedPrice":2500.00,"prePay":3000.00,
    "checkInDate":"2026-06-25",
    "startDate":"2026-06-25",
    "endDate":"2027-06-25"
}
r = requests.post('http://localhost:8080/nursing/checkin', json=data, headers=headers)
print(f'Create: {r.json()}')

r = requests.get('http://localhost:8080/nursing/checkin/list?pageNum=1&pageSize=10', headers=headers)
for row in r.json().get("rows",[]):
    print(f'  ID={row["id"]} | {row["elderName"]} | {row.get("nursingLevelName","")}')
