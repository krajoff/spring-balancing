  var rangeSlider = document.getElementById("slider");
  var circle = document.getElementById("circuit");

  rangeSlider.addEventListener("input", function() {
    var radius = parseInt(rangeSlider.value);
    circle.setAttribute("r", radius);
  });