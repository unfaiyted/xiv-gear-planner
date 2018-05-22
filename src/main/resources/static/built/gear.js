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
/******/ 	return __webpack_require__(__webpack_require__.s = 14);
/******/ })
/************************************************************************/
/******/ ({

/***/ 14:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = {

    settings: {},

    // Constructor more or less? ish
    init: function init() {
        module.exports.initEvents();
    },

    // Declares event handlers
    initEvents: function initEvents() {

        // Resets any exisiting.
        $('.save-stat-type').off();
        $('.del-stat-type').off();
        $('.add-more-stats').off();

        $('.save-stat-type').click(function (e) {
            e.preventDefault();

            var parent = $(this).parent();

            if ($(this).text().includes("Save")) {
                // Show span text with input values
                module.exports.statToggle(parent, "save");
            } else {
                module.exports.statToggle(parent, "edit");
            }

            $(this).text(function (i, text) {
                return text === "Save" ? "Edit" : "Save";
            });
        });

        // delete stats from list
        $('.del-stat-type').click(function (e) {
            e.preventDefault();
            var cfrm = confirm("Are you sure?");
            cfrm ? $(this).parent().remove() : e.preventDefault();
        });

        /* Clone Stats handler */
        $('.add-more-stats').click(function (e) {
            e.preventDefault();
            var clone = $('#stat-form').clone();
            module.exports.duplicateStats(clone);
            module.exports.initEvents();
        });
    },

    statToggle: function statToggle(parent, toggle) {
        var input = parent.find(".input-value");
        var textInput = parent.find(".text-value");
        var textStatType = parent.find(".text-stat-type");

        // Hide/Show inputs
        input.toggle();
        parent.find(".stat-type").toggle();

        if (toggle === "save") {

            var type = parent.find(".stat-type option:selected");

            textInput.text(input.val());textStatType.text(type.text());
        } else {
            textInput.text("");textStatType.text("");
        }
    },

    duplicateStats: function duplicateStats(clone) {
        var curr = module.exports.counter();

        // Update IDs job-type, value
        clone.attr("id", 'stat-form' + curr);
        clone.find('#job-type').attr("id", 'job-type' + curr);
        clone.find('#value').attr("id", 'value' + curr);
        clone.find(".input-value").val("").show();
        clone.find(".stat-type").show();
        clone.find('.save-stat-type').text("Save");
        clone.find(".text-value").text("");
        clone.find(".text-stat-type ").text("");
        clone.appendTo('#more-stats');

        module.exports.counter(1); // adds 1
    },

    counter: function counter(add) {
        add = typeof add !== 'undefined' ? add : 0;
        $('#stat-count').attr("value", parseInt($('#stat-count').val()) + add);
        return $('#stat-count').val();
    }

};

module.exports.init();

/***/ })

/******/ });
//# sourceMappingURL=gear.js.map