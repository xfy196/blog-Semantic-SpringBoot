# 前端基于Semantic-ui框架后端基于SpringBoot的个人博客项目
### 技术栈
- SpringBoot
- Spring JPA
- Thymeleaf
- Mysql
- Semantic-UI
- jQuery

### 后端功能展示
- 博客列表管理

![](projectimages/image1.png)

- 博客编辑功能

![UTOOLS1580650828208.png](projectimages/image2.png)

- 博客搜索功能

![UTOOLS1580652166054.png](projectimages/image3.png)

- 分类展示功能

![UTOOLS1580652207298.png](projectimages/image4.png)

- 标签展示功能

![UTOOLS1580652241428.png](projectimages/image5.png)

- 修改密码功能

![UTOOLS1580652263184.png](projectimages/image6.png)

### 用户端展示功能

- 博客首页

![UTOOLS1580652443220.png](projectimages/image7.png)

博客首页右侧会展示分类，会按照每个分类对应的博客数量倒序排序，还有标签和罪行推荐，最新推荐按照更新时间倒序排序

- 按分类展示文章

![UTOOLS1580652528464.png](projectimages/image8.png)

- 按标签展示文章

![UTOOLS1580652571249.png](projectimages/image9.png)

- 归档（按照时间进行排序显示）

![UTOOLS1580652605092.png](projectimages/image10.png)

- 关于我

![UTOOLS1580652632851.png](projectimages/image11.png)

- 博客搜索功能

![UTOOLS1580652684285.png](projectimages/image12.png)

### 关于如何部署在服务器中

- 首先我们需要创建一个虚拟机，搭建使用Centos7版本的环境

  ```txt
  虚拟机安装完成后我们需要查看防火墙状态
  ```

  

  ```shell
  systemctl status firwalld
  ```

  

  ![](./projectimages/image13.png)

关闭防火墙

```shell
systemctl stop firewalld
```

重新查看防火墙状态

```shell
systemctl status firwalld
```



![](./projectimages/image14.png)



安装Mysql

- 首先我们需要卸载Centos7自带的mariadb数据库

```shell
yum remove -y mariadb*
```

#### 重新安装mysql

```shell
由于Centos7yum源不再支持mysql版本，因此我们需要借助wget通过rpm安装
# yum install -y wget

# wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm
# yum -y install mysql57-community-release-el7-10.noarch.rpm
```

#### 安装mysql服务器

```shell
yum -y install mysql-community-server
```

![](projectimages/image15.png)

#### 启动Mysql服务和查看服务是否正常启动

![](projectimages/image16.png)



#### 查看mysql初始密码

```shell
grep "password" /var/log/mysqld.log
```

![](projectimages/image17.png)

#### 进入数据库

![](projectimages/image18.png)

#### 修改root用户密码

```shell
ALTER USER 'root'@'localhost' IDENTIFIED BY 'new password';
```

#### 由于密码存在拟定的安全验证可能无法这只较短或着比较简单的数据，可以按照以下方法进行修改

```shell
set global validate_password_policy=0;
set global validate_password_length=1;
```

设置之后密码就可以设置如123456这样的密码了

#### 重新设置密码

```shell
ALTER USER 'root'@'localhost' IDENTIFIED BY 'new password';
```

> 扩展： Mysql完整的初始密码规则可以通过以下命令查看
>
> SHOW VARIABLES LIKE ‘validate_password%’;（注意如果mysql没有取消忽略大小写规则，是带有大小写问题的）

![](projectimages/image19.png)

#### 接下来你可以使用第三方工具连接你的数据库了

但是这是后我们的连接工具会出现错误

![](projectimages/image20.png)

我们需要让mysql服务器可以远程连接

```shell
mysql -uroot -p
use mysql;
select user, host from where user;
update user set host= '%'  WHERE user ='root';
flush privileges;
```

这时候我们就连接成功了

![](projectimages/image21.png)

然后创建一个名为blog的数据库



#### 导入bolg数据初始化文件

![](projectimages/image22.png)

![](projectimages/image23.png)

![](projectimages/image24.png)

### 安装tomcat服务

[tomcat官网下载地址](http://tomcat.apache.org/)

![](projectimages/image25.png)

这里我们选择tomcat9.0.30版本

上传服务器中

![](projectimages/image26.png)

```shell
# 解压
tar zxvf apache-tomcat-9.0.30.tar.gz
# 进入apache的webapps目录
cd apache-tomcat-9.0.30/webapps/
# 将blog.war放在这个目录下
# 进入bin目录下启动Apache服务
cd ../bin
# 启动服务
./start.sh
```

浏览器访问localhost:8080

![](projectimages/image27.png)

博客管理路径为localhost:8080/admin

初始账号：admin

初始密码：123456

如果需要更改端口，上下文路径，和域名，具体参考tomcat使用手册和域名配置手册
