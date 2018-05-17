const validate = require("./libs/validate.js");
const alert = require("./libs/alert.js");
const loader = require('./libs/loader.js');

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


            //full page loader
            loader.display(0);
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

// TODO: add some sort of dynamic location for image folder....
// Change the way loader works so it is created inline. less confusing
loader.settings.img ="../../img/loader.svg";
loader.injectFullPageLoader();
