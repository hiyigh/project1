
$('#searchForm').submit(function (event){
        event.preventDefault();
        var searchId = $('.search-type').data('search-id');
        var keyword = $('#searchInput').val();

        $.ajax({
            type:'POST',
            url:'/search',
            contentType:'application/json',
            data:JSON.stringify ({
                keyword:keyword,
                searchId : searchId
                }),
            success:function(response){console.log('success',response);},
            error:function(error){console.log('error',error);}
        });
});