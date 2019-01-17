# LogView

#### 项目介绍
ws: tail -f 查看服务器日志功能 

#### 使用方式
spring boot 项目直接 java -jar LogView.jar 启动即可,没用数据库!右侧为服务器当前的路径的文件，文件夹可以点击！想看哪个日志，
结合上面的路径放入待查看Input查看。
项目分为"宿主机服务器"和"远程服务器" 两种查看方式,只支持linux tail -f  *.log 查看日志使用
1.宿主机服务器查看方式：当前项目部署的机器其他项目日志查看
2.远程机服务器查看方式：宿主机ganymed-SSH2连接目标服务器
http://127.0.0.1:7003/logView/ 回车就可访问默认密码 admin admin123
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/094951_9830af6a_1976963.png "login.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0117/095009_d81a484f_1976963.png "2.png")
![项目截图](https://images.gitee.com/uploads/images/2018/0831/103320_cbe28165_1976963.png "项目截图.png")