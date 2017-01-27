$(function(){
    showEmails();
});

function showEmails() {
    $('.hidden-email').each(function(idx, el) {
        var $el = $(el);
        $el.text($el.attr('data-name') + '@' + $el.attr('data-domain'));
        $el.attr('href', 'mailto:' + $el.text());
    });
}

function keepNumLength(m){
    var i = m.length - 1;
    while (m[i] == '0') {
        i--;
    }
    if (m[i] == '.') {
        i++;
    }
    return i;
}

function zerosToSpaces(m){
    m = Number(m).toFixed(8);
    var i = keepNumLength(m);
    return m.substring(0, i + 1) + Array(m.length - i).join('\xA0');
}

function zerosTrim(m){
    m = Number(m).toFixed(8);
    var i = keepNumLength(m);
    return m.substring(0, i + 1);
}


$(document).ready(function () {
    //Show or Hide extra information or instructions
    $(".collapseExtraInfo-link").on("click", function () {
        var e = $(this).closest(".x_panel"), t = $(this).find("i"), n = e.find(".x_extrainfo_content");
        e.attr("style") ? n.slideToggle(200, function () {
            //e.removeAttr("style")
        }) : (n.slideToggle(200), e.css("height", "auto")), t.toggleClass("fa-plus-square-o fa-square-o")
    })
});


// Import image - copied from Gentelela Alela form_advanced.html

// I dont know if it works.
$(document).ready(function() {
    var $inputImage = $('#inputImage');
    var URL = window.URL || window.webkitURL;
    var blobURL;
    if (URL) {
        $inputImage.change(function () {
            var files = this.files;
            var file;
            if (!$image.data('cropper')) {
                return;
            }
            if (files && files.length) {
                file = files[0];
                if (/^image\/\w+$/.test(file.type)) {
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {

                        // Revoke when load complete
                        URL.revokeObjectURL(blobURL);
                    }).cropper('reset').cropper('replace', blobURL);
                    $inputImage.val('');
                } else {
                    window.alert('Please choose an image file.');
                }
            }
        });
    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }
});
