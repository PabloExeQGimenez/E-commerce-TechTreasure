let queryParams = new URLSearchParams(window.location.search);
let id = queryParams.get("id");

const { createApp } = Vue;
createApp({
    data() {
        return {
            email: "",
            password: "",
            nameRegister: "",
            lastNameRegister: "",
            emailRegister: "",
            addressRegister: [],
            telephoneRegister: [],
            passwordRegister: "",
            intelProducts: [],
            cart: [],
            productsCart: [],
            totalAmount: 0,
            items: 0,
            amountMandar: "",
            loggedIn: false,
            products: [],
            productReviews: [],
            clients: [],

            user: "",
            lastName : "",
            name: "",

            allProducts: [],
            product: [],
            listCategory: [],
            category: "",
            favorite: true,
            //codigo de favoritos
            productId: "",
            heartColor: 'currentColor',

            inputSearch:``,
            filterProducts: [],
        };
    },
    created() {
        let queryParams = new URLSearchParams(window.location.search);
        let id = queryParams.get("id");
        
        axios.get("http://localhost:8080/api/products")
            .then((response) => {
                this.products = response.data;
                console.log(this.products);
            })
            .catch((error) => {
                console.error(error);
            });
        
        //me trae el producto seleccionado  
        axios.get(`http://localhost:8080/api/products/${id}`)
            .then((response) => {
                this.product = response.data;
                this.productReviews = this.product.reviews
                console.log(this.productReviews);

                this.productId = this.product.favorites.length == 0 ? false : true
                this.heartColor= this.product.favorites.length > 0 ? '#fd611a' : 'currentColor'

            })
            .catch((error) => {
                console.error(error);
            });

        // trae la categoria de un producto
        axios.get('/api/categories')
            .then(response => {
                this.listCategory = response.data;

                this.category = this.listCategory.find(cat => cat.id === this.product.categoryId);
                console.log(this.category);
            })
            .catch(error => {
              console.error('Error al obtener las categorías:', error);
            });

        axios.get("/api/customer/current")
            .then(response => {
                this.user = response.data;
                console.log(this.user)
                this.name = this.user.name
                this.lastName = this.user.lastName})

        axios.get("/api/get/customers")
            .then(response => {
                this.clients = response.data
                console.log(this.clients);

                /* this.clientReviews = this.clients.find(client => client.id == ) */
            })
            .catch(error => {
                console.error('Error al obtener clients:', error);
              });
        
    },
    methods:{
        SelectedFavorite() {
                if (this.productId == false) {
                    axios.post('http://localhost:8080/api/new/favorite', `productId=${id}`)
                        .then(response => {
                            Swal.fire("Product added to favorites!")
                            this.heartColor = '#fd611a';
                            this.productId = true;
                            console.log(this.productId);
                        })
                        .catch(error => {
                            Swal.fire({
                              icon: 'error',
                              title: 'Error',
                              text: error.response.data || 'An error occurred',
                              confirmButtonText: 'OK'
                            });
                        })
                } else if (this.productId == true){
                    axios.delete(`http://localhost:8080/api/delete/favorite?productId=${id}`)
                        .then(response => {
                            Swal.fire("Product delete to favorites!")
                            this.productId = false;
                            this.heartColor = 'currentColor';
                        })
                        .catch(error => {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: error.response.data || 'An error occurred',
                                confirmButtonText: 'OK'
                            });
                        })
                }
        },
        getHeartColor() {
            return this.heartColor;
          },



        // login pablo

        redirect() {
            this.amountMandar = this.totalAmount.toString();
            console.log(this.amountMandar);
            window.location.href = "/web/assets/pages/customer.html";
        },
        addCart(productId) {
            const existingProduct = this.cart.find(product => product.id === productId);
  
            if (existingProduct) {
                existingProduct.quantity += 1;
            } else {
                const newProductCart = {
                    ...this.products.find(product => product.id === productId),
                    quantity: 1,
                };
                this.cart.push(newProductCart);
                
            }
  
            localStorage.setItem('cart', JSON.stringify(this.cart));
            this.calculateTotal();
            this.items = this.calculateItems();
        },
  
        calculateTotal() {
            this.totalAmount = this.cart.reduce((total, product) => {
                return total + product.price * product.quantity;
            }, 0).toFixed(2);
        },
  
        calculateItems() {
            return this.cart.reduce((total, product) => {
                return total + product.quantity;
            }, 0);
        },
  
        decrementQuantity(product) {
            if (product.quantity > 1) {
                product.quantity -= 1;
                this.updateCart();
            }
        },
  
        incrementQuantity(product) {
            product.quantity += 1;
            this.updateCart();
        },
  
        updateQuantity(product, event) {
            const newQuantity = Math.max(0, parseInt(event.target.value, 10) || 0);
            product.quantity = newQuantity;
            this.updateCart();
        },
  
        updateCart() {
            localStorage.setItem('cart', JSON.stringify(this.cart));
            this.calculateTotal();
            this.items = this.calculateItems();
        },
  
  
        removeFromCart(productId) {
            const index = this.cart.indexOf(productId);
            if (index !== -1) {
                this.cart.splice(index, 1);
                localStorage.setItem('cart', JSON.stringify(this.cart));
                this.calculateTotal();
                this.items = this.cart.length;
  
            }
        },
  
        clearCart() {
            this.cart = [];
            localStorage.removeItem('cart');
            this.calculateTotal();
            this.items = 0;
        },
  
        remove_(name) {
            return name.replace(/_/g, ' ');
        },
  
        fetchProductsByBrand(brand) {
            const brandUpperCase = brand.toUpperCase();
            console.log(brandUpperCase)
            axios.get(`/api/brands/${brandUpperCase}/products`)
                .then(response => {
                    this.products = response.data;
                })
                .catch(error => {
                    console.error('Error al obtener productos por marca:', error);
                });
        },
  
  
        login() {
            axios.post('/api/login', `email=${this.email}&password=${this.password}`)
                .then(response => {
                    console.log("ok")
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: '#fd611a',
                        title: 'Login Ok',
                        showConfirmButton: false,
                        timer: 1300
                    }), setTimeout(() => {
  
                        const successfulLogin = true;
                        if (successfulLogin) {
                            this.$emit('login-successful');
                            if (this.email.includes("@admin")) {
                                window.open("/web/assets/pages/admin.html", "_blank");
                            } else {
                                window.open('/web/assets/pages/customer.html', '_blank')
                            }
  
                        }
                    }, 1700)
                    this.loggedIn = true;
  
                })
                .catch((error) => {
                    let mesageerrorregister = error.response ? error.response.data : 'Error desconocido';
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        iconColor: 'red',
                        title: 'Login failed',
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            text: 'custom-swal-text'
                        }
                    });
                });
        },
        
        register() {
            axios
                .post('/api/new/customer', `name=${this.nameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&address=${this.addressRegister}&telephone=${this.telephoneRegister}&password=${this.passwordRegister}`)
                .then(response => {
                    console.log(this.nameRegister);
                    return axios.post('/api/login', `email=${this.emailRegister}&password=${this.passwordRegister}`)
  
  
                })
                .then(loginResponse => {
                    console.log('Usuario registrado y logueado con éxito.');
                    if (this.emailRegister.includes("@admin")) {
                        window.open("/web/assets/pages/admin.html", "_blank");
                    } else {
                        window.open("/web/assets/pages/customer.html", "_blank");
                    }
  
  
                })
                .catch(error => {
                    console.error('Error en el registro:', error);
                });
        },
        openLogoutModal() {
            // Aquí puedes agregar cualquier lógica adicional antes de abrir el modal
            const modalElement = new bootstrap.Modal(document.getElementById('modal_login'));
            modalElement.show();
        },
  
        logout() {
            axios.post('/api/logout').then(response => {
                const modalElement = new bootstrap.Modal(document.getElementById('modal_login'));
                modalElement.hide();
                location.pathname = "/web/index.html";

                localStorage.setItem('loggedIn', 'true');
                this.loggedIn = true;
                this.clearCart()
            });
        }
    },

    computed:{
        filter(){
            this.filterProducts = this.products.filter(event => event.name.toLowerCase().includes(this.inputSearch.toLowerCase()))
        },
        
    },
}).mount("#app");