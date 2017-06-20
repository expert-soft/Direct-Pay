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
// decimal_separator : This is what to change the decimal character into.

function NumberFormat (number, numberOfDecimals) { // http://www.hashbangcode.com/blog/format-numbers-commas-javascript
// numberOfDecimals : number of decimal digits
    var nStr = String(number);
// nStr : This is the number to be formatted. This might be a number or a string. No validation is done on this input.
    var three_digits_separator;
    var inD;
// three_digits_separator : This is the group separator, which is usually a comma.
// inD : This is the decimal character for the string. This is usually a dot but might be something else.
    if (decimal_separator == '.') {
        three_digits_separator = ',';
        inD = '.';
    } else {
        three_digits_separator = '.';
        inD = ',';
    }
    var dpos = nStr.indexOf(inD); // position of decimal point
    if (dpos = -1) dpos = nStr.indexOf(three_digits_separator); // alternative decimal point. (only for scanning, not to show)
    var nStrEnd = '';
    inD = '.';
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


function UnformatNumber (value) {
    var three_digits_separator;
    var inD;
// three_digits_separator : This is the separator, which is usually a comma.
// inD : This is the decimal character for the string. This is usually a dot but might be something else.
    if (decimal_separator == '.') {
        three_digits_separator = ',';
        inD = '.';
    } else {
        three_digits_separator = '.';
        inD = ',';
    }

    var value_s = value + "";
    var result_s;

    var dposP = value_s.indexOf(inD); // position of decimal point
    var dposP2 = dposP + 1 + value_s.substring(dposP + 1).indexOf(inD); // position of a second decimal point
    if (dposP2 > dposP) dposP = dposP2;
    dposP2 = dposP + 1 + value_s.substring(dposP + 1).indexOf(inD); // position of a second decimal point
    if (dposP2 > dposP) dposP = dposP2;
    dposP2 = dposP + 1 + value_s.substring(dposP + 1).indexOf(inD); // position of a second decimal point
    if (dposP2 > dposP) dposP = dposP2;

    var dposG = value_s.indexOf(three_digits_separator); // position of a group separator
    var dposG2 = dposG + 1 + value_s.substring(dposG + 1).indexOf(three_digits_separator); // position of a group separator
    if (dposG2 > dposG) dposG = dposG2;
    dposG2 = dposG + 1 + value_s.substring(dposG + 1).indexOf(three_digits_separator); // position of a group separator
    if (dposG2 > dposG) dposG = dposG2;
    dposG2 = dposG + 1 + value_s.substring(dposG + 1).indexOf(three_digits_separator); // position of a group separator
    if (dposG2 > dposG) dposG = dposG2;

//alert(inD + three_digits_separator + " _ P=" + dposP + ", P2=" + dposP2 + ", G=" + dposG + ", G2=" + dposG2);
    if (dposG == value_s.length - 4 && dposG > 0 && dposP == -1)
        if (((value_s.indexOf(three_digits_separator) == dposG) && dposG < 4 && dposG > 0) ||
            (value_s.indexOf(three_digits_separator) == dposG - 4) && (dposG > 4 && dposG < 8))// user did not mean decimal point, but only three_digit_separator
            dposP = value_s.length;
    if (dposP == -1 && dposG == -1)
        dposP = value_s.length;
//alert(value_s.indexOf(three_digits_separator) + " - " + dposG);

    if (dposP < dposG) dposP = dposG; // dposP holds the position of decimal point. (if it is decimal point...)
    result_s = value_s.substring(0, dposP).replace(decimal_separator, "").replace(decimal_separator, "").replace(decimal_separator, "").replace(decimal_separator, "");
    result_s = result_s.replace(three_digits_separator, "").replace(three_digits_separator, "").replace(three_digits_separator, "").replace(three_digits_separator, "");
    if (dposP < value_s.length)
        result_s = result_s + "." + value_s.substring(dposP + 1);

    return result_s;
}