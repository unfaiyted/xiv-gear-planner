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
/******/ 	return __webpack_require__(__webpack_require__.s = 20);
/******/ })
/************************************************************************/
/******/ ({

/***/ 1:
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

/***/ 20:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var validate = __webpack_require__(22);
var alert = __webpack_require__(1);

module.exports = {

    settings: {
        errors: []
    },

    init: function init() {
        //Enabled popover support on page.
        $('[data-toggle="popover"]').popover();

        module.exports.initHandlers();
    },

    initHandlers: function initHandlers() {

        $('#submit-bis').click(function (e) {
            // e.preventDefault();

            //clear last errors, if any.
            module.exports.settings.errors = [];

            if (!module.exports.formValidate()) {
                alert.displayInlineAlert($('#bis-form-error'), module.exports.settings.errors, "warning");
                return false;
            }

            return true;
            // $('#form-import-bis').submit();
        });
    },

    formValidate: function formValidate() {
        var valid = true;

        if (!validate.inputLength($('#ariyala-url').val(), 5, 5)) {
            module.exports.settings.errors.push("Ariyala code is an incorrect length.");
            valid = false;
        }

        if (!validate.inputLength($('#name').val(), 3, 15)) {
            module.exports.settings.errors.push("Name must be between 3 and 15 characters.");
            valid = false;
        }

        return valid;
    }

};

module.exports.init();

/***/ }),

/***/ 22:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// Perform form validations
module.exports = {

    settings: {},

    inputLength: function inputLength(string, min, max) {

        if (typeof string !== "undefined") {
            if (string.length >= min && string.length <= max) {
                return true;
            }
        }
        return false;
    },

    inputsMatch: function inputsMatch(inputA, inputB) {
        if (inputA === inputB) {
            return true;
        }
        return false;
    },

    isEmail: function isEmail(email) {
        var regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (email === '' || !re.test(String(email).toLowerCase())) return false;
        return true;
    },

    isInteger: function isInteger(input) {
        if (typeof input !== "boolean") {
            if (isNumeric(input)) {
                return true;
            }
        }
        return false;
    },

    matchesPattern: function matchesPattern(string, regex) {}

};

/***/ })

/******/ });
//# sourceMappingURL=importer.js.map