# 中州养老管理系统 - 自动化测试脚本

## 环境要求
- Python 3.9+
- Chrome 浏览器
- ChromeDriver (与Chrome版本匹配)
- 中州养老管理系统已启动

## 安装依赖
```bash
pip install -r requirements.txt
```

## 运行测试
```bash
# 运行所有测试
pytest -v

# 运行指定测试模块
pytest -v test_login.py
pytest -v test_checkin.py

# 生成报告
pytest -v --html=report.html --self-contained-html

# 运行并显示print输出
pytest -v -s
```
