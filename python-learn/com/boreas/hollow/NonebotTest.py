import nonebot
from nonebot.adapters.cqhttp import Bot as CQHTTPBot

nonebot.init()
driver = nonebot.get_driver()
## 加载 NoneBot 内置的 CQHTTP 协议适配组件register_adapter 的第一个参数我们传入了一个字符串，该字符串将会在后文 配置 CQHTTP 协议端 时使用。
driver.register_adapter("cqhttp", CQHTTPBot)
## 加载 NoneBot 内置的插件
nonebot.load_builtin_plugins()

if __name__ == "__main__":
    nonebot.run()