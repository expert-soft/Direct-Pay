$(function(){
    var template = Handlebars.compile($("#balance-template").html());

    function show_balance(){
        sum_money = 0;
        API.balance().success(function(balances){
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = zerosToSpaces(Number(balances[i].amount) - Number(balances[i].hold));
                balances[i].amount = zerosToSpaces(balances[i].amount);
                balances[i].hold = zerosToSpaces(balances[i].hold);

                if(balances[i].is_fiat == "true") {
                    $("#available_fiat").html(balances[i].available);
                    $("#hold_fiat").html(balances[i].hold);
                    sum_money += parseFloat(balances[i].amount);
                } else {
                    $("#amount_crypto").html(balances[i].amount);
                    sum_money += parseFloat(balances[i].amount);
                }
                $("#amount_total").html(sum_money);
            }
            $('#balance').html(template(balances));
        });
    }
    show_balance();

});