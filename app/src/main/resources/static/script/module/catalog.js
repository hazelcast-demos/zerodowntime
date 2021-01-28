import { toJson } from "./util.js";
import { fetchOps, updateOpsDisplay } from "./ops.js";
import { fetchCart, updateCartDisplay } from "./cart.js";
import { fetchCustomers } from "./customer.js";

(function () {
    const addToCart = (event) => {
        fetch(
            `/rest/cart/add/${event.target.dataset.productId}`,
            {method: 'POST'}
        ).then(toJson)
        .then(updateCartDisplay)
        .then(updateOpsDisplay);
    };
    const attachEventHandlerToProductButton = (button) => {
        button.onclick = addToCart;
    };
    const attachEventHandlers = () => {
        document.querySelectorAll('.add-to-cart').forEach(attachEventHandlerToProductButton);
    };
    window.onload = () => {
        attachEventHandlers();
        fetchCart();
        fetchOps();
        fetchCustomers('catalog');
    };
})();