const path = require("path"); // Node.js内置模块
const { Template } = require("webpack");
module.exports = {
    entry: './src/main.js', // 配置入口文件（打包入口）
    output: {
        path: path.resolve(__dirname, './dist'), //输出路径， __dirname：当前文件所在路径
        filename: 'bundle.js'  // 输出文件（打包之后的文件）
    },
    module: {
        rules: [
            {
                test: /\.css$/,     // 打包规则应用到以css结尾的文件上
                use: ['style-loader', 'css-loader']
            }
        ]
    }
}



