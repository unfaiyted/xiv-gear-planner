module.exports = {

    settings: { //settings
        url: "http://api.xivdb.com/",
        rateLimit: 5,
    },

    searchFor: function (type, search) {
        type = typeof type !== 'undefined' ? type : '';
        search = typeof search !== 'undefined' ? search : '';
        search = encodeURI(search); // ensure proper encoding for url

        return fetch(`${this.settings.url}search?string=${search}&one=${type}`)
            .then(response => response.json());
    },

    getItemData : (id,param) => {
        return fetch(`${this.settings.url}item/${id}`)
            .then(response => response.json());
    },

    getPlayerdata: (id, param) => { // https://github.com/xivdb/api/blob/master/Character.md
        param = typeof param !== 'undefined' ? `?data=${param}` : '';
        return fetch(`${this.settings.url}character/${id}${param}`)
            .then(response => response.json());
    }

};