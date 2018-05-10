// display loading module spinner thingy
module.exports = {

    settings: {
        img: "../img/loader.svg",
        // Where to inject loader within the page
        // class or ID
        injectOn: '#loader',
        pageLoader: false

    },

    create: (id, injectOn) => {
        // checkif its overwrote and a class, not an ID
        if(injectOn != null && injectOn.contains("#") != true) {
            module.exports.settings.injectOn = `${injectOn}[data-id=${id}]`;
        }
        module.exports.inject(id);
    },


    // creates a loader that is over the whole page
    injectFullPageLoader() {
        if (!module.exports.settings.pageLoader) {
            module.exports.settings.pageLoader = true;
            module.exports.settings.injectOn = "body";
            module.exports.inject(0);
        }
    },

    display: (id) => {
        $(`.loader-img[data-id="${id}"]`).show();
    },

    hide: (id) => {
        $(`.loader-img[data-id="${id}"]`).hide();
    },

    toggle: (id) => {
        $(`.loader-img[data-id="${id}"]`).toggle();
    },

    inject: (id) => {
        // clean loader
        //$(module.exports.settings.injectOn).empty();
        // adding loader to injection site
        $(module.exports.settings.injectOn).append(
            $(`<div class="loader-img" data-id="${id}" style="display: none">`).append(
                $(`<img src="${module.exports.settings.img}" >`)
            )
        )

    },


};