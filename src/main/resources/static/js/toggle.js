function toggleElement() {
    var element = document.getElementById("listToToggle");
    if (element.classList.contains("hidden")) {
        element.classList.remove("hidden");
    } else {
        element.classList.add("hidden");
    }
    return false;
}