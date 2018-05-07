(function($) {

    const xiv = require('./libs/xibdb.js');
    const {addData} = require('./libs/local.js');

    module.exports = {

        settings: { //settings
            minLength: 3,
            userInput: $('#user-input'),
            results: $('#search-results')

        },

        initEvents: () => {

            // Event Handlers
            $('#search').click(function (e) {
                e.preventDefault();
                let query = this.settings.userInput.val();

                if (query.length > 3) {
                    this.playerSearch(encodeURI(query));
                }
            });


        },

        playerSearch: (query) => {
            this.settings.results.empty();

            xiv.searchFor("characters",query).then(data => {
                    return data;
            })

        },

        searchList: (json) => {

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


    }

    // Returns object in JSON format as used in
    // application



    function submitPlayerData(Player) {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        // $(document).ajaxSend(function(e, xhr, options) {
        //     xhr.setRequestHeader(header, token);
        // });

        $.ajax({
            url : "/import/player/data",
            contentType: "application/json",
            type: 'POST',
            async: false,
            data: JSON.stringify(Player),
            beforeSend: function(xhr) {
                // here it is
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                console.log(data);
                pageAlert("Player was imported", "success");


            },
            error: function(result) {
                console.log("error", result);
                pageAlert("Error adding user", "danger");
            }
        });
    }

    function getPlayerInfo(id) {
        return new Promise(function(resolve, reject) {

            $.getJSON("http://api.xivdb.com/character/"+ id)
                .done(function (json) {
                    resolve(json);
                })
                .fail(function (jqxhr, textStatus, error) {
                    pageAlert("Error pulling full character data", "danger");
                    let err = textStatus + ", " + error;
                    reject(Error(err));
                });
            });
    }


    function pageAlert(message, type) {
        $('#alert-content').text(message);
        $('#alert').addClass("alert-"+type).show();
    }

    //Setup event handlers on search completion
    function initEventHandlers() {

        //turns off events
        $('.import').off();
        $('.import').click(function (e) {
            e.preventDefault();

            getPlayerInfo($(this).data('id')).then(function(response) {
                let Player = returnPlayerObject(response);
                submitPlayerData(Player);

            }, function(error) {
                console.error("Failed!", error);
            });

        });

    }

$('#alert').hide();
})(jQuery);
