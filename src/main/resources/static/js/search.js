$(document).ready(function(){
    $('#searchForm').submit(function (event){
        event.preventDefault();
        var keyword = $('#searchInput').val();
        $.ajax({
            type:'POST',
            url:'/post/search',
            contentType:'application/json',
            data:'JSON.stringify({keyword:keyword})',
            success:function(response){console.log('success',response);},
            error:function(error){console.log('error',error);}
        });
    });
});