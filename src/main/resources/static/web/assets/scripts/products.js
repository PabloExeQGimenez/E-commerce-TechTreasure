const { createApp } = Vue;

createApp({
    data() {
        return {

            products: "",
            categories: [],

        };
    },
    created() {

        axios.get("/api/products")
        .then((response =>{
            this.products = response.data
            console.log(this.products)
        }))


    },
    methods: {

    },
}).mount("#app");

