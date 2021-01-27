# 授权系统-获取公钥库,私钥库方法

## 生成密钥对
### 执行cmd命令
```
keytool -genkeypair -alias serverKey -keyalg DSA -keysize 1024 -validity 3650 -keystore serverKeys.store -keypass serverKey2020 -storepass storeKeys2020 -dname "CN=Branko Ma, OU=qqqq331792873, O=com.github, L=南京, ST=江苏, C=中国"
```
### 参数说明:
- genkeypair:生成密钥对(既有公钥又有私钥)
- alias:别名(没有秘钥对的秘钥库是没意义的,所以,会自动生成一对秘钥,这个就是指定生成密钥对的别名)
- keyalg:指定算法
- keysize:秘钥大小
- validity:有效性
- keystore:秘钥库的名字(自己设置)
- storepass:秘钥库的密码(自己设置)
- dname:元信息(自己设置)

## 导出证书
### 执行cmd命令
```
keytool -exportcert -alias serverKey -keystore serverKeys.store -storepass storeKeys2020 -file certfile.cer
```
### 参数说明:
- exportcert:导出证书
- alias:要使用哪个密钥对(上一步写的)
- keystore: 从哪个秘钥库
- storepass:秘钥库密码
- file:生成证书的名字(自己设置)

## 用证书生成公钥库
### 执行cmd命令
```
keytool -import -alias clientKey -file certfile.cer -keystore clientKeys.store -storepass storeKeys2020
```
### 参数说明:
- import:导入证书
- alias:公钥名称(自己设置)
- file:从哪个证书里导入
- keystore:公钥库(自己设置:和上面的秘钥库不一样,上面的秘钥库里公钥私钥都有,这个只有公钥)
- storepass:公钥库密码(自己设置)

ps:证书的作用就是拿来分离公钥私钥的,从证书中拿出单纯的公钥后就没用了,可以删除 certfile.cer


---
从以上几步走完,你现在应该获得到一个服务端使用的私钥库,和一个客户端使用的公钥库.  
服务端使用私钥库,生成并加密license文件.  
客户端使用公钥库解密license文件,并验证授权信息.  
如果你懒得自己操作,可以使用本目录下我们提供的默认公钥库和私钥库.用于测试.  
但实际生产使用时,请替换掉真实数据以保证安全.  