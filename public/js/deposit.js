
$(function() {

    function show_bank_data() {
        API.get_bank_data().success(function(data){
            $('#partner').val(data[0].partner);
            $('#partner_account').val(data[0].partner_account);
        });
    }
    show_bank_data();


    $(document).ready(function () {
        fillMessages();
    });
});


//document.getElementById("uploadBtn1").onchange = function () {
$('#uploadBtn1').change(function() {
    $('#uploadFile1').val(this.value);
    $('#uploadText1').html(this.value);
    $('#uploadDiv1').addClass('btn-default');
    if (this.value == "")
        $('#uploadDiv1').addClass('btn-info');
    else
        $('#uploadDiv1').removeClass('btn-info');
});

// ###See application.scala - line 101 - need to treat exception
$('#value').change(function() { fillInfoIntoFileObject() });
$('#partner').change(function() { fillInfoIntoFileObject() });
$('#partner_account').change(function() { fillInfoIntoFileObject() });
function fillInfoIntoFileObject() { $('#uploadBtn1').attr('name', $('#value').val() + "|" + $('#partner').val() + "|" + $('#partner_account').val()); }


$(function(){
    $('#deposit_form').on('submit',function(event){
        var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
        var value_s = $('#value').val();
        if (decimal_separator == ",")
            value_s = value_s.replace(decimal_separator, ".");
        if ($.isNumeric(value_s)) {
            if (parseFloat(value_s) > 0 ) {
                if($('#uploadText1').text() != "") {
                    //(value_s + " <= " + parseFloat($('#hidden_fees_information').attr('wallet_available')) + " + " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto'))  + " - " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) + " - " + parseFloat($('#total_send_fee').val()));
                    if ($('#hidden_page').val() == "D" || ($('#partner').val() != "00" && $('#partner_account').val() != "")) {
                        // calling API function:
//if($('#hidden_page').val() == "DCS") submit_send(value);
                    }
                    else {
                        event.preventDefault() ;
                        event.stopPropagation();
                        alert(Messages('directpay.formvalidation.accountinformationisincomplete'));
                    }
                } else {
                    event.preventDefault() ;
                    event.stopPropagation();
                    alert(Messages('directpay.formvalidation.youmustselectdepositfile'));
                }
            } else {
                event.preventDefault() ;
                event.stopPropagation();
                alert(Messages('directpay.formvalidation.valuemustbegreaterthanzero'));
            }
        } else {
            event.preventDefault() ;
            event.stopPropagation();
            alert(Messages('directpay.formvalidation.valuemustbenumerical'));
        }
    });
});



