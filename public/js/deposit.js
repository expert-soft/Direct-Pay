
$(function() {
    // this function is not being used. Submission is being done through uploadImage
/*    function submit_deposit() {
        if ($('#partner').val() != "00" && $('#value').val() > 0) {
            var order_type = $('#hidden_page').val();
            var partner = '';
            var doc1 = 'XXX';
            submit_image();
            if (order_type == "DCS") {
                partner = $('#partner').val();
            }
            var status = "Op";
            doc1 = $('#doc1').val();
            var initial_value = $('#value').val();
            API.create_order(order_type, status, partner, initial_value, '', '', '', doc1).success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.ordercreatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            });
        }
        else
            alert("Choose file name and value > 0");
    }
*/

/*
    function submit_image() {
        API.upload_image($('#uploadBtn1'));
        $.pnotify({
            title: Messages("messages.api.success"),
            text: Messages("messages.api.success.manualautomodechanged"),
            styling: 'bootstrap',
            type: 'success',
            text_escape: true
        });
    }
*/

    $(document).ready(function () {
        fillMessages();
    });

/*    $('.triggers_submit').click(function () {
alert(3);
        submit_deposit($('#uploadBtn1'));
    });
*/

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
                        if($('#hidden_page').val() == "DCS") submit_send(value);
                    }
                    else {
                        event.preventDefault() ;
                        event.stopPropagation();
                        alert($('#hidden_form_validation_messages').attr('accountinformationisincomplete'));
                    }
                } else {
                    event.preventDefault() ;
                    event.stopPropagation();
                    alert($('#hidden_form_validation_messages').attr('youmustselectdepositfile'));
                }
            } else {
                event.preventDefault() ;
                event.stopPropagation();
                alert($('#hidden_form_validation_messages').attr('valuemustbegreaterthanzero'));
            }
        } else {
            event.preventDefault() ;
            event.stopPropagation();
            alert($('#hidden_form_validation_messages').attr('valuemustbenumerical'));
        }
    });
});



