const { createApp } = Vue

  createApp({
    data() {
      return {
        currentSection: null,
        brands: [],
        categories: [],
        brandProduct: null,
        selectedCategory: null,
        productId: '',
        deactProduct: false,
        emailCustomer: '',
        nameProduct: "",
        priceProduct: 0.00,
        stockProduct: -1,
        descriptionProduct: "",
        imageURL:"",
        categoryProduct:"",
        products:[],
        searchQuery: '',
        searchCategory:"",
        searchCustomer:"",
        filteredProducts: [],
        filteredCategories: [],
        filteredCustomers:[],
        idProduct:"",
        idProductToDeactivate:"",
        descriptionCategory:"",
        idCategory:"",
        idCategoryToDeactivate:"",
        deactCategory:false,
        customers:[],
        idCustomerToDelete:"",
        idCustomer:"",
        nameCustomer: "",
        lastName: "",
        oldPass: "",
        pass: "",
        emailCustomer: "",
        oldEmailCustomer: "",
        addressCustomer:"",
        telCustomer:"",
      }
    },
    created(){
        this.loadCategories();
        this.loadBrands();
        this.loadProducts();
        this.loadCustomers();
    },
    methods:{
        loadCategories(){
            axios('/api/categories')
            .then(response =>{
                this.categories=response.data.sort((a, b) => a.name.localeCompare(b.name));
            })
            .catch(err => console.log(err))    
        },
        loadBrands(){
            axios('/api/brands')
            .then(response =>{
                this.brands=response.data.sort((a, b) => a.localeCompare(b));
            })
            .catch(err => console.log(err))
        },
        loadProducts(){
            axios('/api/products')
            .then(response =>{
                this.products=response.data.sort((a, b) => a.name.localeCompare(b.name));
            })
            .catch(err => console.log(err))
        },
        loadCustomers(){
            axios('/api/get/customers')
            .then(response =>{
                this.customers=response.data.sort((a, b) => a.email.localeCompare(b.email));
            })
            .catch()
        },
        showSection(section) {
            this.currentSection = section;
            if (section == 'listProduct') {
                this.filteredProducts = [];
            }
            if(section == 'listCategories'){
                this.filteredCategories=[];
            }
            if(section == 'listCustomers'){
                this.filteredCustomers=[];
            }
        },
        createProduct(){
            if (!this.nameProduct) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide a name for the product.',
                });
                return;
            }
            if (this.priceProduct <= 0) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide a valid price for the product.',
                });
                return;
            }
            if (this.stockProduct < 0) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide a valid stock quantity for the product.',
                });
                return;
            }
            if (!this.brandProduct) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide a brand for the product.',
                });
                return;
            }
            if (!this.descriptionProduct) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide a description for the product.',
                });
                return;
            }
            if (!this.imageURL) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide an image URL for the product.',
                });
                return;
            }
            if (!this.categoryProduct) {
                Swal.fire({
                  icon: 'error',
                  title: 'Please provide a category for the product.',
                });
                return;
            }

            const infoProduct={
                name: this.nameProduct,
                price: this.priceProduct,
                stock:this.stockProduct,
                brand:this.brandProduct,
                description:this.descriptionProduct,
                imageURL:this.imageURL,                
                categoryId:this.categoryProduct
            }
            axios.post('/api/products/create',infoProduct)
            .then( () =>{
                Swal.fire({
                    icon: 'success',
                    title: 'Successfully created product',
                    showConfirmButton: false,
                    timer: 1500
                  });
                  this.loadProducts();
                  this.resetForm();
                  this.showSection('listProduct');
            })
            .catch(err => {
                console.log(err)
                Swal.fire({
                    icon: 'error',
                    title: 'Error creating product',
                    text: error.response.data,
                });
            })
        },
        updateProduct(){
            if (!this.nameProduct && this.priceProduct <= 0 && this.stockProduct < 0 && !this.brandProduct && !this.descriptionProduct && !this.imageURL && !this.categoryProduct) {
                Swal.fire({
                    icon: 'error',
                    title: 'Please provide at least one field to update.',
                });
                return;
            }            

            const updatedProduct = {
                name: this.nameProduct,
                price: this.priceProduct,
                stock:this.stockProduct,
                brand:this.brandProduct,
                description:this.descriptionProduct,
                imageURL:this.imageURL,                
                categoryId:this.categoryProduct
            };
        
            axios.post(`/api/products/${this.idProduct}/update`, updatedProduct)
                .then(() => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Successfully updated product',
                        showConfirmButton: false,
                        timer: 1500 
                    });
                    this.loadProducts();
                    this.resetForm();
                    this.searchQuery=""
                    this.showSection('listProduct');
                })
                .catch(error => {
                    console.error(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error updating product',
                        text: error.response.data,
                    });
                });
        },
        deactivateProduct() {
            const newStatus = !this.deactProduct;
            axios.patch(`/api/products/${this.idProductToDeactivate}/deactivate`, `isActive=${!this.deactProduct}`)
                .then(() => {
                    Swal.fire({
                        icon: 'success',
                        title: newStatus ? 'Product successfully activated' : 'Product successfully deactivated',
                        showConfirmButton: false,
                        timer: 1500
                    });
        
                    this.loadProducts();
                    this.resetForm();
                    this.searchQuery=""
                    this.showSection('listProduct');
                })
                .catch(error => {
                    console.error(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error updating product status',
                        text: error.response.data,
                    });
                });
        },
        // createCategory(){
        //     const infoCategory= `?name=${this.categoryName}&description=${this.categoryDescription}`

        //     axios.post('/api/categories/create',infoCategory)
        //     .then(() => {
        //         Swal.fire({
        //             icon: 'success',
        //             title: "Category created successfully",
        //             showConfirmButton: false,
        //             timer: 1500
        //         });
        //     })
        //     .catch(error => {
        //         Swal.fire({
        //             icon: 'error',
        //             title: 'Error creating category',
        //             text: error.response.data,
        //         });
        //     })
        // },
        updateCategory(){
            if (!this.descriptionCategory) {
                Swal.fire({
                    icon: 'error',
                    title: 'Please provide a description for the category.',
                });
                return;
            }
            axios.post(`/api/categories/${this.idCategory}/update`,`description=${this.descriptionCategory}`)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Successfully updated category',
                    showConfirmButton: false,
                    timer: 1500 
                });
                this.loadCategories();
                this.showSection('listCategories');
            })
            .catch(error =>{
                console.error(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error updating category',
                        text: error.response.data,
                    });
            })
        },
        deactivateCategory(){
            console.log('Before axios request, deactCategory:', this.deactCategory);
            const newStatus = !this.deactCategory;
            axios.patch(`/api/categories/${this.idCategoryToDeactivate}/deactivate`, `isActive=${!this.deactCategory}`)
                .then(() => {
                    console.log('After successful axios request, deactCategory:', this.deactCategory);
                    Swal.fire({
                        icon: 'success',
                        title: newStatus ? 'Category successfully activated' : 'Category successfully deactivated',
                        showConfirmButton: false,
                        timer: 1500
                    });
        
                    this.loadCategories();
                    this.showSection('listCategories');
                })
                .catch(error => {
                    console.log('Error in axios request, deactCategory:', this.deactCategory);
                    console.error(error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error updating category status',
                        text: error.response.data,
                    });
                });
        },
        updateName(){
            if (!this.nameCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Name cannot be empty',
                    text: 'Please provide a name to update.',
                });
                return;
            }
            const infoName= `customerId=${this.idCustomer}&newName=${this.nameCustomer}`
                axios.patch('/api/modify/name/customer',infoName)
                .then(() => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Name updated successfully',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    this.loadCustomers();
                    this.showSection('listCustomer');
                })
                .catch(err => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error updating name',
                        text: err.message
                    });
                })
        },
        updateLastName() {
            if (!this.lastName) {
                Swal.fire({
                    icon: 'error',
                    title: 'Last name cannot be empty',
                    text: 'Please provide a last name to update.',
                });
                return;
            }
            const infoLastName= `customerId=${this.idCustomer}&newLastName=${this.lastName}`
            axios.patch('/api/modify/lastName/customer',infoLastName)
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: 'Last name updated successfully',
                    showConfirmButton: false,
                    timer: 1500
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Error updating last name',
                    text: err.message
                });
            })   
        },
        updatePassword(){
            if (!this.oldPass) {
                Swal.fire({
                    icon: 'error',
                    title: 'Old Password cannot be empty',
                    text: 'Please provide the old password.',
                });
                return;
            }
            if (!this.pass) {
                Swal.fire({
                    icon: 'error',
                    title: 'New Password cannot be empty',
                    text: 'Please provide the new password.',
                });
                return;
            }
            if (this.oldPass == this.pass) {
                Swal.fire({
                    icon: 'error',
                    title: 'Old and New Passwords are the same',
                    text: 'Please provide a different new password.',
                });
                return;
            }
            //! VALIDAR CONTRASEÑA, HACER ENDPOINT PASS EN CUSTOMER
            /*const currentPassword= this.customers.find(customer => customer.id == this.idCustomer).password;
            if (this.oldPass != currentPassword) {
                console.log("Old "+oldPass);
                console.log("Current "+currentPassword);
                Swal.fire({
                    icon: 'error',
                    title: 'Invalid Old Password',
                    text: 'Please provide the correct old password.',
                });
                return;
            }*/
            const infoPass = `customerId=${this.idCustomer}&passwordOld=${this.oldPass}&passwordNew=${this.pass}`
            axios.patch('/api/modify/password/customer',infoPass)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Password updated successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Error updating password',
                    text: err.message || 'Unknown error',
                });
            })
        },
        updateEmail(){
            if (!this.oldEmailCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Old Email cannot be empty',
                    text: 'Please provide the old email.',
                });
                return;
            }
        
            if (!this.emailCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'New Email cannot be empty',
                    text: 'Please provide the new email.',
                });
                return;
            }
        
            if (this.oldEmailCustomer == this.emailCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Old and New Emails are the same',
                    text: 'Please provide a different new email.',
                });
                return;
            }
            const infoPass = `customerId=${this.idCustomer}&oldEmail=${this.oldEmailCustomer}&newEmail=${this.emailCustomer}`
            axios.patch('/api/modify/email/customer',infoPass)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Email updated successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Error updating email',
                    text: err.message || 'Unknown error',
                });
            })
        },
        deleteCustommer(customer){
            this.idCustomerToDelete=customer.id;
            console.log(this.idCustomerToDelete);
            axios.delete(`/api/delete/customer?idcustomer=${this.idCustomerToDelete}`)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Customer delete successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Error delete customer',
                    text: err.message || 'Unknown error',
                });
            })
        },
        addAddress(){
            if (!this.addressCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Please enter an address',
                });
                return;
            }
            const infoAddress=`customerId=${this.idCustomer}&newAddress=${this.addressCustomer}`;
            console.log(infoAddress);
            axios.post('/api/add/address',infoAddress)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Address add successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Error add address',
                    text: err.message || 'Unknown error',
                });
            })
        },
        removeAddress(){
            if (!this.addressCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Please enter an address',
                });
                return;
            }
            const encodedAddress = encodeURIComponent(this.addressCustomer);
            axios.delete(`/api/delete/address?customerId=${this.idCustomer}&address=${encodedAddress}`)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Address delete successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Error delete address',
                    text: err.message || 'Unknown error',
                });
            })
        },
        addTel(){
            if (!this.telCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Please enter a telephone',
                });
                return;
            }
            const infoTelephone=`customerId=${this.idCustomer}&newTelephone=${this.telCustomer}`;
            axios.post('/api/add/telephone',infoTelephone)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Telephone add successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Error add telephone',
                    text: err.message || 'Unknown error',
                });
            })
        },
        removeTel(){
            if (!this.telCustomer) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Please enter a telephone',
                });
                return;
            }
            const encodedTelephone = encodeURIComponent(this.telCustomer);
            axios.delete(`/api/delete/telephone?customerId=${this.idCustomer}&telephone=${encodedTelephone}`)
            .then(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Telephone delete successfully',
                    showConfirmButton: false,
                    timer: 1500,
                });
                this.loadCustomers();
                this.showSection('listCustomer');
            })
            .catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Error delete telephone',
                    text: err.message || 'Unknown error',
                });
            })
        },
        getCategoryName(categoryId) {
            const category = this.categories.find(cat => cat.id == categoryId);
            return category ? category.name.replace(/_/g, ' ').toLowerCase().replace(/(?:^|\s)\S/g, char => char.toUpperCase()) : 'Unknown';
        },
        searchProducts() {
            this.filteredProducts = this.products.filter(product =>
                product.name.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        },
        searchCategories() {
            this.filteredCategories = this.categories.filter(category =>
                category.name.toLowerCase().includes(this.searchCategory.toLowerCase())
            );
        },
        searchCustomers() {
            this.filteredCustomers = this.customers.filter(customer =>
                customer.email.toLowerCase().includes(this.searchCustomer.toLowerCase())
            );
        },
        loadUpdateSection(product) {
            this.currentSection = 'updateProd';
            this.idProduct = product.id;
        },
        loadDeactivateSection(product){
            this.currentSection = 'deactiveProd';
            this.idProductToDeactivate=product.id;
        },
        loadUpdateCategory(category){
            this.currentSection = 'updateCategory';
            this.idCategory = category.id;
        },
        loadDeactivateCategory(category){
            this.currentSection = 'deactiveCategory';
            this.idCategoryToDeactivate=category.id;
        },
        loadUpdateCustomer(customer){
            this.currentSection = 'updateCustomer';
            this.idCustomer=customer.id;
        },  
        logOut(){
            axios.post('/api/logout')
            .then(()=>{
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    iconColor: '#fd611a',
                    title: 'Logout Ok',
                    showConfirmButton: false,
                    timer: 1300
                })
                window.location.href="../../index.html"
            })
            .catch(err => console.log(err))
        },
        resetForm() {
            this.idProduct = "";
            this.nameProduct = "";
            this.priceProduct = 0.00;
            this.stockProduct = -1;
            this.brandProduct = "";
            this.descriptionProduct = "";
            this.imageURL = "";
            this.categoryProduct = "";
        },
        formatBrand(brand) {
            return brand.replace(/_/g, ' ').toLowerCase().replace(/(?:^|\s)\S/g, char => char.toUpperCase());
        },
        formatCategoryName(category) {
            return category.replace(/_/g, ' ').toLowerCase().replace(/(?:^|\s)\S/g, char => char.toUpperCase());
        }
    }
  }).mount('#app')