import { toJson } from "./util.js";

export const updateCartDisplay = (json) => {
    const itemCount = json['itemCount'];
    const cartIcon = document.querySelector('#cartIcon');
    cartIcon.className = itemCount === 0 ?'bi bi-cart' : 'bi bi-cart-fill';
    document.querySelector('#cartItemCount').textContent = itemCount;
};

export const fetchCart = () => {
    fetch(`/rest/cart`)
        .then(toJson)
        .then(updateCartDisplay);
}