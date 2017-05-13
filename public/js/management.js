$(function(){
    var data_variable = Handlebars.compile($("#script-template").html());

    function show_management_info(){
        API.management_data().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].country_code = data[i].country_code;
                data[i].number_users = data[i].number_users;
                data[i].fiat_funds = data[i].fiat_funds;
                data[i].crypto_funds = data[i].crypto_funds;
                data[i].partners_balance = data[i].partners_balance;
                data[i].number_pending_orders = data[i].number_pending_orders;
            }

            $('#script-position').html(data_variable(data));
        });
    }
    show_management_info();


});
// one sample of IBAN code CH9300762011623852957


