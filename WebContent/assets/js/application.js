$(document).ready(function(){
    $(".dropdown-opcoes-buscar").dropdown();
    $(".dropdown-menu").find('form').click(function(e){e.stopPropagation();});
});