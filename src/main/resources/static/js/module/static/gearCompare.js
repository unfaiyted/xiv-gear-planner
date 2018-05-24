
module.exports = {

    settings: {
        bis: [],
        current: []
        },

    init: () => {


        // Get Gear Id's
        $('#gear-bis tr').each(function(i, row) {
            if (i>=2 && i < 15) {
                //console.log(i, $(row).data('id'));

                module.exports.settings.bis.push($(row).data('id'));

            }
        });

        // Get Current Gear Ids
        $('#gear-current tr').each(function(i, row) {
            if (i>=2 && i < 15) {
                //console.log(i, $(row).data('id'));

                module.exports.settings.current.push($(row).data('id'));
            }
        });

    module.exports.compareToBis();
    },


    compareToBis() {

        let bis =  module.exports.settings.bis;
        let current =  module.exports.settings.current;
        let count = 0;

        for(var i = 0; i < bis.length; i++) {

            if (bis[i] !== current[i]) {
                module.exports.updateTable(current[i], false);
                count++;
            } else {
                module.exports.updateTable(current[i], true);
            }


        }

        module.exports.updateProgressData(count, bis.length);

    },

    updateTable: (id, success)  => {

        let html = $(`#gear-current tr[data-id="${id}"] .gear-name`).html();
        $(`#gear-current tr[data-id="${id}"] .gear-name`).empty();


        if(success === false) {

            $(`#gear-current tr[data-id="${id}"] .gear-name`).html(
                `<i class="fas  fa-exclamation-triangle fail-check"></i>` + html);

        } else {
            $(`#gear-current tr[data-id="${id}"] .gear-name`).html(
                `<i class="fas fa-check  match-check"></i>` + html);
        }


    },

    updateProgressData: (count, total) => {

        let percent = ((1.0-(count/total)).toFixed(2))*100;

       // console.log(percent);

        $(`.progress-bar`).css('width', percent + '%');

        if(percent >= 0 && percent < 25) {
            $(`.progress-bar`).addClass("bg-danger");

        }
        if(percent >= 25 && percent < 50) {
            $(`.progress-bar`).addClass("bg-danger");

        }
        if(percent >= 50 && percent < 75) {
            $(`.progress-bar`).addClass("bg-info");

        }
        if(percent >= 75 && percent < 100) {

        }
        if(percent >= 100) {
            $(`.progress-bar`).addClass("bg-success");
        }


        let matches = total-count;


        $('#gear-matched-count').text(matches);
        $('#gear-total-count').text(total);
        $('#gear-percent').text(percent);

    }


};