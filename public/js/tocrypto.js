
$(function() {

    function submit_tocrypto() {
        if ($('#value').val() > 0)
        {   var order_type = "C";
            var status = "Op";
            var initial_value = parseFloat($('#value').val());
            API.create_order(order_type, status, '', initial_value, '', '', '', '').success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.ordercreatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            })
        }
        else
            alert("Choose value > 0");
    }

    $(document).ready(function () {
    });
    $('.triggers_submit').click(function () {submit_tocrypto()});
});
