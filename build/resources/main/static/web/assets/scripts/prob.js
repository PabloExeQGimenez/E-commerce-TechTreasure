const { createApp } = Vue;
createApp({
    data() {
        return {
            listbrands: [],
            brand: "",
            productsBrand: [],
        };
    },
    created() {
        let queryParams = new URLSearchParams(window.location.search);
        let brandd = queryParams.get("brand");
        this.brand = brandd.toUpperCase();

        console.log(this.brand)

        axios.get(`http://localhost:8080/api/brands/${this.brand}/products`)
                .then((response) => {
                    this.brands = response.data;
                    console.log(this.brands);
                })
                .catch((error) => {
                    console.error(error);
                });


        // axios.get('/api/brands/ASUS/products')
        //     .then(response => {
        //         this.listbrands = response.data;
        //         console.log(this.listbrands)

        //         // this.brand = this.listbrands.find(brand => brand.name === Name(this.brand));
        //     })
        //     .catch(error => {
        //         console.error('Error al obtener las categorías:', error);
        //     });



    },

    methods: {
        productOfBrand() {
            let queryParams = new URLSearchParams(window.location.search);
            let brandd = queryParams.get("brand");
            this.brand = brandd;

            axios.get(`http://localhost:8080/api/brands/${this.brand}/products`)
                .then((response) => {
                    this.brands = response.data;
                    console.log(this.brands);
                })
                .catch((error) => {
                    console.error(error);
                });
        },



    },
}).mount("#app");