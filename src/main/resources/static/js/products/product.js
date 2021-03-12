var productServices = {
    setupRating : function() {
        var rating = parseFloat($('#default-rating').text());
        $('#default-rating').replaceWith('<div id="rating"></div>');
        for (var i = 1; i <= 5; i++) {
            if (rating >= i) {
                $('#rating').append('<span class="fa fa-star star"></span>');
            } else if (rating >= i - 0.5) {
                $('#rating').append('<span class="fa fa-star-half-o star"></span>');
            } else {
                $('#rating').append('<span class="fa fa-star-o star"></span>');
            }
        }
        $('#rating').append('<span> ' + rating + ' out of 5</span>');

    },
    setup : function() {
        $('head').append('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">');
        productServices.setupRating();
    }
}
$(productServices.setup)