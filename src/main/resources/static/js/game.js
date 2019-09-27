$(document).ready(function($) {
    refresh();
    $(window).on("resize", function () {
        refresh();
    });
    init();
    var dropt = false;
            $('.draggable').draggable({
                 appendTo: "body",
                 revert: "invalid" ,
                 helper: function () {
                     $copy = $(this).clone();
                     $copy.css({"list-style":"none","width":$(this).outerWidth()});
                     return $copy;
                 },
                 scroll: false
            });
            $('.droppable').droppable({
               hoverClass: 'active',
               cursor: "default",
               tolerance: "pointer",
               drop: function(ev, ui) {
                var dropped = ui.draggable;
                var droppedOn = $(this);
                var w=$(droppedOn).width();
                var h=$(droppedOn).height();

                $(dropped).draggable({ containment: '.wrapper',cursor: 'move'});
                $(dropped).detach().css({position:"absolute",width:w, height:h}).appendTo(droppedOn);
                ui.draggable.position({
                    of: $(this),
                    my: 'left top',
                    at: 'left top',
                    using: function (css, calc) {
                        $(this).animate(css, 0, 'linear');
                    }

                });









            }});

});


function init(){
    $('.wrapper').html( '' );
    $('#pieces').html( '' );

    var count = 1;
    for (var i=0; i<40; i++ ) {
        $('#pieces').append('<div class="draggable" id = Bomb'+count+'></div>');
        $('#Bomb'+count).prepend($('<img>',{class:"img-fluid",src:'../images/B_Bomb.png'}));
        count++;
    }

      // Create the card slots
    for ( var i=0; i<=99; i++ ) {
        if ( i == 42 || i == 43 || i == 46 || i == 47 || i == 52 || i == 53 || i == 56 || i == 57){
            $('.wrapper').append('<div id = Box'+i+'></div>');
        }
        else{
            $('.wrapper').append('<div class="droppable" id = Box'+i+'></div>');
        }
    }
}


function refresh() {
    var height = $(window).height() - ($(".logo").outerHeight() + $(".Lost").outerHeight());
    $("#pieces").height(height);
        var x=$(window).width();
        var y=$(window).height();
        if(x>400){
            var a=x/1.5;
            if(a<=950){
                var pad = a*9.5/100;
                $(".wrapper").css('width', a);
                $(".wrapper").css('height', a);
                $(".wrapper").css('padding', pad);
                var w=$(".droppable").width();
                var h=$(".droppable").height();
                $(".droppable .draggable").css('width', w, 'height', h);
            }
            else{
                var pad = 950*9.5/100;
                $(".wrapper").css('width', 950);
                $(".wrapper").css('height', 950);
                $(".wrapper").css('padding', pad);
                var w=$(".droppable").width();
                var h=$(".droppable").height();
                $(".droppable .draggable").css('width', w, 'height', h);
            }
        }
}
