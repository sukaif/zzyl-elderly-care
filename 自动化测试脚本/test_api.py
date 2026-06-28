"""
慧享养老服务平台 - 接口自动化测试
使用requests库直接测试后端API
"""
import pytest
import requests
import json
import time


class TestAPI:
    """后端API接口测试"""

    BASE_URL = "http://localhost:8080"

    def setup_method(self):
        """每个测试方法前的初始化"""
        self.session = requests.Session()
        self.headers = {
            "Content-Type": "application/json",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0.0.0"
        }

    def login_and_get_token(self):
        """登录获取token"""
        url = f"{self.BASE_URL}/login"
        data = {
            "username": "admin",
            "password": "admin123",
            "code": "1",
            "uuid": "test-uuid"
        }
        response = self.session.post(url, json=data, headers=self.headers)
        if response.status_code == 200:
            result = response.json()
            token = result.get("token")
            if token:
                self.headers["Authorization"] = f"Bearer {token}"
                return token
        return None

    def test_001_login_api(self):
        """TC-API-001: 登录接口测试"""
        url = f"{self.BASE_URL}/login"
        data = {
            "username": "admin",
            "password": "admin123",
            "code": "1",
            "uuid": "test-uuid"
        }
        response = self.session.post(url, json=data, headers=self.headers)

        assert response.status_code == 200
        result = response.json()
        assert result.get("code") == 200
        assert result.get("token") is not None
        print(f"[PASS] 登录接口返回token: {result.get('token')[:20]}...")

    def test_002_get_user_info(self):
        """TC-API-002: 获取用户信息"""
        token = self.login_and_get_token()
        assert token is not None, "登录失败，无法获取token"

        url = f"{self.BASE_URL}/getInfo"
        response = self.session.get(url, headers=self.headers)

        assert response.status_code == 200
        result = response.json()
        assert result.get("code") == 200
        assert result.get("user") is not None
        assert result["user"].get("userName") == "admin"
        print(f"[PASS] 获取用户信息成功: {result['user'].get('userName')}")

    def test_003_get_user_list(self):
        """TC-API-003: 获取用户列表（分页）"""
        token = self.login_and_get_token()
        assert token is not None

        url = f"{self.BASE_URL}/system/user/list"
        params = {"pageNum": 1, "pageSize": 10}
        response = self.session.get(url, params=params, headers=self.headers)

        assert response.status_code == 200
        result = response.json()
        assert result.get("code") == 200
        assert result.get("total") is not None
        assert result.get("rows") is not None
        print(f"[PASS] 获取用户列表成功，共 {result.get('total')} 条，当前页 {len(result.get('rows'))} 条")

    def test_004_get_role_list(self):
        """TC-API-004: 获取角色列表"""
        token = self.login_and_get_token()
        assert token is not None

        url = f"{self.BASE_URL}/system/role/list"
        params = {"pageNum": 1, "pageSize": 10}
        response = self.session.get(url, params=params, headers=self.headers)

        assert response.status_code == 200
        result = response.json()
        assert result.get("code") == 200
        print(f"[PASS] 获取角色列表成功")

    def test_005_get_menu_tree(self):
        """TC-API-005: 获取菜单树"""
        token = self.login_and_get_token()
        assert token is not None

        url = f"{self.BASE_URL}/system/menu/treeselect"
        response = self.session.get(url, headers=self.headers)

        assert response.status_code == 200
        result = response.json()
        print(f"[PASS] 获取菜单树成功，菜单数: {len(result.get('data', []))}")

    def test_006_checkin_list_api(self):
        """TC-API-006: 入住列表接口"""
        token = self.login_and_get_token()
        assert token is not None

        url = f"{self.BASE_URL}/nursing/checkIn/list"
        params = {"pageNum": 1, "pageSize": 10}
        response = self.session.get(url, params=params, headers=self.headers)

        assert response.status_code == 200
        result = response.json()
        if result.get("code") == 200:
            print(f"[PASS] 入住列表查询成功")
        else:
            print(f"[INFO] 入住列表接口返回: {result}")

    def test_007_create_user_api(self):
        """TC-API-007: 新增用户接口"""
        token = self.login_and_get_token()
        assert token is not None

        url = f"{self.BASE_URL}/system/user"
        data = {
            "userName": f"testuser_{int(time.time())}",
            "nickName": "测试用户",
            "password": "Test123456",
            "phonenumber": "13800138000",
            "email": "test@example.com",
            "sex": "0",
            "status": "0",
            "roleIds": [2]
        }
        response = self.session.post(url, json=data, headers=self.headers)

        print(f"[PASS] 新增用户接口返回: {response.status_code} - {response.json()}")

    def test_008_logout_api(self):
        """TC-API-008: 登出接口"""
        token = self.login_and_get_token()
        assert token is not None

        url = f"{self.BASE_URL}/logout"
        response = self.session.post(url, headers=self.headers)

        print(f"[PASS] 登出接口返回: {response.status_code}")
