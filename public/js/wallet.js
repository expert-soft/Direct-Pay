$(function() {
    var template = Handlebars.compile($("#balance-template").html());
    function show_balance() {
        sum_money = 0;
        API.balance().success(function (balances) {
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = Number(balances[i].amount) - Number(balances[i].hold);
                balances[i].amount = balances[i].amount;
                balances[i].hold = balances[i].hold;

                if (balances[i].is_fiat == true) {
                    $("#available_fiat").html(parseFloat(balances[i].available).toFixed(2));
                    $("#hold_fiat").html(parseFloat(balances[i].hold).toFixed(2));
                    $('#hidden_fees_information').attr('wallet_available', balances[i].available);
                    $('#hidden_fees_information').attr('wallet_onhold', balances[i].hold);
                    sum_money += parseFloat(balances[i].amount);
                } else {
                    $("#amount_crypto").html(parseFloat(balances[i].amount).toFixed(2));
                    $('#hidden_fees_information').attr('wallet_crypto', balances[i].amount);
                    sum_money += parseFloat(balances[i].amount);
                }
                $("#amount_total").html((sum_money).toFixed(2));
                $('#hidden_fees_information').attr('wallet_total', sum_money);
            }
            $('#balance').html(template(balances));
        });
    }

    show_balance();
});

$(function(){
    $(document).ready(function () {
        fillMessages();
    });
});