$(function(){
    function showHide() {
        if ($("input[name='optionsAutomaticManual']:checked").val() == 'optionAutomatic') {
            $('#manual_operations').hide();
            $('#automatic_operations').show();
            $('.class_manual').hide();
            $('.class_automatic').show();
        }
        if ($("input[name='optionsAutomaticManual']:checked").val() == 'optionManual') {
            $('#manual_operations').show();
            $('#automatic_operations').hide();
            $('.class_manual').show();
            $('.class_automatic').hide();
        }
    }


    $(document).ready(function () {
        $("input[name='optionsAutomaticManual']").change(function () {
            showHide();
            var manualauto_mode = ($("input[name='optionsAutomaticManual']:checked").val() == 'optionManual');
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