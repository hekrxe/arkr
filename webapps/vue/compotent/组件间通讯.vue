<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>

<div id="app">
    <father :f_p_title="title" :f_p_content="content"></father>
</div>

<template id="tmp_title">
    <h2>{{param_title}}</h2>
</template>

<template id="tmp_content">
    <h2>{{param_content}}</h2>
</template>

<template id="tmp_father">
    <div>
        <child_title :param_title="f_p_title"></child_title>
        <child_content :param_content="f_p_content"></child_content>
    </div>
</template>

<script src="../js/vue.js"></script>
<script>
    // 子组件的一个实例
    let ChildTitle = Vue.extend({
        template: '#tmp_title',
        props: ['param_title']
    });
    let ChildContent = Vue.extend({
        template: '#tmp_content',
        props: ['param_content']
    });


    // 注册父组件
    Vue.component('father', {
        props: ['f_p_title', 'f_p_content'],
        components: {
            'child_title': ChildTitle,
            'child_content': ChildContent
        },
        template: '#tmp_father'
    });


    // 从实例传参数到组件 组件再到组件
    let v = new Vue({
        el: '#app',
        data: {
            title: 'TitleTitleTitleTitle',
            content: 'ContentContentContentContent'
        }
    });
</script>
</body>
</html>