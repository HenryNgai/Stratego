$(document).ready(function($) {
    refresh();
    $(window).on("resize", function () {
        refresh();
    });
    init();

            $('.draggable').draggable({ revert: true, cursor: 'move'});
            $('.droppable').droppable({
               hoverClass: 'active',
               cursor: "default",
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
    for (var i=0; i<6; i++ ) {
        $('#pieces').append('<div class="draggable" id = Bomb'+count+'></div>');
        $('#Bomb'+count).prepend($('<img>',{class:"img-fluid",src:'../images/Bomb.png'}));
        count++;
    }

      // Create the card slots
    for ( var i=1; i<=100; i++ ) {
        $('.wrapper').append('<div class="droppable" id = Box'+i+'></div>');
     }
}


function refresh() {
        var x=$(window).width();
        var y=$(window).height();
        if(x < 1300 && x>730){
            var a=x/1.5;
            var pad = a*9.5/100;
            $(".wrapper").css('width', a);
            $(".wrapper").css('height', a);
            $(".wrapper").css('padding', pad);
            var w=$(".droppable").width();
            var h=$(".droppable").height();
            $(".droppable .draggable").css('width', w, 'height', h);
        }
}
