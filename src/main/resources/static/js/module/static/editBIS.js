const alerts = require('../../libs/alert.js');
const api = require('../../libs/local.js');

// Creates a form for editing the currently selected BIS for a given static member
module.exports = {

    settings: {
        openEditorClass: 'bis-edit',
        editorCreated: false,
        memberId: null,
        jobId: null,
        updateMsg: "Are you sure you want to select this Gear set?"
    },

    init: () =>  {
        module.exports.initHandlers();
    },

    initHandlers: () => {

        //reset
        $('.' + module.exports.settings.openEditorClass).off();
        $('.bis-select').off();

        //Open Editor
        $('.' + module.exports.settings.openEditorClass).click(function() {

            module.exports.settings.jobId = $(this).attr('data-job-id');
            module.exports.settings.memberId = $(this).attr('data-member-id');

            // track...current job, current member
            console.log("clicked editor");

            module.exports.displayEditor();

        });

        $(`.bis-select`).click(function () {

            let memberId = module.exports.settings.memberId;
            let bisId =   $(this).attr('data-bis-id');

            // Popup to confirm
            alerts.confirmPopUp(module.exports.settings.updateMsg).then(
                function() {
                    module.exports.assignBIS(memberId, bisId)
                        .then($('#edit-bis-modal').modal('hide')).
                    catch(function (data) {
                        console.log(data);
                        alert.displayPopUpAlert("Error changing gear set.","danger")
                    });
                }, //promise resolved
                function() { console.log('You clicked cancel'); }, //promise rejected
            );
        });


    },

    displayEditor: () => {

        module.exports.getBISList(module.exports.settings.jobId).then(function() {
                //show modal;
            $('#edit-bis-modal').modal('show');
            module.exports.initHandlers();

        });

    },

    injectEditor:(data) => {

        let output = "";
            // clear data
        $(`#bis-results`).empty();

        data.forEach(function (item) {

            output += `<tr>
            <td class="bis-name"><a href="/bis/view/${item.id}">${item.name}</a></td>
             <td class="bis-users">???</td>
            <td class="d-none d-sm-table-cell bis-ilvl">??</td>
            <td class="text-right"><button data-bis-id="${item.id}" class="btn btn-sm btn-secondary bis-select">Select</button> </td>
            </tr>`;
        });

        $(`#bis-results`).append(output);

    },

    getBISList: (jobId) => {
       return api.getData(`bis/${jobId}`).then(function (data) {
            module.exports.injectEditor(data);
       }).catch(function (data) {
           alerts.displayPopUpAlert("Error getting BIS information","danger");
       });
    },

    //Update the current Member BIS
    assignBIS: (memberId, bisId) => {

        let assign = {
            memberId: memberId,
            bisId: bisId
        };

        console.log(assign);

        return api.addData('/api/bis/assign/', assign);
    }


};