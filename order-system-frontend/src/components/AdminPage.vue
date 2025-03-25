<template>
  <NavbarPage />
  <div class="admin-page">

    <h2>Welcome, {{ username }}</h2>
    <h1>Add New Product</h1>





    <form @submit.prevent="addProduct">
      <input v-model="newProduct.name" placeholder="Product Name" required />
      <input v-model="newProduct.cost" type="number" placeholder="Cost" required />
      <input v-model="newProduct.quantity" placeholder="Quantity" required />
      <button type="submit">Add Product</button>
    </form>

    <h2>Product List</h2>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Cost</th>
        <th>Quantity</th>
        <!--th>Actions</th-->
      </tr>
      </thead>
      <tbody>
      <tr v-for="product in products" :key="product.id">
        <td>{{ product.id }}</td>
        <td>{{ product.name }}</td>
        <td>{{ product.cost }} euros</td>
        <td>{{ product.quantity }}</td>
        <td>
          <!--button @click="deleteProduct(product.id)">Delete</button-->
        </td>
      </tr>
      </tbody>
    </table>
    <br>
    <button class ="logout-btn" @click="handleLogout">Logout</button>
  </div>


</template>

<script>
import axios from 'axios';
import NavbarPage from "@/components/NavbarPage.vue";

export default {
  components: {NavbarPage},
  data() {
    return {
      username: localStorage.getItem('username') || 'User',
      newProduct: { name: '', cost: '', quantity: '' },
      products: []
    };
  },
  methods: {
    async fetchProducts() {
      try {
        const token = localStorage.getItem('token'); // Get token from local storage
        const response = await axios.get('http://localhost:9090/api/admin/products', {
          headers: { Authorization: `Bearer ${token}` } // Send token in headers
        });
        this.products = response.data;
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    },

    async addProduct() {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.post(
            'http://localhost:9090/api/admin/add_products',
            this.newProduct,
            {
              headers: { Authorization: `Bearer ${token}` }
            }
        );
        this.products.push(response.data);
        this.newProduct = { name: '', cost: '', quantity: '' };
      } catch (error) {
        console.error('Error adding product:', error);
      }
    },


    async handleLogout() {
      const token = localStorage.getItem('token');
      try {
        await axios.post(
            'http://localhost:9090/api/auth/logout',
            {}, // Empty body
            { headers: { Authorization: `Bearer ${token}` } }
        );
      } catch (error) {
        console.error('Logout failed:', error);
      }

      localStorage.removeItem('token');
      this.$router.push('/login');
    },


    /*async deleteProduct(id) {
      try {
        await axios.delete(`http://localhost:9090/api/products/${id}`);
        this.products = this.products.filter(product => product.id !== id);
      } catch (error) {
        console.error('Error deleting product:', error);
      }
    }*/


  },
  mounted() {
    this.fetchProducts();
  }
};
</script>

<style>
.admin-page {
  max-width: 600px;
  margin: auto;
  text-align: center;
}
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
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
</style>
