import { fetchCart } from "./cart.js";
import { fetchOps } from "./ops.js";
import { fetchCustomers } from "./customer.js";

(function () {
    window.onload = () => {
        fetchCart();
        fetchOps();
        fetchCustomers('catalog');
    };
})();