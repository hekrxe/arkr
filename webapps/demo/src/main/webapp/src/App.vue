<template>
  <div id="app">
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="navbar-header">
        <div class="m-logo">
          <div class="logo"></div>
        </div>
      </div>
      <div class="container-fluid">
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">谭化友</a></li>
            <li><a href="#">退出</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="sidebar">
          <ul class="nav nav-sidebar">
            <li><router-link to="/about">About</router-link> </li>
            <li><router-link to="/customers">Customer</router-link></li>
            <li><router-link to="/add">Add</router-link></li>
          </ul>
        </div>
        <div class="main">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    name: 'App',
    components: {}
  }
</script>

<style>
  .logo {
    position: relative;
    top: 12px;
    left: 22px;
    display: inline-block;
    width: 88px;
    height: 25px;
    background: url("./assets/logo-mini.png") no-repeat;
    background-position: left center;
    background-size: contain;
  }

  .m-logo {
    position: relative;
    width: 180px;
    height: 54px;
    padding: 0;
    font-size: 12px;
    color: #fff;
    line-height: 30px;
    background-color: #5294e0;
  }

  .sidebar {
    width: 180px;
    position: absolute;
    top: 54px;
    left: 0;
    z-index: 1000;
    display: block;
    padding: 20px;
    overflow-x: hidden;
    overflow-y: auto;
    background-color: #f5f5f5;
    border-right: 1px solid #eee;
    right: 0;
    bottom: 0;
    margin-bottom: 0;
  }

  .nav-sidebar {
    margin-right: -21px; /* 20px padding + 1px border */
    margin-bottom: 20px;
    margin-left: -20px;
  }

  .nav-sidebar > li > a {
    padding-right: 20px;
    padding-left: 20px;
  }

  .nav-sidebar > .active > a,
  .nav-sidebar > .active > a:hover,
  .nav-sidebar > .active > a:focus {
    color: #fff;
    background-color: #428bca;
  }

  .main {
    margin-left: 182px;
    margin-top: 54px;
  }
</style>
