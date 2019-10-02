function GetGameDetails(GAMEID) {
    $.ajax({
        type: "POST",
        url: "/getGameDetail",
        data: {
        //Whatever you need to send key: value.
            gameId: GAMEID
        },
        success: function(data){
        console.log(data);
            var arrayData = data.split(",");
            $("#GameDetailsBody").html('');
            for (var i = 0 ; i < arrayData.length-1 ; i++){
                var NewArray = arrayData[i].split(" ");
                $("#GameDetailsBody").append('<tr id="row'+i+'"></tr>');
                $("#GameDetailsBody #row"+i).append('<td scope="row">'+NewArray[0]+'</td>');
                $("#GameDetailsBody #row"+i).append('<td>'+NewArray[1]+'</td>');
                $("#GameDetailsBody #row"+i).append('<td>'+NewArray[2]+'</td>');
            }

        }
    });
}