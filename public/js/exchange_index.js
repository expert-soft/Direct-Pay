$(function(){
    var template = Handlebars.compile($("#balance-template").html());

    function show_balance(){
        sum_money = 0;
        API.balance().success(function(balances){
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = zerosToSpaces(Number(balances[i].amount) - Number(balances[i].hold));
                balances[i].amount = zerosToSpaces(balances[i].amount);
                balances[i].hold = zerosToSpaces(balances[i].hold);

                if(balances[i].currency == $('#hidden_currency_code').val()) {
                    $("#available_fiat").html(balances[i].available);
                    $("#hold_fiat").html(balances[i].hold);
                    sum_money += parseFloat(balances[i].amount);
                }
                if(balances[i].currency == $('#hidden_currency_crypto').val()) {
                    $("#amount_crypto").html(balances[i].amount);
                    sum_money += parseFloat(balances[i].amount);
                }
                $("#amount_total").html(sum_money);
            }
            $('#balance').html(template(balances));
        });
    }
    show_balance();

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
        $("input[name='optionsAutomaticManual']").change(function () {
            showHide();
        });

        $("#btn1").click(function(){
            $("#test1").text("Hello world!");
        });
        $("#btn2").click(function(){
            $("#test2").html("<b>Hello world!</b>");
        });

    });
    showHide();
});