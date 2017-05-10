$(function() {

    function submit_withdraw(initial_value) {
        var order_type = $('#hidden_page').val();
        order_type = "W";
        if ($('#banks').val() != country_settings.preferential_bank1_code && $('#banks').val() != country_settings.preferential_bank2_code && $('#banks').val() != country_settings.preferential_bank3_code && $('#banks').val() != country_settings.preferential_bank4_code)
            order_type = order_type + "."; // not preferential bank
        var total_fees = parseFloat($('#total_withdraw_fee').val());
        var wallet_available = parseFloat($('#hidden_fees_information').attr('wallet_available'));
        var bank = $('#banks').val();
        var agency = $('#agency').val();
        var account = $('#account').val();
        if ((order_type == "W" || order_type == "W." ) && ((initial_value + total_fees) > wallet_available)) {
// place a jquery dialog box to ask if authorize convertion?? ###
            // Need to convert part of crypto-available amount to complete the withdraw amount - not working ###
            API.create_order('F', 'Op', '', initial_value + total_fees - wallet_available, '', '', '', '').success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.ordercreatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
                API.create_order(order_type, 'Op', '', initial_value, bank, agency, account, '').success(function () {
                    $.pnotify({
                        title: Messages("messages.api.success"),
                        text: Messages("messages.api.success.ordercreatedsuccessfully"),
                        styling: 'bootstrap',
                        type: 'success',
                        text_escape: true
                    });
                });
            })
        } else {
            API.create_order(order_type, 'Op', '', initial_value, bank, agency, account, '').success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.ordercreatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            })
        }
        window.location.href = $('#hidden_form_validation_messages').attr('dashboard_url');
    }


    $(document).ready(function () {
        fillMessages();
    });
    $('.triggers_submit').click(function () {
        FormValidating();
    });


    function FormValidating() {
        var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
        var value_s = $('#value').val();
        if (decimal_separator == ",")
            value_s = value_s.replace(decimal_separator, ".");
        if ($.isNumeric(value_s)) {
            var value = parseFloat(value_s);
            if (value > 0) {
                //alert(value_s + " <= " + parseFloat($('#hidden_fees_information').attr('wallet_available')) + " + " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto'))  + " - " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) + " - " + parseFloat($('#total_withdraw_fee').val()));
                if (parseFloat(value_s) <= parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) - parseFloat($('#total_withdraw_fee').val())) {
                    //alert(parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) - parseFloat($('#total_withdraw_fee').val()));
                    if ($('#banks').val() != "00" && $('#agency').val() != "" && $('#account').val() != "")
                        // calling API function:
                        submit_withdraw(value);
                    else {
                        alert($('#hidden_form_validation_messages').attr('bankinginformationisincomplete'));
                    }
                } else {
                    alert($('#hidden_form_validation_messages').attr('amountnotavailable'));
                }
            } else {
                alert($('#hidden_form_validation_messages').attr('valuemustbegreaterthanzero'));
            }
        } else {
            alert($('#hidden_form_validation_messages').attr('valuemustbenumerical'));
        }
    }
});
