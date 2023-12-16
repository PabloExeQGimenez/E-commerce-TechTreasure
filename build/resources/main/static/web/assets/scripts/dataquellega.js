const { createApp } = Vue;

createApp({
    data() {
        return {

            products: [],
            categories: [],
        };
    },
    created() {

        axios
            .get("http://localhost:8080/api/products")
            .then((response) => {
                console.log(response.data)
                this.products = response.data;
                console.log(this.products);

            })
            .catch((error) => {
                console.error(error);
            });

        axios.get("http://localhost:8080/api/categories")
        .then((response => {
            this.categories = response.data;
            console.log("categories")

            console.log(this.categories)
        }))
    },
    methods: {


    },
}).mount("#app");

