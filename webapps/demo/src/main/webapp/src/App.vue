<template>
  <div id="app">
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="/">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">Profile</a></li>
            <li><a href="#">Help</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li><router-link to="/about">About</router-link> </li>
            <li><router-link to="/customers">Customer</router-link></li>
            <li><router-link to="/add">Add</router-link></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
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
  body {
    padding-top: 50px;
  }

  .navbar-fixed-top {
    border: 0;
  }

  .sidebar {
    display: none;
  }

  @media (min-width: 768px) {
    .sidebar {
      position: fixed;
      top: 51px;
      bottom: 0;
      left: 0;
      z-index: 1000;
      display: block;
      padding: 20px;
      overflow-x: hidden;
      overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
      background-color: #f5f5f5;
      border-right: 1px solid #eee;
    }
  }

  /* Sidebar navigation */
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

  /*
   * Main content
   */
  .main {
    padding: 10px;
  }

  @media (min-width: 768px) {
    .main {
      padding-right: 10px;
      padding-left: 10px;
    }
  }

  .main .page-header {
    margin-top: 0;
  }

  .placeholders h4 {
    margin-bottom: 0;
  }

  .placeholder img {
    display: inline-block;
    border-radius: 50%;
  }
</style>
