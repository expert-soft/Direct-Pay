
$(function() {

    function submit_send() {
        if ($('#partner').val() != "00" && $('#value').val() > 0)
        {   var order_type = "S";
            var status = "Op";
            var partner = $('#partner').val();
            var initial_value = $('#value').val();
            //alert(initial_value);
            API.create_order(order_type, status, partner, initial_value).success(function () {
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
            alert("Choose partner and value > 0");
    }



    $(document).ready(function () {

    });

    $('.triggers_submit').click(function () {submit_send()});
});
