<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>
<div id="app">
    <!--msg是形式参数  msgaa是实参
    info: 形式参数, infof是实参
    -->
    <tmp msg="msgaa" info="infof"></tmp>
    <br/>
    <tmp msg="bbbbbbbb" info="aaaaaaaaaaa"></tmp>
</div>



<template id="my_div">
    <div>
        {{msg}}
        {{info}}
    </div>
</template>

<script src="../js/vue.js"></script>
<script>

    Vue.component('tmp', {
        template: '#my_div',
        props: ['msg', 'info'] // 形式参数列表
    });

    let v = new Vue({
        el: '#app',
        data: {
            msg: 'hello world!'
        }
    });
</script>
</body>
</html>