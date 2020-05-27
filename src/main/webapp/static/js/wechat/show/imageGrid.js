//图片展示
$(function () {
    $('.photoset-grid-lightbox').photosetGrid({
        width: '100%',
        gutter: '5px',
        highresLinks: true,
        lowresWidth: 200,
        rel: 'withhearts-gallery',
        borderActive: true,
        borderWidth: '3px',
        borderColor: '#000000',
        borderRadius: '3px',
        borderRemoveDouble: false,
        onComplete: function () {
            $('.photoset-grid-lightbox')
                .attr('style', '')
                .css({
                    'visibility': 'visible'
                });
            $('.photoset-grid-lightbox a').colorbox({
                photo: true,
                scalePhotos: true,
                maxHeight: '100%',
                maxWidth: '100%'
            });
        }
    });
});


