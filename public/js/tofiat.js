
$(function() {

    function submit_tofiat() {
        if ($('#value').val() > 0)
        {   var order_type = "F";
            var status = "Op";
            var initial_value = $('#value').val();
            //alert(initial_value);
            API.create_order(order_type, status, '', initial_value, '', '', '', '').success(function () {
                $.pnotify({
                    title: Messages("java.api.messages.account.twofactorauthentication"),
                    text: Messages("java.api.messages.account.twofactorauthenticationturnedon"),
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
    $('.triggers_submit').click(function () {submit_tofiat()});
});
