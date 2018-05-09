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
/******/ 	return __webpack_require__(__webpack_require__.s = 8);
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
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var xiv = __webpack_require__(9);

var _require = __webpack_require__(10),
    addData = _require.addData;

var _require2 = __webpack_require__(11),
    toPlayerObj = _require2.toPlayerObj;

module.exports = {

    settings: { //settings
        minLength: 3,
        userInput: $('#user-input'),
        results: $('#search-results')
    },

    init: function init() {
        $('#alert').hide();

        module.exports.initEvents();
    },

    initEvents: function initEvents() {

        //turns off events
        $('.import').off();
        $('#search').off();

        // Search click submit
        $('#search').click(function (e) {
            e.preventDefault();
            var query = module.exports.settings.userInput.val();

            if (query.length > 3) {
                module.exports.playerSearch(query).then(function (data) {
                    module.exports.displaySearchData(data);
                }).catch(function () {
                    module.exports.displayAlert("Error pulling search data.", "danger");
                });
            }
        });

        // Import data button
        $('.import').click(function (e) {
            e.preventDefault();
            xiv.getPlayerdata($(this).data('id')).then(function (data) {
                var Player = toPlayerObj(data);
                module.exports.submitPlayerData(Player);
            }).catch(function () {
                module.exports.displayAlert("Error with player data.", "danger");
            });
        });
    },

    // Returns search results with player data in JSON
    playerSearch: function playerSearch(query) {
        module.exports.settings.results.empty();
        return xiv.searchFor("characters", query);
    },

    // Prints display to results location
    displaySearchData: function displaySearchData(json) {

        $.each(json.characters.results, function (i, char) {

            module.exports.settings.results.append($("<tr>").append($('<th scope="row" class="d-none d-sm-table-cell">').text(i + 1), $('<td>').append($('<img src=' + char.icon + '>')), $('<td>').text(char.name), $('<td class="d-none d-sm-table-cell">').text(char.server), $('<td>').append($('<button class="btn btn-secondary import" data-id="' + char.id + '">').text('Import'))));
        });

        //init event handlers
        module.exports.initEvents();
    },

    submitPlayerData: function submitPlayerData(playerObj) {
        return addData("/api/player/add", playerObj).then(function () {
            module.exports.displayAlert("Player added.", "success");
        }).catch(function () {
            module.exports.displayAlert("Error saving player data.", "danger");
        });
    },

    displayAlert: function displayAlert(msg, type) {
        $('#alert-content').text(msg);
        $('#alert').addClass("alert-" + type).show();
    }

};

module.exports.init();

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = {

    settings: { //settings
        url: "http://api.xivdb.com/",
        rateLimit: 5
    },

    searchFor: function searchFor(type, search) {
        type = typeof type !== 'undefined' ? type : '';
        search = typeof search !== 'undefined' ? search : '';
        search = encodeURI(search); // ensure proper encoding for url

        return fetch(module.exports.settings.url + 'search?string=' + search + '&one=' + type).then(function (response) {
            return response.json();
        });
    },

    getItemData: function getItemData(id, param) {
        return fetch(module.exports.settings.url + 'item/' + id).then(function (response) {
            return response.json();
        });
    },

    getPlayerdata: function getPlayerdata(id, param) {
        // https://github.com/xivdb/api/blob/master/Character.md
        param = typeof param !== 'undefined' ? '?data=' + param : '';
        return fetch(module.exports.settings.url + 'character/' + id).then(function (response) {
            return response.json();
        });
    }

};

/***/ }),
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

        return fetch(module.exports.settings.url + type + "" + parameter + query).then(function (response) {
            return response.json();
        });
    }
};

/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// Take JSON data and represent as JSON object for input
module.exports = {

    settings: {},

    toPlayerObj: function toPlayerObj(json) {
        return {
            name: json.data.name,
            gender: json.data.gender === "male" ? 0 : 1,
            avatar: json.data.avatar,
            server: json.data.server,
            portrait: json.data.portrait,
            loadstone_id: json.lodestone_id

        };
    }

};

/***/ })
/******/ ]);
//# sourceMappingURL=player.js.map