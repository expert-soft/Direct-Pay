$(function(){

    var orders_info_template = Handlebars.compile($("#orders-info-template").html());

    function reload(){
        API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].order_id = data[i].order_id;
                data[i].user_id = data[i].user_id;
                data[i].country_id = data[i].country_id;
                data[i].order_type = data[i].order_type;
                data[i].status = data[i].status;
                data[i].partner = data[i].partner;
                data[i].created = data[i].created;
                data[i].currency = data[i].currency;
                data[i].initial_value = data[i].initial_value;
                data[i].total_fee = data[i].total_fee;
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].bank = data[i].bank;
                data[i].agency = data[i].agency;
                data[i].account = data[i].account;
                data[i].closed = data[i].closed;
                data[i].closed_by = data[i].closed_by;
                data[i].closed_value = data[i].closed_value;
                data[i].comment = data[i].comment;
                data[i].key1 = data[i].key1;
                data[i].key2 = data[i].key2;
            }
            $('#orders-info').html(orders_info_template(data));
        });
    }
    reload();
});
