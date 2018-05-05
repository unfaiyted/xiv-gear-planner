module.exports = {

    search: function (type) {


        $.getJSON( "http://api.xivdb.com/search?string=Faiyt+Lee&pretty=1&one=characters")
            .done(function( json ) {
                console.log( "JSON Data: " + json);
            })
            .fail(function( jqxhr, textStatus, error ) {
                var err = textStatus + ", " + error;
                console.log( "Request Failed: " + err );
            });


    },

    player: function (id) {

    }



};