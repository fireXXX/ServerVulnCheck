# ServerVulnCheck
ServerVulnCheck

该项目为java编写，主要目的是希望实现渗透测试过程中对于一些简单的漏洞的自动审计。例如域传输漏洞、snmp弱口令等，让渗透测试人员能专注于审计web方面的逻辑漏洞。
README
===========================
该文件用来测试和展示书写README的各种markdown语法。GitHub的markdown语法在标准的markdown语法基础上做了扩充，称之为`GitHub Flavored Markdown`。简称`GFM`，GFM在GitHub上有广泛应用，除了README文件外，issues和wiki均支持markdown语法。
****
### Author:果冻虾仁
### E-mail:Jelly.K.Wang@qq.com
****
## 目录
* [横线](#横线)
* [标题](#标题)
* [文本](#文本)
    * 普通文本
    * 单行文本
    * 多行文本
    * 文字高亮
    * 换行
    * 斜体
    * 粗体
    * 删除线
* [图片](#图片)
    * 来源于网络的图片
    * GitHub仓库中的图片
* [链接](#链接) 
    * 文字超链接

对于漏洞分类按照3个大方向，分别为域名、ip地址、url。
对于域名方面检查域传输漏洞以及是否正确设置spf记录(防止邮箱伪造)，
对于ip方面检查主要会按照端口进行分类，比如netbios弱口令，
对于url方面的检查目前主要包括部分便于直接用url测试的struts2的命令执行漏洞以及java反序列化漏洞等。

为了使用边防还加入了一些工具模块例如弱口令爆破模块、子域名获取模块、c端查询、同服ip查询。

后面考虑会加入自动化的端口扫描，然后根据端口扫描结果去匹配metasploit已经存在服务器的漏洞。
