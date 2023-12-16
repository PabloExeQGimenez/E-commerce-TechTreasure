const { createApp } = Vue

createApp({
  data() {
    return {
      cardNumber: "",
      cardHolder: "",
      cvc: "",
      expiration: "",
      amount: 0,//Tiene que ser igual al total a pagar desde LocalStorage
      description: "",
      clients: "",
      parameter: "",
      email: "",
      password: "",
      productIdsUrl:[],//Tiene que ser igual al Array con los ids de los productos guardados en el localStorage(solo los ids) repetidos si es necesario
      shippingAddressUrl:"",//Tiene que llegar un string con la dirección de envio.
    }
  },
  created() {
    this.parameter = location.search;
    let params = new URLSearchParams(this.parameter);
    this.amount = params.get("amount");
     this.loadClients();
  },
  methods: {
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
        amount: this.amount,
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
            axios.post('/api/new/buy', {
              productIds:this.productIdsUrl,
              shippingAddress:this.shippingAddressUrl,
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
              });

            })
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
      if (this.cardHolder.trim() === "") {
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
    }
  }
}).mount('#app')