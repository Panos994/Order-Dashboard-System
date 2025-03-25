<!-- src/components/Navbar.vue -->
<template>
  <nav class="navbar">
    <div class="navbar-title" @click="$router.push('/')">OrderDashboard</div>
    <div class="navbar-links">
      <router-link to="/about">About</router-link>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'NavbarPage'
};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #007bff;
  color: white;
  padding: 12px 24px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}

.navbar-title {
  font-weight: bold;
  font-size: 20px;
  cursor: pointer;
}

.navbar-links a {
  color: white;
  text-decoration: none;
  margin-left: 20px;
}

.navbar-links a:hover {
  text-decoration: underline;
}
</style>
