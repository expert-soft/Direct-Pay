
$(function() {

    function submit_tocrypto(initial_value) {
        API.create_order("C", "Op", '', initial_value, '', '', '', '').success(function () {
            $.pnotify({
                title: Messages("messages.api.success"),
                text: Messages("messages.api.success.ordercreatedsuccessfully"),
                styling: 'bootstrap',
                type: 'success',
                text_escape: true
            });
            window.location.href = '/dashboard';
        });
    }

    $(document).ready(function () {
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
                // calling API function:
                submit_tocrypto(value);
            }
            else {
                alert(Messages('directpay.formvalidation.valuemustbegreaterthanzero'));
            }
        }
        else {
            alert(Messages('directpay.formvalidation.valuemustbenumerical'));
        }
    }
});
