const { createApp } = Vue;

createApp({
    data() {
        return {
            user: {},
            name: "",
            personalData: false,
            lastName: "",
            address: [],
            telephone: [],
            email: "",
            purchaseOrders: false,
            historyPurchase: [],
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
            name: "",
            lastName: "",
            welcome: false,
            showPayments: true,
            allProductIds : [],
            totalPay:0.0,
            cardNumber: "",
            cardHolder: "",
            cvc: "",
            expiration: "",
            description:"",
            shippingAddresses:"",
            selectedShippingAddress:"",

            amountComment: 0,
            descriptionComment: "",
            commentProduct: 0,
            commentId: [],
            IdPro: 0,
        };
    },
    created() {
        axios.get("/api/customer/current")
            .then(response => {
                // Se ejecuta cuando la solicitud es exitosa
                this.user = response.data;
                console.log(this.user);
    
                this.name = this.user.name;
                this.lastName = this.user.lastName;
                this.address = [this.user.address];
                this.email = this.user.email;
                this.telephone = [this.user.telephone];
                this.historyPurchase = this.user.purchasedProducts;
                console.log(this.historyPurchase[0].name);
                console.log(this.address);
                this.shippingAddresses = this.user.address;
    
             
                const storedLoggedIn = localStorage.getItem('loggedIn');
                this.loggedIn = storedLoggedIn == 'true';
    
                
                const storedCart = localStorage.getItem('cart');
                this.cart = storedCart ? JSON.parse(storedCart) : [];
                this.calculateTotal();
                this.items = this.cart.length;
    
                this.cart.forEach(product => {
                    const productId = product.id;
                    const quantity = product.quantity || 1;
                    const price = product.price || 0;
                    for (let i = 0; i < quantity; i++) {
                        this.allProductIds.push(Number(productId));
                    }
                    this.totalPay += quantity * price;
                });
    
                const userA = this.user;
                console.log(userA);
                console.log(this.allProductIds);
    
                let queryParams = new URLSearchParams(window.location.search);
                let id = queryParams.get("id");
                this.categoryUrl = id;
    
                // axios.get(`http://localhost:8080/api/categories/${id}/products`)
                //     .then(response => {
                //         this.categories = response.data;
                //     })
                //     .catch(error => {
                //         console.error(error);
                //     });
    
                axios.get('/api/categories')
                    .then(response => {
                        this.listCategory = response.data;
                        console.log(this.listCategory);
                    })
                    .catch(error => {
                        console.error('Error al obtener las categorías:', error);
                    });
    
                axios.get("/api/products")
                    .then(response => {
                        this.products = response.data;
                        this.intelProducts = this.products.filter(product => product.name.toLowerCase().includes("intel i"));
                        console.log(this.intelProducts);
                    })
                    .catch(error => {
                        console.error(error);
                    });
    
                this.loadClients();
            })
            .catch(error => {
                // Se ejecuta en caso de error en la solicitud
                console.error("error", error);
            });
    },    
    methods: {
        addComment(){
          axios.post(`/api/new/review`, `productId=${this.commentProduct}&comment=${this.descriptionComment}&rating=${this.amountComment}`)
            .then(response=>{
                Swal.fire("successful Comment!")
                .then(()=> location.pathname = "/web/assets/pages/customer.html")
            })
            .catch(error => {
                Swal.fire({
                  icon: 'error',
                  title: 'Error',
                  text: error.response.data || 'An error occurred',
                  confirmButtonText: 'OK'
                });
            })
        },

        handleDeleteComment(commentId) {
          this.commentId = commentId;
          this.IdPro = this.commentId[0].id
          console.log(this.commentId);
          console.log(this.IdPro);

          this.deleteComment()
        },

        deleteComment(){
          axios.patch(`/api/modify/review`, `reviewId=${this.IdPro}`)
          .then(() => {
            Swal.fire({
                icon: 'success',
                text: 'Successfully delet Account',
                showConfirmButton: false,
                timer: 2000,
            })
          })
          .catch(error => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: error.response.data || 'An error occurred',
              confirmButtonText: 'OK'
            });
          })
        },

        showPaymentsm() {
            this.showPayments = true;
            this.personalData = false;
            this.purchaseOrders = false;
            this.welcome = false;
            this.$emit('showPaymentsEvent');
        },

        mostrarDatosPersonales() {
            this.personalData = true;
            this.purchaseOrders = false;
            this.welcome = false;
            this.showPayments = false;
        },

        showPurchaseOrders() {
            this.purchaseOrders = true;
            this.personalData = false;
            this.welcome = false;
            this.showPayments = false;

        },

        limitWords(text, limit) {
            const words = text.split(' ');
            if (words.length > limit) {
                return words.slice(0, limit).join(' ') + '...';
            }
            return text;
        },
        loadClients() {
            axios('http://localhost:8081/api/clients')
              .then(response => {
                this.clients = response.data;
                console.log(this.clients);
              })
              .catch(err => console.log(err));
          },
        payWithCard() {
            if (!this.validateForm()) {
              return;
            }
      
            let infoPay = {
              numberCard: this.cardNumber,
              cvc: this.cvc,
              amount: this.totalAmount,
              description: this.description,
              cardHolder: this.cardHolder,
              expiration: this.expiration
            }
      
            const client = this.clients.find(client => client.cards.some(card => card.number == this.cardNumber));
            console.log(client);
            if (!client) {
              this.showError("That card number does not have a customer associated with the bank. (The card does not exist.)");
              return false;
            }
      
            const cardDebit = client.cards.find(card => card.number == this.cardNumber);
      
            const today = new Date().toISOString().split("T")[0];
            if (cardDebit.truDate < today) {
              this.showError("The card is expired");
              return false;
            }
      
            if (cardDebit.type != "DEBIT") {
              this.showError("The card must be a debit card");
              return false;
            }
      
            if (cardDebit.cvv != this.cvc) {
              this.showError("The cvc provided does not belong to the associated card");
              return false;
            }
      
            const activeAccounts = client.accounts.filter(account => account.active);
            console.log(activeAccounts);
      
            const account = activeAccounts.reduce((minDateAccount, currentAccount) => {
              return minDateAccount.creationDate < currentAccount.creationDate ? minDateAccount : currentAccount;
            }, activeAccounts[0]);
            console.log(account);
      
            if (account.balance < this.amount) {
              this.showError("The amount cannot exceed the amount in the account");
              return false;
            }
      
            axios.post('http://localhost:8081/api/cards/pay', infoPay)
              .then(async () => {
                await Swal.fire({
                  position: "center",
                  icon: "success",
                  title: "the payment was made successfully",
                  showConfirmButton: true,
                });
                setTimeout(() => {
                    console.log(this.selectedShippingAddress);
                    console.log(this.allProductIds);
                  axios.post('/api/new/buy', {
                    productIds:this.allProductIds,
                    shippingAddress:this.selectedShippingAddress,
                  }).then((res) => {
      
                    const blob = new Blob([res.data], { type: 'application/pdf' })
                    const url = window.URL.createObjectURL(blob)
                    const link = document.createElement('a')
                    link.href = url;
                    link.setAttribute('download', 'ticket.pdf')
                    document.body.appendChild(link)
                    link.click();
                    window.URL.revokeObjectURL(url)
      
                  }).catch((err) => {
                    console.log(err);
                    Swal.fire({
                        position: "center",
                        icon: "error",
                        title: "An error occurred with your payment method, please validate the data and try again.",
                        showConfirmButton: true,
                    }).then((result) => {
                        if (result.isConfirmed) {
                            console.log("User clicked OK");
                        }
                    });
                });
                }, 1200);
                // location.reload()
                setTimeout(()=>{
                 location.href = "/web/assets/pages/customer.html"
                },1600)
      
              })
              .catch(err => {
                console.log(err);
                Swal.fire({
                  position: "center",
                  icon: "error",
                  title: "An error occurred with your payment method, please validate the data and try again.",
                  showConfirmButton: true,
                });
              })
              .finally(() => {
                this.cardNumber = "";
                this.cvc = "";
                this.amount = 0.0;
                this.description = "";
              })
          },
          validateForm() {
            return (
              this.validateCardHolder() &&
              this.validateCardNumber() &&
              this.validateExpiration() &&
              this.validateCVC() &&
              this.validateAmount() &&
              this.validateDescription()
            );
          },
          validateCardNumber() {
            const numericCardNumber = this.cardNumber.replace(/[-\s]/g, '');
            if (!/^\d+$/.test(numericCardNumber)) {
              this.showError("Card number should only contain numbers.");
              return false;
            }
            if (!/^\d{4}-\d{4}-\d{4}-\d{4}$/.test(this.cardNumber)) {
              this.showError("Invalid card number format. Please use the format 1234-5678-9012-3456.");
              return false;
            }
            return true;
          },
          handleCVCInput() {
            this.cvc = this.cvc.replace(/\D/g, '');
      
            this.cvc = this.cvc.slice(0, 3);
          },
          validateCVC() {
            if (!/^\d{3}$/.test(this.cvc)) {
              this.showError("CVC must be a 3-digit number");
              return false;
            }
      
            return true;
          },
          validateAmount() {
            if (this.amount <= 0) {
              this.showError("Enter a value greater than zero");
              return false;
            }
            return true;
          },
          validateDescription() {
            if (this.description.trim() === "") {
              this.showError("Missing description");
              return false;
            }
            return true;
          },
          showError(message) {
            Swal.fire({
              position: "center",
              icon: "error",
              title: message,
              showConfirmButton: true,
            });
          },
          formatCardNumber() {
            this.cardNumber = this.cardNumber.replace(/[^0-9]/g, '');
      
            this.cardNumber = this.cardNumber.slice(0, 16);
      
            const formattedCardNumber = this.cardNumber.match(/.{1,4}/g)?.join('-') || '';
      
            this.cardNumber = formattedCardNumber;
          },
          validateCardHolder() {
            if (this.cardHolder.trim() == "") {
              this.showError("Missing cardholder name");
              return false;
            }
            return true;
          },      
          validateExpiration() {
            if (this.expiration.trim() === "") {
              this.showError("Missing expiration date");
              return false;
            }
      
            if (!/^\d{2}\/\d{2}$/.test(this.expiration)) {
              this.showError("Invalid expiration date format. Please use the format MM/YY");
              return false;
            }
      
            return true;
          },
          formatExpiration() {
            this.expiration = this.expiration.replace(/[^0-9]/g, ''); // Eliminar caracteres no numéricos
      
            if (this.expiration.length > 2) {
              const firstTwo = this.expiration.substring(0, 2);
              const month = parseInt(firstTwo, 10);
      
              if (month < 1 || month > 12) {
                this.expiration = '01';
              } else {
                const remainingDigits = this.expiration.substring(2, 4);
                this.expiration = `${firstTwo}/${remainingDigits}`;
              }
            }
          },
        //carrito

        redirect() {
            this.amountMandar = this.totalAmount.toString()
            console.log(this.amountMandar)
            window.location.reload();
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
                                location.href = '/web/assets/pages/customer.html'
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
                   
                    return axios.post('/api/login', `email=${this.emailRegister}&password=${this.passwordRegister}`)


                })
                .then(loginResponse => {
                    console.log('Usuario registrado y logueado con éxito.');
                    if (this.emailRegister.includes("@admin")) {
                        window.open("/web/assets/pages/admin.html", "_blank");
                    } else {
                        window.open("/web/assets/pages/customer.html", "_blank");
                    }

                    localStorage.setItem('loggedIn', 'false');

                    this.loggedIn = false;
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
}).mount("#app");