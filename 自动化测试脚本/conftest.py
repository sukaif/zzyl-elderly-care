"""
慧享养老服务平台 - Selenium自动化测试配置
"""
import pytest
import os
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class TestConfig:
    """测试配置"""
    BASE_URL = "http://localhost:80"  # 前端地址
    API_BASE_URL = "http://localhost:8080"  # 后端API地址
    ADMIN_USER = "admin"
    ADMIN_PASS = "admin123"

    # 隐式等待时间（秒）
    IMPLICIT_WAIT = 10
    EXPLICIT_WAIT = 20

    # 截图目录
    SCREENSHOT_DIR = os.path.join(os.path.dirname(__file__), "screenshots")

    @staticmethod
    def get_chrome_driver():
        """获取Chrome浏览器驱动"""
        chrome_options = Options()
        chrome_options.add_argument("--window-size=1920,1080")
        chrome_options.add_argument("--disable-notifications")
        chrome_options.add_argument("--disable-gpu")
        # chrome_options.add_argument("--headless")  # 无头模式，需要时可取消注释

        driver = webdriver.Chrome(options=chrome_options)
        driver.implicitly_wait(TestConfig.IMPLICIT_WAIT)
        driver.maximize_window()
        return driver


@pytest.fixture(scope="function")
def driver():
    """每个测试用例提供独立的浏览器实例"""
    _driver = TestConfig.get_chrome_driver()
    yield _driver
    _driver.quit()


@pytest.fixture(scope="function")
def login_driver(driver):
    """已登录状态的浏览器实例"""
    driver.get(TestConfig.BASE_URL)

    # 等待登录页面加载
    WebDriverWait(driver, TestConfig.EXPLICIT_WAIT).until(
        EC.presence_of_element_located(("xpath", "//input[@placeholder='用户名']"))
    )

    # 输入用户名
    username_input = driver.find_element("xpath", "//input[@placeholder='用户名']")
    username_input.clear()
    username_input.send_keys(TestConfig.ADMIN_USER)

    # 输入密码
    password_input = driver.find_element("xpath", "//input[@placeholder='密码']")
    password_input.clear()
    password_input.send_keys(TestConfig.ADMIN_PASS)

    # 输入验证码（需要手动处理或调用后端API获取）
    captcha_input = driver.find_element("xpath", "//input[@placeholder='验证码']")
    captcha_input.send_keys("1")  # 开发环境默认验证码

    # 点击登录
    login_button = driver.find_element("xpath", "//button[contains(@class, 'login-btn')]")
    login_button.click()

    # 等待跳转到首页
    WebDriverWait(driver, TestConfig.EXPLICIT_WAIT).until(
        EC.presence_of_element_located(("xpath", "//div[contains(@class, 'sidebar')]"))
    )

    return driver
