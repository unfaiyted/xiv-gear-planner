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
/******/ ({

/***/ 10:
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

/***/ 13:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var lib = __webpack_require__(10);
var member = __webpack_require__(18);

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
        $('#member-find').keydown(function () {
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

/***/ }),

/***/ 18:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


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
        $(addClass).click(function () {

            var id = $(this).data("id");
            var name = $(this).parent().parent().find(".char-name").text();
            var img = $(this).parent().parent().find(".char-img").attr("src");
            var server = $(this).parent().parent().find(".char-name").text();

            // add member to lis
            module.exports.addMember({ id: id, name: name, img: img, server: server });
        });

        // delete member handler
        $(deleteClass).click(function () {

            // get id of member clicked
            var id = $(this).data("id");

            // trigger delete from list
            module.exports.deleteMember(id);
        });
    },

    addMember: function addMember(player) {

        //add member to list
        module.exports.settings.list.push(player);
        // render new member list
        module.exports.renderList();
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
        return '<div class="col-3">\n                \n                <img class="img-fluid m-1 hover-members" src="' + member.img + '" title="Exodus" />\n                    <span class="deleteMember" data-id="' + member.id + '">&times;</span> \n                    <span>' + member.name + '</span>\n                  </div>';
    }
};

/***/ })

/******/ });
//# sourceMappingURL=static.js.map