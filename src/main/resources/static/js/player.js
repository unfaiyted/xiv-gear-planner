const xiv = require('./libs/xibdb.js');
const {addData} = require('./libs/local.js');
const {toPlayerObj} = require('./libs/objects');

module.exports = {

        settings: { //settings
            minLength: 3,
            userInput: $('#user-input'),
            results: $('#search-results')
        },

        init: () => {
            $('#alert').hide();

            this.initEvents();
        },

        initEvents: () => {

            //turns off events
            $('.import').off();
            $('#search').off();

            // Search click submit
            $('#search').click(function (e) {
                e.preventDefault();
                let query = this.settings.userInput.val();

                if (query.length > 3) {
                    this.playerSearch(query).then(data =>{
                        this.displaySearchData(data);
                    }).catch(() => {
                        this.displayAlert("Error pulling search data.","danger");
                    });
                }
            });

            // Import data button
            $('.import').click(function (e) {
                e.preventDefault();
                xiv.getPlayerdata($(this).data('id')).then(data => {
                    let Player = toPlayerObj(data);
                    this.submitPlayerData(Player);
                }).catch(() => {
                    this.displayAlert("Error pulling player data.","danger");
                });
            });

        },

        // Returns search results with player data in JSON
        playerSearch: (query) => {
            this.settings.results.empty();
            return xiv.searchFor("characters",query);
        },

        // Prints display to results location
        displaySearchData: (json) => {

            $.each(json.characters.results, function (i, char) {

                this.settings.results.append(
                    $("<tr>").append(
                        $('<th scope="row" class="d-none d-sm-table-cell">').text(i + 1),
                        $('<td>').append(
                            $(`<img src=${char.icon}>`)
                        ),
                        $('<td>').text(char.name),
                        $('<td class="d-none d-sm-table-cell">').text(char.server),
                        $('<td>').append(
                            $(`<button class="btn btn-secondary import" data-id="${char.id}">`).text('Import')
                        )
                    )
                );

            });

            //init event handlers
            this.initEvents();
        },

        submitPlayerData: (playerObj) => {
            return addData("/api/player/add",playerObj).then(() => {
                this.displayAlert("Player added.","success");
            }).catch(() => {
                this.displayAlert("Error saving player data.","danger");
            });
        },

        displayAlert: (msg, type) => {
            $('#alert-content').text(message);
            $('#alert').addClass("alert-"+type).show();
        }

};


module.exports.init();
