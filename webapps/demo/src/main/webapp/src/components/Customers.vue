<template>
  <div class="customers ">
    <Alert v-if="alert" v-bind:message="alert"/>
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
        <td>
          <router-link class="btn btn-default" v-bind:to="'/customer/'+customer.id">详情</router-link>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import Alert from './Alert'

  export default {
    name: "customers",
    data() {
      return {
        customers: [],
        alert: ''
      }
    },
    components: {Alert},
    methods: {
      fetchCustomers() {
        this.$http.get("http://localhost:3000/users").then(function (response) {
          this.customers = response.body;
        })
      }
    },
    created() {
      this.fetchCustomers()
    },
    updated() {
      let alert = this.$route.query.alert;
      if (alert) {
        this.alert = alert
      }
      this.fetchCustomers()
    }
  }
</script>

<style scoped>

</style>
