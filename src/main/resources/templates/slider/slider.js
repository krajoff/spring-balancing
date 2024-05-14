$(document).ready(function(){
    $("#slider").on("input", function(){
        var sliderValue = $(this).val();
        $("#sliderValue").text(sliderValue);
        $.ajax({
            url: "/slider/slider",
            method: "POST",
            data: {sliderValue: sliderValue},
            success: function(response) {
                console.log("Success");
            },
            error: function(xhr, status, error) {
                console.error("Error", error);
            }
        });
    });
});