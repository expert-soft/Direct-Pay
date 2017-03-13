
$(function() {

    function submit_deposit() {
        if ($('#partner').val() != "00" && $('#value').val() > 0)
        {   var order_type = $('#hidden_page').val();
            var partner = '';
            var doc1 = 'XXX';
            if(order_type == "DCS") { // automatic operation
                partner = $('#partner').val();
            }
            var status = "Op";
            doc1 = $('#doc1').val();
            var initial_value = $('#value').val();
//            alert (order_type);
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



document.getElementById("local").onchange = function() {

    var img = document.createElement("img"),
        reader = new FileReader();

    img.file = this.files[0];

    reader.onload = (function(aImg) {
        return function(img) {
            aImg.src = img.target.result;
            document.body.appendChild(aImg);
        }
    })(img);

    reader.readAsDataURL(img.file);

}