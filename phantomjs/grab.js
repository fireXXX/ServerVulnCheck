system = require('system')   //����һЩ��Ҫ�Ĳ�����js�ļ�  
address = system.args[1];//��������еڶ������� ��Ҳ����ָ��Ҫ���ص�ҳ���ַ�����������õ�    
var page = require('webpage').create();  
var url = address; 
page.settings.resourceTimeout = 10000;

page.onResourceTimeout = function(e) {
	console.log(page.url);
	phantom.exit(1);
};

page.open(url, function (status) {  
    if (status !== 'success') {  
    	console.log(page.url);
    } else {  
        console.log(page.url);  
    }  
    phantom.exit();  
});