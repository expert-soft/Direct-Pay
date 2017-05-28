$(function(){


    var orders_open_template = Handlebars.compile($("#orders-open-template").html());
    function openOrders(){
        var critical_value1 = $('#hidden_critical_value1').val();
        var critical_value2 = $('#hidden_critical_value2').val();
        var temp_s = "";
        API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {
                temp_s = "";
                data[i].class_value = "";
                if(data[i].initial_value >= critical_value1)
                    data[i].class_value = "class=important";
                if(data[i].initial_value >= critical_value2)
                    data[i].class_value = "class=smallfail";
                data[i].created = moment(data[i].created).format("YYYY-MM-DD HH:mm:ss");
                data[i].initial_value_s = NumberFormat(data[i].initial_value, 2);
                data[i].total_fee_s = NumberFormat(data[i].total_fee, 2);
                if (data[i].bank != "") { temp_s = data[i].bank }
                if (data[i].agency != "") { if (temp_s != "") temp_s += ", "; temp_s += data[i].agency }
                if (data[i].account != "") { if (temp_s != "") temp_s += ", "; temp_s += data[i].account }
                data[i].bank_info = temp_s;

                data[i].popupType="requestPopUp";
                data[i].input_visible = "inline";
                data[i].doc_type = "";
                if (data[i].net_value == 0 && data[i].status != "Rj") {
                    data[i].net_value = NumberFormat(data[i].initial_value, 2);
                } else data[i].net_value = NumberFormat(data[i].initial_value, 2);
                data[i].popupButtonOKDisplay = "inline";
                data[i].popupButtonRjDisplay = "inline";
                data[i].popupButtonLockDisplay = "none";
                data[i].popupButtonUploadDisplay = "none";
                data[i].doc1_text_visible = "inline";
                data[i].doc1_i_visible = "none";
//                data[i].doc1 = "";
                if(data[i].order_type == "W" || data[i].order_type == "W.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = $('#hidden_general_messages').attr('withdraw');
                    data[i].popupType = "requestBrowser";
                    data[i].doc1_text_visible = "none";
                    data[i].doc1_i_visible = "inline";
                    data[i].doc1 = $('#hidden_general_messages').attr('uploadwithdrawreceipt');
                    if (data[i].status == "Op") {
                        data[i].popupButtonOKDisplay = "none";
                        data[i].popupButtonLockDisplay = "inline";
                        data[i].doc1_i_visible = "none";
                    } else if (data[i].status == "Lk") {
                        data[i].popupButtonOKDisplay = "none";
                        data[i].popupButtonUploadDisplay = "inline";
                    }
                }
                else if(data[i].order_type == "RFW" || data[i].order_type == "RFW.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = $('#hidden_general_messages').attr('receiveandwithdraw');
                    data[i].popupType = "requestBrowser";
                    data[i].doc1_text_visible = "none";
                    data[i].doc1_i_visible = "inline";
                    data[i].doc1 = $('#hidden_general_messages').attr('uploadwithdrawreceipt');
                    if (data[i].status == "Op") {
                        data[i].popupButtonOKDisplay = "none";
                        data[i].popupButtonLockDisplay = "inline";
                        data[i].doc1_i_visible = "none";
                    } else if (data[i].status == "Lk") {
                        data[i].popupButtonOKDisplay = "none";
                        data[i].popupButtonUploadDisplay = "inline";
                    }
                }
                else if(data[i].order_type == "D") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = $('#hidden_general_messages').attr('deposit')
                }
                else if (data[i].order_type == "DCS") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = $('#hidden_general_messages').attr('depositandsend')
                    data[i].bank_info = data[i].partner + ", " + data[i].account;
                }
                else if(data[i].order_type == "V") {
                    data[i].class_type = "class=bgn_blue";
                    data[i].explained_type = $('#hidden_general_messages').attr('documentverification');
                    data[i].input_visible = "none";
                    data[i].initial_value = "";
                    data[i].total_fee = "";
                    data[i].currency = "";
                    data[i].net_value = "";
                    data[i].doc_type = data[i].partner;
                    if (data[i].doc_type == "doc1") data[i].bank_info = $('#popUpPictureDoc1').val(); else
                    if (data[i].doc_type == "doc2") data[i].bank_info = $('#popUpPictureDoc2').val(); else
                    if (data[i].doc_type == "doc3") data[i].bank_info = $('#popUpPictureDoc3').val(); else
                    if (data[i].doc_type == "doc4") data[i].bank_info = $('#popUpPictureDoc4').val(); else
                    if (data[i].doc_type == "doc5") data[i].bank_info = $('#popUpPictureDoc5').val();
                }
                else
                    data[i].class_type = "class=center_bold";
            }
            $('#orders-open').html(orders_open_template(data));

            $('#requestPopupDetails').live('click', function() {
                $('#the_picture')[0].attributes[0].nodeValue = "/images/" + $(this).attr('image_id');
                function do_transfer(e) { e.preventDefault() }
                transfer_details($(this).attr('popup_type'), $(this).attr('order_id'), $(this).attr('user'), $(this).attr('email'), $(this).attr('order_type'), $(this).attr('doc1'), $(this).attr('doc_type'));
            });
        });
    }
    openOrders();

    var orders_search_template = Handlebars.compile($("#orders-search-template").html());
    function searchedOrders(){
        var critical_value1 = $('#hidden_critical_value1').val();
        var critical_value2 = $('#hidden_critical_value2').val();
        API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].created = moment(data[i].created).format("YYYY-MM-DD HH:mm:ss");

                data[i].class_value = "";
                if(data[i].initial_value >= critical_value1)
                    data[i].class_value = "class=important";
                if(data[i].initial_value >= critical_value2)
                    data[i].class_value = "class=smallfail";
                data[i].initial_value = NumberFormat(data[i].initial_value, 2);
                data[i].total_fee = NumberFormat(data[i].total_fee, 2);
                data[i].net_value = NumberFormat(data[i].net_value, 2);

                data[i].popupType="requestPopUp";
                data[i].popupHash = "#popupPic";
//                data[i].doc1_text_visible = "inline";
//                data[i].doc1_i_visible = "none";
                data[i].doc1 = "";
                if(data[i].order_type == "W" || data[i].order_type == "W.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = $('#hidden_general_messages').attr('withdraw');
                    data[i].popupType = "requestBrowse";
                    data[i].popupHash = "#popupBrowse";
//                    data[i].doc1_text_visible = "none";
//                    data[i].doc1_i_visible = "inline";
                }
                else if(data[i].order_type == "RFW" || data[i].order_type == "RFW.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = $('#hidden_general_messages').attr('receiveandwithdraw');
                    data[i].popupType = "requestBrowse";
                    data[i].popupHash = "#popupBrowse";
//                    data[i].doc1_text_visible = "none";
//                    data[i].doc1_i_visible = "inline";
                }
                else if(data[i].order_type == "D") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = $('#hidden_general_messages').attr('deposit')
                }
                else if (data[i].order_type == "DCS") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = $('#hidden_general_messages').attr('depositandsend')
                }
                else if(data[i].order_type == "V") {
                    data[i].class_type = "class=bgn_blue";
                    data[i].explained_type = $('#hidden_general_messages').attr('documentverification')
                }
                else
                    data[i].class_type = "class=center_bold";

                if(data[i].status == "Op") {
                    data[i].class_status = "class=bgn_yellow";  //Bootstrap line 2844
                    data[i].explained_status = $('#hidden_general_messages').attr('openorder')
                }
                else if(data[i].status == "Lk") {
                    data[i].class_status = "class=bgn_brown";
                    data[i].explained_status = $('#hidden_general_messages').attr('orderlocked')
                }
                else if(data[i].status == "Ch") {
                    data[i].class_status = "class=bgn_blue";
                    data[i].explained_status = $('#hidden_general_messages').attr('executionmodified')
                }
                else if(data[i].status == "Rj") {
                    data[i].class_status = "class=bgn_red";
                    data[i].explained_status = $('#hidden_general_messages').attr('orderrejected')
                }
                else if(data[i].status == "OK") {
                    data[i].class_status = "class=bgn_green";
                    data[i].explained_status = $('#hidden_general_messages').attr('orderexecuted')
                }
                else
                    data[i].class_status = "class=center_bold";


                if(data[i].status == "Ch")
                    data[i].class_value = "class=smallfail";
                else if(data[i].order_type == "V") {
                    data[i].initial_value = "";
                    data[i].currency = "";
                    data[i].net_value = "";
                }
            }
            $('#orders-search').html(orders_search_template(data));
        });
    }
    searchedOrders();



    $(document).ready(function () {
        $( window ).resize(function() {
            resizeDiv()
        });
        resizeDiv();

        $('.triggers_details').live('change', function() {
            $('#net_value' + $('#hidden_order_id').val()).val($('#popUpNet_value').val());
            $('#comment' + $('#hidden_order_id').val()).val($('#popUpComment').val());
         });

/*Updating dB (example):
dashboard.scala - 81
dashboard.js - 20
Api.js - 123
routes - 48
APIv1.scala - 276
UserModel - 288
4.sql - 43


Verification
Op - OK or Rj - revision brings it to Op
Deposit
Op - Lock - OK, Ch or Rj - revision brings it to Lock
DCS
Op - Lock - Ch or Rj - S - OK or cancel S that goes to Changed - revision would require S and C undo if possible and than Lock
Conversion to Crypto
Op - Ok
Send
Op - Ok or Rj
Withdraw
Op - Lock - Ok, Ch or Rj - revision brings it to Lock
RFW
Op - F or Rj - W - Lock - Ok, Ch or Rj - revision brings it to Lock
Receive
Op - OK or Rj
Convert to Fiat
Op - OK

*/
    // Approving order
        $('.triggers_Approval').live('click', function() {
            var order_id = parseInt($(this).attr('order_id'));
            var order_type = $(this).attr('order_type');
            var status = "OK";
            var initial_value = parseFloat($('#hidden_initial_value' + order_id).val());
            var net_value_s = $('#net_value' + order_id).val();
            var comment = $('#comment' + order_id).val();
            var is_lock_button = ($(this).attr('id') == "btnLock" + order_id)

            var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
            var applyAPI = true;
            if (decimal_separator == ",")
                net_value_s = net_value_s.replace(decimal_separator, ".");
            if ($.isNumeric(net_value_s) || order_type == "V") {
                var net_value = 0;
                if (order_type != "V")
                    net_value = parseFloat(net_value_s);
                if (net_value > 0 || (order_type != "D" && order_type != "DCS")) {
                    if(comment != "" || is_lock_button) {
                        if (Math.abs(initial_value - net_value) > parseFloat($('#hidden_fees_information').attr('country_minimum_difference')) && (order_type == "D" || order_type == "DCS" || order_type == "W" || order_type == "W." || order_type == "RFW" || order_type == "RFW.")) {
                            status = "Ch"; //approved or executed value is significantly different
                            applyAPI = false;
                            applyAPI = confirm(Messages('directpay.formvalidation.confirmnewvalue') + ' (' + NumberFormat(initial_value, 2) + ' -> ' + NumberFormat(net_value, 2) + ')');
                        }
//applyAPI = false; //### temporary (only testing)
                        if (applyAPI) {
                            if ($('#popupType').val() == "requestPopUp" || is_lock_button || $('#popupType').val() == "" || order_type == "V")
                                HideButtons(order_id, is_lock_button);
                            $('#popupDetails').css('opacity', 0); // this hides the modal
                            // calling API function:
                            var success_message_type = "success";
                            var success_message = "";
                            if (order_type == "V")
                                success_message = "messages.api.success.documentapproved";
                            else if (status != "Ch")
                                success_message = "messages.api.success.orderapproved";
                            else
                                success_message = "messages.api.success.orderapprovedwithchanges";
                            if (is_lock_button) {
                                success_message_type = "info";
                                success_message = "messages.api.success.orderlockedtobeprocessed";
                            }
                            API.update_order(order_id, order_type, status, net_value, comment).success(function () {
                                $.pnotify({
                                    title: Messages("messages.api.success"),
                                    text: Messages(success_message),
                                    styling: 'bootstrap',
                                    type: success_message_type,
                                    text_escape: true
                                });
                            })
                        }
                    } else {
                        event.preventDefault() ;
                        event.stopPropagation();
                        alert(Messages('directpay.formvalidation.commentcannotbeempty'));
                    }
                } else {
                    event.preventDefault() ;
                    event.stopPropagation();
                    alert(Messages('directpay.formvalidation.valuemustbegreaterthanzero'));
                }
            } else {
                event.preventDefault() ;
                event.stopPropagation();
                alert(Messages('directpay.formvalidation.valuemustbenumerical'));
            }
        });


        $('.triggers_Rejection').live('click', function() {
            if ($(this).attr('order_type') == "V")
                $('#image-holder').attr('src', '/assets/img/brUserDocs/' + $(this).attr('name'));
            var order_id = parseInt($(this).attr('order_id'));
            var order_type = $(this).attr('order_type');
            var status = "Rj";
            var initial_value = parseFloat($('#hidden_initial_value' + order_id).val());
            var comment = $('#comment' + order_id).val();
//comment = ""; //### temporary (only testing)
            if (comment != "") {
                HideButtons (order_id, false);
                $('#popupDetails').css('opacity', 0);
                if (order_type == "V") {
                    var success_message = "messages.api.success.documentrejected";
                    initial_value = 0;
                }
                else
                    var success_message = "messages.api.success.orderrejected";
                API.update_order(order_id, order_type, status, initial_value, comment).success(function () {
                    $.pnotify({
                        title: Messages("messages.api.success"),
                        text: Messages(success_message),
                        styling: 'bootstrap',
                        type: 'notice',
                        text_escape: true
                    });
                });
            } else
                alert(Messages('directpay.formvalidation.commentcannotbeempty'));
        });

        $('.triggers_Upload').live('click', function() {
            $('#the_picture')[0].attributes[0].nodeValue = "/images/" + $(this).attr('image_id');
            function do_transfer(e) { e.preventDefault() }
            transfer_details("requestBrowser", $(this).attr('order_id'), $(this).attr('user'), $(this).attr('email'), $(this).attr('order_type'), $(this).attr('doc1'), $(this).attr('doc_type'));
        });


        $('.triggers_Revision').live('click', function() {
            if ($(this).attr('order_type') == "V")
                $('#image-holder').attr('src', '/assets/img/brUserDocs/' + $(this).attr('name'));
            alert("### Revision");
        });


        $('#btnZoomIn').live('click', function() {
            $('#the_picture').width(parseInt($('#the_picture').width() * 1.5));
        });

        $('#btnZoomOut').live('click', function() {
            $('#the_picture').width(parseInt($('#the_picture').width() / 1.5));
        });
    });
});

function transfer_details (popup_type, order_id, user_name, user_email, order_type, doc, doc_type) {
    $('#hidden_order_id').val(order_id);
    $('#popUpPictureInfo1').html(user_name);
    $('#popUpPictureInfo2').html(user_email);
    if (doc_type == "doc1") $('#popUpPictureTitle').html($('#popUpPictureDoc1').val()); else
    if (doc_type == "doc2") $('#popUpPictureTitle').html($('#popUpPictureDoc2').val()); else
    if (doc_type == "doc3") $('#popUpPictureTitle').html($('#popUpPictureDoc3').val()); else
    if (doc_type == "doc4") $('#popUpPictureTitle').html($('#popUpPictureDoc4').val()); else
    if (doc_type == "doc5") $('#popUpPictureTitle').html($('#popUpPictureDoc5').val()); else
        $('#popUpPictureTitle').html($('#explained_type' + order_id).val());
    $('#popUpPictureInfo3').html(doc);
    $('#popUpPictureInfo4').html(doc_type);
    $('#popUpNet_value').attr('value', $('#net_value' + order_id).val());
    $('#popUpComment').attr('value', $('#comment' + order_id).val());
    $('#popUpNet_value').attr("disabled", $('#net_value' + order_id).attr("disabled"));
    $('#popUpComment').attr("disabled", $('#comment' + order_id).attr("disabled"));
    if(order_type == 'V') {
        $('#popUpMessageValue').css('display', 'none');
        $('#popUpNet_value').hide();
    } else {
        $('#popUpMessageValue').css('display', 'inline');
        $('#popUpNet_value').show();
    }
    if (popup_type == "requestPopUp") {
        $('#btnZoomIn').css('display', 'inline');
        $('#btnZoomOut').css('display', 'inline');
        $('#the_picture').css('display', 'inline');
        $('#popUpPictureInfo3').css('display', 'inline');
        $('#upload_picture_form').css('display', 'none');
        $('#popupType').val("requestPopUp");
        $('#btnApprove').attr('order_type', order_type);
        $('#btnApprove').attr('order_id', order_id);
        $('#btnReject').attr('order_type', order_type);
        $('#btnReject').attr('order_id', order_id);
        $('#btnApprove').css('display', $('#btnOK' + order_id).css('display'));
        $('#btnReject').css('display', $('#btnReject' + order_id).css('display'));
    } else { // "requestBrowser" (order = W, must have picture uploaded)
        $('#btnZoomIn').css('display', 'none');
        $('#btnZoomOut').css('display', 'none');
        $('#the_picture').css('display', 'none');
        $('#popUpPictureInfo3').css('display', 'none');
        $('#upload_picture_form').css('display', 'inline');
        $('#popupType').val("requestBrowser");
        $('#btnApprove').css('display', 'none');
        $('#btnReject').css('display', 'none');
        $('#btnApproveWithdraw').attr('order_type', order_type);
        $('#btnApproveWithdraw').attr('order_id', order_id);
        $('#btnRejectWithdraw').attr('order_type', order_type);
        $('#btnRejectWithdraw').attr('order_id', order_id);
        $('#uploadBtn1').val("");
        $('#uploadText1').html("");
    }
    resizeDiv()
}

function HideButtons (order_id, is_lock_button) {
    if (is_lock_button) {
        $('#btnLock' + order_id).hide();
        $('#btnUpload' + order_id).show();
    } else {
        $('#btnLock' + order_id).hide();
        $('#btnOK' + order_id).hide();
        $('#btnReject' + order_id).hide();
        $('#btnUpload' + order_id).hide();
        $('#net_value' + order_id).attr("disabled", "disabled");
        $('#comment' + order_id).attr("disabled", "disabled");
    }
}

//function insert_user_image(character varying, bytea) does not exist Dica: No function matches the given

$('#uploadBtn1').change(function() {
    $('#uploadFile1').val(this.value);
    $('#uploadText1').html(this.value);
    $('#uploadDiv1').addClass('btn-default');
    if (this.value == "")
        $('#uploadDiv1').addClass('btn-info');
    else
        $('#uploadDiv1').removeClass('btn-info');
});

// ###See application.scala - line 165 - need to treat exception
function fillInfoIntoFileObject() { $('#uploadBtn1').attr('name', $('#popUpNet_value').val() + "|" + $('#popUpComment').val() + "|" + pressedButton + "|" + $('#btnApproveWithdraw').attr('order_type') + "|" + $('#btnApproveWithdraw').attr('order_id')); }


var pressedButton = "";

// saving pictures
$('#btnApproveWithdraw').click(function() {
    var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
    var value_s = $('#popUpNet_value').val();
    var comment = $('#popUpComment').val();
    var initial_value = $('#hidden_initial_value' + $('#btnApproveWithdraw').attr('order_id')).val();
    var processed_value = 0.0;
    pressedButton = "Ch";
    if (decimal_separator == ",")
        value_s = value_s.replace(decimal_separator, ".");
    if ($.isNumeric(value_s)) {
        if (parseFloat(value_s) > 0) {
            processed_value = parseFloat(value_s);
            if($('#uploadText1').text() != "") {
                //(value_s + " <= " + parseFloat($('#hidden_fees_information').attr('wallet_available')) + " + " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto'))  + " - " +  parseFloat($('#hidden_fees_information').attr('wallet_crypto_onhold')) + " - " + parseFloat($('#total_send_fee').val()));
                if ($('#popUpComment').val() != "") {
                    if ((processed_value - initial_value) < parseFloat($('#hidden_fees_information').attr('country_minimum_difference')) && (initial_value - processed_value) < parseFloat($('#hidden_fees_information').attr('country_minimum_difference'))) pressedButton = "OK";
                    // form will be submitted to upload withdraw picture
                    fillInfoIntoFileObject();
                    $('form#upload_picture_form').submit();
                } else {
                    alert(Messages('directpay.formvalidation.commentcannotbeempty'));
                }
            } else {
                alert(Messages('directpay.formvalidation.youmustselectdepositfile'));
            }
        } else {
            alert(Messages('directpay.formvalidation.valuemustbegreaterthanzero'));
        }
    } else {
        alert(Messages('directpay.formvalidation.valuemustbenumerical'));
    }
});


$('#btnRejectWithdraw').click(function(){
    pressedButton = "Rj";
    var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
    var order_id = parseInt($(this).attr('order_id'));
    var order_type = $(this).attr('order_type');
    var status = "Rj";
    var initial_value = parseFloat($('#hidden_initial_value' + order_id).val());
    var value_s = $('#popUpNet_value').val();
    var comment = $('#popUpComment').val();

    if (decimal_separator == ",")
        value_s = value_s.replace(decimal_separator, ".");
    if ($.isNumeric(value_s)) {
        if (parseFloat(value_s) >= 0) {
            if (comment != "") {
                if($('#uploadText1').text() != "") {
                    // form will be submitted to upload withdraw picture
                    fillInfoIntoFileObject();
                    $('form#upload_picture_form').submit();
                } else { //update order without picture
                    HideButtons (order_id, false);
                    $('#popupDetails').css('opacity', 0);
                    API.update_order(order_id, order_type, status, initial_value, comment).success(function () {
                        $.pnotify({
                            title: Messages("messages.api.success"),
                            text: Messages("messages.api.success.orderrejected"),
                            styling: 'bootstrap',
                            type: 'notice',
                            text_escape: true
                        });
                    });
                }
            } else {
                alert(Messages('directpay.formvalidation.commentcannotbeempty'));
            }
        } else {
            alert(Messages('directpay.formvalidation.valuemustbegreaterthanzero'));
        }
    } else {
        alert(Messages('directpay.formvalidation.valuemustbenumerical'));
    }
});

function resizeDiv (){
    $('#popupInfoDiv').css('width', parseInt($( document ).width()*0.8));
    $('#popupInfoDiv').css('height', parseInt($( document ).height()*0.5));
}
