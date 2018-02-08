var fading_time = 2000;
var index_nav_item_faded = 1;
var number_of_nav_items = 4;
var interval;
var top_position;
var navbar_horitontally = false;

for(var i=1;i<=number_of_nav_items;i++){
    document.getElementById('nav_item'+i.toString()).style.display = "none";
}


function addListenersToDocument() {
    $(window).scroll(function(){
        top_position = window.pageYOffset || document.documentElement.scrollTop;
        if(top_position > 20){
            $('.navbar')[0].style.backgroundColor = "#d0d0d0";
        } else {
            $('.navbar')[0].style.backgroundColor = "transparent";
        }
    });
}


$(document).ready(function() {
    interval = setInterval( function () {
        fadeNavBar();
    },  1000 );
    if(index_nav_item_faded > number_of_nav_items) {
        clearInterval(interval);
    }

    function fadeNavBar(){
        $('#nav_item'+index_nav_item_faded.toString()).fadeIn(fading_time);
        index_nav_item_faded = index_nav_item_faded+1;
    }

    addListenersToDocument();

});



