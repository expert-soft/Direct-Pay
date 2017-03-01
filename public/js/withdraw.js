
$(function() {

    function submit_withdraw() {
        if ($('#banks').val() != "00" && $('#value').val() > 0 && $('#agency').val() != "" && $('#account').val() != "")
        {   if($('#banks').val() == country_settings.preferential_bank1_code || $('#banks').val() == country_settings.preferential_bank2_code || $('#banks').val() == country_settings.preferential_bank3_code || $('#banks').val() == country_settings.preferential_bank4_code)
                var order_type = "W"; // preferential bank
            else
                var order_type = "W."; // not preferential bank
            if($('#hidden_manual_auto').val() == "false") order_type = "RF" + order_type; // automatic operation
            var status = "Op";
            var initial_value = $('#value').val();
            var bank = $('#banks').val();
            var agency = $('#agency').val();
            var account = $('#account').val();
            API.create_order(order_type, status, '', initial_value, bank, agency, account, '').success(function () {
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
            alert("Choose bank and value > 0");
    }

    $(document).ready(function () {
    });
    $('.triggers_submit').click(function () {submit_withdraw()});
});
