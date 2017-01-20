$(function(){
    var template = Handlebars.compile($("#balance-template").html());

    function show_balance(){
        sum_money = 0;
        API.balance().success(function(balances){
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = zerosToSpaces(Number(balances[i].amount) - Number(balances[i].hold));
                balances[i].amount = zerosToSpaces(balances[i].amount);
                balances[i].hold = zerosToSpaces(balances[i].hold);

                if(balances[i].is_fiat == true) {
                    $("#available_fiat").html(balances[i].available);
                    $("#hold_fiat").html(balances[i].hold);
                    $("#hidden_wallet_available").val(balances[i].available);
                    $("#hidden_wallet_onhold").val(balances[i].hold);
                    sum_money += parseFloat(balances[i].amount);
                } else {
                    $("#amount_crypto").html(balances[i].amount);
                    $("#hidden_wallet_crypto").val(balances[i].amount);
                    sum_money += parseFloat(balances[i].amount);
                }
                $("#amount_total").html(sum_money);
                $("#hidden_wallet_total").val(sum_money);
            }
            $('#balance').html(template(balances));
        });
    }
    show_balance();


    function show_fees(){
       /*   $("#available_fiat").html(balances[i].available);
        $("#hold_fiat").html(balances[i].hold);
        $("#hidden_wallet_available").val(balances[i].available);
        $("#hidden_wallet_onhold").val(balances[i].hold);
        $("#amount_crypto").html(balances[i].amount);
        $("#hidden_wallet_crypto").val(balances[i].amount);
        $("#amount_total").val(300);
     */
        aux_value = 30;
        $("span_convertion_rate_crypto").text(aux_value);

        $("#hidden_X").val(10);
    }
    show_fees();
});
/*<input type="hidden" id="hidden_nominal_fee_doc_verification" value="@globals.country_nominal_fee_doc_verification"></input>
    <input type="hidden" id="hidden_nominal_fee_withdrawal_workbank" value="@globals.country_nominal_fee_withdrawal_workbank"></input>
    <input type="hidden" id="hidden_nominal_fee_withdrawal_notworkbank" value="@globals.country_nominal_fee_withdrawal_notworkbank"></input>
    <input type="hidden" id="hidden_fee_deposit_percent" value="@globals.country_fee_deposit_percent"></input>
    <input type="hidden" id="hidden_fee_withdrawal_percent" value="@globals.country_fee_withdrawal_percent"></input>
    <input type="hidden" id="hidden_fee_send_percent" value="@globals.country_fee_send_percent"></input>
    <input type="hidden" id="hidden_fee_tofiat_percent" value="@globals.country_fee_tofiat_percent"></input>
*/