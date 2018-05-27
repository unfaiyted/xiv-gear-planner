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
            module.exports.settings.jobsList =  api.getData('job');
            return api.getData('job');
        }
        return module.exports.settings.jobsList;
    },

    saveJob: (parent) => {
        let jobDisplay = "." + module.exports.settings.jobDisplayClass;
        //save action, return to edit


        // get selected job value and place as text
        let jobId = parent.find(".job-display select option:selected").val();
        let jobName = parent.find(".job-display select option:selected").text();
        let memberId = parent.find(".edit-toggle").data("id");

        // updates the value of data for current job
        $(`.bis-edit[data-member-id="${memberId}"]`).attr("data-job-id", jobId);


        parent.find(jobDisplay).empty();
        parent.find(jobDisplay).append(`<span class="job-display-text">${jobName}</span>`);



        let arr = {
            memberId: memberId,
            jobId: jobId
            };

        api.addData("/api/static/job/update", JSON.stringify(arr)).then((data) => {
            $(`.job-img[data-id="${memberId}"]`).attr("src", "../img/jobs/" + data.assignedJob.abbr + ".png");
            }
        );
    },

    editJob: (parent) => {
        let jobDisplay = "." + module.exports.settings.jobDisplayClass;
       //empty data
        parent.find(jobDisplay).empty();
        //add editor

        module.exports.createJobEditor().then((data) => {
            parent.find(jobDisplay).append(data);
            // creates special picker
            $('.job-list-select').selectpicker({
                style: 'btn-info',
                size: 5
            });

           $('.job-list-select').selectpicker('setStyle', 'btn-sm', 'add');

        });
    },

    createJobEditor: () => {
        return new Promise((resolve, reject) => {

            let JobEditorHTML =
                `<div class="col-12"><select class="job-list-select" data-width="100%" name="jobSelector">`;

            //Populates job list if empty.
            module.exports.getJobsList().then(function(data) {

                $.each(data, function (i,job) {
                    JobEditorHTML += `<option value="${job.id}">${job.name}</option>`
                });

                JobEditorHTML += `</select></div>`;

                resolve(JobEditorHTML);
            });

        });
    },

};