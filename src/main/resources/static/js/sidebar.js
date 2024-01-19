$(document).ready(function () {
    $('.category-link').click(function (event){
       event.preventDefault();
        var categoryId = $(this).data('category-id');

        $.ajax({
            type: 'GET',
            url: '/post/list',
            data: {categoryId: categoryId},
            success: function (response) {console.log('send success', response);},
            error: function(error) {console.error('send error', error);}
        });
    });
});