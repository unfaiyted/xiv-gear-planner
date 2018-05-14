const alert = require('./../../libs/alert.js');
const api = require('../../libs/local.js');

// Module for adding members to static list
module.exports = {

    settings: {
        count: 0,
        list: [],
        addClass: 'add-member',
        deleteClass: 'delete-member',
        memberList: $('#member-list')
    },

    initModule: (addClass, deleteClass) => {
        module.exports.settings.addClass = typeof addClass !== 'undefined' ? addClass : module.exports.settings.addClass;
        module.exports.settings.deleteClass = typeof deleteClass !== 'undefined' ? deleteClass : module.exports.settings.deleteClass;

        module.exports.initHandler();
    },

    initHandler: () => {
        let addClass =    "." + module.exports.settings.addClass;
        let deleteClass = "." + module.exports.settings.deleteClass;

        //Resets any exisint handlers for same name.
        $(addClass).off();  $(deleteClass).off();  $("#save-member-add").off();

        //add member handler
        $(addClass).click(function (e) {
            e.preventDefault();

            let id = $(this).data("id");
            let name = $(this).parent().parent().find(".char-name").text();
            let img = $(this).parent().parent().find(".char-img").attr("src");
            let server = $(this).parent().parent().find(".char-name").text();

            // add member to lis
            module.exports.addMember({id, name, img, server});

        });

        // delete member handler
        $(deleteClass).click(function (e) {
            e.preventDefault();
            // get id of member clicked
            let id = $(this).data("id");

            // trigger delete from list
            module.exports.deleteMember(id);

        });

        $("#save-member-add").click(function (e) {
            e.preventDefault();
            if(module.exports.settings.list.length <= 0) {
                return alert.displayPopUpAlert("You need to add members first.","warning");
            }
            module.exports.saveMembers();

        });

    },

    addMember: (player) => {
       let curr = typeof $("#member-count").val() !== 'undefined' ? $("#member-count").val() : 0;
       let total = parseInt(curr) + module.exports.settings.count;

        if(total < 8) {
            //add member to list
            module.exports.settings.list.push(player);
            // render new member list
            module.exports.renderList();
            module.exports.settings.count++;
            return true;
        }
        return alert.displayPopUpAlert("You can't add any more members.","warning");

    },

    // delete by id
    deleteMember: (id) => {

        // find member
        for (let i = 0; i < module.exports.settings.list.length; i++)
            if (module.exports.settings.list[i].id === id) {
                module.exports.settings.list.splice(i, 1);
                break;
            }
        //render new member list
        module.exports.settings.count--;
        module.exports.renderList();

    },

    saveMembers: () => {
        return api.addData("/api/static/member/add", JSON.stringify(module.exports.settings.list))
            .then(function () {
                location.reload();
            }).catch(alert.displayPopUpAlert("Adding members failed","danger"));

    },

    renderList: () => {

        let i = 0;
        //render member list
        module.exports.settings.memberList.empty();

        let memberList = `<div class="row">`;

        module.exports.settings.list.forEach((member) => {

            if(i % 4 === 0) {
                memberList += `</div> 
                                <div class="row">`
            }

            memberList += module.exports.renderSingleMember(member);
            i++;
        });

        memberList += `</div>`;

        module.exports.settings.memberList.append(memberList);
        module.exports.initHandler();

    },

    renderSingleMember: (member) => {
        let deleteClass =   module.exports.settings.deleteClass;
        return `<div class="col-3">
                <img class="img-fluid hover-members" src="${member.img}" title="Exodus" />
                    <span class="${deleteClass}" data-id="${member.id}">&times;</span> 
                    <span class="member-name">${member.name}</span>
                    <input type="hidden" name="member[]" value="${member.id}">
                  </div>`;

    }
};