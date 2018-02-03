<template>
  <div class="customers ">
<!--
    <h2 class="page-header"></h2>
-->
    <table class="table table-striped table-hover">
      <caption>Customers</caption>
      <thead>
      <tr>
        <th>姓名</th>
        <th>电话</th>
        <th>邮箱</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="customer in customers">
        <td>{{customer.name}}</td>
        <td>{{customer.phone}}</td>
        <td>{{customer.email}}</td>
        <td>编辑</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  export default {
    name: "customers",
    data() {
      return {
        customers: []
      }
    },
    methods: {
      fetchCustomers() {
        this.$http.get("http://localhost:3000/users").then(function (response) {
          this.customers = response.body;
        })
      }
    },
    created() {
      this.fetchCustomers()
    }
  }
</script>

<style scoped>

</style>
