<template>
  <div class=" add">
    <form v-on:submit="addCustomer">
      <h4>用户信息</h4>
      <div class="well">
        <div class="form-group">
          <label>姓名:</label>
          <input type="text" class="form-control" placeholder="name" v-model="customer.name"/>
        </div>
        <div class="form-group">
          <label>电话:</label>
          <input type="text" class="form-control" placeholder="phone" v-model="customer.phone"/>
        </div>
        <div class="form-group">
          <label>邮箱:</label>
          <input type="text" class="form-control" placeholder="email" v-model="customer.email"/>
        </div>
        <button type="submit" class="btn btn-primary">添加</button>
      </div>
    </form>
  </div>
</template>

<script>
  export default {
    name: "add",
    data() {
      return {
        customer: {name: '', phone: '', email: ''}
      }
    },
    methods: {
      addCustomer(e) {
        if (!this.customer.name || !this.customer.phone || !this.customer.email) {
          alert("请填数据");
        } else {
          let newCustomer = {
            name: this.customer.name,
            phone: this.customer.phone,
            email: this.customer.email,
          }
          this.$http.post("http://localhost:3000/users", newCustomer)
            .then(function () {
              // 跳回主页
              this.$router.push({path: "/", query: {alert: "添加成功"}});
            });
        }
        // 阻止默认事件
        e.preventDefault();
      }
    }
  }
</script>

<style scoped>

</style>
