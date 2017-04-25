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





var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
var three_digits_separator;
if (decimal_separator == '.') three_digits_separator = ','; else three_digits_separator = '.';
// decimal_separator : This is what to change the decimal character into.
// three_digits_separator : This is the separator, which is usually a comma.
function NumberFormat (number, numberOfDecimals) { // http://www.hashbangcode.com/blog/format-numbers-commas-javascript
// numberOfDecimals : number of decimal digits
// nStr : This is the number to be formatted. This might be a number or a string. No validation is done on this input.
    var nStr = String(number);
// inD : This is the decimal character for the string. This is usually a dot but might be something else.
    var inD = '.';
    var dpos = nStr.indexOf(inD); // position of decimal point
    var nStrEnd = '';
    if (numberOfDecimals != 0) {
        if (dpos == -1)
            nStrEnd = decimal_separator;
        else {
            nStrEnd = decimal_separator + nStr.substring(dpos + 1, dpos + 1 + numberOfDecimals);
            nStr = nStr.substring(0, dpos);
        }
        while (nStrEnd.length < numberOfDecimals + 1) nStrEnd += '0';
    }
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(nStr)) nStr = nStr.replace(rgx, '$1' + three_digits_separator + '$2');
    return nStr + nStrEnd;
}