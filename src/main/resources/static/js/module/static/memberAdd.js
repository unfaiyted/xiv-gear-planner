
// Module for adding members to static list
module.exports = {

    settings: {
        count: 0,
        list: [],
        addClass: 'addMember',
        deleteClass: 'deleteMember',
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
        $(addClass).off();  $(deleteClass).off();

        //add member handler
        $(addClass).click(function () {

            let id = $(this).data("id");
            let name = $(this).parent().parent().find(".char-name").text();
            let img = $(this).parent().parent().find(".char-img").attr("src");
            let server = $(this).parent().parent().find(".char-name").text();

            // add member to lis
            module.exports.addMember({id, name, img, server});

        });

        // delete member handler
        $(deleteClass).click(function () {

            // get id of member clicked
            let id = $(this).data("id");

            // trigger delete from list
            module.exports.deleteMember(id);

        });


    },


    addMember: (player) => {

        //add member to list
        module.exports.settings.list.push(player);
        // render new member list
        module.exports.renderList();

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
        module.exports.renderList();

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

            memberList += module.exports.createSingleMember(member);
            i++;
        });

        memberList += `</div>`;

        module.exports.settings.memberList.append(memberList);
        module.exports.initHandler();

    },

    createSingleMember: (member) => {
        return `<div class="col-3">
                
                <img class="img-fluid m-1 hover-members" src="${member.img}" title="Exodus" />
                    <span class="deleteMember" data-id="${member.id}">&times;</span> 
                    <span>${member.name}</span>
                  </div>`;

    }
};