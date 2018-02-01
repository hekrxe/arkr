<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div id="app">
    <tmp></tmp>
</div>

<!--template标签里只能有一个闭合标签-->
<template id="tmp-id">
    <div>
        <div>Tmp</div>
        <div>
            <input type="color">
        </div>
    </div>
</template>

<script src="../js/vue.js"></script>
<script>

    Vue.component('tmp', {
        template: '#tmp-id'
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