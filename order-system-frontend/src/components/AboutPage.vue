<!-- src/components/AboutPage.vue -->
<template>
  <NavbarPage />
  <div class="about-page">
    <div class="card">
      <button @click="goBack" class="back-button">← Back</button>
      <h1>About OrderDashboard</h1>
      <p>This app was developed as part of the Cloud Computing course for the 2024–2025 Spring Semester.</p>
    </div>
  </div>
</template>

<script>
import NavbarPage from "@/components/NavbarPage.vue";

export default {
  name: 'AboutPage',
  components: { NavbarPage },
  methods: {
    goBack() {
      this.$router.go(-1); // Go back to the previous page
    }
  }
};
</script>

<style scoped>
.about-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f9f9f9;
}

.card {
  background-color: #fff;
  padding: 20px 30px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  width: 100%;
  text-align: center;
}

h1 {
  font-family: 'Arial', sans-serif;
  font-size: 2rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
}

p {
  font-family: 'Arial', sans-serif;
  font-size: 1rem;
  color: #555;
  line-height: 1.6;
}

.back-button {
  background-color: #007BFF;
  color: #fff;
  font-size: 1rem;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 20px;
}

.back-button:hover {
  background-color: #0056b3;
}
</style>
