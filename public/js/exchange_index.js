$(function(){
    var template = Handlebars.compile($("#balance-template").html());

    function show_balance(){
        API.balance().success(function(balances){
            for (var i = 0; i < balances.length; i++) {
                balances[i].available = zerosToSpaces(Number(balances[i].amount) - Number(balances[i].hold));
                balances[i].amount = zerosToSpaces(balances[i].amount);
                balances[i].hold = zerosToSpaces(balances[i].hold);
            }
            $('#balance').html(template(balances));
        });
    }
    show_balance();
});