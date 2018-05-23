
// Creates a member PANEL



module.exports = {

    settings: {
        lastPos:0,
        updatePos: true,
        stickyScroll: {}

    },

    init: () => {
      module.exports.initHandler();
    },


    initHandler: () => {

        // $('.toggle-panel').off();  $('.overlay').off();
      //  $( window ).off();
        let panelMember = $('#panel-member').scotchPanel({
            containerSelector: '.main-container', // As a jQuery Selector
            direction: 'left', // Make it toggle in from the left
            duration: 300, // Speed in ms how fast you want it to be
            transition: 'ease', // CSS3 transition type: linear, ease, ease-in, ease-out, ease-in-out, cubic-bezier(P1x,P1y,P2x,P2y)
            clickSelector: '.toggle-panel', // Enables toggling when clicking elements of this class
            distanceX: '350px', // Size fo the toggle
            enableEscapeKey: true // Clicking Esc will close the panel
        });


        $('.toggle-panel').click(function () {
            //disable sticky handler default
            $(window).unbind('scroll');
            // move to top
            $('html,body').animate({scrollTop:0},600);

            let top = $( ".sticky-parent" ).css("top");

            $( ".sticky-parent" ).animate({
                top: "-=" + top ,
            }, 150, function() {

                setTimeout(function () {
                    module.exports.settings.lastPos =0;
                   module.exports.stickyHandler();
               },1000);

            });
        });


        $('.overlay').click(function() {
            // CLOSE ONLY
            panelMember.close();
        });

        module.exports.stickyHandler()

    },


    stickyHandler: () => {

        let stickyScroll = $( window ).scroll(function() {
            let lastPos = module.exports.settings.lastPos;

            if (lastPos > $(document).scrollTop() ) {
                let diff = Math.abs($(document).scrollTop() - lastPos);
                module.exports.animateMoveSticky("up",diff)

            } else {
                let diff = Math.abs(lastPos - $(document).scrollTop());
                module.exports.animateMoveSticky("down",diff)
            }
            module.exports.settings.lastPos = $(document).scrollTop();
        });

    },


    animateMoveSticky: (direction, value) => {

        let  action = "-=" + value;
        if(direction === "down") {
            action = "+=" + value;
        }


        if(module.exports.settings.updatePos = true) {

            $(".sticky-parent").animate({
                top: action,
            }, 150, function () {
                module.exports.settings.updatePos = false;
            });

        }

        setTimeout(function() {
            module.exports.settings.updatePos = true
        }, 400)

    }


};













