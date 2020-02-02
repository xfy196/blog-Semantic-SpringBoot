/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 02/02/2020 20:55:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (41);
INSERT INTO `hibernate_sequence` VALUES (41);
INSERT INTO `hibernate_sequence` VALUES (41);
INSERT INTO `hibernate_sequence` VALUES (41);
INSERT INTO `hibernate_sequence` VALUES (41);

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` bigint(20) NOT NULL,
  `appreciation` bit(1) NOT NULL,
  `commentabled` bit(1) NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `create_time` datetime DEFAULT NULL,
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `published` bit(1) NOT NULL,
  `recommend` bit(1) NOT NULL,
  `share_statement` bit(1) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK292449gwg5yf7ocdlmswv9w4j`(`type_id`) USING BTREE,
  INDEX `FK8ky5rrsxh01nkhctmo7d48p82`(`user_id`) USING BTREE,
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES (18, b'1', b'1', 'Spring AOP 中的动态代理主要有两种方式，JDK 动态代理 和 CGLIB 动态代理。JDK 动态代理通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。JDK 动态代理的核心是 InvocationHandler 接口和 Proxy 类。\r\n\r\n如果目标类没有实现接口，那么 Spring AOP 会选择使用 CGLIB 来动态代理目标类。CGLIB（Code Generation Library），是一个代码生成的类库，可以在运行时动态的生成某个类的子类，注意，CGLIB 是通过继承的方式做的动态代理，因此如果某个类被标记为 final，那么它是无法使用 CGLIB 做动态代理的。', '2020-01-30 14:20:03', 'https://picsum.photos/id/1002/800/450', '原创', b'1', b'1', b'1', 'Spring AOP 实现原理', '2020-02-01 13:52:45', 8, 4, 1, 'Spring AOP 中的动态代理主要有两种方式，JDK 动态代理 和 CGLIB 动态代理。JDK 动态代理通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。JDK 动态代理的核心是 InvocationHandler 接口和 Proxy 类。\r\n\r\n如果目标类没有实现接口，那么 Spring AOP 会选择使用 CGLIB 来动态代理目标类。CGLIB（Code Generation Library），是一个代码生成的类库，可以在运行时动态的生成某个类的子类，注意，CGLIB 是通过继承的方式做的动态代理，因此如果某个类被标记为 final，那么它是无法使用 CGLIB 做动态代理的。');
INSERT INTO `t_blog` VALUES (25, b'1', b'1', '# Spring 事务底层原理\r\n### 划分处理单元IOC\r\n由于 Spring 解决的问题是对单个数据库进行局部事务处理的，具体的实现首相用 Spring 中的 IOC 划分了事务处理单元。并且将对事务的各种配置放到了 IOC 容器中（设置事务管理器，设置事务的传播特性及隔离机制）。\r\n### AOP 拦截需要进行事务处理的类\r\nSpring 事务处理模块是通过 AOP 功能来实现声明式事务处理的，具体操作（比如事务实行的配置和读取，事务对象的抽象），用 TransactionProxyFactoryBean 接口来使用 AOP 功能，生成 proxy 代理对象，通过 TransactionInterceptor 完成对代理方法的拦截，将事务处理的功能编织到拦截的方法中。读取 IOC 容器事务配置属性，转化为 Spring 事务处理需要的内部数据结构（TransactionAttributeSourceAdvisor），转化为 TransactionAttribute 表示的数据对象。\r\n### 对事物处理实现（事务的生成、提交、回滚、挂起）\r\nSpring 委托给具体的事务处理器实现。实现了一个抽象和适配。适配的具体事务处理器：DataSource 数据源支持、Hibernate 数据源事务处理支持、JDO 数据源事务处理支持，JPA、JTA 数据源事务处理支持。这些支持都是通过设计 PlatformTransactionManager、AbstractPlatforTransaction 一系列事务处理的支持。 为常用数据源支持提供了一系列的 TransactionManager。\r\n### 结合\r\nPlatformTransactionManager 实现了 TransactionInterception 接口，让其与 TransactionProxyFactoryBean 结合起来，形成一个 Spring 声明式事务处理的设计体系。', '2020-01-30 14:20:05', 'https://picsum.photos/id/1002/800/450', '原创', b'1', b'1', b'1', 'Spring 事务底层原理', '2019-12-01 13:45:18', 24, 9, 1, '由于 Spring 解决的问题是对单个数据库进行局部事务处理的，具体的实现首相用 Spring 中的 IOC 划分了事务处理单元。并且将对事务的各种配置放到了 IOC 容器中（设置事务管理器，设置事务的传播特性及隔离机制）。');
INSERT INTO `t_blog` VALUES (30, b'1', b'1', '### 1、DOCTYPE有什么作用？标准莫诗雨混杂模式如何区分？它们有何意义？\r\n\r\n告诉浏览器使用哪个版本的HTML规范来渲染文档。DOCTYPE不存在或形式不正确会导致HTML文档以混杂模式呈现。  标准模式（Standards mode）以浏览器支持的最高标准运行；混杂模式（Quirks mode）中页面是一种比较宽松的向后兼容的方式显示。\r\n\r\n### 2、HTML5为什么只需要写`<!DICTYPE HTML>`?\r\n\r\nHTML5不基于SGML（Standard Generalized Markup Language 标准通用标记语言），因此不需要对DTD（DTD 文档类型定义）进行引用，但是需要DOCTYPE来规范浏览器行为。\r\n\r\nHTML4.01基于SGML，所以需要引用DTD。才能告知浏览器文档所使用的文档类型，如下：\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n\r\n### 3、行内元素有那些？块级元素有哪些？空（void）元素有哪些？\r\n\r\n行内元素：a span img input select  \r\n块级元素：div ul ol li dl dt dd h1 p  \r\n空元素：<br> <hr> <link> <meta>\r\n\r\n### 4、页面导入样式时，使用link和@import有什么区别？\r\n\r\n相同的地方，都是外部引用CSS方式，区别：\r\n\r\n1. link是xhtml标签，除了加载css外，还可以定义RSS等其他事务；@import属于CSS范畴，只能加载CSS\r\n    \r\n2. link引用CSS时候，页面载入时同时加载；@import需要在页面完全加载以后加载，而且@import被引用的CSS会等到引用它的CSS文件被加载完才加载\r\n    \r\n3. link是xhtml标签，无兼容问题；@import是在css2.1提出来的，低版本的浏览器不支持\r\n    \r\n4. link支持使用javascript控制去改变样式，而@import不支持\r\n    \r\n5. link方式的样式的权重高于@import的权重\r\n    \r\n6. import在html使用时候需要``标签\r\n    \r\n\r\n### 5、容闪烁(FOUC) Flash of Unstyle Content\r\n\r\n@import导入CSS文件会等到文档加载完后再加载CSS样式表。因此，在页面DOM加载完成到CSS导入完成之间会有一段时间页面上的内容是没有样式的。\r\n\r\n解决方法：使用link标签加载CSS样式文件。因为link是顺序加载的，这样页面会等到CSS下载完之后再下载HTML文件，这样先布局好，就不会出现FOUC问题。\r\n\r\n### 6、介绍一下你对浏览器内核的理解?\r\n\r\n主要分成两部分：渲染引擎(Layout Engine或Rendering Engine)和JS引擎。\r\n\r\n渲染引擎：负责取得网页的内容（HTML、XML、图像等等）、整理讯息（例如加入CSS等），以及计算网页的显示方式，然后会输出至显示器或打印机。浏览器的内核的不同对于网页的语法解释会有不同，所以渲染的效果也不相同。  JS引擎：解析和执行javascript来实现网页的动态效果。\r\n\r\n最开始渲染引擎和JS引擎并没有区分的很明确，后来JS引擎越来越独立，内核就倾向于只指渲染引擎。\r\n\r\n### 7、浏览器内核有哪些？\r\n\r\n1. Trident( MSHTML )：IE MaxThon TT The World 360 搜狗浏览器\r\n    \r\n2. Geckos：Netscape6及以上版本 FireFox Mozilla Suite/SeaMonkey\r\n    \r\n3. Presto：Opera7及以上(Opera内核原为：Presto，现为：Blink)\r\n    \r\n4. Webkit：Safari Chrome\r\n    \r\n\r\n### 8、HTML5有哪些新特性，移除了哪些元素？如何处理HTML5新标签的浏览器兼容问题？如何区分HTML和HTML5\r\n\r\n新增加了图像、位置、存储、多任务等功能。  新增元素：\r\n\r\n1. canvas\r\n    \r\n2. 用于媒介回放的video和audio元素\r\n    \r\n3. 本地离线存储。localStorage长期存储数据，浏览器关闭后数据不丢失;sessionStorage的数据在浏览器关闭后自动删除\r\n    \r\n4. 语意化更好的内容元素，比如 article footer header nav section\r\n    \r\n5. 位置API：Geolocation\r\n    \r\n6. 表单控件，calendar date time email url search\r\n    \r\n7. 新的技术：web worker(web worker是运行在后台的 JavaScript，独立于其他脚本，不会影响页面的性能。您可以继续做任何愿意做的事情：点击、选取内容等等，而此时 web worker 在后台运行) web socket\r\n    \r\n8. 拖放API：drag、drop\r\n    \r\n\r\n移除的元素：\r\n\r\n1. 纯表现的元素：basefont big center font s strike tt u\r\n    \r\n2. 性能较差元素：frame frameset noframes\r\n    \r\n\r\n区分：\r\n\r\n1. DOCTYPE声明的方式是区分重要因素\r\n    \r\n2. 根据新增加的结构、功能来区分\r\n    \r\n\r\n### 9、简述下你对HTML语义化的理解？\r\n\r\n1. 去掉或丢失样式的时候能够让页面呈现出清晰的结构。\r\n    \r\n2. 有利于SEO和搜索引擎建立良好沟通，有助于爬虫抓取更多的信息，爬虫依赖于标签来确定上下文和各个关键字的权重。\r\n    \r\n3. 方便其它设备解析。\r\n    \r\n4. 便于团队开发和维护，语义化根据可读性。\r\n    \r\n\r\n### 10、HTML5文件离线存储怎么使用，工作原理是什么？\r\n\r\n在线情况下，浏览器发现HTML头部有manifest属性，它会请求manifest文件，如果是第一次访问，那么浏览器就会根据manifest文件的内容下载相应的资源，并进行离线存储。如果已经访问过并且资源已经离线存储了，那么浏览器就会使用离线的资源加载页面。然后浏览器会对比新的manifest文件与旧的manifest文件，如果文件没有发生改变，就不会做任何操作，如果文件改变了，那么就会重新下载文件中的资源，并且进行离线存储。例如，\r\n\r\n在页面头部加入manifest属性\r\n\r\n<html manifest=\'cache.manifest\'>\r\n\r\n在cache.manifest文件中编写离线存储的资源\r\n\r\nCACHE MANIFEST  \r\n#v0.11  \r\nCACHE:  \r\njs/app.js  \r\ncss/style.css  \r\nNETWORK:  \r\nResourse/logo.png  \r\nFALLBACK:  \r\n //offline.html\r\n\r\n### 11、cookies, sessionStoragehe localStorage的区别\r\n\r\n共同点：都是保存在浏览器端，且是同源的。\r\n\r\n区别：\r\n\r\n1. cookies是为了标识用户身份而存储在用户本地终端上的数据，始终在同源http请求中携带，即cookies在浏览器和服务器间来回传递，而sessionstorage和localstorage不会自动把数据发给服务器，仅在本地保存。\r\n    \r\n2. 存储大小的限制不同。cookie保存的数据很小，不能超过4k，而sessionstorage和localstorage保存的数据大，可达到5M。\r\n    \r\n3. 数据的有效期不同。cookie在设置的cookie过期时间之前一直有效，即使窗口或者浏览器关闭。sessionstorage仅在浏览器窗口关闭之前有效。localstorage始终有效，窗口和浏览器关闭也一直保存，用作长久数据保存。\r\n    \r\n4. 作用域不同。cookie在所有的同源窗口都是共享；sessionstorage不在不同的浏览器共享，即使同一页面；localstorage在所有同源窗口都是共享\r\n    \r\n\r\n### 12、iframe框架有哪些优缺点？\r\n\r\n优点：\r\n\r\n1. iframe能够原封不动的把嵌入的网页展现出来。\r\n    \r\n2. 如果有多个网页引用iframe，那么你只需要修改iframe的内容，就可以实现调用的每一个页面内容的更改，方便快捷。\r\n    \r\n3. 网页如果为了统一风格，头部和版本都是一样的，就可以写成一个页面，用iframe来嵌套，可以增加代码的可重用。\r\n    \r\n4. 如果遇到加载缓慢的第三方内容如图标和广告，这些问题可以由iframe来解决。\r\n    \r\n\r\n缺点：\r\n\r\n1. 搜索引擎的爬虫程序无法解读这种页面\r\n    \r\n2. 框架结构中出现各种滚动条\r\n    \r\n3. 使用框架结构时，保证设置正确的导航链接。\r\n    \r\n4. iframe页面会增加服务器的http请求\r\n    \r\n\r\n### label的作用是什么？是怎么用的？\r\n\r\nlabel标签用来定义表单控件间的关系,当用户选择该标签时，浏览器会自动将焦点转到和标签相关的表单控件上。label 中有两个属性是非常有用的, FOR和ACCESSKEY。  FOR属性功能：表示label标签要绑定的HTML元素，你点击这个标签的时候，所绑定的元素将获取焦点。例如，\r\n\r\n<Label FOR=\"InputBox\">姓名</Label><input ID=\"InputBox\" type=\"text\"> \r\n\r\nACCESSKEY属性功能：表示访问label标签所绑定的元素的热键，当您按下热键，所绑定的元素将获取焦点。例如，\r\n\r\n<Label FOR=\"InputBox\" ACCESSKEY＝\"N\">姓名</Label><input ID=\"InputBox\" type=\"text\">\r\n\r\n### 14、HTML5的form如何挂麻痹自动完成功能\r\n\r\nTML的输入框可以拥有自动完成的功能，当你往输入框输入内容的时候，浏览器会从你以前的同名输入框的历史记录中查找出类似的内容并列在输入框下面，这样就不用全部输入进去了，直接选择列表中的项目就可以了。但有时候我们希望关闭输入框的自动完成功能，例如当用户输入内容的时候，我们希望使用AJAX技术从数据库搜索并列举而不是在用户的历史记录中搜索。\r\n\r\n方法：\r\n\r\n1. 在IE的internet选项菜单中里的自动完成里面设置\r\n    \r\n2. 设置form输入框的autocomplete为on或者off来来开启输入框的自动完成功能\r\n    \r\n\r\n### 15、如何实现浏览器内多个标签页之间的通信\r\n\r\n1. WebSocket SharedWorker\r\n    \r\n2. 也可以调用 localstorge、cookies 等本地存储方式。 localstorge 在另一个浏览上下文里被添加、修改或删除时，它都会触发一个事件，我们通过监听事件，控制它的值来进行页面信息通信。\r\n    \r\n\r\n注意：Safari 在无痕模式下设置 localstorge 值时会抛出QuotaExceededError 的异常\r\n\r\n### 16、webSocket如何兼容低版本浏览器？\r\n\r\n1. Adobe Flash Socket ActiveX HTMLFile (IE) 基于 multipart 编码发送 XHR 基于长轮询的 XHR\r\n    \r\n2. 引用WebSocket.js这个文件来兼容低版本浏览器。\r\n    \r\n\r\n### 17、页面可见性 (Page Visbilty) API 可以有哪些用途？\r\n\r\n1. 通过visibility state的值得检测页面当前是否可见，以及打开网页的时间。\r\n    \r\n2. 在页面被切换到其他后台进程时，自动暂停音乐或视频的播放。\r\n    \r\n\r\n### 18、如何在页面上实现一个圆形的可点击区域？\r\n\r\n1. map+area或者svg\r\n    \r\n2. border-radius\r\n    \r\n3. 纯js实现，一个点不在圆上的算法\r\n    \r\n\r\n### 19、网页验证码是干什么的。是为了解决什么安全问题？\r\n\r\n1. 区别用户是计算机还是人的程序；\r\n    \r\n2. 可以防止恶意破解密码、刷票、论坛灌水；\r\n    \r\n\r\n### 20、实现不使用border画出1px高的线，在不同浏览器的Quirks mode和CSS Compat模式下都保持同一效果\r\n\r\n<div style=\"height:1px;overflow:hidden;background:red\"></div>\r\n\r\n### 21、title与h1的区别、b与strong的区别、i和em的区别？\r\n\r\ntitle属性没有明确意义，只表示标题；h1表示层次明确的标题，对页面信息的抓取也有很大的影响  strong标明重点内容，语气加强含义；b是无意义的视觉表示  em表示强调文本；i是斜体，是无意义的视觉表示  视觉样式标签：`b i u s`  语义样式标签：`strong em ins del code`\r\n\r\n### 22、元素的alt和title有什么异同？\r\n\r\n在alt和title同时设置的时候，alt作为图片的替代文字出现，title是图片的解释文字。', '2020-01-31 08:53:10', 'https://img.hacpai.com/bing/20180120.jpg?imageView2/1/w/960/h/540/interlace/1/q/100', '原创', b'1', b'1', b'1', '20道HTML基础面试题(附答案)', '2018-02-01 13:52:36', 31, 5, 1, '2020年最新HTML面试宝典解析');

-- ----------------------------
-- Table structure for t_blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags`  (
  `blogs_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  INDEX `FK5feau0gb4lq47fdb03uboswm8`(`tags_id`) USING BTREE,
  INDEX `FKh4pacwjwofrugxa9hpwaxg6mr`(`blogs_id`) USING BTREE,
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_blog_tags
-- ----------------------------
INSERT INTO `t_blog_tags` VALUES (25, 14);
INSERT INTO `t_blog_tags` VALUES (25, 7);
INSERT INTO `t_blog_tags` VALUES (25, 23);
INSERT INTO `t_blog_tags` VALUES (30, 12);
INSERT INTO `t_blog_tags` VALUES (30, 8);
INSERT INTO `t_blog_tags` VALUES (30, 13);
INSERT INTO `t_blog_tags` VALUES (18, 7);
INSERT INTO `t_blog_tags` VALUES (18, 8);

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `blog_id` bigint(20) DEFAULT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  `admin_comment` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKke3uogd04j4jx316m1p51e05u`(`blog_id`) USING BTREE,
  INDEX `FK4jj284r3pb7japogvo6h72q95`(`parent_comment_id`) USING BTREE,
  CONSTRAINT `FK4jj284r3pb7japogvo6h72q95` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (31, '/images/avatar.png', '测试评论', '2020-02-01 06:03:08', 'xfy196@outlook.com', '小白', 30, NULL, b'0');
INSERT INTO `t_comment` VALUES (32, '/images/avatar.png', '哈哈', '2020-02-01 06:04:11', 'xfy106@126.com', '小花', 30, NULL, b'0');
INSERT INTO `t_comment` VALUES (33, '/images/avatar.png', '你好', '2020-02-01 06:09:04', 'xfy106@126.com', '小花', 30, 32, b'0');
INSERT INTO `t_comment` VALUES (34, '/images/avatar.png', '小黄回复了小白', '2020-02-01 07:49:25', 'xfy196@126.com', '小黄', 30, 31, b'0');
INSERT INTO `t_comment` VALUES (35, '/images/avatar.png', '你们好啊，我是一个it从业人员', '2020-02-01 07:49:50', 'xfy196@126.com', 'it哥', 30, NULL, b'0');
INSERT INTO `t_comment` VALUES (36, '/images/avatar.png', '管理员回复你们了', '2020-02-01 10:09:30', 'xfy196@outlook.com', '小小荧', 30, NULL, b'1');
INSERT INTO `t_comment` VALUES (37, '/images/avatar.png', '你错了', '2020-02-01 10:10:07', 'xfy196@outlook.com', '小小荧', 30, 36, b'1');
INSERT INTO `t_comment` VALUES (38, '/images/avatar.png', '哈哈', '2020-02-01 10:10:19', 'xfy196@outlook.com', '小小荧', 30, 33, b'1');
INSERT INTO `t_comment` VALUES (39, '/images/avatar.png', '真好呢', '2020-02-01 10:12:50', 'xfy196@outlook.com', '小小荧', 30, NULL, b'1');
INSERT INTO `t_comment` VALUES (40, '/images/avatar.png', '好的', '2020-02-01 10:14:01', 'xfy196@outlook.com', '小小荧', 30, 31, b'1');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (7, 'Java');
INSERT INTO `t_tag` VALUES (8, 'CSS');
INSERT INTO `t_tag` VALUES (12, 'JavaScript');
INSERT INTO `t_tag` VALUES (13, 'HTML');
INSERT INTO `t_tag` VALUES (14, 'Mybatis');
INSERT INTO `t_tag` VALUES (15, '软件方法');
INSERT INTO `t_tag` VALUES (23, '算法');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (4, '学习日志');
INSERT INTO `t_type` VALUES (5, '前端');
INSERT INTO `t_type` VALUES (6, '清单');
INSERT INTO `t_type` VALUES (7, '认识升级');
INSERT INTO `t_type` VALUES (8, '创业');
INSERT INTO `t_type` VALUES (9, '全栈开发');
INSERT INTO `t_type` VALUES (10, '开发者手册');
INSERT INTO `t_type` VALUES (11, '自由职业');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'https://unsplash.it/100/100?image=1005', '2020-01-16 17:20:47', 'admin@outlook.com', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, '2020-01-16 17:21:15', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
