// Functions for local json file interactions
module.exports = {

    settings: { //settings
        url: "/api/",
        rateLimit: 5,
        token: $("meta[name='_csrf']").attr("content"),
        header: $("meta[name='_csrf_header']").attr("content")
    },

    //Inserts data into server
    addData: (location, data) => {
        location = typeof location !== 'undefined' ? location : "";
        return fetch(location, {
            method: "post",
            credentials: "same-origin",
            headers: {
                "X-CSRF-Token": module.exports.settings.token,
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }).then(response => response.json());
    },

    deleteData: (location, data) => {
        return module.exports.addData(location, data);
    },

    // query for post data
    // parameter for url info
    // ex: players/Name+Last/?post=3 type/parameter/query
    getData: (type, parameter, query) => {
        parameter = typeof parameter !== 'undefined' ? parameter : "";
        query = typeof query !== 'undefined' ? query : "";

        return fetch(module.exports.settings.url + type +"/"+ parameter + query)
            .then(response => response.json());
    }
};