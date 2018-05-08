// site wide alert system code
module.exports = {

    settings: {
          alertId: "alert",
          alertFiller: $('#alert'),    // site wide alertID
          alertType: "popup"
    },

    displayAlert: () => {
        $(`#alertModal`).modal('show')
    },

    hideAlert: () => {
        $(`#alertModal`).modal('hide')
    },

    populateAlert: (message, type) => {
        $('.alert').addClass(type);
        $('#alert-message').text(message);
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
                                $(` <span aria-hidden="true">`).text("&times;")
                            ),
                            $(`<span id="alert-message">`).text("Alert Message")
        ))))));
    },


};