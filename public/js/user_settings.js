$(function(){

    var user_info_template = Handlebars.compile($("#user-info-template").html());

    function reload(){
        API.user_name_info().success(function(data){
            user_name_info = data[0];

            user_name_info.name = user_name_info.name;
            user_name_info.surname = user_name_info.surname;
            user_name_info.middle_name = user_name_info.middle_name;
            user_name_info.prefix = user_name_info.prefix;

                $('#user-info').html(user_info_template(user_name_info));
            });

        API.country().success(function(data){
            country_info = data[0];

            country_info.country_code = country_info.country_code;
            country_info.country_name = country_info.country_name;
            country_info.country_local_name = country_info.country_local_name;
            country_info.site_name = country_info.site_name;
            country_info.site_url1 = country_info.site_url1;
            country_info.site_url2 = country_info.site_url2;
            country_info.language_name = country_info.language_name;
            country_info.language_code = country_info.language_code;
            country_info.currency_symbol = country_info.currency_symbol;
            country_info.currency_code = country_info.currency_code;
            country_info.currency_crypto = country_info.currency_crypto;
            country_info.currency_name = country_info.currency_name;
            country_info.currency_name_plural = country_info.currency_name_plural;
            country_info.currency_approximate_value = country_info.currency_approximate_value;
            country_info.critical_value = country_info.critical_value;
            country_info.working_bank_1 = country_info.working_bank_1;
            country_info.working_bank_2 = country_info.working_bank_2;
            country_info.working_bank_3 = country_info.working_bank_3;
            country_info.working_bank_4 = country_info.working_bank_4;

                $('#user-info').html(user_info_template(country_info));
            });

        API.country_docs().success(function(data){
            country_docs_info = data[0];

            country_docs_info.doc1_name = country_docs_info.doc1_name;
            country_docs_info.doc1_required = country_docs_info.doc1_required;
            country_docs_info.doc1_ispicture = country_docs_info.doc1_ispicture;
            country_docs_info.doc1_format = country_docs_info.doc1_format;
            country_docs_info.doc2_name = country_docs_info.doc2_name;
            country_docs_info.doc2_required = country_docs_info.doc2_required;
            country_docs_info.doc2_ispicture = country_docs_info.doc2_ispicture;
            country_docs_info.doc2_format = country_docs_info.doc2_format;
            country_docs_info.doc3_name = country_docs_info.doc3_name;
            country_docs_info.doc3_required = country_docs_info.doc3_required;
            country_docs_info.doc3_ispicture = country_docs_info.doc3_ispicture;
            country_docs_info.doc3_format = country_docs_info.doc3_format;
            country_docs_info.doc4_name = country_docs_info.doc4_name;
            country_docs_info.doc4_required = country_docs_info.doc4_required;
            country_docs_info.doc4_ispicture = country_docs_info.doc4_ispicture;
            country_docs_info.doc4_format = country_docs_info.doc4_format;
            country_docs_info.doc5_name = country_docs_info.doc5_name;
            country_docs_info.doc5_required = country_docs_info.doc5_required;
            country_docs_info.doc5_ispicture = country_docs_info.doc5_ispicture;
            country_docs_info.doc5_format = country_docs_info.doc5_format;

                $('#user-info').html(user_info_template(country_docs_info));
            });

        API.banks_list().success(function(data){
            for (var i = 0; i < data.length; i++) {
                data[i].country_code = data[i].country_code;
                data[i].bank_code = data[i].bank_code;
                data[i].bank_name = data[i].bank_name;
            }

            $('#banks').html(banks_template(data));
        });
    }
    reload();

     function reload_reg(){

        API.user_name_info().success(function(data){
            var user_reg_template_one = Handlebars.compile($("#user-reg-one-template").html());
            reg_info = data[0];
            reg_info.name = data[0].name;
            reg_info.surname = data[0].surname;
            reg_info.middle_name = data[0].middle_name;
            reg_info.prefix = data[0].prefix;
            $('#user-reg-one').html(user_reg_template_one(reg_info));

            reloadsrc();
        });

    }

    reload_reg();

    function reloadsrc(){
        $(document).ready(function() {
            $('#wizard').smartWizard();

            $('#wizard_verticle').smartWizard({
                transitionEffect: 'slide'
            });

            $('.buttonNext').addClass('btn btn-success');
            $('.buttonPrevious').addClass('btn btn-primary');
            $('.buttonFinish').addClass('btn btn-default');
        });

        $(document).ready(function() {
            $(":input").inputmask();
        });
    }

    reloadsrc();
});
