var productServices = {
    setupRating : function() {
        var rating = parseFloat($('#default-rating').text());
        $('#default-rating').replaceWith('<div id="rating"></div>');
        $('#rating').append('<star-rating rating='+rating+'></star-rating>');
        $('#rating').append('<span> ' + rating + ' out of 5</span>');
    },
    setupReviews : function() {
        var empty = true;
        $("tr.row").each(function() {
            empty = false;
            row = $(this);
            var user = row.find("td.row-user").text();
            var text = row.find("td.row-text").text();
            var rating = row.find("td.row-rating").text();
            $('body').append('<review-pane user="' + user + '" rating="' + rating +'">' + text + '</review-pane>');
        });
        if (empty) {
            $('body').append('<h4 class="content">No reviews. Be the first!</h4>');
        }
        $('#default-reviews').remove();
    },
    setup : function() {
        $('head').append('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">');
        productServices.setupRating();
        productServices.setupReviews();
    }
}
$(productServices.setup)