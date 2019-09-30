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
                success: function(data, textStatus, request){
                    if (request.getResponseHeader("endgame") == "lost") {
                        window.location = "/lost";
                    }
                    if (request.getResponseHeader("endgame") == "won") {
                         window.location = "/won";
                    }
                    var arrayData = data.split(" ");
                    console.log(arrayData);
                    if (arrayData[0] == "False") {
                      return_value = true;
                    }
                    else if(arrayData[0] == "W"){
                        // remove
                        var pName = $(droppedOn.children()).attr('id').replace(/[0-9]/g, '');
                        $($(droppedOn.children())).children('img').attr("src", "../images/"+pName+".png")
                        $(droppedOn.children()).detach().appendTo('#pieces');
                        $(dropped).detach().appendTo(droppedOn);
                        //AI MOVE
                    }
                    else if(arrayData[0] == "D"){
                        //Remove both
                        var pName = $(droppedOn.children()).attr('id').replace(/[0-9]/g, '');
                        $($(droppedOn.children())).children('img').attr("src", "../images/"+pName+".png")
                        $(droppedOn.children()).detach().appendTo('#pieces').removeAttr("style").removeClass();
                        var pName = $(dropped).attr('id').replace(/[0-9]/g, '');
                        var pNumber = $(dropped).attr('id').replace(/\D/g,'');
                        $(dropped).remove();
                        $('#pieces').prepend('<div id ='+pName+pNumber+'></div>');
                        $(dropped.children()).detach().appendTo("#"+pName+pNumber);

                    }
                    else if(arrayData[0] == "L"){
                        var pName = $(droppedOn.children()).attr('id').replace(/[0-9]/g, '');
                        $($(droppedOn.children())).children('img').attr("src", "../images/"+pName+".png")
                        var pName = $(dropped).attr('id').replace(/[0-9]/g, '');
                        var pNumber = $(dropped).attr('id').replace(/\D/g,'');
                        $(dropped).remove();
                        $('#pieces').prepend('<div id ='+pName+pNumber+'></div>');
                        $(dropped.children()).detach().appendTo("#"+pName+pNumber);
                    }
                    else{
                        $(dropped).detach().css({position:"absolute",width:w, height:h}).appendTo(droppedOn);
                    }
                    setTimeout(function() {
                        var currAIpos = (parseInt(arrayData[1]) * 10) + parseInt(arrayData[2]);
                        var newAIpos = (parseInt(arrayData[3]) * 10) + parseInt(arrayData[4]);
                        AImove(currAIpos, newAIpos, arrayData[5]);
                    }, 750);
                }
            });

            if (return_value){
                return $(ui.draggable).addClass('drag-revert');
            }


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
            $('#Box'+i).append('<div id ='+array[i]+count+'></div>');
            $('#'+array[i]+count).prepend($('<img>',{class:"img-fluid",src:'../images/Blank.png'}));
            $('#'+array[i]+count).css({"position":"absolute"});
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
    var height = $(window).height() - ($(".logo").outerHeight() + $("#thembuttons").outerHeight());
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


function autoSetup(){
    $.ajax({
        async: false,
        type: "GET",
        url: "/autoSetup",
        success: function(data){
        var array = data.split(" ");
        var count = 0;
        var w=$('.droppable').width();
        var h=$('.droppable').height();
        for (var i = 60; i<100;i++){
            if($('#Box'+i).children().length == 0 && count<array.length){
                $('#Box'+i).append('<div class="draggable" id ='+array[count]+(count+1)+'></div>');
                $('#'+array[count]+(count+1)).prepend($('<img>',{class:"img-fluid",src:'../images/B_'+array[count]+'.png'}));
                $('#'+array[count]+(count+1)).css({"position":"absolute","width":w, "height":h});
                count++;
            }
        }
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
        $('#pieces').html('');
        }
    });
}


function autoPlay(){
    $.ajax({
        async: false,
        type: "POST",
        url: "/autoMove",
        success: function(data, textStatus, request){
            if (request.getResponseHeader("endgame") == "lost") {
                window.location = "/lost";
            }
            if (request.getResponseHeader("endgame") == "won") {
                 window.location = "/won";
            }
            var arrayData = data.split(" ");
            console.log(arrayData);
            var oldppos = (parseInt(arrayData[0]) * 10) + parseInt(arrayData[1]);
            var newppos = (parseInt(arrayData[2]) * 10) + parseInt(arrayData[3]);
            if(arrayData[4] == "W"){
                var pName = $($('#Box'+newppos).children()).attr('id').replace(/[0-9]/g, '');
                $($('#Box'+newppos).children()).children('img').attr("src", "../images/"+pName+".png")
                $($('#Box'+newppos).children()).detach().appendTo('#pieces');
                $($('#Box'+oldppos).children()).detach().appendTo('#Box'+newppos);
            }
            else if (arrayData[4] == "D"){
                var pName = $($('#Box'+newppos).children()).attr('id').replace(/[0-9]/g, '');
                $($('#Box'+newppos).children()).children('img').attr("src", "../images/"+pName+".png")
                var pName = $($('#Box'+oldppos).children()).attr('id').replace(/[0-9]/g, '');
                var pNumber = $($('#Box'+oldppos).children()).attr('id').replace(/\D/g,'');
                var element = $($('#Box'+oldppos).children()).remove();
                $('#pieces').prepend('<div id ='+pName+pNumber+'></div>');
                $($(element).children()).detach().appendTo("#"+pName+pNumber);
                $($('#Box'+newppos).children()).detach().appendTo('#pieces');
            }
            else if (arrayData[4] == "L"){
                var pName = $($('#Box'+newppos).children()).attr('id').replace(/[0-9]/g, '');
                $($('#Box'+newppos).children()).children('img').attr("src", "../images/"+pName+".png")
                var pName = $($('#Box'+oldppos).children()).attr('id').replace(/[0-9]/g, '');
                var pNumber = $($('#Box'+oldppos).children()).attr('id').replace(/\D/g,'');
                var element = $($('#Box'+oldppos).children()).remove();
                $('#pieces').prepend('<div id ='+pName+pNumber+'></div>');
                $(element).detach().appendTo("#"+pName+pNumber);
            }
            else{
                $($('#Box'+oldppos).children()).detach().appendTo('#Box'+newppos);
            }
            $('#pieces div').removeAttr('style').removeClass();

            setTimeout(function() {
                  var currAIpos = (parseInt(arrayData[5]) * 10) + parseInt(arrayData[6]);
                  var newAIpos = (parseInt(arrayData[7]) * 10) + parseInt(arrayData[8]);
                  AImove(currAIpos, newAIpos, arrayData[9]);
            }, 750);

        }
    });
}

function AImove(currAIpos, newAIpos, mode){
    if(mode == "W"){
        var pName = $($('#Box'+currAIpos).children()).attr('id').replace(/[0-9]/g, '');
        $($('#Box'+currAIpos).children()).children('img').attr("src", "../images/"+pName+".png")
        var pName = $($('#Box'+newAIpos).children()).attr('id').replace(/[0-9]/g, '');
        var pNumber = $($('#Box'+newAIpos).children()).attr('id').replace(/\D/g,'');
        var element = $($('#Box'+newAIpos).children()).remove();
        $('#pieces').prepend('<div id ='+pName+pNumber+'></div>');
        $($(element).children()).detach().appendTo("#"+pName+pNumber);
        $($('#Box'+currAIpos).children()).detach().appendTo('#Box'+newAIpos);
    }
    else if (mode == "D"){
        var pName = $($('#Box'+currAIpos).children()).attr('id').replace(/[0-9]/g, '');
        $($('#Box'+currAIpos).children()).children('img').attr("src", "../images/"+pName+".png")
        var pName = $($('#Box'+newAIpos).children()).attr('id').replace(/[0-9]/g, '');
        var pNumber = $($('#Box'+newAIpos).children()).attr('id').replace(/\D/g,'');
        var element = $($('#Box'+newAIpos).children()).remove();
        $('#pieces').prepend('<div id ='+pName+pNumber+'></div>');
        $($(element).children()).detach().appendTo("#"+pName+pNumber);
        $($('#Box'+currAIpos).children()).detach().appendTo('#pieces');
    }
    else if (mode == "L"){
        var pName = $($('#Box'+currAIpos).children()).attr('id').replace(/[0-9]/g, '');
        $($('#Box'+currAIpos).children()).children('img').attr("src", "../images/"+pName+".png")
        $($('#Box'+currAIpos).children()).detach().fadeOut("slow", function() { // code to run after the fadeOut is complete
                $(this).prependTo('#pieces').fadeIn('slow');
                $('#pieces div').removeAttr('style').removeClass();
        })
    }
    else{
        $($('#Box'+currAIpos).children()).detach().appendTo('#Box'+newAIpos);
    }
    $('#pieces div').removeAttr('style').removeClass();
}
