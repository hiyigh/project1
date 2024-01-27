var searchId;
function handleSearchChange(selectedValue){
    searchId = selectedValue;
}

$('#searchForm').submit(function (event) {
        event.preventDefault();

        var keyword = $('#searchInput').val();
        searchId = $('#search-dropdown').val();

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