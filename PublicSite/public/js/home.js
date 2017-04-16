var fading_time = 2000;
var index_nav_item_faded = 1;
var number_of_nav_items = 4;
var interval;
var top_position;
var navbar_horitontally = false;

// define heights of each div in the home page (windows/6)
var window_height=($(document).height()/5).toString();
var divs_home=$('.home_div');
for(var i=0;i<divs_home.length;i++){
    divs_home[i].setAttribute("style","height:"+window_height+"px");
}

//hide navbar links before fading

for(var i=1;i<=number_of_nav_items;i++){
    document.getElementById('nav_item'+i.toString()).style.display = "none";
}



function addListenersToDocument() {
    $(window).scroll(function(){
        top_position = window.pageYOffset || document.documentElement.scrollTop;
        if(top_position > 250){
            $('.navbar')[0].style.backgroundColor = "#d0d0d0";
        } else {
            $('.navbar')[0].style.backgroundColor = "transparent";
        }
    });
}




$(document).ready(function() {

    /*$('.nav_item').each(function () {
     $(this).fadeIn(fading_time);
     });*/
    /*var nav_items = $('.nav_item');
     for(var i=0;i<nav_items.length;i++){
     nav_items[i].fadeIn(fading_time);
     }*/

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



