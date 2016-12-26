$(function(){

    var orders_open_template = Handlebars.compile($("#orders-open-template").html());

    function openOrders(){
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
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].bank = data[i].bank;
                data[i].agency = data[i].agency;
                data[i].account = data[i].account;
                data[i].closed_value = data[i].closed_value;
                data[i].comment = data[i].comment;
                data[i].email = data[i].email;
                data[i].first_name = data[i].first_name;
                data[i].middle_name = data[i].middle_name;
                data[i].surname = data[i].surname;

                if(data[i].order_type == "W" || data[i].order_type == "RFW")
                    data[i].class_type = "class=bgn_yellow";
                else if(data[i].order_type == "D" || data[i].order_type == "DCS")
                    data[i].class_type = "class=bgn_green";
                else if(data[i].order_type == "V")
                    data[i].class_type = "class=bgn_blue";
                else
                    data[i].class_type = "class=center_bold";

                data[i].class_value = "class=right_bold";

            }
            $('#orders-open').html(orders_open_template(data));
        });
    }
    openOrders();


    var orders_search_template = Handlebars.compile($("#orders-search-template").html());

    function searchedOrders(){
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
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].bank = data[i].bank;
                data[i].agency = data[i].agency;
                data[i].account = data[i].account;
                data[i].closed = data[i].closed;
                data[i].closed_value = data[i].closed_value;
                data[i].comment = data[i].comment;
                data[i].email = data[i].email;
                data[i].first_name = data[i].first_name;
                data[i].middle_name = data[i].middle_name;
                data[i].surname = data[i].surname;

                if(data[i].order_type == "W" || data[i].order_type == "RFW")
                    data[i].class_type = "class=bgn_yellow";
                else if(data[i].order_type == "D" || data[i].order_type == "DCS")
                    data[i].class_type = "class=bgn_green";
                else if(data[i].order_type == "V")
                    data[i].class_type = "class=bgn_blue";
                else
                    data[i].class_type = "class=center_bold";

                if(data[i].status == "Op")
                    data[i].class_status = "class=bgn_yellow"; //Bootstrap line 2844
                else if(data[i].status == "Ch")
                    data[i].class_status = "class=bgn_brown";
                else if(data[i].status == "Rj")
                    data[i].class_status = "class=bgn_red";
                else if(data[i].status == "OK")
                    data[i].class_status = "class=bgn_green";
                else
                    data[i].class_status = "class=center_bold";

/*                if(data[i].value >= globals.country_critical_value1)
                    data[i].class_value = "class=important";
                if(data[i].value >= globals.country_critical_value2)
                    data[i].class_value = "class=smallfail";
*/
                if(data[i].status == "Ch")
                    data[i].class_value = "class=bigfail";

                else if(data[i].order_type == "V") {
                    data[i].initial_value = "";
                    data[i].currency = "";
                    data[i].closed_value = "";
                }
            }
            $('#orders-search').html(orders_search_template(data));
        });
    }
    searchedOrders();
});
