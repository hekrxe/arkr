<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div id="app">
    <com-name></com-name>
    <com-name-color></com-name-color>
</div>

<script src="../lib/js/vue.js"></script>
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

    let Profile1 = Vue.extend({
        // 1.1 模板选项
        template: `
            <div>
                <input type="color"/>
                <p>废了</p>
            </div>
        `
    });

    let v = new Vue({
        el: '#app',
        components: {
            'com-name': Profile,
            'com-name-color': Profile1
        }
    });
</script>
</body>
</html>