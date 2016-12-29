$(function(){
    var template = Handlebars.compile($("#balance-template").html());
    var amount_fiat;
    var available_fiat;
    var hold_fiat;
    var amount_crypto;
    var available_crypto;
    var hold_crypto;

    function show_balance(){
        API.balance().success(function(balances){
            var specificbalances;
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = zerosToSpaces(Number(balances[i].amount) - Number(balances[i].hold));
                balances[i].amount = zerosToSpaces(balances[i].amount);
                balances[i].hold = zerosToSpaces(balances[i].hold);

                if(balances[i].currency == $('#hidden_currency_code').val()) {
                    alert(balances[i].amount);
                    specificbalances.amount_fiat = balances[i].amount;
                    specificbalances.available_fiat = balances[i].available;
                    specificbalances.hold_fiat = balances[i].hold;
                    alert(balances[i].amount);
                }
                if(balances[i].currency == $('#hidden_currency_crypto').val()) {
                    alert(balances[i].amount);
                    specificbalances.amount_crypto = balances[i].amount;
                    specificbalances.available_crypto = balances[i].available;
                    specificbalances.hold_crypto = balances[i].hold;
                }

            }
            $('#balance').html(template(balances));
            $('#balance').html(template(specificbalances));
        });
    }
    show_balance();

    function show_wallet(){

    }

    function showHide() {
        if ($("input[name='optionsAutomaticManual']:checked").val() == 'optionAutomatic') {
            $('#manual_operations').hide();
            $('#automatic_operations').show();
        }
        if ($("input[name='optionsAutomaticManual']:checked").val() == 'optionManual') {
            $('#manual_operations').show();
            $('#automatic_operations').hide();
        }
    }

    $(document).ready(function () {
        show_wallet();
        $("input[name='optionsAutomaticManual']").change(function () {
            showHide();
        });
    });
    showHide();
    show_wallet();
});