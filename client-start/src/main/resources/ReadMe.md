# 授权系统使用方法

## 如何使用

- ### 引入项目
```
<dependency>
	<groupId>com.github.qqqq331792873.mbblicense</groupId>
	<artifactId>client-start</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```

- ### 在您的application.yml文件中配置参数
```
mbblicense:
  client:
    resource: C:/Users/马冰冰/Desktop/绝对路径/clientKeys.store
    storePwd: storeKeys2020
    alias: clientKey
    subject: mbblicense
    licPath: C:/Users/马冰冰/Desktop/绝对路径/license.lic
    debugger: true
```

- ### 将授权文件放在配置中描述的位置
    + clientKeys.store
    + license.lic
    
- ### 在您的项目中使用安装/验证功能
    您可以通过在任何地方引入管理器,直接操作授权文件信息.
```
@Resource
ClientLicenseManager clientLicenseManager;
```

- ### 启动项目
    现在您可以启动您的项目了

## 检查时机
    1. 您可以在项目启动监听器中进行安装和检查.并在检查未通过时抛出异常或跳转到提醒过期的页面.
    2. 也可以在一些拦截器,如登录拦截器中进行检查.并跳转到续费页面.
    3. 定时器我们也在我们建议的方式中.每天凌晨的时候进行检查可以降低授权系统的频繁检查.
    4. 其他:请发挥您的想象自行处理.
    
## 一些小建议
    我们提供了默认的公钥库和私钥库.但这有使您的项目被破解的风险.在投入生产前,请使用keytool替换掉默认的秘钥库.