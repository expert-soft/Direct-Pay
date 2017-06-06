$(function() {

    function show_bank_data() {
        API.get_bank_data().success(function(data){
            $('#partner').val(data[0].partner);
            $('#partner_account').val(data[0].partner_account);
        });
    }
    show_bank_data();



    function submit_send() {
//treatment of , as decimal separator: parseFloat(str.replace(',','.').replace(' ',''))
        var order_type = "S"; // send fiat direct, without converting
        if($('#hidden_fees_information').attr('country_operations_organized') == "convert")
            order_type = "S."; // sending crypto-currency
        else
            order_type = "S"; // sending fiat

        API.create_order(order_type, "Op", $('#partner').val(), $('#value').val(), '', '', '', '').success(function () {
            $.pnotify({
                title: Messages("messages.api.success"),
                text: Messages("messages.api.success.ordercreatedsuccessfully"),
                styling: 'bootstrap',
                type: 'success',
                text_escape: true
            });
        });
        window.location.href = '/dashboard';
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
                //(value_s + " <= " + parseFloat($('#hidden_fees_information').attr('wallet_available')) + " + " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto'))  + " - " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) + " - " + parseFloat($('#total_send_fee').val()));
                if (parseFloat(value_s) <= parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) - parseFloat($('#total_send_fee').val())) {
                    if ($('#partner').val() != "00" && $('#partner_account').val() != "")
                    // calling API function:
                        submit_send(value);
                    else {
                        alert(Messages('directpay.formvalidation.accountinformationisincomplete'));
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
