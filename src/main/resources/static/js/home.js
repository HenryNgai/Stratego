function GetGameDetails() {
    $.ajax({
        async: false,
        type: "POST",
        url: "/getGameDetail",
        data: {
        //Whatever you need to send key: value.
            gameID: $()
        },
        success: function(data){
        //Data that is returned here. Do whatever you want here."
        }
    });
}