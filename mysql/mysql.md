# mysql

## mysql安装

<https://www.cnblogs.com/zhangxl1016/articles/14646832.html>

<https://www.cnblogs.com/laumians-notes/p/9069498.html>

<https://blog.51cto.com/u_13242922/2698218>

>进程： 一段执行的程序，而程序则被用来实现用户的业务逻辑。
>
>服务：系统提供的管理进程的服务，用户可以通过“服务”来管理进程。
>
>例如：系统要执行一个程序，需要可执行程序的位置，依赖项，权限，自动/手动启动方式等等。用户可以创建一个服务来要求系统按照设定的方式来启动程序。这个服务可以被创建，启动，停止，查询等。

1. 在mysql的bin文件夹下运行cmd，输入net  stop  mysql 停止mysql服务。以下命令会移除mysql服务：         										

   ```
   mysqld remove MySQL(服务名称，在mysqld install时指定) 
   ```

2. 拷贝my.ini到下载的mysql的根目录下，my.inid 内容如下：

```ini
[mysqld]
# 设置3306端口
port=3306

# 设置mysql的安装目录
basedir=C:\Program Files\mysql-8.0.26-winx64

# 设置mysql数据库的数据的存放目录
datadir=C:\Program Files\mysql-8.0.26-winx64\Data

# 允许最大连接数
max_connections=200

# 允许连接失败的次数。
max_connect_errors=10

# 服务端使用的字符集默认为utf8mb4
character-set-server=utf8mb4

# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB

# 默认使用“mysql_native_password”插件认证
#mysql_native_password
default_authentication_plugin=mysql_native_password

[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8mb4

[client]
# 设置mysql客户端连接服务端时默认使用的端口
port=3306

#跳过密码登陆
#skip-grant-tables
```

3. 在bin目录下运行cmd在命令框中输入mysqld --initialize --console

```
要初始化数据目录(data directory)，请使用--initialize或--initialize-insecure选项调用mysqld，这取决于是希望服务器为'root'@'localhost'帐户生成随机初始密码，还是创建没有密码的帐户

mysqld --initialize --console  	//这个命令创建data目录和root用户并告诉你root初始密码  (6vIcqClzj+1
Create the default database and exit. Create a super user with a random expired password and store it into the log.

mysqld --initialize-insecure 	//这个命令会创建data目录和无密码的root用户
Create the default database and exit. Create a super user with empty password.
```

4. 创建mysql服务并启动

```
mysqld --install MYSQL2 //MYSQL2是服务名
net start MYSQL2 //先将mysql8.0的服务关闭
```

![image-20230318132933301](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230318132933301.png)

5. 修改密码
