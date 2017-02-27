
var country_settings = {
    decimal_separator : "",
    minimum_value : "",
    critical_value1 : "",
    critical_value2 : "",
    preferential_bank1_code : "",
    preferential_bank1_name : "",
    preferential_bank2_code : "",
    preferential_bank2_name : "",
    preferential_bank3_code : "",
    preferential_bank3_name : "",
    preferential_bank4_code : "",
    preferential_bank4_name : "",
    nominal_fee_doc_verification : "",
    nominal_fee_withdrawal_preferential_bank : "",
    nominal_fee_withdrawal_not_preferential_bank : "",
    fees_global_percentage : "",
    fee_deposit_percent : "",
    fee_withdrawal_percent : "",
    fee_send_percent : "",
    fee_tofiat_percent : ""
};

/*
function get_country_settings(){ // JSon function
    API.country_settings().success(function(data) {
        country_settings.decimal_separator = data.decimal_separator;
        country_settings.minimum_value = data.minimum_value;
        country_settings.critical_value1 = data.critical_value1;
        country_settings.critical_value2 = data.critical_value2;
        country_settings.preferential_bank1_code = data.preferential_bank1_code;
        country_settings.preferential_bank2_code = data.preferential_bank2_code;
        country_settings.preferential_bank3_code = data.preferential_bank3_code;
        country_settings.preferential_bank4_code = data.preferential_bank4_code;
        country_settings.nominal_fee_doc_verification = data.nominal_fee_doc_verification;
        country_settings.nominal_fee_withdrawal_preferential_bank = data.nominal_fee_withdrawal_preferential_bank;
        country_settings.nominal_fee_withdrawal_not_preferential_bank = data.nominal_fee_withdrawal_not_preferential_bank;
        country_settings.fees_global_percentage = data.fees_global_percentage;
        country_settings.fee_deposit_percent = data.fee_deposit_percent;
        country_settings.fee_withdrawal_percent = data.fee_withdrawal_percent;
        country_settings.fee_send_percent = data.fee_send_percent;
        country_settings.fee_tofiat_percent = data.fee_tofiat_percent;
        fillFeeMessages()
    });
};
*/

$(function(){
    $(document).ready(function () {

    });

    $('.triggers_fee_calculation').change(function () {fillMessages()});
});


function get_country_settings() { // Read from hidden variables
    country_settings.decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
    country_settings.minimum_value = $('#hidden_fees_information').attr('minimum_value');
    country_settings.critical_value1 = $('#hidden_fees_information').attr('critical_value1');
    country_settings.critical_value2 = $('#hidden_fees_information').attr('critical_value2');
    country_settings.preferential_bank1_code = $('#hidden_fees_information').attr('preferential_bank1_code');
    country_settings.preferential_bank2_code = $('#hidden_fees_information').attr('preferential_bank2_code');
    country_settings.preferential_bank3_code = $('#hidden_fees_information').attr('preferential_bank3_code');
    country_settings.preferential_bank4_code = $('#hidden_fees_information').attr('preferential_bank4_code');
    country_settings.nominal_fee_doc_verification = $('#hidden_fees_information').attr('nominal_fee_doc_verification');
    country_settings.nominal_fee_withdrawal_preferential_bank = $('#hidden_fees_information').attr('nominal_fee_withdrawal_preferential_bank');
    country_settings.nominal_fee_withdrawal_not_preferential_bank = $('#hidden_fees_information').attr('nominal_fee_withdrawal_not_preferential_bank');
    country_settings.fee_deposit_percent = $('#hidden_fees_information').attr('fee_deposit_percent');
    country_settings.fee_withdrawal_percent = $('#hidden_fees_information').attr('fee_withdrawal_percent');
    country_settings.fee_send_percent = $('#hidden_fees_information').attr('fee_send_percent');
    country_settings.fee_tofiat_percent = $('#hidden_fees_information').attr('fee_tofiat_percent');
}
get_country_settings();
fillMessages();

function fillMessages() {
    if ($('#hidden_page').val() == "V")
        fillVerificationMessages()
    else if ($('#hidden_page').val() == "D" || $('#hidden_page').val() == "DCS")
        fillDepositMessages()
    else if ($('#hidden_page').val() == "S")
        fillSendMessages()
    else if ($('#hidden_page').val() == "W" || $('#hidden_page').val() == "RFW")
        fillWithdrawMessages()
    else if ($('#hidden_page').val() == "F")
        fillToFiatMessages();
}

/*
if (order_type == "V") {
    return globals.country_nominal_fee_doc_verification.asInstanceOf[Double]
} else if (order_type == "D") {
    return initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01
} else if (order_type == "S") {
    return initial_value * globals.country_fee_send_percent.asInstanceOf[Double] * 0.01
} else if (order_type == "DCS") {
    return initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01
} else if (order_type == "W") { // withdrawal to a preferential bank
    return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01
} else if (order_type == "W.") { // withdrawal to a non preferential bank
    return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + globals.country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01
} else if (order_type == "RFW") { // withdrawal to a preferential bank
    return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01
} else if (order_type == "RFW.") { // withdrawal to a non preferential bank
    return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + globals.country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01
} else if (order_type == "F") {
    return initial_value * globals.country_fee_tofiat_percent.asInstanceOf[Double] * 0.01
} else return 0
*/

/* Fills the spaces for fees and values information inside Deposit page */
function fillDepositMessages() {
    var aux = parseFloat(country_settings.fee_deposit_percent);
    if($('#hidden_page').val() == "DCS")  // automatic operation
        aux += parseFloat(country_settings.fee_send_percent);
    if (aux != 0 && $.isNumeric($('#value').val()))
        $('#calc_deposit_fee').html((parseFloat($('#value').val()) * aux * 0.01).toFixed(2))
    else
        $('#calc_deposit_fee').html("- - - ");
    $('#calc_deposit_minimum_value').html(parseFloat(country_settings.minimum_value).toFixed(2));
    $('#calc_deposit_minimum_value_extra_fee').html((parseFloat(country_settings.minimum_value) * 0.02).toFixed(2));
}


/* Fills the spaces for fees and values information inside Send page */
function fillSendMessages() {
    if (country_settings.fee_send_percent != 0 && $.isNumeric($('#value').val()))
        $('#calc_send_fee').html((parseFloat($('#value').val()) * country_settings.fee_send_percent * 0.01).toFixed(2))
    else
        $('#calc_send_fee').html("- - - ");
    $('#calc_maximum_send').html((($('#hidden_fees_information').attr('wallet_available') + $('#hidden_fees_information').attr('wallet_crypto')) * (1 - country_settings.fee_send_percent * 0.01)).toFixed(2));
}


/* Fills the spaces for fees and values information inside Convert to Fiat page */
function fillToFiatMessages() {
    if (country_settings.fee_tofiat_percent != 0 && $.isNumeric($('#value').val()))
        $('#calc_convertion_net').html((parseFloat($('#value').val()) * (100 - country_settings.fee_tofiat_percent) * 0.01).toFixed(2))
    else
        $('#calc_convertion_net').html("- - - ");
    $('#calc_convertion_rate').html((parseFloat(100 - country_settings.fee_tofiat_percent) * 0.01).toFixed(3))
}


/* Fills the spaces for fees and values information inside withdraw page */
function fillWithdrawMessages() {
    var aux = parseFloat(country_settings.fee_withdrawal_percent);
    if ($('#hidden_page').val() == "RFW") { // automatic operation
        aux += parseFloat(country_settings.fee_tofiat_percent);
        $('#calc_convertion_rate').html((parseFloat(100 - country_settings.fee_tofiat_percent) * 0.01).toFixed(3))
    }
    var calc_value = 0;
    if($('#banks').val() == "00" || $('#banks').val() == country_settings.preferential_bank1_code || $('#banks').val() == country_settings.preferential_bank2_code || $('#banks').val() == country_settings.preferential_bank3_code || $('#banks').val() == country_settings.preferential_bank4_code) {
        if($.isNumeric($('#value').val())) {
            $('#calc_withdraw_fee').html((parseFloat($('#value').val()) * aux * 0.01 + parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank)).toFixed(2));
        } else {
            $('#calc_withdraw_fee').html(" - - - ");
        }
        calc_value = ($('#hidden_fees_information').attr('wallet_available') + $('#hidden_fees_information').attr('wallet_crypto') - parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2)) * (1 - aux * 0.01);
        if (calc_value < country_settings.minimum_value ) { calc_value -= country_settings.minimum_value * 0.02 };
        if (calc_value < 0 ) calc_value = 0;
        $('#calc_maximum_withdraw').html(calc_value.toFixed(2));
    } else {
        if($.isNumeric($('#value').val())) {
            $('#calc_withdraw_fee').html((parseFloat($('#value').val()) * aux * 0.01 + parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank)).toFixed(2));
        } else {
            $('#calc_withdraw_fee').html(" - - - ");
        }
        calc_value = ($('#hidden_fees_information').attr('wallet_available') + $('#hidden_fees_information').attr('wallet_crypto') - parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank).toFixed(2)) * (1 - aux * 0.01);
        if (calc_value < country_settings.minimum_value ) { calc_value -= country_settings.minimum_value * 0.02 };
        if (calc_value < 0 ) calc_value = 0;
        $('#calc_maximum_withdraw').html(calc_value.toFixed(2));
    }


    calc_value = $('#hidden_fees_information').attr('wallet_available') + $('#hidden_fees_information').attr('wallet_crypto') - calc_value;

    $('#calc_withdraw_preferential_bank_fee').html(parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2));
    $('#calc_withdraw_not_preferential_bank_extra_fee').html(parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank - country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2));

    $('#calc_withdraw_minimum_value').html(parseFloat(country_settings.minimum_value).toFixed(2));
    $('#calc_withdraw_minimum_value_extra_fee').html((parseFloat(country_settings.minimum_value)*0.02).toFixed(2));

    var bank_list = $('#hidden_fees_information').attr('preferential_bank1_name');
    if(country_settings.preferential_bank2_name != '') bank_list += ', ' + country_settings.preferential_bank2_name;
    if(country_settings.preferential_bank3_name != '') bank_list += ', ' + country_settings.preferential_bank3_name;
    if(country_settings.preferential_bank4_name != '') bank_list += ', ' + country_settings.preferential_bank4_name;
    $('#calc_list_preferential_banks').html(bank_list);
}





/* Fills the spaces for fees and values information inside User Settings page */
function fillVerificationMessages() {
    alert(12);
}
