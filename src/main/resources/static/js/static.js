const lib = require('./libs/local.js');
const loader = require('./libs/loader.js');


// Static Specific Functionality
const member = require('./module/static/memberAdd');
const job = require('./module/static/editJob.js');
const deleteMember = require('./libs/remove.js');
const syncGear = require('./module/static/gearSync.js');
const bis = require('./module/static/editBIS');
const compare = require('./module/static/gearCompare.js');
const panel = require('./module/static/panel.js');


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

            // Prevent Enter from submitting the form and doing things
            $(window).keydown(function(event){
                if(event.keyCode == 13) {
                    event.preventDefault();
                    return false;
                }
            });


            let callback = function (e) {
               e.preventDefault();
                // takes search btn or input value
                let name = ($(this).val() !== "") ? $(this).val() : $('#member-search').val() ;

                if (name.length > 2 &&  module.exports.settings.rateMax === false) {

                    //full page loader
                    loader.display(0);

                    module.exports.settings.rateMax = true;
                    // Set backl to false, after timeout.
                    setTimeout(function () {
                        module.exports.settings.rateMax = false;
                    }, module.exports.settings.rateLimit);


                    lib.getData("players", encodeURI(name)).then(data => {
                        module.exports.displaySearch(data);
                    });
                }
            };


            // Press Enter or Type Submit
          //  $('#member-find').keydown(callback);
            $('#member-find').click(callback);

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
                            $(`<button class="btn btn-secondary import add-member" data-id="${char.id}">`).text('Add')
                        )
                    )
                );
            });

            //init event handlers
            module.exports.initEvents();
            member.initModule();

            setTimeout(function () {
                //full page loader hide. finished
                loader.hide(0);
            }, 1000)


        },

};


// init main function
module.exports.init();

// Generates a page loader, hidden on creation
loader.injectFullPageLoader();

// Init the job functionality to edit job inline.
job.init();

// Allowes BIS editing functionality.
bis.init();

// Initilized functionality to delete an object form data/display.
deleteMember.init({
    dataSet: "../api/static/member/delete",
    triggerClass: "delete-btn",
    displayClass: "static-member",
    deleteMsg: "Are you sure you want to delete this static member?"
});


syncGear.init();

$(document).scroll(function() {
    var y = $(this).scrollTop();
    if (y > 100) {
        $('#member-list-container').css("position","fixed");
        $('#member-list-container').css("display","absolute");
        $('#member-list-container').css("width","400px");
    }

    if (y < 100) {
        $('#member-list-container').removeAttr("style");
    }

});


compare.init();

panel.init();
