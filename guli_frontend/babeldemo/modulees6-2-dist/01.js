"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
// 定义方法，设置哪些方法可以被其它js调用

exports.default = {
    getList: function getList() {
        console.log("获取数据列表");
    },
    save: function save() {
        console.log("保存数据");
    }
};