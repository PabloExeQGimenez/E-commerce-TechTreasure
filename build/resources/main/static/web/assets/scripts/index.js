

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
            listCategory: [],
            intelProducts: [],
            categories: [],
            cart: [],
            productsCart: [],
            products: [],
            totalAmount: 0,
            items: 0,
            amountMandar: "",
            loggedIn: true,
            inputSearch: "",
            filterProducts: [],
            name:"",
            lastName:"",
            notebooks:[],


        };


    },

    created() {
        axios.get("/api/customer/current")
        .then(response => {
            this.user = response.data;
            console.log(this.user)
            this.name = this.user.name
            this.lastName = this.user.lastName
            this.address = [this.user.address]
            this.email = this.user.email
            this.telephone = [this.user.telephone]
            this.historyPurchase = this.user.purchasedProducts
            console.log(this.historyPurchase[0].name)

        })
        .catch(error => {
            console.error("error", error);
        });
        const storedLoggedIn = localStorage.getItem('loggedIn');
        this.loggedIn = storedLoggedIn === 'true';

        console.log(this.nameRegister)
        const storedCart = localStorage.getItem('cart');
        this.cart = storedCart ? JSON.parse(storedCart) : [];
        this.calculateTotal();
        this.items = this.cart.length;


        let queryParams = new URLSearchParams(window.location.search);
        let id = queryParams.get("id");
        this.categoryUrl = id;

        axios.get(`http://localhost:8080/api/categories/${id}/products`)
            .then((response) => {
                this.categories = response.data;


            })
            .catch((error) => {
                console.error(error);
            });

        axios.get('/api/categories')
            .then(response => {
                this.listCategory = response.data;
                console.log(this.listCategory);
            })
            .catch(error => {
                console.error('Error al obtener las categorías:', error);
            });

        axios
            .get("/api/products")
            .then((response) => {
                this.products = response.data;
                this.intelProducts = this.products.filter(product => product.name.toLowerCase().includes("intel i"));
                console.log(this.intelProducts);
                this.notebooks = this.products.filter(product => product.name.toLowerCase().includes("notebook"));
                console.log(this.notebooks)

            })
            .catch((error) => {
                console.error(error);
            });


    },



    methods: {

        //carrito

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

        // fin carrito

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
                                location.href = "/web/assets/pages/admin.html";
                            } else {
                                location.href='/web/assets/pages/customer.html'
                            }

                        }
                    }, 1700)
                    localStorage.setItem('loggedIn', 'false');

                    this.loggedIn = false;

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
    computed: {
        filter() {
            this.filterProducts = this.products.filter(event => event.name.toLowerCase().includes(this.inputSearch.toLowerCase()))
        }
    },
}).mount('#app');


