/$(function(){

    var orders_info_template = Handlebars.compile($("#orders-info-template").html());

    function reload(){
 /*       API.orders_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].id = data[i].id;
                data[i].created = data[i].created;
                data[i].email = data[i].email;
                data[i].active = data[i].active;
                data[i].name = data[i].name;
                data[i].surname = data[i].surname;
                data[i].middle_name = data[i].middle_name;
                data[i].prefix = data[i].prefix;
            }

            $('#orders-info').html(orders_info_template(data));
        });
 */   }
    reload();
});
