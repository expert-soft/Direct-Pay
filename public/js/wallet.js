$(function() {
    var template = Handlebars.compile($("#balance-template").html());

    function show_balance() {
        sum_money = 0;
        API.balance().success(function (balances) {
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = zerosToSpaces(Number(balances[i].amount) - Number(balances[i].hold));
                balances[i].amount = zerosToSpaces(balances[i].amount);
                balances[i].hold = zerosToSpaces(balances[i].hold);

                if (balances[i].is_fiat == true) {
                    $("#available_fiat").html(parseFloat(balances[i].available).toFixed(2));
                    $("#hold_fiat").html(parseFloat(balances[i].hold).toFixed(2));
                    $("#hidden_wallet_available").val(balances[i].available);
                    $("#hidden_wallet_onhold").val(balances[i].hold);
                    sum_money += parseFloat(balances[i].amount);
                } else {
                    $("#amount_crypto").html(parseFloat(balances[i].amount).toFixed(2));
                    $("#hidden_wallet_crypto").val(balances[i].amount);
                    sum_money += parseFloat(balances[i].amount);
                }
                $("#amount_total").html((sum_money).toFixed(2));
                $("#hidden_wallet_total").val(sum_money);
            }
            $('#balance').html(template(balances));
        });
    }
    show_balance();
})