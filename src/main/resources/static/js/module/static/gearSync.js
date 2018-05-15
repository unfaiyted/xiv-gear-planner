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
                    console.log('You clicked ok' + memberId);
                    }, //promise resolved
                function() { console.log('You clicked cancel'); }, //promise rejected
            );
    },

    getLodestoneData: () => {
           return api.addData('/lodestone/import/',memberId).then(function () {
               
           }).catch(alert.displayPopUpAlert("Error importing gear","danger"));
    }



};