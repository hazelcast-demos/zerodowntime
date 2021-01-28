import { toJson } from "./util.js";
import { fetchOps } from "./ops.js";

(function () {
    const deleteCartLine = (event) => {
        const lineId = event.target.dataset.lineId;
        fetch(
            `/rest/checkout/delete/${lineId}`,
            {method: 'DELETE'}
        ).then(toJson)
        .then(updateCheckout.bind(null, lineId));
    };
    const attachEventHandlerToDeleteIcon = (icon) => {
        icon.onclick = deleteCartLine;
    };
    const attachEventHandlers = () => {
        document.querySelectorAll('.delete-cart-line').forEach(attachEventHandlerToDeleteIcon);
    };
    const updateCheckout = (lineId, json) => {
        const lineNode = document.querySelector(`#cart-line-${lineId}`);
        const tableBody = lineNode.parentNode;
        tableBody.removeChild(lineNode);
        document.querySelector('#price').innerText = '$' + json;
    };
    window.onload = () => {
        attachEventHandlers();
        fetchOps();
    };
})();