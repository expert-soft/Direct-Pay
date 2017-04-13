$(function() {
    var template = Handlebars.compile($("#balance-template").html());
    function show_balance() {
        API.balance().success(function (balances) {
            balances[0].available = Number(balances[0].amount) - Number(balances[0].hold);
            balances[0].amount = balances[0].amount;
            balances[0].hold = balances[0].hold;
            balances[0].amount_c = balances[0].amount_c;
            balances[0].hold_c = balances[0].hold_c;

            $("#available_fiat").html(parseFloat(balances[0].available).toFixed(2));
            $("#hold_fiat").html(parseFloat(balances[0].hold).toFixed(2));
            $('#hidden_fees_information').attr('wallet_available', balances[0].available);
            $('#hidden_fees_information').attr('wallet_onhold', balances[0].hold);
            $("#amount_crypto").html(parseFloat(balances[0].amount_c).toFixed(2));
            $('#hidden_fees_information').attr('wallet_crypto', balances[0].amount_c);

            $("#amount_total").html((parseFloat(balances[0].amount) + parseFloat(balances[0].amount_c)).toFixed(2));
            $('#hidden_fees_information').attr('wallet_total', parseFloat(balances[0].amount) + parseFloat(balances[0].amount_c));

            $('#balance').html(template(balances));

            fillMessages();  // due to assyncronous threads of API, need to run fillMessages after wallet completelly filled
        });
    }

    show_balance();
});