$(function() {

    function show_bank_data() {
        API.get_bank_data().success(function(data){
            $('#hidden_bank').val(data[0].bank);
            $('#agency').val(data[0].agency);
            $('#account').val(data[0].account);
            $('#banks').val($('#hidden_bank').val());
        });
    }
    show_bank_data();


    function submit_withdraw(initial_value) {
        var order_type = $('#hidden_page').val();
        order_type = "W";
        if ($('#is_iban').val() == "false" && $('#banks').val() != country_settings.preferential_bank1_code && $('#banks').val() != country_settings.preferential_bank2_code && $('#banks').val() != country_settings.preferential_bank3_code && $('#banks').val() != country_settings.preferential_bank4_code)
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
        window.location.href = ('/dashboard');
    }


    $(document).ready(function () {
        fillMessages();
    });
    $('.triggers_submit').click(function () {
        FormValidating();
    });


    function FormValidating() {
        var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
        var value_s = UnformatNumber($('#value').val());
        if ($.isNumeric(value_s)) {
            var value = parseFloat(value_s);
            if (value > 0) {
                //alert(value_s + " <= " + parseFloat($('#hidden_fees_information').attr('wallet_available')) + " + " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto'))  + " - " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) + " - " + parseFloat($('#total_withdraw_fee').val()));
                if (parseFloat(value_s) <= parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) - parseFloat($('#total_withdraw_fee').val())) {
                    //alert(parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) - parseFloat($('#total_withdraw_fee').val()));
                    if($('#is_iban').val() == "true")
                        if ($('#account').val() != "") // ### should be iban validation
                            // calling API function:
                            submit_withdraw(value);
                        else {
                            alert(Messages('directpay.formvalidation.bankinginformationisnotvalid'));
                        }
                    else
                        if ($('#banks').val() != "00" && $('#agency').val() != "" && $('#account').val() != "")
                        // calling API function:
                            submit_withdraw(value);
                        else {
                            alert(Messages('directpay.formvalidation.bankinginformationisincomplete'));
                        }
                } else {
                    alert(Messages('directpay.formvalidation.amountnotavailable'));
                }
            } else {
                alert(Messages('directpay.formvalidation.valuemustbegreaterthanzero'));
            }
        } else {
            alert(Messages('directpay.formvalidation.valuemustbenumerical'));
        }
    }
});
