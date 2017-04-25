
$(function() {

    function submit_tofiat() {
        if ($('#value').val() > 0)
        {   var order_type = "F";
            var status = "Op";
            var initial_value = parseFloat($('#value').val());
            //alert(initial_value);
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
        fillMessages();
    });
    $('.triggers_submit').click(function () {submit_tofiat()});
});