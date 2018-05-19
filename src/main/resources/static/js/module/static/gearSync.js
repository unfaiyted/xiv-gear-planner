const alert = require('../../libs/alert.js');
const api = require('../../libs/local.js')


module.exports = {

    settings: {
    },


    init: () => {
        module.exports.initHandler();
    },

    initHandler:() => {

        $('.member-gear-sync').click(function (e) {
            e.preventDefault();
            console.log($(this).data('id'));
            let memberId = $(this).data('id');

            module.exports.syncMemberToLodestone(memberId);

        });
    },

    syncMemberToLodestone: (memberId) => {
        alert.confirmPopUp("Are you sure you want to sync?").then(
                function() {
                    module.exports.getLodestoneData(memberId);
                    }, //promise resolved
                function() { console.log('You clicked cancel'); }, //promise rejected
            );
    },

    getLodestoneData: (memberId) => {
        console.log(memberId);
        return api.addData(`/api/lodestone/import/${memberId}`,memberId).then(function (data) {
               console.log(data);
               if(data.success === false) alert.displayPopUpAlert(data.errors.display, "danger");
               if(data.success === true) alert.displayPopUpAlert("Success!", "success");

           }).catch(function(data) {
               alert.displayPopUpAlert("Error! Data not imported", "danger");
           });
    }



};