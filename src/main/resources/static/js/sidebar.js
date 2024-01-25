    $('.category-link').click(function (event){
       event.preventDefault();
        var categoryId = $(this).data('category-id');
        var pageNum = 1;

        $.ajax({
            type: 'GET',
            url: '/post/list',
            data: {
                categoryId: categoryId,
                pageNum : pageNum
            },
            success: function (response) {console.log('send success', response);},
            error: function(error) {console.error('send error', error);}
        });
    });

    $('#category-edit').click(function(){
        var url = '/category/edit';
        window.location.href=url;
    });


