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
/******/ 	return __webpack_require__(__webpack_require__.s = 15);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
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
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

// site wide alert system code
module.exports = {

    settings: {
        alertId: "alert",
        alertFiller: $('#alert'), // site wide alertID
        alertType: "popup",
        createdPopUpAlert: false,
        createdConfirmAlert: false
    },

    displayPopUpAlert: function displayPopUpAlert(message, type) {
        // check if alert modal exists
        if (module.exports.settings.createdPopUpAlert === false) module.exports.createPopupAlert();

        module.exports.settings.createdPopUpAlert = true;

        //cleans alerts coloring
        module.exports.removeAlertTypes();

        $('.alert').addClass("alert-" + type);
        $('#alert-message').text(message);

        $("#alertModal").modal('show');
    },

    displayInlineAlert: function displayInlineAlert(appendLocation, message, type) {

        //empty append location
        appendLocation.empty();

        module.exports.createInlineAlert(appendLocation);

        // Message String
        if (typeof message === "string") {
            $('#inline-alert-message').text(message);
        }

        // Message Array
        if ((typeof message === "undefined" ? "undefined" : _typeof(message)) === "object") {

            var errors = "<div class=\"row\">";

            message.forEach(function (m) {
                errors += "<div class=\"col-12\">" + m + "</div>";
            });

            errors += "</div>";

            $('#inline-alert-message').html(errors);
        }

        $('.alert').addClass("alert-" + type);
    },

    removeAlertTypes: function removeAlertTypes() {
        $('.alert').removeClass("alert-danger").removeClass("alert-warning").removeClass("alert-success").removeClass("alert-warning").removeClass("alert-primary").removeClass("alert-secondary").removeClass("alert-light").removeClass("alert-dark");
    },

    confirmPopUp: function confirmPopUp(message) {
        // check if popup modal exists, or creates it.
        if (module.exports.settings.createdConfirmAlert === false) module.exports.createPopUpConfirm();

        module.exports.settings.createdConfirmAlert = true;

        $('#confirm-message').text(message);
        $("#confirmModal").modal('show');

        var dfd = $.Deferred();
        $('#confirmModal')
        //turn off any events that were bound to the buttons last
        //time you called showprompt()
        .off('click.prompt').on('click.prompt', '#ok', function () {
            dfd.resolve();$("#confirmModal").modal('hide');
        }) //resolve the deferred
        .on('click.prompt', '#cancel', function () {
            dfd.reject();$("#confirmModal").modal('hide');
        }); //reject the deferred
        return dfd.promise();
    },

    createPopUpConfirm: function createPopUpConfirm() {

        //create new alert if not...
        $('body').append($("<div class=\"modal p-2 fade\" id=\"confirmModal\">").append($("<div class=\"modal-dialog modal-dialog-centered modal-sm\" role=\"document\">").append($(" <div class=\"modal-content p-2\">").append($(" <div class=\"fade show\" role=\"alert\">").append($("<span id=\"confirm-message\" class=\"p-1\">").text("Confirm Message"), $("<div class=\"text-right\">").append($("<button class=\"btn btn-sm btn-primary p-1 m-1\" id=\"ok\">").text("Ok"), $("<button class=\"btn btn-sm btn-secondary p-1 m-1\" id=\"cancel\">").text("Cancel")))))));
    },

    createInlineAlert: function createInlineAlert(location) {

        location.append($(" <div class=\"alert alert-dismissible fade show\" role=\"alert\">").append($("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">").append($(" <span aria-hidden=\"true\">").html("&times;")), $("<span id=\"inline-alert-message\">").text("Alert Message")));
    },

    createPopupAlert: function createPopupAlert() {

        //create new alert if not...
        $('body').append($("<div class=\"modal fade\" id=\"alertModal\">").append($("<div class=\"modal-dialog\" role=\"document\">").append($(" <div class=\"modal-content\">").append($(" <div class=\"modal-content\">").append($(" <div class=\"alert alert-dismissible fade show\" role=\"alert\">").append($("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">").append($(" <span aria-hidden=\"true\">").html("&times;")), $("<span id=\"alert-message\">").text("Alert Message")))))));
    }

};

/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// display loading module spinner thingy
module.exports = {

    settings: {
        img: "../img/loader.svg",
        // Where to inject loader within the page
        // class or ID
        injectOn: '#loader',
        pageLoader: false

    },

    create: function create(id, injectOn) {
        // checkif its overwrote and a class, not an ID
        if (injectOn != null && injectOn.contains("#") != true) {
            module.exports.settings.injectOn = injectOn + "[data-id=" + id + "]";
        }
        module.exports.inject(id);
    },

    // creates a loader that is over the whole page
    injectFullPageLoader: function injectFullPageLoader() {
        if (!module.exports.settings.pageLoader) {
            module.exports.settings.pageLoader = true;
            module.exports.settings.injectOn = "body";
            module.exports.inject(0);
        }
    },


    display: function display(id) {
        $(".loader-img[data-id=\"" + id + "\"]").show();
    },

    hide: function hide(id) {
        $(".loader-img[data-id=\"" + id + "\"]").hide();
    },

    toggle: function toggle(id) {
        $(".loader-img[data-id=\"" + id + "\"]").toggle();
    },

    inject: function inject(id) {
        // clean loader
        //$(module.exports.settings.injectOn).empty();
        // adding loader to injection site
        $(module.exports.settings.injectOn).append($("<div class=\"loader-img\" data-id=\"" + id + "\" style=\"display: none\">").append($("<img src=\"" + module.exports.settings.img + "\" >")));
    }

};

/***/ }),
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var lib = __webpack_require__(0);
var loader = __webpack_require__(2);

// Static Specific Functionality
var member = __webpack_require__(16);
var job = __webpack_require__(17);
var deleteMember = __webpack_require__(18);
var syncGear = __webpack_require__(19);
var bis = __webpack_require__(22);
var compare = __webpack_require__(24);

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

        // Prevent Enter from submitting the form and doing things
        $(window).keydown(function (event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                return false;
            }
        });

        var callback = function callback(e) {
            e.preventDefault();
            // takes search btn or input value
            var name = $(this).val() !== "" ? $(this).val() : $('#member-search').val();

            if (name.length > 2 && module.exports.settings.rateMax === false) {

                //full page loader
                loader.display(0);

                module.exports.settings.rateMax = true;
                // Set backl to false, after timeout.
                setTimeout(function () {
                    module.exports.settings.rateMax = false;
                }, module.exports.settings.rateLimit);

                lib.getData("players", encodeURI(name)).then(function (data) {
                    module.exports.displaySearch(data);
                });
            }
        };

        // Press Enter or Type Submit
        //  $('#member-find').keydown(callback);
        $('#member-find').click(callback);
    },

    displaySearch: function displaySearch(json) {
        // Clear data
        module.exports.settings.results.empty();

        // Prints display to results location
        $.each(json, function (i, char) {

            module.exports.settings.results.append($("<tr>").append($('<th scope="row" class="d-none d-sm-table-cell">').text(i + 1), $('<td>').append($('<img src=' + char.avatar + ' class="char-img">')), $('<td class="char-name">').text(char.name), $('<td class="d-none d-sm-table-cell char-server">').text(char.server), $('<td>').append($('<button class="btn btn-secondary import add-member" data-id="' + char.id + '">').text('Add'))));
        });

        //init event handlers
        module.exports.initEvents();
        member.initModule();

        setTimeout(function () {
            //full page loader hide. finished
            loader.hide(0);
        }, 1000);
    }

};

// init main function
module.exports.init();

// Generates a page loader, hidden on creation
loader.injectFullPageLoader();

// Init the job functionality to edit job inline.
job.init();

// Allowes BIS editing functionality.
bis.init();

// Initilized functionality to delete an object form data/display.
deleteMember.init({
    dataSet: "../api/static/member/delete",
    triggerClass: "delete-btn",
    displayClass: "static-member",
    deleteMsg: "Are you sure you want to delete this static member?"
});

syncGear.init();

$(document).scroll(function () {
    var y = $(this).scrollTop();
    if (y > 100) {
        $('#member-list-container').css("position", "fixed");
        $('#member-list-container').css("display", "absolute");
        $('#member-list-container').css("width", "400px");
    }

    if (y < 100) {
        $('#member-list-container').removeAttr("style");
    }
});

compare.init();

var scotchPanel =
////////// TESTING PANEL ////
$('#panel-member').scotchPanel({
    containerSelector: '.main-container', // As a jQuery Selector
    direction: 'left', // Make it toggle in from the left
    duration: 300, // Speed in ms how fast you want it to be
    transition: 'ease', // CSS3 transition type: linear, ease, ease-in, ease-out, ease-in-out, cubic-bezier(P1x,P1y,P2x,P2y)
    clickSelector: '.toggle-panel', // Enables toggling when clicking elements of this class
    distanceX: '350px', // Size fo the toggle
    enableEscapeKey: true, // Clicking Esc will close the panel
    beforePanelOpen: function beforePanelOpen() {},
    afterPanelOpen: function afterPanelOpen() {
        $('.scotch-panel-wrapper').animate({
            backgroundColor: "rgba( 0, 0, 0 , 0.75)"
        }, 400);
    }

});

$('.toggle-panel').click(function () {
    console.log("yes");
    setTimeout(function () {}, 200);
});

$('.overlay').click(function () {
    // CLOSE ONLY
    scotchPanel.close();
});

/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var alert = __webpack_require__(1);
var api = __webpack_require__(0);

// Module for adding members to static list
module.exports = {

    settings: {
        count: 0,
        list: [],
        addClass: 'add-member',
        deleteClass: 'delete-member',
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
        $(addClass).off();$(deleteClass).off();$("#save-member-add").off();

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
        $(deleteClass).click(function (e) {
            e.preventDefault();
            // get id of member clicked
            var id = $(this).data("id");

            // trigger delete from list
            module.exports.deleteMember(id);
        });

        $("#save-member-add").click(function (e) {
            e.preventDefault();
            if (module.exports.settings.list.length <= 0) {
                return alert.displayPopUpAlert("You need to add members first.", "warning");
            }
            module.exports.saveMembers();
        });
    },

    addMember: function addMember(player) {
        var curr = typeof $("#member-count").val() !== 'undefined' ? $("#member-count").val() : 0;
        var total = parseInt(curr) + module.exports.settings.count;

        if (total < 8) {
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

    saveMembers: function saveMembers() {
        return api.addData("/api/static/member/add", JSON.stringify(module.exports.settings.list)).then(function () {
            location.reload();
        }).catch(alert.displayPopUpAlert("Adding members failed", "danger"));
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

            memberList += module.exports.renderSingleMember(member);
            i++;
        });

        memberList += '</div>';

        module.exports.settings.memberList.append(memberList);
        module.exports.initHandler();
    },

    renderSingleMember: function renderSingleMember(member) {
        var deleteClass = module.exports.settings.deleteClass;
        return '<div class="col-3">\n                <img class="img-fluid hover-members" src="' + member.img + '" title="Exodus" />\n                    <span class="' + deleteClass + '" data-id="' + member.id + '">&times;</span> \n                    <span class="member-name">' + member.name + '</span>\n                    <input type="hidden" name="member[]" value="' + member.id + '">\n                  </div>';
    }
};

/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var api = __webpack_require__(0);

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


        // get selected job value and place as text
        var jobId = parent.find(".job-display select option:selected").val();
        var jobName = parent.find(".job-display select option:selected").text();
        var memberId = parent.find(".edit-toggle").data("id");

        // updates the value of data for current job
        $('.bis-edit[data-member-id="' + memberId + '"]').attr("data-job-id", jobId);

        parent.find(jobDisplay).empty();
        parent.find(jobDisplay).append('<span class="job-display-text">' + jobName + '</span>');

        var arr = {
            memberId: memberId,
            jobId: jobId
        };

        api.addData("/api/static/job/update", JSON.stringify(arr)).then(function (data) {
            $('.job-img[data-id="' + memberId + '"]').attr("src", "../img/jobs/" + data.assignedJob.abbr + ".png");
        });
    },

    editJob: function editJob(parent) {
        var jobDisplay = "." + module.exports.settings.jobDisplayClass;
        //empty data
        parent.find(jobDisplay).empty();
        //add editor

        module.exports.createJobEditor().then(function (data) {
            parent.find(jobDisplay).append(data);
            // creates special picker
            $('.job-list-select').selectpicker({
                style: 'btn-info',
                size: 5
            });

            $('.job-list-select').selectpicker('setStyle', 'btn-sm', 'add');
        });
    },

    createJobEditor: function createJobEditor() {
        return new Promise(function (resolve, reject) {

            var JobEditorHTML = '<div class="col-12"><select class="job-list-select" data-width="100%" name="jobSelector">';

            //Populates job list if empty.
            module.exports.getJobsList().then(function (data) {

                $.each(data, function (i, job) {
                    JobEditorHTML += '<option value="' + job.id + '">' + job.name + '</option>';
                });

                JobEditorHTML += '</select></div>';

                resolve(JobEditorHTML);
            });
        });
    }

};

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var api = __webpack_require__(0);
var alert = __webpack_require__(1);

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
        alert.confirmPopUp(module.exports.settings.deleteMsg).then(function () {
            module.exports.updateServer(id).then(module.exports.removeVisual(id)).catch(function (data) {
                console.log(data);
                alert.displayPopUpAlert("Error removing item", "danger");
            });
        }, //promise resolved
        function () {
            console.log('You clicked cancel');
        } //promise rejected

        );
    },

    removeVisual: function removeVisual(id) {
        $('.' + module.exports.settings.displayClass + '[data-id="' + id + '"]').remove();
    },

    updateServer: function updateServer(id) {

        console.log(module.exports.settings.dataSet);

        var json = { memberId: id };
        return api.deleteData(module.exports.settings.dataSet, JSON.stringify(json));
    }

};

/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var alert = __webpack_require__(1);
var api = __webpack_require__(0);

module.exports = {

    settings: {},

    init: function init() {
        module.exports.initHandler();
    },

    initHandler: function initHandler() {

        $('.member-gear-sync').click(function (e) {
            e.preventDefault();
            console.log($(this).data('id'));
            var memberId = $(this).data('id');

            module.exports.syncMemberToLodestone(memberId);
        });
    },

    syncMemberToLodestone: function syncMemberToLodestone(memberId) {
        alert.confirmPopUp("Are you sure you want to sync?").then(function () {
            module.exports.getLodestoneData(memberId);
        }, //promise resolved
        function () {
            console.log('You clicked cancel');
        } //promise rejected
        );
    },

    getLodestoneData: function getLodestoneData(memberId) {
        console.log(memberId);
        return api.addData('/api/lodestone/import/' + memberId, memberId).then(function (data) {
            console.log(data);
            if (data.success === false) alert.displayPopUpAlert(data.errors.display, "danger");
            if (data.success === true) alert.displayPopUpAlert("Success!", "success");
        }).catch(function (data) {
            alert.displayPopUpAlert("Error! Data not imported", "danger");
        });
    }

};

/***/ }),
/* 20 */,
/* 21 */,
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var alerts = __webpack_require__(1);
var api = __webpack_require__(0);

// Creates a form for editing the currently selected BIS for a given static member
module.exports = {

    settings: {
        openEditorClass: 'bis-edit',
        editorCreated: false,
        memberId: null,
        jobId: null,
        bisName: null,
        updateMsg: "Are you sure you want to select this Gear set?"
    },

    init: function init() {
        module.exports.initHandlers();
    },

    initHandlers: function initHandlers() {

        //reset
        $('.' + module.exports.settings.openEditorClass).off();
        $('.bis-select').off();

        //Open Editor
        $('.' + module.exports.settings.openEditorClass).click(function () {

            module.exports.settings.jobId = $(this).attr('data-job-id');
            module.exports.settings.memberId = $(this).attr('data-member-id');

            // track...current job, current member
            console.log("clicked editor");

            module.exports.displayEditor();
        });

        $('.bis-select').click(function () {

            var memberId = module.exports.settings.memberId;
            var bisId = $(this).attr('data-bis-id');
            var bisName = $(this).attr('data-bis-name');

            // Popup to confirm
            alerts.confirmPopUp(module.exports.settings.updateMsg).then(function () {
                module.exports.assignBIS(memberId, bisId).then(function () {

                    $('.bis-name[data-member-id="' + module.exports.settings.memberId + '"]').text(bisName);
                    $('#edit-bis-modal').modal('hide');
                }).catch(function (data) {
                    console.log(data);
                    alert.displayPopUpAlert("Error changing gear set.", "danger");
                });
            }, //promise resolved
            function () {
                console.log('You clicked cancel');
            } //promise rejected
            );
        });
    },

    displayEditor: function displayEditor() {

        module.exports.getBISList(module.exports.settings.jobId).then(function () {
            //show modal;
            $('#edit-bis-modal').modal('show');
            module.exports.initHandlers();
        });
    },

    injectEditor: function injectEditor(data) {

        var output = "";
        // clear data
        $('#bis-results').empty();

        data.forEach(function (item) {

            output += '<tr>\n            <td class="bis-name"><a href="/bis/view/' + item.id + '">' + item.name + '</a></td>\n             <td class="bis-users">???</td>\n            <td class="d-none d-sm-table-cell bis-ilvl">??</td>\n            <td class="text-right"><button data-bis-id="' + item.id + '" data-bis-name="' + item.name + '" class="btn btn-sm btn-secondary bis-select">Select</button> </td>\n            </tr>';
        });

        $('#bis-results').append(output);
    },

    getBISList: function getBISList(jobId) {
        return api.getData('bis/' + jobId).then(function (data) {
            module.exports.injectEditor(data);
        }).catch(function (data) {
            alerts.displayPopUpAlert("Error getting BIS information", "danger");
        });
    },

    //Update the current Member BIS
    assignBIS: function assignBIS(memberId, bisId) {

        var assign = {
            memberId: memberId,
            bisId: bisId
        };

        console.log(assign);

        return api.addData('/api/bis/assign/', assign);
    }

};

/***/ }),
/* 23 */,
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = {

    settings: {
        bis: [],
        current: []
    },

    init: function init() {

        // Get Gear Id's
        $('#gear-bis tr').each(function (i, row) {
            if (i >= 2 && i < 15) {
                console.log(i, $(row).data('id'));

                module.exports.settings.bis.push($(row).data('id'));
            }
        });

        // Get Current Gear Ids
        $('#gear-current tr').each(function (i, row) {
            if (i >= 2 && i < 15) {
                console.log(i, $(row).data('id'));

                module.exports.settings.current.push($(row).data('id'));
            }
        });

        module.exports.compareToBis();
    },

    compareToBis: function compareToBis() {

        var bis = module.exports.settings.bis;
        var current = module.exports.settings.current;
        var count = 0;

        for (var i = 0; i < bis.length; i++) {

            if (bis[i] !== current[i]) {
                module.exports.updateTable(current[i]);
                count++;
            }
        }

        module.exports.updateProgressData(count, bis.length);
    },


    updateTable: function updateTable(id) {
        $('#gear-current tr[data-id="' + id + '"]').css("background-color", "#6d121287");
    },

    updateProgressData: function updateProgressData(count, total) {

        var percent = (1.0 - count / total).toFixed(2) * 100;

        console.log(percent);

        $('.progress-bar').css('width', percent + '%');

        if (percent >= 0 && percent < 25) {
            $('.progress-bar').addClass("bg-danger");
        }
        if (percent >= 25 && percent < 50) {
            $('.progress-bar').addClass("bg-danger");
        }
        if (percent >= 50 && percent < 75) {
            $('.progress-bar').addClass("bg-info");
        }
        if (percent >= 75 && percent < 100) {}
        if (percent >= 100) {
            $('.progress-bar').addClass("bg-success");
        }

        var matches = total - count;

        $('#gear-matched-count').text(matches);
        $('#gear-total-count').text(total);
        $('#gear-percent').text(percent);
    }

};

/***/ })
/******/ ]);
//# sourceMappingURL=static.js.map