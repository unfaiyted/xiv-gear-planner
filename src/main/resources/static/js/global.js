(function($) {


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



})(jQuery);