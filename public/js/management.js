$(function(){

    function submit_search(search_criteria, search_value) {
        window.location.href = '/orders_list/' + search_criteria + '/' + search_value;
    }

    $('.triggers_submit').click(function () {
        var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
        var search_criteria = $('#search_criteria').val();
        var search_value = ($('#search_value').val());
        var apply_submit = false;
        if (search_criteria != "00")
            if (search_value != "")
                if (search_criteria == "orderid") {
//
                } else if (search_criteria == "userid") {
//
                } else if (search_criteria == "ordertype") {
                    apply_submit = true;
//
                } else if (search_criteria == "orderstatus") {
                    apply_submit = true;
//
                } else if (search_criteria == "ordercreated") {
//
                    if (Date.parse(search_value)) {
                        //alert("valid date"); // must be DD-MM-YYYY format
                        apply_submit = true;
                    }
                    else
                        alert(Messages('directpay.formvalidation.### not valid date'));
                } else if (search_criteria == "processedvalue") {
                    if (decimal_separator == ",")
                        search_value = search_value.replace(decimal_separator, ".");
                    if($.isNumeric(search_value))
                        apply_submit = true;
                    else
                        alert(Messages('directpay.formvalidation.valuemustbenumerical'));
                }
            else
                alert(Messages('directpay.formvalidation.###valuemustbegreaterthanzero'));
        else
            alert(Messages('directpay.formvalidation.###valuemustbegreaterthanzero'));
        if (apply_submit)
            submit_search(search_criteria, search_value);
    });


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


