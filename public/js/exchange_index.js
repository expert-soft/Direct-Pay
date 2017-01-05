$(function(){
    function showHide() {
        if ($("input[name='optionsAutomaticManual']:checked").val() == 'optionAutomatic') {
            $('#manual_operations').hide();
            $('#automatic_operations').show();
        }
        if ($("input[name='optionsAutomaticManual']:checked").val() == 'optionManual') {
            $('#manual_operations').show();
            $('#automatic_operations').hide();
        }
    }


    $(document).ready(function () {
        $("input[name='optionsAutomaticManual']").change(function () {
            showHide();
        });

        $("#btn1").click(function(){
            $("#test1").text("Hello world!");
        });
        $("#btn2").click(function(){
            $("#test2").html("<b>Hello world!</b>");
        });

    });
    showHide();
});