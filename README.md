# sabotz-ftp-spring-boot-starter
自定义springboot整合ftpclient,，commons-pool2启动器starter


最近在自己瞎完的过程中需要用到FtpClient上传和下载文件到阿里云上的FTP服务器，配置连接登录十分有点繁琐，就想用池管理一下这些FtpClient，于是动手写了个整合FtpClient和commons-pool2的启动器sprig-boot-starter。

实现过程如下：

###### 1. 使用Apache-commons-pool2实现FtpClient连接池。主要包含以下内容：

1. 池化对象即FTPClient对象
2. 定义池化工厂FTPClientFactory 继承 BasePooledObjectFactory<FTPClient> ，重写create，warp，destroyObject，validateObject四个方法
3. 配置FTPClientPool作为对象池，在构造方法中注入GenericObjectPool<FTPClient> ftpClientPool，重写get 和return 两个方法。


###### 2. 自定义spring-boot-starter
1. 连接ftp服务器的参数，以及配置池的参数反正FTPProperties，和PoolProperties两个类中，加上@ConfigurationProperties配置自动读取配置文件
2. 配置自动装配类FTPAutoConfiguration，配合@EnableConfigurationProperties({FTPProperties.class, PoolProperties.class})读入两个配置类
3. 将上传下载的方法放在FTPUtils类中，在自动装配类里通过@Bean注解注入spring容器


然后maven install 导出就完工了，在需要的地方导入一下依赖，配置application.yml，注入FTPUtil就可以注解使用。

```xml
<dependency>
	<groupId>com.sabotz</groupId>
	<artifactId>sabotz-ftp-spring-boot-starter</artifactId>
	<version>1.4.0-SNAPSHOT</version>
</dependency>
```

```yaml
sabotz-ftp:
  server:
    host: ---.---.---.---
    username: ------
    password: ------
    port: --
```

```java
@Autowired
private FTPUtils ftpUtils;
```
