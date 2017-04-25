$(function() {

    function apply_docs_info() {
        API.get_docs_info().success(function (data) {

            data[0].doc1 = data[0].doc1;
            data[0].doc2 = data[0].doc2;
            data[0].doc3 = data[0].doc3;
            data[0].doc4 = data[0].doc4;
            data[0].doc5 = data[0].doc5;
            data[0].ver1 = data[0].ver1;
            data[0].ver2 = data[0].ver2;
            data[0].ver3 = data[0].ver3;
            data[0].ver4 = data[0].ver4;
            data[0].ver5 = data[0].ver5;
            data[0].pic1 = data[0].pic1;
            data[0].pic2 = data[0].pic2;
            data[0].pic3 = data[0].pic3;
            data[0].pic4 = data[0].pic4;
            data[0].pic5 = data[0].pic5;

            $('#uploadText1').html(data[0].doc1);
            $('#uploadText2').html(data[0].doc2);
            $('#uploadText3').html(data[0].doc3);
            $('#uploadText4').html(data[0].doc4);
            $('#uploadText5').html(data[0].doc5);

            $('#requestPopupDetails1').attr('fileName', data[0].doc1);
            $('#requestPopupDetails1').attr('verified', data[0].ver1);
            $('#requestPopupDetails1').attr('image_id', data[0].pic1);
            $('#requestPopupDetails2').attr('fileName', data[0].doc2);
            $('#requestPopupDetails2').attr('verified', data[0].ver2);
            $('#requestPopupDetails2').attr('image_id', data[0].pic2);
            $('#requestPopupDetails3').attr('fileName', data[0].doc3);
            $('#requestPopupDetails3').attr('verified', data[0].ver3);
            $('#requestPopupDetails3').attr('image_id', data[0].pic3);
            $('#requestPopupDetails4').attr('fileName', data[0].doc4);
            $('#requestPopupDetails4').attr('verified', data[0].ver4);
            $('#requestPopupDetails4').attr('image_id', data[0].pic4);
            $('#requestPopupDetails5').attr('fileName', data[0].doc5);
            $('#requestPopupDetails5').attr('verified', data[0].ver5);
            $('#requestPopupDetails5').attr('image_id', data[0].pic5);

            if (data[0].ver1) $('#docCheck1').show();
            if (data[0].ver2) $('#docCheck2').show();
            if (data[0].ver3) $('#docCheck3').show();
            if (data[0].ver4) $('#docCheck4').show();
            if (data[0].ver5) $('#docCheck5').show();

            $('.requestPopUp').live('click', function() {
                $('#the_picture')[0].attributes[0].nodeValue = "/images/" + $(this).attr('image_id');
                function do_transfer(e) { e.preventDefault() }
                transfer_details ($(this).attr('doc'), $(this).attr('fileName'), $(this).attr('verified'));
            });
        });
    }

    apply_docs_info();
});


$(document).ready(function () {
    $( window ).resize(function() {
        resizeDiv()
    });

    function resizeDiv (){
        $('#popupInfoDiv').css('width', parseInt($( document ).width()*0.8));
        $('#popupInfoDiv').css('height', parseInt($( document ).height()*0.5));
    }

    $('#btnZoomIn').live('click', function() {
        $('#the_picture').width(parseInt($('#the_picture').width() * 1.5));
    });

    $('#btnZoomOut').live('click', function() {
        $('#the_picture').width(parseInt($('#the_picture').width() / 1.5));
    });
});

function transfer_details (doc, fileName, verified) {
    if (verified == "true") {
        $('#popUpPictureInfo1').html($('#hidden_verified').attr('yes'));
        $('#popupCheck').show();
    } else {
        $('#popUpPictureInfo1').html($('#hidden_verified').attr('no'));
        $('#popupCheck').hide();
    }
    $('#popUpPictureInfo2').html(fileName);
    $('#popUpPictureTitle').html($('#popUpPictureDoc' + doc).val());
    resizeDiv()
}




$('#uploadBtn1').change(function() {
    $('#uploadFile1').val(this.value);
    $('#uploadText1').html(this.value);
    $('#uploadDiv1').addClass('btn-default');
    $('#docCheck1').hide();
    if (this.value == "")
        $('#uploadDiv1').addClass('btn-info');
    else
        $('#uploadDiv1').removeClass('btn-info');
});

$('#uploadBtn2').change(function() {
    $('#uploadFile2').val(this.value);
    $('#uploadText2').html(this.value);
    $('#uploadDiv2').addClass('btn-default');
    $('#docCheck2').hide();
    if (this.value == "")
        $('#uploadDiv2').addClass('btn-info');
    else
        $('#uploadDiv2').removeClass('btn-info');
});


$('#uploadBtn3').change(function() {
    $('#uploadFile3').val(this.value);
    $('#uploadText3').html(this.value);
    $('#uploadDiv3').addClass('btn-default');
    $('#docCheck3').hide();
    if (this.value == "")
        $('#uploadDiv3').addClass('btn-info');
    else
        $('#uploadDiv3').removeClass('btn-info');
});


$('#uploadBtn4').change(function() {
    $('#uploadFile4').val(this.value);
    $('#uploadText4').html(this.value);
    $('#uploadDiv4').addClass('btn-default');
    $('#docCheck4').hide();
    if (this.value == "")
        $('#uploadDiv4').addClass('btn-info');
    else
        $('#uploadDiv4').removeClass('btn-info');
});


$('#uploadBtn5').change(function() {
    $('#uploadFile5').val(this.value);
    $('#uploadText5').html(this.value);
    $('#uploadDiv5').addClass('btn-default');
    $('#docCheck5').hide();
    if (this.value == "")
        $('#uploadDiv5').addClass('btn-info');
    else
        $('#uploadDiv5').removeClass('btn-info');
});

