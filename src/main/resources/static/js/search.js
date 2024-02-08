var type;

function handleSearchChange(selectedValue){
    type = selectedValue;
}

$('#searchForm').submit(function (event) {
        event.preventDefault();

        var keyword = $('#searchInput').val();
        type = $('#search-dropdown').val();
        window.location.href='/search?keyword=' + keyword + '&type=' + type;
});