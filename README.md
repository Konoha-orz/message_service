# message_service
## Pulingle消息服务 
用户之间私信收发，系统消息发送，验证码，通知等服务。

###  服务功能
* 用户消息:
  * 1	私信收发
用户通过点击账号一栏的好友进入好友页面。好友信息列表显示在左侧，若好友有发送用户新消息会置顶，并显示新消息数。点击查看更多消息，则会加载历史消息记录。用户点击好友头像，右侧消息对话框弹出，显示双方发送的消息记录，和时间（后台根据双方用户id查询数据库）。用户在下方发送内容框内填入发送信息，点击发送消息按钮或按回车键进行发送。后台校验合法性，数据库插入信息，返回正确消息。
  * 2 短信验证码
当用户需要注册账号或修改密码时需要通过短信验证码校验。用户先填写手机号或者绑定手机号后，点击发送短信验证码按钮（60秒内不能再次发送）调用发送短信接口。后台


## 关于Pulingle
### Pulingle简介
* Pulingle是我们小组三人（[zkTom](https://github.com/zkTom)、[TeemoSmithLee](https://github.com/TeemoSmithLee)）的本科毕业设计项目,是基于SpringCloud微服务架构的微社交应用。我们应用意在参考微信的朋友圈功能，设计一个功能更为简单的，界面更为简洁，包含动态发布、私信发送、图片分享等功能的社交软件。</br>
### 主要功能
1.	用户通过手机号，在进行短信验证码之后进行注册。
2.	用户注册之后可以凭注册账号密码进行登录，以进行应用提供功能服务。
3.	用户可以通过简单的操作进行文章，图片动态发布。
4.	用户可以浏览自己过往发布的动态消息，以及其评论消息。
5.	用户可以浏览朋友的动态，功能类比微信的朋友圈。并且可对其动态进行评论。
6.	用户可以上传照片到相册，并可以浏览。
7.	用户好友之间可以进行消息发送，并对用户新消息的推送。
8.	用户对账号资料的修改保存
###  项目架构

对功能的分析，以及结合微服务架构设计，把整个项目大致划分为4个服务。服务之间通过服务治理实现相互调用。
* 用户服务：提供用户注册、登录、信息修改、好友系统等相关服务。
* 动态服务：用户发布动态、浏览他人如好友或二次好友动态、评论他人动态等服务。
* 图片服务：提供用户上传图片、管理图片、相册图片管理等相对应服务。
* 消息服务：用户之间私信收发，系统消息发送，验证码，通知等服务。
![功能图](https://pulingle.oss-cn-shenzhen.aliyuncs.com/%E5%8A%9F%E8%83%BD%E5%9B%BE.png)

* 架构设计 
![Pulingle架构图](https://pulingle.oss-cn-shenzhen.aliyuncs.com/Pulingle%E6%9E%B6%E6%9E%84%E5%9B%BE%20%282%29.png)

组件说明：
Spring Cloud 组件：
* Eureka Server: 提供在分布式环境下的服务发现，服务注册的功能。每个服务及组件服务都在其中注册以及发现。
* 网关服务Zuul:边缘服务工具，是提供动态路由，监控，弹性，安全等的边缘服务。
* Config Server: 配置管理开发工具包，可以让你把配置放到远程服务器，目前支持本地存储、Git以及Subversion。这里我们采用从远程Github拉取配置信息。远程配置配置文件加密存储，从ConfigServer拉取时经过解密。
* Spring Cloud Bus: 事件、消息总线，用于在集群（例如，配置变化事件）中传播状态变化
* Git Repo:在Github上提供配置信息。
* RabbitMQ: 用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面都非常的优秀。是当前最主流的消息中间件之一。
#### 技术栈
* 后端</br>
•	Maven 3</br>
•	Java 8</br>
•	SpringCloud（Eureka、Zuul、Ribbon、Feign、Config）</br>
•	SpringBoot+MyBatis</br>
•	MySQL 5.7.22</br>
•	Redis 3.0.6</br>
* 前端</br>
•	node >= 8.9.3</br>
•	npm >= 5.5.1</br>
•	vue  </br>
•	axios  </br>
•	js-cookie  </br>
•	lodash </br>
•	vuex</br>
•	es6-promise</br>
•	photoswipe</br>
•	vue-lazyload </br>
* 使用RAP2进行接口文档管理: [RAP2](https://github.com/thx/RAP)
* 使用阿里云OSS服务作图片资源空间
* 短信验证码使用阿里云短信服务
#### 项目代码链接
* 微服务</br>
用户服务:   [user-service](https://github.com/Konoha-orz/user_service)</br>
消息服务:   [message-service](https://github.com/Konoha-orz/message_service)</br>
图片服务:   [picture-service](https://github.com/Konoha-orz/picture_service)</br>
动态服务:   [moment-service](https://github.com/Konoha-orz/moment_service)</br>
* Spring Cloud 组件</br>
服务发现：[eureka_server](https://github.com/Konoha-orz/eureka_server)</br>
服务网关：[gateway_zuul](https://github.com/Konoha-orz/gateway_zuul)</br>
服务配置：[config_server](https://github.com/Konoha-orz/config_server)</br>
* 统一管理配置</br>
[Pulingle-Config-Repo](https://github.com/Konoha-orz/Pulingle-Config-Repo)</br>
#### 项目部署
我们当初的项目是部署在阿里云ECS学生服务器上的。确保已安装环境依赖Java/MySQL/Redis</br>
* 部署运行顺序：</br>
1.eureka_server</br>
2.config_server和gateway_zuul</br>
3.user-service、message-service、picture-service、moment-service</br>
（注：确保根据jar运行环境在同一管理配置[Pulingle-Config-Repo](https://github.com/Konoha-orz/Pulingle-Config-Repo)配置好对应的信息，如端口、IP等）
###  项目演示</br>
* [视频演示地址](https://pulingle.oss-cn-shenzhen.aliyuncs.com/Pulingle%E6%BC%94%E7%A4%BA%E5%BD%95%E5%B1%8F.mp4)</br>
