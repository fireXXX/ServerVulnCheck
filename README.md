README
===========================
## 项目简介
该项目为java编写，主要目的是希望实现渗透测试过程中对于一些简单的漏洞的自动审计。</br>
例如域传输漏洞、snmp弱口令等，让渗透测试人员能专注于审计web方面的逻辑漏洞。
## 如何使用
手工选择从/launch/Launch.java文件启动或将项目导出成jar包后运行</br>
运行截图:</br>
[baidu-logo]:/example.png "百度logo"
## 功能介绍
* 域名相关漏洞检查
  * 域传输漏洞
  * 邮件服务器spf记录
* ip相关漏洞检查
  * 按照端口分类，例如139/443端口的netbios弱口令
* url相关漏洞检查
  * 便于直接用url测试的struts2的命令执行漏洞以及java反序列化漏洞等
* 工具类功能
  * 常见服务弱口令爆破
  * 子域名获取
  * C段查询
  * 同服IP查询
  * 支持从文件导入扫描目标和导出扫描结果以及日志
* 准备完善的功能
  * 端口扫描
  * 匹配metasploit的漏洞
## 目录结构
* /options</br>
  存放sql注入的配置项以及爆破的字典
* /phantomjs</br>
  模拟浏览器发出请求，不过因为速度过慢，所有只有在获取ip对应的网站主页时才会使用
* /src
  * /UI</br>
  图形界面
  * /action
    * /brute</br>
    服务弱口令爆破模块
    * /domain</br>
    域名相关的工具和漏洞
    * /subdomain</br>
    提供子域名到ip的转换以及子域名网站主页获取
    * /ip</br>
    与端口相关的漏洞以及查询C段等工具类
    * /url</br>
    与url相关的漏洞，例如sql注入、xss等
  * /global</br>
    提供全局配置项，例如代理地址，后面要做成配置文件的形式
  * /launch</br>
    程序的启动位置
  * /threadpool</br>
    线程池，分配线程去完成对目标的操作任务，根据反射去加载/action目录下相应的类
  * /tools</br>
    封装了日志、http连接请求以及字符串匹配等操作
  * /weblogic</br>
    仅为反序列化使用
