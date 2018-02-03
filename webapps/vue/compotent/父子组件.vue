<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div id="app">
    <father></father>
</div>

<script src="../lib/js/vue.js"></script>
<script>

    // 1 子组件构造器
    let Child1 = Vue.extend({
        template: `
            <div>
                <input type="date"/>
            </div>
        `
    });

    let Child2 = Vue.extend({
        template: `
            <div>
                <p>飞了</p>
            </div>
        `
    });

    // 父组件注册
    Vue.component('father', {
        components: { // 子组件
            'child1': Child1,
            'child2': Child2
        },
        template: `
            <div>
            <child1></child1>
            <child2></child2>
            </div>
        `
    });

    let v = new Vue({
        el: '#app'
    });
</script>
</body>
</html>