new Vue({
    el: '#app',
    data: {
        list: []
    },
    // 组件已经加载完毕,请求网络数据,业务处理
    mounted() {
        // 直接请求数据
        this.getData()
    },
    methods: {
        //1 请求数据
        getData() {
            // 这是通用写法
            // this.$http.get("/url").then(response => {
            //     this.list = response.body;
            // }, response => {
            //     alert("Error!");
            // })

            this.$http.get("data.json").then(response => {
                console.log(response.body);
            }, response => {
                alert("Error!");
            })

        }
    }
});