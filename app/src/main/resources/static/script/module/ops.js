import { toJson } from "./util.js";

export const updateOpsDisplay = (json) => {
    document.querySelector('#version').textContent = json.version;
    document.querySelector('#hostname').textContent = json.hostname;
};

export const fetchOps = () => {
    fetch(`/rest/ops`)
        .then(toJson)
        .then(updateOpsDisplay);
}