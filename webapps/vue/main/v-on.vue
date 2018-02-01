<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>


<div id="app">
    <p :style="{color: fontColor}">{{msg}}</p>
    <button @click="msg='aaaaaaaaaaa'">改变内容</button>
    <br/>
    <button @click="changeContent()">改变内容</button>
</div>

<script src="js/vue.js"></script>
<script>
    let v = new Vue({
        el: '#app',
        data: {
            msg: 'hello world!',
            fontColor: 'red'
        },
        methods: {
            changeContent() {
                this.msg = 'aaaaaaa';
            }
        }
    });
</script>

</body>
</html>