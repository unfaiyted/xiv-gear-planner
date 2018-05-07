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

            module.exports.initEvents();
        },

        initEvents: () => {

            //turns off events
            $('.import').off();
            $('#search').off();

            // Search click submit
            $('#search').click(function (e) {
                e.preventDefault();
                let query = module.exports.settings.userInput.val();

                if (query.length > 3) {
                    module.exports.playerSearch(query).then(data =>{
                        module.exports.displaySearchData(data);
                    }).catch(() => {
                        module.exports.displayAlert("Error pulling search data.","danger");
                    });
                }
            });

            // Import data button
            $('.import').click(function (e) {
                e.preventDefault();
                xiv.getPlayerdata($(this).data('id')).then(data => {
                    let Player = toPlayerObj(data);
                    module.exports.submitPlayerData(Player);
                }).catch(() => {
                    module.exports.displayAlert("Error with player data.","danger");
                });
            });

        },

        // Returns search results with player data in JSON
        playerSearch: (query) => {
            module.exports.settings.results.empty();
            return xiv.searchFor("characters",query);
        },

        // Prints display to results location
        displaySearchData: (json) => {

            $.each(json.characters.results, function (i, char) {

                module.exports.settings.results.append(
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
            module.exports.initEvents();
        },

        submitPlayerData: (playerObj) => {
            return addData("/api/player/add",playerObj).then(() => {
                module.exports.displayAlert("Player added.","success");
            }).catch(() => {
                module.exports.displayAlert("Error saving player data.","danger");
            });
        },

        displayAlert: (msg, type) => {
            $('#alert-content').text(msg);
            $('#alert').addClass("alert-"+type).show();
        }

};


module.exports.init();