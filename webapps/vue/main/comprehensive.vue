<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        #app {
            margin: 50px auto;
            width: 800px;
        }

        fieldset {
            border: 1px solid red;
            margin-bottom: 50px;
        }

        fieldset input {
            width: 200px;
            height: 30px;
            margin: 10px 0;
        }

        table {
            width: 800px;
            border: 2px solid red;
            text-align: center;
        }

        thead {
            background-color: aqua;
        }
    </style>

</head>
<body>

<div id="app">
    <!--1-->
    <fieldset>
        <legend>录入</legend>
        <div>
            <span>姓名:</span>
            <input type="text" placeholder="输入姓名" v-model="new_person.name">
        </div>
        <div>
            <span>年龄:</span>
            <input type="text" placeholder="输入年龄" v-model="new_person.age">
        </div>
        <div>
            <span>性别:</span>
            <select v-model="new_person.sex">
                <option value="男">男</option>
                <option value="女">女</option>
            </select>
        </div>
        <div>
            <span>手机:</span>
            <input type="text" placeholder="输入手机" v-model="new_person.phone">
        </div>
        <button @click="create_new_person()">创建新用户</button>
    </fieldset>

    <!--2-->
    <table>
        <thead>
        <tr>
            <td>姓名</td>
            <td>年龄</td>
            <td>性别</td>
            <td>手机</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <tr v-for="( p , index ) in persons">
            <td>{{p.name}}</td>
            <td>{{p.age}}</td>
            <td>{{p.sex}}</td>
            <td>{{p.phone}}</td>
            <td>
                <button @click="delete_persons(index)">删除</button>
            </td>
        </tr>
        </tbody>
    </table>

</div>

<script src="js/vue.js"></script>
<script>
    let v = new Vue({
        el: '#app',
        data: {
            persons: [
                {name: 'zhangsan', age: 20, sex: '男', phone: '18345555555'},
                {name: 'lisi', age: 30, sex: '女', phone: '18345553333'},
                {name: 'wangwu', age: 50, sex: '男', phone: '18345522225'},
                {name: 'zhaoliu', age: 10, sex: '女', phone: '18341231551'}
            ],
            new_person: {name: '', age: 0, sex: '女', phone: ''}
        },
        methods: {
            create_new_person() {
                if (this.new_person.name === '' || this.new_person.age <= 0 || this.new_person.phone === '') {
                    alert("参数不能为空");
                    return;
                }
                this.persons.unshift(this.new_person);
                this.new_person = {name: '', age: 0, sex: '女', phone: ''};
            },

            delete_persons(index) {
                this.persons.splice(index, 1);
            }
        }
    });
</script>

</body>
</html>