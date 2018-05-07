// Functions for local json file interactions
module.exports = {

    settings: { //settings
        url: "http://localhost:9090/",
        rateLimit: 5,
        token: $("meta[name='_csrf']").attr("content"),
        header: $("meta[name='_csrf_header']").attr("content")
    },

    addData: (location, data) => {
        location = typeof location !== 'undefined' ? location : "";

        return fetch("/api/player/add", {
            method: "post",
            credentials: "same-origin",
            headers: {
                "X-CSRFToken": this.settings.token,
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }).then(response => response.json());
    }



};