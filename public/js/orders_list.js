/$(function(){

    var orders_info_template = Handlebars.compile($("#orders-info-template").html());

    function reload(){
       API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].order_id = data[i].order_id;
                data[i].user_id = data[i].user_id;
                data[i].country_id = data[i].country_id;
                data[i].user_email = data[i].user_email;
                data[i].type = data[i].type;
                data[i].creation = data[i].creation;

            }
 //          partner	valueReq	timeReq	feeRate	feeFiat	currency	name	doc1	doc2	bank	agency	account	Status	timeExec	adminExec	valueExec	key1	key2	Comment 1

            $('#orders-info').html(orders_info_template(data));
        });
    }
    reload();
});
