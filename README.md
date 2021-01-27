# 授权系统

## 说明

这个系统包括两部分:
- server-web
- client-start

## 流程
1. 通过keytool生成私钥库和公钥库(不会自行搜索)
2. 打开server-web,修改application.yml文件.(指定私钥库位置:建议绝对路径)
3. 启动server-web,访问localhost:8080 
4. 输入参数,生成license文件.

至此,server-web的价值就结束了.<br/>
将client-start引入项目.配置公钥库和license文件位置.启动你的项目即可.具体请阅读client-start的README.md
