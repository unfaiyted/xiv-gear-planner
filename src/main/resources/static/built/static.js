/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 13);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */,
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// Functions for local json file interactions
module.exports = {

    settings: { //settings
        url: "/api/",
        rateLimit: 5,
        token: $("meta[name='_csrf']").attr("content"),
        header: $("meta[name='_csrf_header']").attr("content")
    },

    //Inserts data into server
    addData: function addData(location, data) {
        location = typeof location !== 'undefined' ? location : "";
        return fetch(location, {
            method: "post",
            credentials: "same-origin",
            headers: {
                "X-CSRF-Token": module.exports.settings.token,
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }).then(function (response) {
            return response.json();
        });
    },

    deleteData: function deleteData(location, data) {
        return module.exports.addData(location, data);
    },

    // query for post data
    // parameter for url info
    // ex: players/Name+Last/?post=3 type/parameter/query
    getData: function getData(type, parameter, query) {
        parameter = typeof parameter !== 'undefined' ? parameter : "";
        query = typeof query !== 'undefined' ? query : "";

        return fetch(module.exports.settings.url + type + "/" + parameter + query).then(function (response) {
            return response.json();
        });
    }
};

/***/ }),
/* 11 */,
/* 12 */,
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var lib = __webpack_require__(10);
var member = __webpack_require__(18);
var job = __webpack_require__(20);
var deleteMember = __webpack_require__(21);

module.exports = {

    settings: {
        rateLimit: 250, // limit the rate the search will go off.
        rateMax: false,
        results: $('#search-results')
    },

    // Constructor more or less? ish
    init: function init() {
        module.exports.initEvents();
    },

    // Declares event handlers
    initEvents: function initEvents() {

        // Turn all off.
        $('#member-find').off();

        // Press Enter or Type Submit
        $('#member-find').keydown(function (e) {
            var name = $(this).val();

            if (name.length > 2 && module.exports.settings.rateMax === false) {

                module.exports.settings.rateMax = true;
                // Set backl to false, after timeout.
                setTimeout(function () {
                    module.exports.settings.rateMax = false;
                }, module.exports.settings.rateLimit);

                lib.getData("players", encodeURI(name)).then(function (data) {
                    module.exports.displaySearch(data);
                });
            }
        });
    },

    displaySearch: function displaySearch(json) {
        // Clear data
        module.exports.settings.results.empty();

        // Prints display to results location
        $.each(json, function (i, char) {

            module.exports.settings.results.append($("<tr>").append($('<th scope="row" class="d-none d-sm-table-cell">').text(i + 1), $('<td>').append($('<img src=' + char.avatar + ' class="char-img">')), $('<td class="char-name">').text(char.name), $('<td class="d-none d-sm-table-cell char-server">').text(char.server), $('<td>').append($('<button class="btn btn-secondary import addMember" data-id="' + char.id + '">').text('Add'))));
        });

        //init event handlers
        module.exports.initEvents();
        member.initModule();
    }

};

module.exports.init();

// Init the job functionality to edit job inline.
job.init();

// Initilized functionality to delete an object form data/display.
deleteMember.init({
    dataSet: "../api/static/member/delete",
    triggerClass: "delete-btn",
    displayClass: "static-member",
    deleteMsg: "Are you sure you want to delete this static member?"
});

/***/ }),
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var alert = __webpack_require__(19);

// Module for adding members to static list
module.exports = {

    settings: {
        count: 0,
        list: [],
        addClass: 'addMember',
        deleteClass: 'deleteMember',
        memberList: $('#member-list')

    },

    initModule: function initModule(addClass, deleteClass) {
        module.exports.settings.addClass = typeof addClass !== 'undefined' ? addClass : module.exports.settings.addClass;
        module.exports.settings.deleteClass = typeof deleteClass !== 'undefined' ? deleteClass : module.exports.settings.deleteClass;

        module.exports.initHandler();
    },

    initHandler: function initHandler() {
        var addClass = "." + module.exports.settings.addClass;
        var deleteClass = "." + module.exports.settings.deleteClass;

        //Resets any exisint handlers for same name.
        $(addClass).off();$(deleteClass).off();

        //add member handler
        $(addClass).click(function (e) {
            e.preventDefault();

            var id = $(this).data("id");
            var name = $(this).parent().parent().find(".char-name").text();
            var img = $(this).parent().parent().find(".char-img").attr("src");
            var server = $(this).parent().parent().find(".char-name").text();

            // add member to lis
            module.exports.addMember({ id: id, name: name, img: img, server: server });
        });

        // delete member handler
        $(deleteClass).click(function () {
            e.preventDefault();
            // get id of member clicked
            var id = $(this).data("id");

            // trigger delete from list
            module.exports.deleteMember(id);
        });
    },

    addMember: function addMember(player) {

        if (module.exports.settings.count < 8) {
            //add member to list
            module.exports.settings.list.push(player);
            // render new member list
            module.exports.renderList();
            module.exports.settings.count++;
            return true;
        }
        return alert.displayPopUpAlert("You can't add any more members.", "warning");
    },

    // delete by id
    deleteMember: function deleteMember(id) {

        // find member
        for (var i = 0; i < module.exports.settings.list.length; i++) {
            if (module.exports.settings.list[i].id === id) {
                module.exports.settings.list.splice(i, 1);
                break;
            }
        } //render new member list
        module.exports.settings.count--;
        module.exports.renderList();
    },

    renderList: function renderList() {

        var i = 0;
        //render member list
        module.exports.settings.memberList.empty();

        var memberList = '<div class="row">';

        module.exports.settings.list.forEach(function (member) {

            if (i % 4 === 0) {
                memberList += '</div> \n                                <div class="row">';
            }

            memberList += module.exports.createSingleMember(member);
            i++;
        });

        memberList += '</div>';

        module.exports.settings.memberList.append(memberList);
        module.exports.initHandler();
    },

    createSingleMember: function createSingleMember(member) {
        var deleteClass = module.exports.settings.deleteClass;
        return '<div class="col-3">\n                <img class="img-fluid m-1 hover-members" src="' + member.img + '" title="Exodus" />\n                    <span class="' + deleteClass + '" data-id="' + member.id + '">&times;</span> \n                    <span>' + member.name + '</span>\n                    <input type="hidden" name="member[]" value="' + member.id + '">\n                  </div>';
    }
};

/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// site wide alert system code
module.exports = {

    settings: {
        alertId: "alert",
        alertFiller: $('#alert'), // site wide alertID
        alertType: "popup",
        createdPopUpAlert: false
    },

    displayPopUpAlert: function displayPopUpAlert(message, type) {
        // check if alert modal exists
        if (module.exports.settings.createdPopUpAlert === false) module.exports.createPopupAlert();

        module.exports.settings.createdPopUpAlert = true;

        $('.alert').addClass("alert-" + type);
        $('#alert-message').text(message);

        $("#alertModal").modal('show');
    },

    createInlineAlert: function createInlineAlert() {

        alertFiller.append($(" <div class=\"alert alert-dismissible fade show\" role=\"alert\">").append($("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">").append($(" <span aria-hidden=\"true\">").text("&times;")), $("<span id=\"alert-message\">").text("Alert Message")));
    },

    createPopupAlert: function createPopupAlert() {

        //TODO: check if alert exists

        //create new alert if not...
        $('body').append($("<div class=\"modal fade\" id=\"alertModal\">").append($("<div class=\"modal-dialog\" role=\"document\">").append($(" <div class=\"modal-content\">").append($(" <div class=\"modal-content\">").append($(" <div class=\"alert alert-dismissible fade show\" role=\"alert\">").append($("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">").append($(" <span aria-hidden=\"true\">").html("&times;")), $("<span id=\"alert-message\">").text("Alert Message")))))));
    }

};

/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var api = __webpack_require__(10);

// Module for adding members to static list
module.exports = {

    settings: {
        jobsList: [],
        editToggle: $('.edit-toggle'),
        jobDisplayClass: "job-display"
    },

    init: function init() {
        module.exports.initHandlers();
    },

    initHandlers: function initHandlers() {

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
    getJobsList: function getJobsList() {
        if (module.exports.settings.jobsList.length === 0) {
            module.exports.settings.jobsList = api.getData('jobs');
            return api.getData('jobs');
        }
        return module.exports.settings.jobsList;
    },

    saveJob: function saveJob(parent) {
        var jobDisplay = "." + module.exports.settings.jobDisplayClass;
        //save action, return to edit

        var jobId = parent.find(".job-display select option:selected").val();
        var jobName = parent.find(".job-display select option:selected").text();
        var memberId = parent.find(".edit-toggle").data("id");

        parent.find(jobDisplay).empty();
        parent.find(jobDisplay).append(jobName);

        var arr = {
            memberId: memberId,
            jobId: jobId
        };

        api.addData("/api/static/job/update", JSON.stringify(arr)).then(function (data) {
            console.log(data.assignedJob.icon);

            console.log(parent.find('img .job-img').attr("src"));
            $('.job-img[data-id="' + memberId + '"]').attr("src", data.assignedJob.icon);
        });
    },

    editJob: function editJob(parent) {
        var jobDisplay = "." + module.exports.settings.jobDisplayClass;
        //empty data
        parent.find(jobDisplay).empty();
        //add editor

        module.exports.createJobEditor().then(function (data) {
            parent.find(jobDisplay).append(data);
        });
    },

    createJobEditor: function createJobEditor() {
        return new Promise(function (resolve, reject) {

            var JobEditorHTML = '<select class="job-list-select" name="jobSelector">';

            //Populates job list if empty.
            module.exports.getJobsList().then(function (data) {

                $.each(data, function (i, job) {
                    JobEditorHTML += '<option value="' + job.id + '">' + job.name + '</option>';
                });

                JobEditorHTML += '</select>';

                resolve(JobEditorHTML);
            });
        });
    }

};

/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var api = __webpack_require__(10);
var alert = __webpack_require__(19);

// Trigger on page to remove entries from page, settings need to be setup to delete
// both visual and database data from user.
module.exports = {

    settings: {
        triggerClass: "delete-btn",
        displayClass: "object-display",
        dataSet: null,
        deleteMsg: "Are you sure you'd like to delete this?"
    },

    init: function init(_ref) {
        var dataSet = _ref.dataSet,
            triggerClass = _ref.triggerClass,
            displayClass = _ref.displayClass,
            deleteMsg = _ref.deleteMsg;

        module.exports.settings.dataSet = typeof dataSet !== 'undefined' ? dataSet : module.exports.settings.dataSet;
        module.exports.settings.triggerClass = typeof triggerClass !== 'undefined' ? triggerClass : module.exports.settings.triggerClass;
        module.exports.settings.displayClass = typeof displayClass !== 'undefined' ? displayClass : module.exports.settings.displayClass;
        module.exports.settings.deleteMsg = typeof deleteMsg !== 'undefined' ? deleteMsg : module.exports.settings.deleteMsg;

        if (module.exports.settings.dataSet != null) {
            return module.exports.initHandlers();
        }

        console.log("Error: Init must contain a dataSet for deletion or be false");
    },

    initHandlers: function initHandlers() {

        $('.' + module.exports.settings.triggerClass).click(function () {
            var id = $(this).data("id");
            module.exports.confirmRemove(id);
        });
    },

    confirmRemove: function confirmRemove(id) {
        var cfrm = confirm(module.exports.settings.deleteMsg);
        if (cfrm) module.exports.updateServer(id).then(module.exports.removeVisual(id)).catch(function (data) {
            console.log(data);
            alert.displayPopUpAlert("Error removing item", "danger");
        });
    },

    removeVisual: function removeVisual(id) {
        console.log("remov...in theory" + id);
        $('.' + module.exports.settings.displayClass + '[data-id="' + id + '"]').remove();
    },

    updateServer: function updateServer(id) {
        var json = { memberId: id };
        return api.deleteData(module.exports.settings.dataSet, JSON.stringify(json));
    }

};

/***/ })
/******/ ]);
//# sourceMappingURL=static.js.map