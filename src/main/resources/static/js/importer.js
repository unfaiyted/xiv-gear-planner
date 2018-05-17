const validate = require("./libs/validate.js");
const alert = require("./libs/alert.js");

module.exports = {

    settings: {
        errors: []
    },

    init: () => {
        //Enabled popover support on page.
        $('[data-toggle="popover"]').popover();

        module.exports.initHandlers();
    },

    initHandlers: () => {

        $('#submit-bis').click(function (e) {
           // e.preventDefault();

            //clear last errors, if any.
            module.exports.settings.errors = [];

            if(!module.exports.formValidate()) {
                alert.displayInlineAlert($('#bis-form-error'), module.exports.settings.errors, "warning");
                return false;
            }

            return true;
           // $('#form-import-bis').submit();
        });


    },

    formValidate: () => {
        let valid = true;

        if(!validate.inputLength($('#ariyala-url').val(),5,5)) {
            module.exports.settings.errors.push("Ariyala code is an incorrect length.")
            valid = false;
        }

        if(!validate.inputLength($('#name').val(),3,15)) {
            module.exports.settings.errors.push("Name must be between 3 and 15 characters.");
            valid = false;
        }

        return valid;

    }

};








module.exports.init();