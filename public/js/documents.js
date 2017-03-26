
$(function() {

    function submit_image() {
        API.upload_image($('#uploadBtn1'));
    }

    $(document).ready(function () {
    });

    $('.triggers_submit').click(function () {
        submit_deposit($('#uploadBtn1'));
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

$('#uploadBtn2').change(function() {
    $('#uploadFile2').val(this.value);
    $('#uploadText2').html(this.value);
    $('#uploadDiv2').addClass('btn-default');
    if (this.value == "")
        $('#uploadDiv2').addClass('btn-info');
    else
        $('#uploadDiv2').removeClass('btn-info');
});


$('#uploadBtn3').change(function() {
    $('#uploadFile3').val(this.value);
    $('#uploadText3').html(this.value);
    $('#uploadDiv3').addClass('btn-default');
    if (this.value == "")
        $('#uploadDiv3').addClass('btn-info');
    else
        $('#uploadDiv3').removeClass('btn-info');
});


$('#uploadBtn4').change(function() {
    $('#uploadFile4').val(this.value);
    $('#uploadText4').html(this.value);
    $('#uploadDiv4').addClass('btn-default');
    if (this.value == "")
        $('#uploadDiv4').addClass('btn-info');
    else
        $('#uploadDiv4').removeClass('btn-info');
});


$('#uploadBtn5').change(function() {
    $('#uploadFile5').val(this.value);
    $('#uploadText5').html(this.value);
    $('#uploadDiv5').addClass('btn-default');
    if (this.value == "")
        $('#uploadDiv5').addClass('btn-info');
    else
        $('#uploadDiv5').removeClass('btn-info');
});

