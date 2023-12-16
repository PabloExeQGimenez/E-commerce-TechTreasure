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

            user: "",
            lastName : "",
            name: "",

            products: [],
            categories: [], // productos por marca
            brandName: "",  //nombre de la marca
            categoryName: [],
            selectedBrand: "",
            sortByPrice: "",
            listCategory: [], //todas las categorias
            categoryId: [],
            

            filterBrandProduct: [], //me filtra por marca 

            inputSearch:``,
            filterProducts: [],
            
        };
    },
    created() {
        // Guardar los productos originales por categoría al cargar la página
       /*  this.productOfCategory(); */

        /*************************** trae todos los productos ********************************************************/             
        axios.get("http://localhost:8080/api/products")
            .then((response) => {
                this.products = response.data;
                console.log(this.products);
            })
            .catch((error) => {
                console.error(error);
            });

        /*************************** trae todos los productos de una sola los marca ***********************************/ 
        let queryParams = new URLSearchParams(window.location.search);
        let productBrand = queryParams.get("brand");
        this.brandName = productBrand

        axios.get(`/api/brands/${productBrand}/products`)
            .then((response) => {
                this.categories = response.data;
                console.log(this.categories);
            })
            .catch((error) => {
                console.error(error);
            });

        /* trae todas las categorias ********************************************************************************/    
        axios.get('/api/categories')
            .then(response => {
                this.listCategory = response.data;
                console.log(this.listCategory);



                // Suponiendo que this.category es un array de objetos
                const productCategoryId = this.categories.map(category => category.categoryId);
                console.log(productCategoryId);

                // Filtrar this.listCategory por los categoryIds presentes en this.category
                this.categoryName = this.listCategory.filter(category => productCategoryId.includes(category.id));
                
                console.log(this.categoryName);
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

    },
    watch: {
        /* selectedBrand(newValue, oldValue) {
          if (newValue !== oldValue && newValue !== "") {

            axios
              .get(`/api/brands/${newValue}/products`)
              .then((response) => {
                this.filterBrandProduct = response.data;
                
                    if (this.selectedBrand !== "") {
                    this.categories = this.filterBrandProduct.filter(product => product.categoryId == this.categoryId.id);
                    }

              })
              .catch((error) => {
                console.error('Error ', error);
              });
            }
            else{
                this.productOfCategory()
            }
        }, */

        sortByPrice(newValue, oldValue) {
            if (newValue !== oldValue){
                if(this.sortByPrice == "asc"){
                    this.categories = this.categories.sort((a, b) => a.price - b.price);
                } else if (this.sortByPrice == 'desc') {
                    this.categories = this.categories.sort((a, b) => b.price - a.price);
                }
            }
        }
    },

    methods: {
        /* updateSelectedBrand(brand) {
            if (this.selectedBrand !== brand) {
              this.selectedBrand = brand;
            } else {
              this.selectedBrand = '';
            }
        }, */

        
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
        }
      },
}).mount("#app");