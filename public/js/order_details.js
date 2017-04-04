$(function() {
    var template = Handlebars.compile($("#balance-template").html());
    function show_balance() {
        var picture_id =
        API.get_all_img().success(function (images) {
            for (var i = 0; i < images.length; i++) {
                images[i].id = images[i].id;
                images[i].name = images[i].name;
            }

            $('#balance').html(template(images));
        });
    }

    show_balance();
});
