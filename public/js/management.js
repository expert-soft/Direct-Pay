$(function(){


    $('#submit_search').live('click', function() {
        submit_search();
    });

    $('#submit_admin_form').live('click', function() {
        submit_admin_form();
    });

    function submit_admin_form() {
        if ($('#admin_g1').val() != "" && $('#admin_l1').val() != "") {
            alert(8);
            API.save_admins($('#hidden_fees_information').attr('country_code'), $('#admin_g1').val(), $('#admin_g2').val(), $('#admin_l1').val(), $('#admin_l2').val(), $('#admin_o1').val(), $('#admin_o2').val()).success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.administratorssavedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            });
        }
        else
            alert(Messages('directpay.formvalidation.global1andlocal1administratorsmustnotbeempty'));
    }

    function submit_search(){
        var decimal_separator = $('#hidden_fees_information').attr('decimal_separator');
        var search_criteria = $('#search_criteria').val();
        var search_value = ($('#search_value').val());
        var apply_submit = false;
        if (search_criteria != "00")
            if (search_value != "")
                if (search_criteria == "orderid") {
                } else if (search_criteria == "userid") {
// ### not implemented
                } else if (search_criteria == "ordertype") {
                    apply_submit = true;
                } else if (search_criteria == "orderstatus") {
                    apply_submit = true;
                } else if (search_criteria == "ordercreated") {
                    if (Date.parse(search_value)) {
                        //alert("valid date"); // must be DD-MM-YYYY format
                        apply_submit = true;
                    }
                    else
                        alert(Messages('directpay.formvalidation.thisisnotavaliddate'));
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
            window.location.href = '/orders_list/' + search_criteria + '/' + search_value;
    }


    var data_variable = Handlebars.compile($("#script-template").html());
    function show_management_info(){
        API.management_data().success(function(data){
            for (var i = 0; i < data.length; i++) {
                data[i].country_code = data[i].country_code;
                data[i].currency = data[i].currency;
                data[i].expected_bank_balance = NumberFormat(parseFloat(data[i].fiat_funds) - parseFloat(data[i].system_balance), 2);
                data[i].number_users = data[i].number_users;
                data[i].fiat_funds = NumberFormat(data[i].fiat_funds, 2);
                data[i].crypto_funds = NumberFormat(data[i].crypto_funds, 2);
                data[i].system_balance = NumberFormat(data[i].system_balance, 2);
                data[i].number_pending_orders = data[i].number_pending_orders;
            }

            $('#script-position').html(data_variable(data));
        });
    }
    show_management_info();

    var admins_variable = Handlebars.compile($("#script_admins-template").html());
    function show_admins_info(){
        API.get_admins().success(function(data){
            admin_info = data[0];
            admin_info.country_code = $('#hidden_fees_information').attr('country_code');
            admin_info.admin_g1 = data[0].admin_g1;
            admin_info.admin_g2 = data[0].admin_g2;
            admin_info.admin_l1 = data[0].admin_l1;
            admin_info.admin_l2 = data[0].admin_l2;
            admin_info.admin_o1 = data[0].admin_o1;
            admin_info.admin_o2 = data[0].admin_o2;
            admin_info.email_g1 = data[0].email_g1;
            admin_info.email_g2 = data[0].email_g2;
            admin_info.email_l1 = data[0].email_l1;
            admin_info.email_l2 = data[0].email_l2;
            admin_info.email_o1 = data[0].email_o1;
            admin_info.email_o2 = data[0].email_o2;
/*            if(admin_info.admin_g1 == "0") admin_info.display_g1 = "none"; else admin_info.display_g1 = "block";
            if(admin_info.admin_g2 == "0") admin_info.display_g2 = "none"; else admin_info.display_g2 = "block";
            if(admin_info.admin_l1 == "0") admin_info.display_l1 = "none"; else admin_info.display_l1 = "block";
            if(admin_info.admin_l2 == "0") admin_info.display_l2 = "none"; else admin_info.display_l2 = "block";
            if(admin_info.admin_o1 == "0") admin_info.display_o1 = "none"; else admin_info.display_o1 = "block";
            if(admin_info.admin_o2 == "0") admin_info.display_o2 = "none"; else admin_info.display_o2 = "block";
*/
            $('#script_admins-position').html(admins_variable(admin_info));
        });
    }
    show_admins_info();

});
// one sample of IBAN code CH9300762011623852957

//Error: Server Error Left(UnexpectedNullableFound(ColumnName(.admin_g2,Some(admin_g2))))
