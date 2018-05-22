const alerts = require('../../libs/alert.js');
const api = require('../../libs/local.js');

// Creates a form for editing the currently selected BIS for a given static member
module.exports = {

    settings: {
        openEditorClass: 'bis-edit',
        editorCreated: false,
        memberId: null,
        jobId: null
    },

    init: () =>  {
        module.exports.initHandlers();
    },

    initHandlers: () => {

        //reset
        $('.' + module.exports.settings.openEditorClass).off();

        //Open Editor
        $('.' + module.exports.settings.openEditorClass).click(function() {

            module.exports.settings.jobId = $(this).attr('data-job-id');
            module.exports.settings.memberId = $(this).attr('data-member-id');

            // track...current job, current member
            console.log("clicked editor");

            module.exports.displayEditor();

        });
    },

    displayEditor: () => {

        module.exports.getBISList(module.exports.settings.jobId).then(function() {
                //show modal;
            $('#edit-bis-modal').modal('show');

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
            <td class="text-right"><button  class="btn btn-sm btn-secondary bis-select">Select</button> </td>
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

        return api.updateData('/api/bis/assign/', assign);
    }


};