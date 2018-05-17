// site wide alert system code
module.exports = {

    settings: {
          alertId: "alert",
          alertFiller: $('#alert'),    // site wide alertID
          alertType: "popup",
          createdPopUpAlert: false,
          createdConfirmAlert: false
    },

    displayPopUpAlert: (message, type) => {
        // check if alert modal exists
        if(module.exports.settings.createdPopUpAlert === false) module.exports.createPopupAlert();

        module.exports.settings.createdPopUpAlert = true;

        //cleans alerts coloring
        module.exports.removeAlertTypes();

        $('.alert').addClass("alert-"+type);
        $('#alert-message').text(message);

        $(`#alertModal`).modal('show');
    },

    displayInlineAlert: (appendLocation, message, type) => {

        //empty append location
        appendLocation.empty();

        module.exports.createInlineAlert(appendLocation);

        // Message String
        if (typeof message === "string") {
            $('#inline-alert-message').text(message);
        }


        // Message Array
        if (typeof message === "object") {

            let errors = `<div class="row">`;

            message.forEach(function (m) {
                errors += `<div class="col-12">${m}</div>`;
            });

            errors += `</div>`;

            $('#inline-alert-message').html(errors);

        }

        $('.alert').addClass("alert-"+type);


    },


    removeAlertTypes: () => {
        $('.alert').removeClass("alert-danger")
            .removeClass("alert-warning")
            .removeClass("alert-success")
            .removeClass("alert-warning")
            .removeClass("alert-primary")
            .removeClass("alert-secondary")
            .removeClass("alert-light")
            .removeClass("alert-dark")

        ;

    },


    confirmPopUp: (message) => {
        // check if popup modal exists, or creates it.
        if(module.exports.settings.createdConfirmAlert === false) module.exports.createPopUpConfirm();

        module.exports.settings.createdConfirmAlert = true;

        $('#confirm-message').text(message);
        $(`#confirmModal`).modal('show');

        let dfd = $.Deferred();
        $('#confirmModal')
        //turn off any events that were bound to the buttons last
        //time you called showprompt()
        .off('click.prompt')
        .on('click.prompt','#ok',function() { dfd.resolve();   $(`#confirmModal`).modal('hide'); }) //resolve the deferred
        .on('click.prompt','#cancel',function() { dfd.reject();  $(`#confirmModal`).modal('hide'); });   //reject the deferred
        return dfd.promise();

    },

    createPopUpConfirm:() => {

        //create new alert if not...
        $('body').append(
            $(`<div class="modal p-2 fade" id="confirmModal">`).append(
                $(`<div class="modal-dialog modal-dialog-centered modal-sm" role="document">`).append(
                    $(` <div class="modal-content p-2">`).append(
                            $(` <div class="fade show" role="alert">`).append(
                                $(`<span id="confirm-message" class="p-1">`).text("Confirm Message"),
                                $(`<div class="text-right">`).append(
                                    $(`<button class="btn btn-sm btn-primary p-1 m-1" id="ok">`).text("Ok"),
                                    $(`<button class="btn btn-sm btn-secondary p-1 m-1" id="cancel">`).text("Cancel"),
                            ))))));

    },

    createInlineAlert: (location) => {

        location.append(
            $(` <div class="alert alert-dismissible fade show" role="alert">`).append(
                $(`<button type="button" class="close" data-dismiss="alert" aria-label="Close">`).append(
                    $(` <span aria-hidden="true">`).html("&times;")
                ),
                $(`<span id="inline-alert-message">`).text("Alert Message")
        ));

    },

    createPopupAlert: () => {

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