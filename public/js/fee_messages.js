
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
get_country_settings();



/* Fills the spaces for fees and values information at html pages */
function fillFeeMessages(){
    var calc_value = 0;
        if($('#banks').val() == "0" || $('#banks').val() == country_settings.preferential_bank1_code || $('#banks').val() == country_settings.preferential_bank2_code || $('#banks').val() == country_settings.preferential_bank3_code || $('#banks').val() == country_settings.preferential_bank4_code) {
            if($.isNumeric($('#value').val())) {
                $('#calc_withdraw_fee').html($('#value').val() * country_settings.fee_withdrawal_percent/100 + parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2))
            } else {
                $('#calc_withdraw_fee').html(" - - - ");
            }
            calc_val ($('#hidden_fees_information').attr('wallet_available') + $('#hidden_fees_information').attr('wallet_crypto') - parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2)) * (1 - country_settings.fee_withdrawal_percent/100);
            if (calc_value < 0 ) calc_value = 0;
            $('#calc_maximum_withdraw').html(calc_value.toFixed(2));
        } else {
            if($.isNumeric($('#value').val())) {
                $('#calc_withdraw_fee').html($('#value').val() * country_settings.fee_withdrawal_percent/100 + parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank).toFixed(2));
            } else {
                $('#calc_withdraw_fee').html(" - - - ");
            }
            $('#calc_maximum_withdraw').html(calc_value.toFixed(2));
        }
    }

    calc_value = $('#hidden_fees_information').attr('wallet_available') + $('#hidden_fees_information').attr('wallet_crypto') - calc_value;

    $('#calc_withdraw_preferential_bank_fee').html(parseFloat(country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2));
    $('#calc_withdraw_not_preferential_bank_extra_fee').html(parseFloat(country_settings.nominal_fee_withdrawal_not_preferential_bank - country_settings.nominal_fee_withdrawal_preferential_bank).toFixed(2));

    $('#calc_withdraw_minimum_value').html(parseFloat(country_settings.minimum_value).toFixed(2));
    $('#calc_withdraw_minimum_value_extra_fee').html((parseFloat(country_settings.minimum_value)*0.05).toFixed(2));

    var bank_list = $('#hidden_fees_information').attr('preferential_bank1_name');
    if(country_settings.preferential_bank2_name != '') bank_list += ', ' + country_settings.preferential_bank2_name;
    if(country_settings.preferential_bank3_name != '') bank_list += ', ' + country_settings.preferential_bank3_name;
    if(country_settings.preferential_bank4_name != '') bank_list += ', ' + country_settings.preferential_bank4_name;
    $('#calc_list_preferential_banks').html(bank_list);


}


$(function(){
    $(document).ready(function () {

    });

    $('.triggers_fee_calculation').change(function () {fillFeeMessages()});
});
