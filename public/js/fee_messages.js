
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

$(function(){
    $(document).ready(function () {
//        fillMessages();
    });

    $('.triggers_fee_calculation').change(function () {fillMessages()});
});
get_country_settings();


function get_country_settings() { // Read from hidden variables
    country_settings.decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
    country_settings.minimum_value = $('#hidden_fees_information').attr('minimum_value');
    country_settings.critical_value1 = $('#hidden_fees_information').attr('critical_value1');
    country_settings.critical_value2 = $('#hidden_fees_information').attr('critical_value2');
    country_settings.preferential_bank1_code = $('#hidden_fees_information').attr('preferential_bank1_code');
    country_settings.preferential_bank2_code = $('#hidden_fees_information').attr('preferential_bank2_code');
    country_settings.preferential_bank3_code = $('#hidden_fees_information').attr('preferential_bank3_code');
    country_settings.preferential_bank4_code = $('#hidden_fees_information').attr('preferential_bank4_code');
    country_settings.preferential_bank1_name = $('#hidden_fees_information').attr('preferential_bank1_name');
    country_settings.preferential_bank2_name = $('#hidden_fees_information').attr('preferential_bank2_name');
    country_settings.preferential_bank3_name = $('#hidden_fees_information').attr('preferential_bank3_name');
    country_settings.preferential_bank4_name = $('#hidden_fees_information').attr('preferential_bank4_name');
    country_settings.nominal_fee_doc_verification = $('#hidden_fees_information').attr('nominal_fee_doc_verification');
    country_settings.nominal_fee_withdrawal_preferential_bank = $('#hidden_fees_information').attr('nominal_fee_withdrawal_preferential_bank');
    country_settings.nominal_fee_withdrawal_not_preferential_bank = $('#hidden_fees_information').attr('nominal_fee_withdrawal_not_preferential_bank');
    country_settings.fee_deposit_percent = $('#hidden_fees_information').attr('fee_deposit_percent');
    country_settings.fee_withdrawal_percent = $('#hidden_fees_information').attr('fee_withdrawal_percent');
    country_settings.fee_send_percent = $('#hidden_fees_information').attr('fee_send_percent');
    country_settings.fee_tofiat_percent = $('#hidden_fees_information').attr('fee_tofiat_percent');
}

function fillMessages() {
    if ($('#hidden_page').val() == "Dashboard") {
        fillDepositMessages();
        fillSendMessages();
        fillWithdrawMessages();
        fillToFiatMessages();
    }
    else if ($('#hidden_page').val() == "D" || $('#hidden_page').val() == "DCS")
        fillDepositMessages()
    else if ($('#hidden_page').val() == "S")
        fillSendMessages()
    else if ($('#hidden_page').val() == "W" || $('#hidden_page').val() == "RFW")
        fillWithdrawMessages()
    else if ($('#hidden_page').val() == "F")
        fillToFiatMessages();
}


/* Fills the spaces for fees and values information inside Deposit page */
function fillDepositMessages() {
    var aux = parseFloat(country_settings.fee_deposit_percent);
    if($('#hidden_page').val() == "DCS")  // automatic operation
        aux += parseFloat(country_settings.fee_send_percent);
    if (aux != 0 && $.isNumeric($('#value').val())) {
        if (aux < parseFloat(country_settings.minimum_value))
            $('#calc_deposit_fee').html(NumberFormat(parseFloat($('#value').val()) * aux * 0.01 + country_settings.minimum_value * 0.02, 2));
        else
            $('#calc_deposit_fee').html(NumberFormat(parseFloat($('#value').val()) * aux * 0.01, 2))
    }
    else
        $('#calc_deposit_fee').html("- - - ");
    $('#calc_deposit_critical_value2').html(NumberFormat(parseFloat(country_settings.critical_value2), 2));
    $('#calc_deposit_minimum_value').html(NumberFormat(parseFloat(country_settings.minimum_value), 2));
    $('#calc_deposit_minimum_value_extra_fee').html(NumberFormat(parseFloat(country_settings.minimum_value) * 0.02, 2));

}


/* Fills the spaces for fees and values information inside Send page */
function fillSendMessages() {
    if (country_settings.fee_send_percent != 0 && $.isNumeric($('#value').val()))
        $('#calc_send_fee').html(NumberFormat(parseFloat($('#value').val()) * parseFloat(country_settings.fee_send_percent) * 0.01, 2))
    else
        $('#calc_send_fee').html("- - - ");
    $('#calc_maximum_send').html(NumberFormat((parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto'))) * (1 - country_settings.fee_send_percent * 0.01), 2));
}


/* Fills the spaces for fees and values information inside Convert to Fiat page */
function fillToFiatMessages() {
    if (country_settings.fee_tofiat_percent != 0 && $.isNumeric($('#value').val()) && $('#value').val() > parseFloat($('#hidden_fees_information').attr('wallet_crypto')))
        $('#calc_convertion_net').html(NumberFormat(($('#value').val() * (100 - country_settings.fee_tofiat_percent) * 0.01), 2))
    else
        $('#calc_convertion_net').html("- - - ");
    $('#calc_convertion_rate').html(NumberFormat((100 - country_settings.fee_tofiat_percent) * 0.01, 3))
}


/* Fills the spaces for fees and values information inside withdraw page */
function fillWithdrawMessages() {
    var calc_value = 0;
    var aux = parseFloat(country_settings.fee_withdrawal_percent);
    var aux2 = 0;
    if ($('#hidden_page').val() == "RFW") { // automatic operation
        aux += parseFloat(country_settings.fee_tofiat_percent);
        $('#calc_convertion_rate').html(NumberFormat(parseFloat(100 - country_settings.fee_tofiat_percent) * 0.01, 3))
    }
    if($.isNumeric($('#value').val()))
        if (parseFloat($('#value').val()) < parseFloat(country_settings.minimum_value))
            aux2 = country_settings.minimum_value * 0.02;
    if($('#banks').val() == "00" || $('#banks').val() == country_settings.preferential_bank1_code || $('#banks').val() == country_settings.preferential_bank2_code || $('#banks').val() == country_settings.preferential_bank3_code || $('#banks').val() == country_settings.preferential_bank4_code) {
        if($.isNumeric($('#value').val())) {
            $('#total_withdraw_fee').val((calc_value + parseFloat($('#value').val()) * aux * 0.01 + parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank) + aux2).toFixed(2));
            $('#calc_withdraw_fee').html(NumberFormat($('#total_withdraw_fee').val(), 2));
        } else {
            $('#total_withdraw_fee').val(0);
            $('#calc_withdraw_fee').html(" - - - ");
        }
        calc_value = (parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank)) * (1 - aux * 0.01);
        if (calc_value < 0 ) calc_value = 0;
        $('#calc_maximum_withdraw').html(NumberFormat(calc_value, 2));
    } else {
        if($.isNumeric($('#value').val())) {
            $('#total_withdraw_fee').val((calc_value + parseFloat($('#value').val()) * aux * 0.01 + parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank) + aux2).toFixed(2));
            $('#calc_withdraw_fee').html(NumberFormat($('#total_withdraw_fee').val(), 2));
        } else {
            $('#total_withdraw_fee').val(0);
            $('#calc_withdraw_fee').html(" - - - ");
        }
        calc_value = (parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank)) * (1 - aux * 0.01);
        if (calc_value < 0 ) calc_value = 0;
        $('#calc_maximum_withdraw').html(NumberFormat(calc_value, 2));
    }


    calc_value = parseFloat($('#hidden_fees_information').attr('wallet_available')) + parseFloat($('#hidden_fees_information').attr('wallet_crypto')) - calc_value;

    $('#calc_deposit_critical_value2').html(NumberFormat(parseFloat(country_settings.critical_value2), 2));
    $('#calc_withdraw_preferential_bank_fee').html(NumberFormat(country_settings.nominal_fee_withdrawal_preferential_bank, 2));
    $('#calc_withdraw_not_preferential_bank_extra_fee').html(NumberFormat(country_settings.nominal_fee_withdrawal_not_preferential_bank - country_settings.nominal_fee_withdrawal_preferential_bank, 2));

    $('#calc_withdraw_minimum_value').html(NumberFormat(country_settings.minimum_value, 2));
    $('#calc_withdraw_minimum_value_extra_fee').html(NumberFormat(parseFloat(country_settings.minimum_value)*0.02, 2));

    var bank_list = country_settings.preferential_bank1_name;
    if(country_settings.preferential_bank2_name != '') bank_list += ', ' + country_settings.preferential_bank2_name;
    if(country_settings.preferential_bank3_name != '') bank_list += ', ' + country_settings.preferential_bank3_name;
    if(country_settings.preferential_bank4_name != '') bank_list += ', ' + country_settings.preferential_bank4_name;
    $('#calc_list_preferential_banks').html(bank_list);

    FillDocumentsNotVerifiedMessages ();
}


function FillDocumentsNotVerifiedMessages () {
    var list_of_documents = $('#hidden_listofdocuments').attr('listofdocuments_message');
    list_of_documents += ' ' + $('#hidden_listofdocuments').attr('first_name');
    list_of_documents += ', ' + $('#hidden_listofdocuments').attr('last_name');
    if($('#hidden_listofdocuments').attr('country_doc1')) list_of_documents += ', ' + $('#hidden_listofdocuments').attr('country_doc1');
    if($('#hidden_listofdocuments').attr('country_doc2')) list_of_documents += ', ' + $('#hidden_listofdocuments').attr('country_doc2');
    if($('#hidden_listofdocuments').attr('country_doc3')) list_of_documents += ', ' + $('#hidden_listofdocuments').attr('country_doc3');
    if($('#hidden_listofdocuments').attr('country_doc4')) list_of_documents += ', ' + $('#hidden_listofdocuments').attr('country_doc4');
    if($('#hidden_listofdocuments').attr('country_doc5')) list_of_documents += ', ' + $('#hidden_listofdocuments').attr('country_doc5');
    $('#incomplete_docs').attr('title', list_of_documents);
}
