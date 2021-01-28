import { toJson } from "./util.js";

const updateCustomersDisplay = (destination, json) => {
    const customersDropdown = document.querySelector('#customers');
    json.forEach((customer) => {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.href = `${window.location.origin}/customer/${customer.id}?to=${destination}`;
            a.className = 'dropdown-item';
            a.innerText = customer.name;
            li.appendChild(a);
            customersDropdown.appendChild(li);
        }
    );
};

export const fetchCustomers = (destination) => {
    fetch(`/rest/customer`)
        .then(toJson)
        .then(updateCustomersDisplay.bind(null, destination));
}