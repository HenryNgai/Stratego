$(document).ready(function($) {
    refresh();
    $(window).on("resize", function () {
        refresh();
    });
    init();
        $('.draggable').draggable({
            appendTo: "body",
            revert: function() {
                if ($(this).hasClass('drag-revert')) {
                  $(this).removeClass('drag-revert');
                  return true;
                }
              },
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
            var bank = false;
            var prevX = "-1";
            var prevY = "-1";

            if ($(dropped).parent().attr('id') == "pieces"){
                bank = true;
            }
            else{
               var temp =  parseInt($(dropped).parent().attr("id").substring(3));
               prevX = Math.floor(temp/10);
               prevY = Math.floor(temp%10);
            }

            $(dropped).draggable({ containment: '.wrapper',cursor: 'move'});

            var boxNumber = parseInt(this.id.substring(3));
            var coordx = Math.floor(boxNumber/10);
            var coordy = Math.floor(boxNumber%10);
            var isAI = "False";
            var pieceName = $(dropped).attr('id')
            pieceName = pieceName.replace(/[0-9]/g, '');

            var return_value = false;
            $.ajax({
                async: false,
                type: "POST",
                url: "/validate-move",
                data: {
                    piece: pieceName,
                    previousX: prevX,
                    previousY: prevY,
                    newX: coordx,
                    newY: coordy,
                    AI: isAI
                },
                success: function(data){
                    var arrayData = data.split(" ");
                    if (arrayData[0] == "False") {
                      dropped.children().remove
                      return_value = true;
                    }
                    else if(arrayData[0] == "W"){
                        // remove
                        droppedOn.children().remove();
                        //AI MOVE
                    }
                    else if(arrayData[0] == "D"){
                        //Remove both
                        droppedOn.children().remove();
                        dropped.remove();
                        //AI Move

                    }
                    else if(arrayData[0] == "L"){
                        //Remove user piece.
                        dropped.remove();

                        //AI Move
                    }
                    else if (arrayData[0] == "GW"){
                        //Render win page

                    }
                    else if (arrayData[0] == "GL"){
                        //Render lost page
                    }
                }
            });

            console.log(return_value);
            if (return_value){
                return $(ui.draggable).addClass('drag-revert');
            }

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
    // Calls backend to get user data.
    $.get("AIsetup", function(data){
        var array = data.split(" ");
        var count = 41;
        var w=$('.droppable').width();
        var h=$('.droppable').height();
        for (var i = 0; i<array.length;i++){
            $('#Box'+i).append('<div class="draggable" id ='+array[i]+count+'></div>');
            $('#'+array[i]+count).prepend($('<img>',{class:"img-fluid",src:'../images/'+array[i]+'.png'}));
            $('#'+array[i]+count).css({"position":"absolute","width":w, "height":h});
            count++;
        }
    });

    for (var i=0; i<6; i++ ) {
        $('#pieces').append('<div class="draggable" id = Bomb'+(i+1)+'></div>');
        $('#Bomb'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Bomb.png'}));
    }

    for (var i=0; i<1; i++ ) {
        $('#pieces').append('<div class="draggable" id = Flag'+(i+1)+'></div>');
        $('#Flag'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Flag.png'}));
    }

    for (var i=0; i<1; i++ ) {
        $('#pieces').append('<div class="draggable" id = Marshall'+(i+1)+'></div>');
        $('#Marshall'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Marshall.png'}));
    }

    for (var i=0; i<1; i++ ) {
        $('#pieces').append('<div class="draggable" id = General'+(i+1)+'></div>');
        $('#General'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_General.png'}));
    }

    for (var i=0; i<2; i++ ) {
        $('#pieces').append('<div class="draggable" id = Colonel'+(i+1)+'></div>');
        $('#Colonel'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Colonel.png'}));
    }

    for (var i=0; i<3; i++ ) {
        $('#pieces').append('<div class="draggable" id = Major'+(i+1)+'></div>');
        $('#Major'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Major.png'}));
    }

    for (var i=0; i<4; i++ ) {
        $('#pieces').append('<div class="draggable" id = Captain'+(i+1)+'></div>');
        $('#Captain'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Captain.png'}));
    }
    for (var i=0; i<4; i++ ) {
        $('#pieces').append('<div class="draggable" id = Lieutenant'+(i+1)+'></div>');
        $('#Lieutenant'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Lieutenant.png'}));
    }
    for (var i=0; i<4; i++ ) {
        $('#pieces').append('<div class="draggable" id = Sergeant'+(i+1)+'></div>');
        $('#Sergeant'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Sergeant.png'}));
    }

    for (var i=0; i<5; i++ ) {
        $('#pieces').append('<div class="draggable" id = Miner'+(i+1)+'></div>');
        $('#Miner'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Miner.png'}));
    }

    for (var i=0; i<8; i++ ) {
        $('#pieces').append('<div class="draggable" id = Scout'+(i+1)+'></div>');
        $('#Scout'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Scout.png'}));
    }

    for (var i=0; i<1; i++ ) {
        $('#pieces').append('<div class="draggable" id = Spy'+(i+1)+'></div>');
        $('#Spy'+(i+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_Spy.png'}));
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
