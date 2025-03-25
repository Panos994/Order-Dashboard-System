<template>
  <NavbarPage />
  <div class="auth-form">

    <div class="form-container">



      <h2>Login</h2>
      <form @submit.prevent="login">
        <div class="input-group">
          <label for="username">Username</label>
          <input type="text" v-model="username" required />
        </div>

        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" v-model="password" required />
        </div>

        <button type="submit" class="login-btn">Login</button>
      </form>

      <p class="signup-link">
        Don't have an account?
        <router-link to="/signup">Sign up here</router-link>
      </p>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

import NavbarPage from '@/components/NavbarPage.vue';

export default {
  components: { NavbarPage },
  name: 'LoginPage',
  data() {
    return {
      username: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('http://localhost:9090/api/auth/signin', {
          username: this.username,
          password: this.password
        });

        const { accessToken, roles } = response.data;
        const userRole = roles[0] || ''; // Ensure it's not undefined

        // Store values correctly
        localStorage.setItem('username', this.username);
        localStorage.setItem('token', accessToken); // Fix: Ensure it's 'token'
        localStorage.setItem('userRole', userRole);

        // Redirect based on role
        if (userRole === 'ROLE_ADMIN') {
          this.$router.push({ name: 'AdminPage' });
        } else if (userRole === 'ROLE_USER') {
          this.$router.push({ name: 'MainPage' });
        } else {
          this.errorMessage = 'Unknown role. Contact support.';
        }
      } catch (error) {
        this.errorMessage = 'Invalid login credentials';
      }
    }
  }
};
</script>



<style scoped>
.auth-form {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f7f7f7;
}

.form-container {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: 600;
  color: #333;
}

.input-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

input[type="text"]:focus,
input[type="password"]:focus {
  outline: none;
  border-color: #007bff;
}

.login-btn {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s;
}

.login-btn:hover {
  background-color: #0056b3;
}

.signup-link {
  margin-top: 20px;
  font-size: 14px;
}

.signup-link a {
  color: #007bff;
  text-decoration: none;
}

.signup-link a:hover {
  text-decoration: underline;
}

.error {
  color: red;
  margin-top: 15px;
}
</style>