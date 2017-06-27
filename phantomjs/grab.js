system = require('system')   //传递一些需要的参数给js文件  
address = system.args[1];//获得命令行第二个参数 ，也就是指定要加载的页面地址，接下来会用到    
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