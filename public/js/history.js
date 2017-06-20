$(function(){
    var initial_sum = 0;
    var initial_sum_hold = 0;
    var initial_sum_c = 0;
    var initial_sum_hold_c = 0;
    var final_sum = 0;
    var final_sum_hold = 0;
    var final_sum_c = 0;
    var final_sum_hold_c = 0;

    function show_balance() {
        API.balance().success(function (balances) {
            final_sum = parseFloat(balances[0].amount) - parseFloat(balances[0].hold);
            final_sum_hold = parseFloat(balances[0].hold);
            final_sum_c = parseFloat(balances[0].amount_c);
            final_sum_hold_c = parseFloat(balances[0].hold_c);
            initial_sum = final_sum;
            initial_sum_hold = final_sum_hold;
            initial_sum_c = final_sum_c;
            initial_sum_hold_c = final_sum_hold_c;
            searchedOrders();

/* This part of the code is not needed ###
            balances[0].available = Number(balances[0].amount) - Number(balances[0].hold);
            balances[0].amount = balances[0].amount;
            balances[0].hold = balances[0].hold;
            balances[0].amount_c = balances[0].amount_c;
            balances[0].hold_c = balances[0].hold_c;
            $("#available_fiat").html(NumberFormat(balances[0].available, 2));
            $("#hold_fiat").html(NumberFormat(balances[0].hold, 2));
            $('#hidden_fees_information').attr('wallet_available', balances[0].available);
            $('#hidden_fees_information').attr('wallet_onhold', balances[0].hold);
            $("#amount_crypto").html(NumberFormat(balances[0].amount_c, 2));
            $('#hidden_fees_information').attr('wallet_crypto', balances[0].amount_c);
            $('#hidden_fees_information').attr('wallet_crypto_onhold', balances[0].hold_c);
            $("#amount_total").html(NumberFormat(parseFloat(balances[0].amount) + parseFloat(balances[0].amount_c), 2));
            $('#hidden_fees_information').attr('wallet_total', parseFloat(balances[0].amount) + parseFloat(balances[0].amount_c));
*/
        });
    }
    show_balance();


    var data_variable = Handlebars.compile($("#orders-script-template").html());
    function searchedOrders(){
        var critical_value1 = $('#hidden_critical_value1').val();
        var critical_value2 = $('#hidden_critical_value2').val();
        API.orders_list('history', '').success(function(data){
            for (var i = 0; i < data.length; i++) {
                data[i].order_id = data[i].order_id;
                data[i].user_id = data[i].user_id;
                data[i].country_id = data[i].country_id;
                data[i].order_type = data[i].order_type;
                data[i].status = data[i].status;
                data[i].partner = data[i].partner;
                data[i].created = moment(data[i].created).format("YYYY-MM-DD HH:mm:ss");
                data[i].currency = data[i].currency;
                data[i].initial_value_s = NumberFormat(data[i].initial_value, 2);
                data[i].total_fee_s = NumberFormat(data[i].total_fee, 2);
                data[i].net_value_s = NumberFormat(data[i].net_value, 2);
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].bank = data[i].bank;
                data[i].agency = data[i].agency;
                data[i].account = data[i].account;
                if (data[i].status == 'OK' || data[i].status == 'Ch' || data[i].status == 'Rj')
                    data[i].closed = moment(data[i].closed).format("YYYY-MM-DD HH:mm:ss");
                else
                    data[i].closed = "";

                data[i].comment = data[i].comment;
                data[i].image_id = data[i].image_id;
                data[i].email = data[i].email;
                data[i].first_name = data[i].first_name;
                data[i].middle_name = data[i].middle_name;
                data[i].surname = data[i].surname;

                data[i].popupType="requestPopUp";
                data[i].popupHash = "#popupPic";
                if(data[i].order_type == "W" || data[i].order_type == "W.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = "withdraw";
                    data[i].popupType = "requestBrowse";
                    data[i].popupHash = "#popupBrowse";
                }
                else if(data[i].order_type == "RW" || data[i].order_type == "RW." ||data[i].order_type == "RFW" || data[i].order_type == "RFW.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = "receive + withdraw";
                    data[i].popupType = "requestBrowse";
                    data[i].popupHash = "#popupBrowse";
                }
                else if(data[i].order_type == "D") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = "deposit"
                }
                else if (data[i].order_type == "DS" || data[i].order_type == "DCS") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = "deposit + send"
                }
                else if(data[i].order_type == "V") {
                    data[i].class_type = "class=bgn_blue";
                    data[i].explained_type = "document verification"
                }
                else
                    data[i].class_type = "class=center_bold";

                if(data[i].initial_value >= critical_value1)
                    data[i].class_value = "class=important";
                if(data[i].initial_value >= critical_value2)
                    data[i].class_value = "class=smallfail";


                if(data[i].status == "Op") {
                    data[i].class_status = "class=bgn_yellow";  //Bootstrap line 2844
                    data[i].explained_status = Messages('directpay.legend.openorder')
                }
                else if(data[i].status == "Lk") {
                    data[i].class_status = "class=bgn_brown";
                    data[i].explained_status = Messages('directpay.legend.orderlocked')
                }
                else if(data[i].status == "Ch") {
                    data[i].class_status = "class=bgn_blue";
                    data[i].explained_status = Messages('directpay.legend.executionmodified')
                }
                else if(data[i].status == "Rj") {
                    data[i].class_status = "class=bgn_red";
                    data[i].explained_status = Messages('directpay.legend.orderrejected')
                }
                else if(data[i].status == "OK") {
                    data[i].class_status = "class=bgn_green";
                    data[i].explained_status = Messages('directpay.legend.orderexecuted')
                }
                else if(data[i].status == "S") {
                    data[i].class_status = "class=center_bold";
                    data[i].explained_status = Messages('directpay.legend.sendingtopartner')
                }
                else
                    data[i].class_status = "class=center_bold";


                if(data[i].status == "Ch")
                    data[i].class_value = "class=bigfail";
                else if(data[i].order_type == "V") {
                    data[i].initial_value = "";
                    data[i].currency = "";
                    data[i].net_value = "";
                }

                data[i].initial_sum = NumberFormat(initial_sum, 2);
                data[i].initial_sum_hold = NumberFormat(initial_sum_hold, 2);
                data[i].initial_sum_c = NumberFormat(initial_sum_c, 2);
                data[i].initial_sum_available_c = NumberFormat(initial_sum_c - initial_sum_hold_c, 2);
                data[i].initial_sum_hold_c = NumberFormat(initial_sum_hold_c, 2);

//alert(initial_sum);
// ### this code still being developped
                if (data[i].order_type == "D") {
                    if (data[i].status == "Op")
                        initial_sum_hold -= parseFloat(data[i].initial_value);
                    else if(data[i].status == "OK" || data[i].status == "Ch")
                        initial_sum -= parseFloat(data[i].net_value) - parseFloat(data[i].total_fee);
                    else //  || data[i].status == "Rj")
                        initial_sum += 0;
                } else if (data[i].order_type == "DS" || data[i].order_type == "DCS") { //### need verification
                    if (data[i].status == "Op")
                        initial_sum_hold -= parseFloat(data[i].initial_value);
                    else if (data[i].order_type == "DS" && data[i].status == "S") {
                        initial_sum_hold -= parseFloat(data[i].net_value); //### need verification
                    } else if (data[i].order_type == "DCS" && data[i].status == "S") {
                        initial_sum_c -= parseFloat(data[i].net_value);
                    } else // if(data[i].status == "OK" || data[i].status == "Ch" || data[i].status == "Rj")
                        initial_sum -= parseFloat(data[i].net_value);
                } else if (data[i].order_type == "W" || data[i].order_type == "W.") {
                    if (data[i].status == "Op" || data[i].status == "Lk") {
                        initial_sum_hold -= parseFloat(data[i].initial_value) + parseFloat(data[i].total_fee);
                        initial_sum += parseFloat(data[i].initial_value) + parseFloat(data[i].total_fee);
                    } else if(data[i].status == "OK" || data[i].status == "Ch")
                        initial_sum += parseFloat(data[i].net_value) + parseFloat(data[i].total_fee);
                    else //  || data[i].status == "Rj")
                        initial_sum += 0;
                } else if (data[i].order_type == "RW" || data[i].order_type == "RW." ) {  //### need verification
                    initial_sum += parseFloat(data[i].initial_value);
                } else if (data[i].order_type == "RFW" || data[i].order_type == "RFW." ) {  //### need verification
                    initial_sum += parseFloat(data[i].initial_value);
                } else if (data[i].order_type == "C") {
                    initial_sum += parseFloat(data[i].initial_value);
                    initial_sum_c -= parseFloat(data[i].initial_value);
                } else if (data[i].order_type == "F") {
                    initial_sum -= parseFloat(data[i].initial_value) - parseFloat(data[i].total_fee);
                    initial_sum_c += parseFloat(data[i].initial_value);
                }
//alert(initial_sum);
            }

            $('#orders-script-position').html(data_variable(data));
            showSum();
        });
    }


    function showSum() {
        $('#initial_sum').html(NumberFormat(initial_sum, 2));
        $('#initial_sum_hold').html(NumberFormat(initial_sum_hold, 2));
        $('#initial_sum_c').html(NumberFormat(initial_sum_c, 2));
        $('#initial_sum_hold_c').html(NumberFormat(initial_sum_hold_c, 2));
        $('#final_sum').html(NumberFormat(final_sum, 2));
        $('#final_sum_hold').html(NumberFormat(final_sum_hold, 2));
        $('#final_sum_c').html(NumberFormat(final_sum_c, 2));
        $('#final_sum_hold_c').html(NumberFormat(final_sum_hold_c, 2));

        $('#final_sum').attr('title', (Messages("terminology.wallet.available") + " = " + NumberFormat(final_sum, 2) + ", " + Messages("terminology.wallet.onhold") + " = " + NumberFormat(final_sum_hold, 2)));
        $('#final_sum_c').attr('title', (Messages("terminology.wallet.available") + " = " + NumberFormat(final_sum_c, 2) + ", " + Messages("terminology.wallet.onhold") + " = " + NumberFormat(final_sum_hold_c, 2)));
        $('#initial_sum').attr('title', (Messages("terminology.wallet.available") + " = " + NumberFormat(initial_sum, 2) + ", " + Messages("terminology.wallet.onhold") + " = " + NumberFormat(initial_sum_hold, 2)));
        $('#initial_sum_c').attr('title', (Messages("terminology.wallet.available") + " = " + NumberFormat(initial_sum_c, 2) + ", " + Messages("terminology.wallet.onhold") + " = " + NumberFormat(initial_sum_hold_c, 2)));
    }

    var data_log_variable = Handlebars.compile($("#log-script-template").html());
    function searchedLog(){
        API.get_log_events().success(function(data){
            for (var i = 0; i < data.length; i++) {
                data[i].id = data[i].id;
                data[i].email = data[i].email;
                data[i].ip = data[i].ip;
                data[i].created = moment(data[i].created).format("YYYY-MM-DD, HH:mm:ss");
                data[i].type = data[i].type; //one of: login_partial_success, login_success, login_failure, logout, session_expired
            }

            $('#log-script-position').html(data_log_variable(data));
        });
    }
    searchedLog();


    $('#requestPopupDetails').live('click', function() {
        $('#the_picture')[0].attributes[0].nodeValue = "/images/" + $(this).attr('image_id');
        function do_transfer(e) { e.preventDefault() }
        transfer_details ($(this).attr('order_id'), $(this).attr('user'), $(this).attr('email'), $(this).attr('order_type'), $(this).attr('doc'), '');
    });


    $(document).ready(function () {
        $( window ).resize(function() {
            resizeDiv();
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
});



function transfer_details (order_id, user_name, user_email, order_type, doc, attr4) {
    $('#hidden_order_id').val(order_id);
    $('#popUpPictureInfo1').html(user_name);
    $('#popUpPictureInfo2').html(user_email);
    if (doc == "1") $('#popUpPictureTitle').html($('#popUpPictureDoc1').val());
    if (doc == "2") $('#popUpPictureTitle').html($('#popUpPictureDoc2').val());
    if (doc == "3") $('#popUpPictureTitle').html($('#popUpPictureDoc3').val());
    if (doc == "4") $('#popUpPictureTitle').html($('#popUpPictureDoc4').val());
    if (doc == "5") $('#popUpPictureTitle').html($('#popUpPictureDoc5').val());
    $('#popUpPictureInfo3').html(doc);
    $('#popUpPictureInfo4').html(attr4);
    $('#popUpNet_value').attr('value', $('#net_value' + order_id).val());
    $('#popUpComment').attr('value', $('#comment' + order_id).val());
    $('#btnApprove').attr('order_type', order_type);
    $('#btnApprove').attr('order_id', order_id);
    $('#btnReject').attr('order_type', order_type);
    $('#btnReject').attr('order_id', order_id);
    resizeDiv()
}
