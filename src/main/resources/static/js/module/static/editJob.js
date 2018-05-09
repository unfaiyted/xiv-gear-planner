const api = require('../../libs/local.js');

// Module for adding members to static list
module.exports = {

    settings: {
        jobsList: [],
        editToggle:  $('.edit-toggle'),
        jobDisplayClass: "job-display"
    },

    init: () => {

        module.exports.initHandlers();
    },

    initHandlers: () => {

        //toggle edit and save
        $('.edit-toggle').click(function (e) {
            e.preventDefault();

           if ($(this).text().includes("edit")) {
               module.exports.editJob($(this).parent());
               $(this).text('save');
           } else {
               module.exports.saveJob($(this).parent());
               $(this).text('edit');
           }
        });

    },

    //Populates JobsList if empty.
    getJobsList: () => {
        if(module.exports.settings.jobsList.length === 0) {
            module.exports.settings.jobsList =  api.getData('jobs');
            return api.getData('jobs');
        }
        return module.exports.settings.jobsList;
    },

    saveJob: (parent) => {
        let jobDisplay = "." + module.exports.settings.jobDisplayClass;
        //save action, return to edit



        let jobId = parent.find(".job-display select option:selected").val();
        let jobName = parent.find(".job-display select option:selected").text();
        let memberId = parent.find(".edit-toggle").data("id");


        parent.find(jobDisplay).empty();
        parent.find(jobDisplay).append(jobName);

        let arr = {
            memberId: memberId,
            jobId: jobId
            };

        api.addData("/api/static/job/update", JSON.stringify(arr));
    },

    editJob: (parent) => {
        let jobDisplay = "." + module.exports.settings.jobDisplayClass;
       //empty data
        parent.find(jobDisplay).empty();
        //add editor

        module.exports.createJobEditor().then((data) => {
            parent.find(jobDisplay).append(data);
        });
    },

    createJobEditor: () => {
        return new Promise((resolve, reject) => {

            let JobEditorHTML =
                `<select class="job-list-select" name="jobSelector">`;

            //Populates job list if empty.
            module.exports.getJobsList().then(function(data) {

                $.each(data, function (i,job) {
                    JobEditorHTML += `<option value="${job.id}">${job.name}</option>`
                });

                JobEditorHTML += `</select>`;

                resolve(JobEditorHTML);
            });

        });
    },

    refreshJobInfo: () => {

    }
};