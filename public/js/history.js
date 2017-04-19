$(function(){

    var data_variable = Handlebars.compile($("#orders-script-template").html());
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
                    data[i].doc1 = "upload";
                }
                else if(data[i].order_type == "RFW" || data[i].order_type == "RFW.") {
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
                else if(data[i].status == "Lk") {
                    data[i].class_status = "class=bgn_brown";
                    data[i].explained_status = "Order Locked"
                }
                else if(data[i].status == "Ch") {
                    data[i].class_status = "class=bgn_blue";
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

            $('#orders-script-position').html(data_variable(data));
        });
    }
    searchedOrders();



    var data_log_variable = Handlebars.compile($("#log-script-template").html());
    function searchedLog(){
        API.get_log_events().success(function(data){
            for (var i = 0; i < data.length; i++) {
                data[i].log_id = data[i].log_id;
                data[i].log_email = data[i].log_email;
                data[i].log_ip = data[i].log_ip;
                data[i].log_created = moment(data[i].log_created).format("YYYY-MM-DD, HH:mm:ss");
                data[i].log_type = data[i].log_type; //one of: login_partial_success, login_success, login_failure, logout, session_expired
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