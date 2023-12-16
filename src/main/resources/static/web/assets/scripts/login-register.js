const { createApp } = Vue;

createApp({
    data() {
        return {
            email: "",
            password: "",
            listCategory: []

        };


    },

    created() {

        axios.get('/api/categories')
            .then(response => {
                this.listCategory = response.data;
                console.log(this.listCategory);
            })
            .catch(error => {
                console.error('Error al obtener las categorías:', error);
            });
    },


    methods: {
        login() {
            axios.post('http://localhost:8080/api/login', `email=${this.email}&password=${this.password}`)
                .then(response => {
                    console.log("ok")
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        iconColor: 'grey',
                        title: 'Login Ok ',
                        showConfirmButton: false,
                        timer: 900
                    }), setTimeout(() => {
                        const successfulLogin = true;
                        if (successfulLogin) {
                            this.$emit('login-successful');
                            window.location.href = '/web/index.html';
                        }
                    }, 1700)
                })
                .catch((error) => {
                    let mesageerrorregister = error.response ? error.response.data : 'Error desconocido';
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        iconColor: 'red',
                        text: mesageerrorregister,
                        showConfirmButton: false,
                        timer: 1500,
                        customClass: {
                            text: 'custom-swal-text'
                        }
                    });
                });
        },

        logout() {
            axios.post('/api/logout').then(response => {
                location.pathname = "/web/index.html";
            });
        }
    },
}).mount('#app');
