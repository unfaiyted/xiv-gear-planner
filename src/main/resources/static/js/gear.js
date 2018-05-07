(function($) {


    function eventInit() {

        $('.save-stat-type').off();
        $('.del-stat-type').off();

        $('.save-stat-type').click(function (e) {
            e.preventDefault();


            let input = $(this).parent().find(".input-value");
            let type = $(this).parent().find(".stat-type option:selected");

            let textInput = $(this).parent().find(".text-value");
            let textStatType = $(this).parent().find(".text-stat-type ");

            // Hide/Show inputs
            input.toggle();
            $(this).parent().find(".stat-type").toggle();


            if ($(this).text().includes("Save")) {

                // Show span text with input values
                textInput.text(input.val());
                textStatType.text(type.text());

            } else {

                textInput.text("");
                textStatType.text("");

            }

            $(this).text(function (i, text) {
                return text === "Save" ? "Edit" : "Save";
            });
        });


        // delete stat from list
        $('.del-stat-type').click(function(e) {
            e.preventDefault();
            let cfrm = confirm("Are you sure?");

            (cfrm) ?  $(this).parent().remove() : e.preventDefault();

        });


    }


    /* Clone STATS */
    // Add more stats to list, copy stat input
    $('.add-more-stats').click(function(e) {
        e.preventDefault();
        let clone = $('#stat-form').clone();
        let count = $('#stat-count').attr("value");

        // Update IDs job-type, value
        clone.attr("id",'stat-form'+count);
        clone.find('#job-type').attr("id",'job-type'+count);
        clone.find('#value').attr("id",'value'+count);

        clone.find(".input-value").val("").show();
        clone.find(".stat-type").show();

        clone.find('.save-stat-type').text("Save");

        clone.find(".text-value").text("");
        clone.find(".text-stat-type ").text("");

        clone.appendTo('#more-stats');

        $('#stat-count').attr("value", parseInt(count)+1);
        // Reset event handlers
        eventInit();

    });




    eventInit();

})(jQuery);
