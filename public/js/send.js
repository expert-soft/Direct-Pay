
$(function() {

    function submit_send() {
//treatment of , as decimal separator: parseFloat(str.replace(',','.').replace(' ',''))
        if ($('#partner').val() != "00" && $('#value').val() > 0)
        {   var order_type = "S";
            var status = "Op";
            var partner = $('#partner').val();
            var initial_value = $('#value').val();
            //alert(initial_value);
            API.create_order(order_type, status, partner, initial_value, '', '', '', '').success(function () {
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
            alert("Choose partner and value > 0");
    }

    $(document).ready(function () {
        fillMessages();
    });
    $('.triggers_submit').click(function () {submit_send()});
});
