(function($) {

    // Event Handlers
    $('#search').click(function(e) {
        e.preventDefault();
        let query = $('#user-input').val();
        search(encodeURI(query));
    });

    // Creates Search Data
    function search(query) {

        $('#search-results').empty();

        // Prevent searching for nothing
        if(query === undefined || query.length < 2) {

            $('#search-results').append(
                $("<tr>").append(
                    $('<td>').append(
                        $(`<p>`).text("Search incorrect")
                    ),
                )
            );

            return;
        }

        $.getJSON("http://api.xivdb.com/search?string="+ query +"&one=characters")
            .done(function (json) {
                $.each(json.characters.results, function (i, char) {

                    $('#search-results').append(
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

                    //Enables import event handlers
                    initEventHandlers();

                });

            })
            .fail(function (jqxhr, textStatus, error) {

                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            });

    }

    function getFullPlayerData(id) {
        $.getJSON("http://api.xivdb.com/character/"+ id)
            .done(function (json) {
               return json;
            })
            .fail(function (jqxhr, textStatus, error) {
                var err = textStatus + ", " + error;
                console.log("Request Failed: " + err);
            });
    }


    function returnPlayerObject(json) {
        const Player = {
            name: json.data.name,
            gender: json.data.gender,
            avatar: json.data.avatar,
            portrait: json.data.portrait,
            lodestone_id: json.data.id
        };

            console.log(Player);

    }

    function initEventHandlers() {

        $('.import').off();
        $('.import').click(function (e) {
            e.preventDefault();

            let json = getFullPlayerData($(this).data("id"));

            returnPlayerObject(json);


            $(this).disable();
        });

    }


})(jQuery);
