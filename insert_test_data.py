# -*- coding: utf-8 -*-
"""插入测试数据 - 使用正确的UTF-8编码"""
import requests
import subprocess
import json
import time

BASE = "http://localhost:8080"

# 1. 登录
r = requests.get(f"{BASE}/captchaImage")
captcha = r.json()
uuid = captcha["uuid"]

proc = subprocess.run(
    ["D:/fw/Redis/redis-cli.exe", "-p", "6380", "get", f"captcha_codes:{uuid}"],
    capture_output=True, text=True
)
code = proc.stdout.strip().strip('"')

r = requests.post(f"{BASE}/login", json={
    "username": "admin", "password": "admin123",
    "code": code, "uuid": uuid
})
token = r.json().get("token", "")
print(f"Login OK: {token[:20]}...")
headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}

# 2. 创建入住（含正确中文）
data = {
    "name": "张三",
    "age": 75,
    "gender": "0",
    "idCardNo": "110101195001011234",
    "phone": "13800138000",
    "address": "北京市朝阳区幸福路1号",
    "familyInfo": json.dumps([
        {"name": "张伟", "relation": "儿子", "phone": "13900139000"}
    ]),
    "nursingLevelId": 76,
    "nursingFee": 50.00,
    "bedId": 1,
    "bedPrice": 2500.00,
    "prePay": 3000.00,
    "checkInDate": "2026-06-25",
    "startDate": "2026-06-25",
    "endDate": "2027-06-25"
}

r = requests.post(f"{BASE}/nursing/checkin", json=data, headers=headers)
resp = r.json()
print(f"Create check-in: code={resp['code']}, msg={resp['msg']}")

if resp['code'] == 200:
    # 3. 验证
    r = requests.get(f"{BASE}/nursing/checkin/list?pageNum=1&pageSize=10", headers=headers)
    rows = r.json().get("rows", [])
    if rows:
        row = rows[0]
        print(f"  ID={row['id']} | 姓名={row['elderName']} | 年龄={row['age']} | 身份证={row['idCardNo']}")
        print(f"  护理等级={row['nursingLevelName']} | 状态={'已入住' if row['status']=='1' else '未入住'}")

        # 4. 验证入住详情
        r = requests.get(f"{BASE}/nursing/checkin/{row['id']}", headers=headers)
        detail = r.json().get("data", {})
        if detail:
            print(f"  详情: 费用={detail.get('totalCost')}元/月 | 预交={detail.get('prePay')}元")
            contract = detail.get("contract", {})
            print(f"  合同编号: {contract.get('contractNo', 'N/A')}")
    else:
        print("  No data returned!")
else:
    print(f"  Failed: {resp}")
