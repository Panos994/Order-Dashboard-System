<template>
  <div class="main-page">
    <div class="header">
      <h2>Welcome, {{ username }}</h2>
      <button class="logout-btn" @click="handleLogout">Logout</button>
    </div>

    <div class="orders-section">
      <h3>Your Orders:</h3>
      <ul v-if="orders.length">
        <li v-for="order in orders" :key="order.id">
          <span><strong>Order ID:</strong>  {{ order.id }}</span>
          <span><strong>Product:</strong> {{ order.product.name }}</span>
          <span><strong>Quantity:</strong> {{ order.quantity }}</span>
          <span><strong>Total Cost:</strong> {{ order.totalCost }} euros</span><br><br>
          <span><strong>Order received in: </strong>{{order.createTime}}</span>
        </li>
      </ul>
      <p v-else>No orders found.</p>
    </div>

    <div class="order-form">
      <h3>Place a New Order</h3>
      <form @submit.prevent="placeOrder">
        <!--input v-model="productId" type="text" placeholder="Product ID" required /-->

        <!-- Dropdown for selecting a product -->
        <label for="product">Choose a product:</label>
        <select v-model="selectedProductId" required>
          <option v-for="product in products" :key="product.id" :value="product.id">
            {{ product.name }}: {{product.cost}} euros
          </option>
        </select>

        <input v-model="quantity" type="number" placeholder="Quantity" required />
        <button type="submit">Place Order</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: localStorage.getItem('username') || 'User',
      orders: [],
      //productId: '',
      quantity: '',
      products: [], // Store all products
      selectedProductId: '', // Stores the selected product ID
      cost:'',
      createTime:'',

      totalCost:''
    };
  },
  created() {
    this.checkUserRole();
    this.fetchOrders();

    this.fetchProducts(); // Load products when page loads
  },
  methods: {
    checkUserRole() {
      const role = localStorage.getItem('userRole');
      if (role !== 'ROLE_USER') {
        this.$router.push('/login'); // Prevent unauthorized access
      }
    },

    async fetchOrders() {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          this.$router.push('/login');
          return;
        }

        const response = await axios.get('http://localhost:9090/api/orders', {
          headers: { Authorization: `Bearer ${token}` },
        });
        this.orders = response.data;
      } catch (error) {
        console.error(error);
        this.$router.push('/login');
      }
    },

    async fetchProducts() {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:9090/api/admin/products', {
          headers: { Authorization: `Bearer ${token}` },
        });
        this.products = response.data; // Store all available products
      } catch (error) {
        console.error(error);
      }
    },

    async placeOrder() {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.post(
            'http://localhost:9090/api/orders/submit',
            { productId: this.selectedProductId, quantity: this.quantity },
            { headers: { Authorization: `Bearer ${token}` } }
        );
        this.orders.push(response.data);
      } catch (error) {
        console.error(error);
      }
    },

    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('userRole');
      localStorage.removeItem('username');
      this.$router.push('/login');
    },
  },
};
</script>


<style scoped>
.main-page {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logout-btn {
  padding: 8px 16px;
  background: #d9534f;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.logout-btn:hover {
  background: #c9302c;
}

.orders-section {
  margin-top: 20px;
}

.orders-section ul {
  list-style: none;
  padding: 0;
}

.orders-section li {
  background: white;
  padding: 10px;
  margin: 5px 0;
  border-radius: 5px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.order-form {
  margin-top: 20px;
}

.order-form form {
  display: flex;
  flex-direction: column;
}

.order-form input {
  margin: 5px 0;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.order-form button {
  margin-top: 10px;
  padding: 10px;
  background: #5cb85c;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.order-form button:hover {
  background: #4cae4c;
}


 .orders-section li span {
   display: inline-block;
   margin-right: 10px; /* Adjust spacing between spans */
 }
</style>
