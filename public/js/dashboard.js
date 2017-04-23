$(function(){
    function showHide() {
        if ($('#manualauto_mode').attr("checked") == 'checked') {
            $('#manual_operations').hide();
            $('#automatic_operations').show();
            $('.class_manual').hide();
            $('.class_automatic').show();
        } else {
            $('#manual_operations').show();
            $('#automatic_operations').hide();
            $('.class_manual').show();
            $('.class_automatic').hide();
        }
    }

    function showFeeMessages()
    {
        $('#calc_convertion_rate').html(NumberFormat((100 - parseFloat($('#hidden_fees_information').attr('fee_tofiat_percent'))) * 0.01, 3));
        $('#calc_withdraw_preferential_bank_fee').html(NumberFormat($('#hidden_fees_information').attr('nominal_fee_withdrawal_preferential_bank'), 2));
        $('#calc_withdraw_not_preferential_bank_extra_fee').html(NumberFormat(parseFloat($('#hidden_fees_information').attr('nominal_fee_withdrawal_not_preferential_bank') - parseFloat($('#hidden_fees_information').attr('nominal_fee_withdrawal_preferential_bank'))), 2));
        $('#calc_convertion_rate2').html($('#calc_convertion_rate').text());
        $('#calc_withdraw_preferential_bank_fee2').html($('#calc_withdraw_preferential_bank_fee').text());
        $('#calc_withdraw_not_preferential_bank_extra_fee2').html($('#calc_withdraw_not_preferential_bank_extra_fee').text());
    }


    $(document).ready(function () {
        $('#manualauto_mode').change(function () {
            showHide();
            var manualauto_mode = ($('#manualauto_mode').attr("checked") != 'checked');
            API.change_manualauto(manualauto_mode).success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.manualautomodechanged"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            })

        });
    });
    showHide();

    showFeeMessages();

});




