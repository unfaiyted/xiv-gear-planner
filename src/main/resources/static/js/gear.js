module.exports = {

    settings:  {

    },

    // Constructor more or less? ish
    init: () => {
        module.exports.initEvents();
    },

    // Declares event handlers
    initEvents: () => {

        // Resets any exisiting.
        $('.save-stat-type').off();
        $('.del-stat-type').off();
        $('.add-more-stats').off();


        $('.save-stat-type').click(function (e) {
            e.preventDefault();

            let parent = $(this).parent();

            if ($(this).text().includes("Save")) {
                // Show span text with input values
                module.exports.statToggle(parent,"save");
            } else {
                module.exports.statToggle(parent,"edit");
            }

            $(this).text(function (i, text) {
                return text === "Save" ? "Edit" : "Save";
            });
        });


        // delete stats from list
        $('.del-stat-type').click(function(e) {
            e.preventDefault();
            let cfrm = confirm("Are you sure?");
            (cfrm) ?  $(this).parent().remove() : e.preventDefault();

        });


        /* Clone Stats handler */
        $('.add-more-stats').click(function(e) {
            e.preventDefault();
            let clone = $('#stat-form').clone();
            module.exports.duplicateStats(clone);
            module.exports.initEvents();

        });

    },

    statToggle: (parent, toggle) => {
        let input = parent.find(".input-value");
        let textInput = parent.find(".text-value");
        let textStatType = parent.find(".text-stat-type");

        // Hide/Show inputs
        input.toggle();
        parent.find(".stat-type").toggle();

        if (toggle === "save") {

            let type = parent.find(".stat-type option:selected");

            textInput.text(input.val()); textStatType.text(type.text());
        } else {
            textInput.text(""); textStatType.text("");
        }

    },

    duplicateStats: (clone) => {
        let curr = module.exports.counter();

        // Update IDs job-type, value
        clone.attr("id",'stat-form'+curr);
        clone.find('#job-type').attr("id",'job-type'+curr);
        clone.find('#value').attr("id",'value'+curr);
        clone.find(".input-value").val("").show();
        clone.find(".stat-type").show();
        clone.find('.save-stat-type').text("Save");
        clone.find(".text-value").text("");
        clone.find(".text-stat-type ").text("");
        clone.appendTo('#more-stats');

        module.exports.counter(1); // adds 1
    },

    counter: (add) => {
        add = typeof add !== 'undefined' ? add : 0;
        $('#stat-count').attr("value", parseInt($('#stat-count').val())+add);
        return $('#stat-count').val();

    }

};


module.exports.init();






