
$(function() {

    function submit_deposit() {
        if ($('#partner').val() != "00" && $('#value').val() > 0)
        {   var order_type = "D";
            var partner = '';
            if($('#hidden_manual_auto').val() == "false") { // automatic operation
                order_type =  "DCS";
                partner = $('#partner').val();
            }
            var status = "Op";
            var doc1 = $('#doc1').val();
            var initial_value = $('#value').val();
            API.create_order(order_type, status, partner, initial_value, '', '', '', doc1).success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.ordercreatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            })


            routes.Application.uploadImage();
        }
        else
            alert("Choose file name and value > 0");
    }

    $(document).ready(function () {
    });

    $('.triggers_submit').click(function () {submit_deposit()});



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