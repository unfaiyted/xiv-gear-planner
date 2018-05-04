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

    // Returns object in JSON format as used in
    // application
    function returnPlayerObject(json) {
        console.log(json);
        const Player = {
            name: json.data.name,
            gender: (json.data.gender === "male") ? 0 : 1,
            avatar: json.data.avatar,
            server: json.data.server,
            portrait: json.data.portrait,
            loadstone_id: json.lodestone_id
        };

        return Player;
    }


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
