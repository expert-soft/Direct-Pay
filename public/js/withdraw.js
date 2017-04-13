
$(function() {

    function submit_withdraw() {
        if ($('#banks').val() != "00" && $('#value').val() > 0 && $('#agency').val() != "" && $('#account').val() != "")
        {   var order_type = $('#hidden_page').val();
            order_type = "W";
            if($('#banks').val() != country_settings.preferential_bank1_code && $('#banks').val() != country_settings.preferential_bank2_code && $('#banks').val() != country_settings.preferential_bank3_code && $('#banks').val() != country_settings.preferential_bank4_code)
                order_type = order_type + "."; // not preferential bank
            var status = "Op";
            var initial_value = parseFloat($('#value').val());
            var total_fees = parseFloat($('#total_withdraw_fee').val());
            var wallet_available = parseFloat($('#hidden_fees_information').attr('wallet_available'));
            var bank = $('#banks').val();
            var agency = $('#agency').val();
            var account = $('#account').val();
            if (order_type == "W" && ((initial_value + total_fees) > wallet_available)) {
// place a jquery dialog box to ask if authorize convertion?? ###
                // Need to convert part of crypto-available amount to complete the withdraw amount - not working ###
                API.create_order("F", "Op", '', initial_value + total_fees - wallet_available, '', '', '', '').success(function () {
                    $.pnotify({
                        title: Messages("messages.api.success"),
                        text: Messages("messages.api.success.ordercreatedsuccessfully"),
                        styling: 'bootstrap',
                        type: 'success',
                        text_escape: true
                    });
                })
            }

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
        fillMessages();
    });
    $('.triggers_submit').click(function () {submit_withdraw()});
});
