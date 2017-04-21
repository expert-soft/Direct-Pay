$(function(){
    function showHide() {
//alert($('#manualauto_mode').val());
        if ($('#manualauto_mode').val() == 'on') {
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

//    $('#manualauto_mode').live('click', alert(9));


    $(document).ready(function () {
        $('#manualauto_mode').change(function () {
            showHide();
            var manualauto_mode = ($('#manualauto_mode').val() != 'on');
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
});