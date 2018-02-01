<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>


<div id="app">
    <com-name></com-name>
</div>

<script src="../js/vue.js"></script>
<script>
    // 1 创建组件构造器
    let Profile = Vue.extend({
        // 1.1 模板选项
        template: `
            <div>
                <input type="date"/>
                <p>飞了</p>
            </div>
        `
    });

    // 2 注册全局组件
    /**
     * 组件名称,模板
     */
    Vue.component('com-name', Profile);

    // 全局组件 在任何实例里都可以使用

    // 这是一个实例
    let v = new Vue({
        el: '#app',
        data: {
            msg: 'hello world!'
        }
    });
</script>

</body>
</html>