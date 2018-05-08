const lib = require('./libs/local.js');
const member = require('./module/static/memberAdd');


module.exports = {

        settings: {
            rateLimit: 250, // limit the rate the search will go off.
            rateMax: false,
            results:  $('#search-results')
        },

    // Constructor more or less? ish
    init: () => {
        module.exports.initEvents();
    },

    // Declares event handlers
    initEvents: () => {

            // Turn all off.
            $('#member-find').off();

            // Press Enter or Type Submit
            $('#member-find').keydown(function () {
                let name = $(this).val();

                if (name.length > 2 &&  module.exports.settings.rateMax === false) {

                    module.exports.settings.rateMax = true;
                    // Set backl to false, after timeout.
                    setTimeout(function () {
                        module.exports.settings.rateMax = false;
                    }, module.exports.settings.rateLimit);

                    lib.getData("players", encodeURI(name)).then(data => {
                        module.exports.displaySearch(data);
                    });
                }
            });
    },

    displaySearch: (json) => {
            // Clear data
        module.exports.settings.results.empty();

        // Prints display to results location
            $.each(json, function (i, char) {

                module.exports.settings.results.append(
                    $("<tr>").append(
                        $('<th scope="row" class="d-none d-sm-table-cell">').text(i + 1),
                        $('<td>').append(
                            $(`<img src=${char.avatar} class="char-img">`)
                        ),
                        $('<td class="char-name">').text(char.name),
                        $('<td class="d-none d-sm-table-cell char-server">').text(char.server),
                        $('<td>').append(
                            $(`<button class="btn btn-secondary import addMember" data-id="${char.id}">`).text('Add')
                        )
                    )
                );
            });

            //init event handlers
            module.exports.initEvents();
            member.initModule();

        },

};

module.exports.init();