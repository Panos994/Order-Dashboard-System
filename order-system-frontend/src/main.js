import { createApp } from 'vue';
import App from './App.vue';
import { createRouter, createWebHistory } from 'vue-router';
import axios from 'axios';

import LoginPage from './components/LoginPage.vue';
import SignUpPage from './components/SignUpPage.vue';
import MainPage from './components/MainPage.vue';
import AdminPage from "@/components/AdminPage.vue";

// Set baseURL for axios
axios.defaults.baseURL = 'http://localhost:9090';

// Define routes
const routes = [
    { path: '/', redirect: '/login' }, // Default to login
    { path: '/login', name: 'LoginPage', component: LoginPage },
    { path: '/signup', name: 'SignUpPage', component: SignUpPage },
    { path: '/main', name: 'MainPage', component: MainPage },
    {path: '/admin', name: 'AdminPage', component: AdminPage}
];


const router = createRouter({
    history: createWebHistory(),
    routes
});

// Add navigation guard
router.beforeEach((to, from, next) => {
    const isAuthenticated = !!localStorage.getItem('authToken'); // Check if user is authenticated

    if (to.name === 'MainPage' && !isAuthenticated) {
        next({ name: 'LoginPage' }); // Redirect to login if not authenticated
    } else {
        next(); // Proceed if authenticated
    }
});

// Create Vue app instance
const app = createApp(App);
app.use(router);

app.config.globalProperties.$axios = axios;
app.mount('#app');
