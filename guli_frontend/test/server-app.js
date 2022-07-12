// nodejs 简单模拟服务端
const http = require('http');
http.createServer(function(request, response) {
    // 发送HTTP头部
    response.writeHead(200, {'Content-Type': 'text/plain'});
    // 发送响应数据
    response.end('Hello Node.js')
}).listen(8888);
// 在终端打印信息
console.log('Server running at http://127.0.0.1:8888/')
