
$(function() {

    function submit_deposit() {
        if ($('#partner').val() != "00" && $('#value').val() > 0)
        {   var order_type = "D";
            var partner = '';
            if($('#hidden_manual_auto').val() == "false") { // automatic operation
                order_type =  "DCS";
                partner = $('#partner').val();
            }
            var status = "Op";
            var doc1 = $('#doc1').val();
            var initial_value = $('#value').val();
            API.create_order(order_type, status, partner, initial_value, '', '', '', doc1).success(function () {
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
            alert("Choose file name and value > 0");
    }

    $(document).ready(function () {
    });
    $('.triggers_submit').click(function () {submit_deposit()});
});
