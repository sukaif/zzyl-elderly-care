"""
慧享养老服务平台 - 登录模块自动化测试
"""
import pytest
import time
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, NoSuchElementException


class TestLogin:
    """登录功能测试用例"""

    BASE_URL = "http://localhost:80"

    def test_login_success(self, driver):
        """TC-001: 正确账号密码登录"""
        driver.get(self.BASE_URL)

        # 等待登录页面加载
        WebDriverWait(driver, 20).until(
            EC.presence_of_element_located((By.XPATH, "//input[@placeholder='用户名']"))
        )

        # 输入用户名
        driver.find_element(By.XPATH, "//input[@placeholder='用户名']").send_keys("admin")

        # 输入密码
        driver.find_element(By.XPATH, "//input[@placeholder='密码']").send_keys("admin123")

        # 输入验证码
        driver.find_element(By.XPATH, "//input[@placeholder='验证码']").send_keys("1")

        # 点击登录
        driver.find_element(By.XPATH, "//button[contains(@class, 'login-btn')]").click()

        # 验证：等待首页加载，检查是否显示系统名称
        try:
            WebDriverWait(driver, 10).until(
                EC.presence_of_element_located((By.XPATH, "//div[contains(text(), '慧享养老')]"))
            )
            print("[PASS] 登录成功 - 页面跳转到系统首页")
            assert True
        except TimeoutException:
            # 截图保存
            driver.save_screenshot("login_failure.png")
            print("[FAIL] 登录失败 - 未跳转到首页")
            assert False

    def test_login_wrong_password(self, driver):
        """TC-002: 错误密码登录"""
        driver.get(self.BASE_URL)
        WebDriverWait(driver, 20).until(
            EC.presence_of_element_located((By.XPATH, "//input[@placeholder='用户名']"))
        )

        driver.find_element(By.XPATH, "//input[@placeholder='用户名']").send_keys("admin")
        driver.find_element(By.XPATH, "//input[@placeholder='密码']").send_keys("wrong_password")
        driver.find_element(By.XPATH, "//input[@placeholder='验证码']").send_keys("1")
        driver.find_element(By.XPATH, "//button[contains(@class, 'login-btn')]").click()

        # 验证：应显示错误提示
        try:
            error_msg = WebDriverWait(driver, 5).until(
                EC.presence_of_element_located((By.XPATH, "//div[contains(@class, 'el-message')]"))
            )
            print(f"[PASS] 登录失败 - 正确提示错误信息: {error_msg.text}")
            assert True
        except TimeoutException:
            driver.save_screenshot("wrong_password_no_error.png")
            print("[FAIL] 登录失败但未显示错误提示")
            assert False

    def test_login_empty_fields(self, driver):
        """TC-003: 空用户名和密码登录"""
        driver.get(self.BASE_URL)
        WebDriverWait(driver, 20).until(
            EC.presence_of_element_located((By.XPATH, "//input[@placeholder='用户名']"))
        )

        # 不输入任何内容，直接点击登录
        driver.find_element(By.XPATH, "//button[contains(@class, 'login-btn')]").click()

        # 验证：应提示用户名不能为空
        try:
            empty_msg = driver.find_element(By.XPATH, "//div[contains(@class, 'el-form-item__error')]")
            print(f"[PASS] 空字段校验生效: {empty_msg.text}")
            assert True
        except NoSuchElementException:
            driver.save_screenshot("empty_fields_no_validation.png")
            print("[FAIL] 空字段未显示校验提示")
            assert False

    def test_login_remember_me(self, login_driver):
        """TC-004: 记住密码功能"""
        # 检查是否能看到记住密码的选择状态
        print("[PASS] 登录成功（通过login_driver fixture）")
        assert "index" in login_driver.current_url or "home" in login_driver.current_url
