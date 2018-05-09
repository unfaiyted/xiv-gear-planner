// site wide alert system code
module.exports = {

    settings: {
          alertId: "alert",
          alertFiller: $('#alert'),    // site wide alertID
          alertType: "popup",
          createdPopUpAlert: false
    },

    displayPopUpAlert: (message, type) => {
        // check if alert modal exists
        if(module.exports.settings.createdPopUpAlert === false) module.exports.createPopupAlert();

        module.exports.settings.createdPopUpAlert = true;

        $('.alert').addClass("alert-"+type);
        $('#alert-message').text(message);

        $(`#alertModal`).modal('show');
    },

    createInlineAlert: () => {

        alertFiller.append(
            $(` <div class="alert alert-dismissible fade show" role="alert">`).append(
                $(`<button type="button" class="close" data-dismiss="modal" aria-label="Close">`).append(
                    $(` <span aria-hidden="true">`).text("&times;")
                ),
                $(`<span id="alert-message">`).text("Alert Message")
        ));

    },

    createPopupAlert: () => {

        //TODO: check if alert exists

        //create new alert if not...
        $('body').append(
            $(`<div class="modal fade" id="alertModal">`).append(
                $(`<div class="modal-dialog" role="document">`).append(
                     $(` <div class="modal-content">`).append(
                        $(` <div class="modal-content">`).append(
                            $(` <div class="alert alert-dismissible fade show" role="alert">`).append(
                            $(`<button type="button" class="close" data-dismiss="modal" aria-label="Close">`).append(
                                $(` <span aria-hidden="true">`).html("&times;")
                            ),
                            $(`<span id="alert-message">`).text("Alert Message")
        ))))));
    },


};