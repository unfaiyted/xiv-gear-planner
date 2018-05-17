
// Perform form validations
module.exports = {

    settings: {


    },

    inputLength: (string, min, max) => {

        if(typeof string !== "undefined") {
            if (string.length >= min && string.length <= max) {
                return true;
            }
        }
        return false;
    },

    inputsMatch: (inputA, inputB) => {
        if(inputA === inputB) {
            return true;
        }
        return false;
    },

    isEmail: (email) => {
        let regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (email === '' ||  !re.test(String(email).toLowerCase())) return false;
        return true;
    },


    isInteger: (input) => {
        if (typeof input !== "boolean") {
          if(isNumeric(input)) {
                return true;
          }
        }
        return false;
    },

    matchesPattern: (string, regex) => {

    }


};