$(function(){


    var orders_open_template = Handlebars.compile($("#orders-open-template").html());
    function openOrders(){
        var critical_value1 = $('#hidden_critical_value1').val();
        var critical_value2 = $('#hidden_critical_value2').val();
        API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].order_id = data[i].order_id;
                data[i].user_id = data[i].user_id;
                data[i].country_id = data[i].country_id;
                data[i].order_type = data[i].order_type;
                data[i].status = data[i].status;
                data[i].partner = data[i].partner;
                data[i].created = moment(data[i].created).format("YYYY-MM-DD HH:mm:ss");
                data[i].currency = data[i].currency;
                data[i].initial_value = data[i].initial_value;
                data[i].total_fee = data[i].total_fee;
                data[i].net_value = data[i].net_value;
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].bank = data[i].bank;
                data[i].agency = data[i].agency;
                data[i].account = data[i].account;
                data[i].comment = data[i].comment;
                data[i].email = data[i].email;
                data[i].first_name = data[i].first_name;
                data[i].middle_name = data[i].middle_name;
                data[i].surname = data[i].surname;

                data[i].popupType="requestPopUp";
                if(data[i].order_type == "W" || data[i].order_type == "W.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = "withdraw";
                    data[i].popupType = "requestBrowser";
                    data[i].doc1 = "upload";
                }
                else if(data[i].order_type == "RFW" || data[i].order_type == "RFW.") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = "receive + withdraw";
                    data[i].popupType = "requestBrowser";
                    data[i].doc1 = "upload";
                }
                else if(data[i].order_type == "D") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = "deposit"
                }
                else if (data[i].order_type == "DCS") {
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

            }
            $('#orders-open').html(orders_open_template(data));
        });
    }
    openOrders();


    var orders_search_template = Handlebars.compile($("#orders-search-template").html());
    function searchedOrders(){
        var critical_value1 = $('#hidden_critical_value1').val();
        var critical_value2 = $('#hidden_critical_value2').val();
        API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].order_id = data[i].order_id;
                data[i].user_id = data[i].user_id;
                data[i].country_id = data[i].country_id;
                data[i].order_type = data[i].order_type;
                data[i].status = data[i].status;
                data[i].partner = data[i].partner;
                data[i].created = moment(data[i].created).format("YYYY-MM-DD HH:mm:ss");
                data[i].currency = data[i].currency;
                data[i].initial_value = data[i].initial_value;
                data[i].total_fee = data[i].total_fee;
                data[i].net_value = data[i].net_value;
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].bank = data[i].bank;
                data[i].agency = data[i].agency;
                data[i].account = data[i].account;
                data[i].closed = data[i].closed;
                data[i].comment = data[i].comment;
                data[i].email = data[i].email;
                data[i].first_name = data[i].first_name;
                data[i].middle_name = data[i].middle_name;
                data[i].surname = data[i].surname;

                data[i].popupType="requestPopUp";
                data[i].popupHash = "#popupPic";
                if(data[i].order_type == "W") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = "withdraw";
                    data[i].popupType = "requestBrowse";
                    data[i].popupHash = "#popupBrowse";
                    data[i].doc1 = "upload";
                }
                else if(data[i].order_type == "RFW") {
                    data[i].class_type = "class=bgn_yellow";
                    data[i].explained_type = "receive + withdraw";
                    data[i].popupType = "requestBrowse";
                    data[i].popupHash = "#popupBrowse";
                    data[i].doc1 = "upload";
                }
                else if(data[i].order_type == "D") {
                    data[i].class_type = "class=bgn_green";
                    data[i].explained_type = "deposit"
                }
                else if (data[i].order_type == "DCS") {
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
                    data[i].explained_status = "Open order"
                }
                else if(data[i].status == "Ch") {
                    data[i].class_status = "class=bgn_brown";
                    data[i].explained_status = "Order Changed"
                }
                else if(data[i].status == "Rj") {
                    data[i].class_status = "class=bgn_red";
                    data[i].explained_status = "Order Rejected"
                }
                else if(data[i].status == "OK") {
                    data[i].class_status = "class=bgn_green";
                    data[i].explained_status = "Executed order"
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
            }
            $('#orders-search').html(orders_search_template(data));
        });
    }
    searchedOrders();


    $(document).ready(function () {
        $('.requestPopUp').live('click', function() {
            if($(this).attr('type') == "V")
                $('#image-holder').attr('src', '/assets/img/brUserDocs/' + $(this).attr('name'));
            else
                $('#image-holder').attr('src', '/assets/img/brReceipts/' + $(this).attr('name'));
            var id_value = $(this).attr('id');
            if ($(this).attr('doc') == "1") $('#popUpPictureTitle').html($('#popUpPictureDoc1').val());
            if ($(this).attr('doc') == "2") $('#popUpPictureTitle').html($('#popUpPictureDoc2').val());
            if ($(this).attr('doc') == "3") $('#popUpPictureTitle').html($('#popUpPictureDoc3').val());
            if ($(this).attr('doc') == "4") $('#popUpPictureTitle').html($('#popUpPictureDoc4').val());
            if ($(this).attr('doc') == "5") $('#popUpPictureTitle').html($('#popUpPictureDoc5').val());
            $('#popUpPictureInfo1').html($(this).attr('user'));
            $('#popUpPictureInfo2').html($(this).attr('email'));
        });


/*
Updating dB (example):
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
Op - Lock - C or Rj - S - OK or cancel S that goes to Changed - revision would require S and C undo if possible and than Lock
Conversion to Crypto
Op - Ok (Ch possible?)
Send
Op - Ok or Rj
Withdraw
Op - Lock - Ok, Ch or Rj - revision brings it to Lock
RFW
Op - F or Rj - W - Lock - Ok, Ch or Rj - revision brings it to Lock
Receive
Op - OK or Rj
Convert to Fiat
Op - OK (Ch possible?)

*/
    // Approving order
        $('.triggers_Approval').live('click', function() {
            var order_id = parseFloat($(this).attr('order_id').replace(",", "."));
            var order_type = $(this).attr('order_type');
            var status = $('#net_value' + order_id).val();
            status = "OK";
            var net_value = parseFloat($('#net_value' + order_id).val());
            var comment = $('#comment' + order_id).val();
//            alert(status + net_value + comment);
            API.update_order(order_id, order_type, status, net_value, comment).success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.orderupdatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            })
        });

        $('.triggers_Rejection').live('click', function() {
            if ($(this).attr('type') == "V")
                $('#image-holder').attr('src', '/assets/img/brUserDocs/' + $(this).attr('name'));
            alert("Rj");
        });

        $('.triggers_Revision').live('click', function() {
            if ($(this).attr('type') == "V")
                $('#image-holder').attr('src', '/assets/img/brUserDocs/' + $(this).attr('name'));
            alert("Rv");
        });

    });

});
