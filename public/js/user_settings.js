$(function(){

    var user_info_template = Handlebars.compile($("#user-info-template").html());
    var user_reg_template = Handlebars.compile($("#user-reg-template").html());

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

            $('#user-info').html(user_info_template(data));
        });
    }
   reload();

     function reload_reg(){

        API.user_name_info().success(function(data){
            reg_info = data[0];

            reg_info.name = data[0].name;
            reg_info.surname = data[0].surname;
            reg_info.middle_name = data[0].middle_name;
            reg_info.prefix = data[0].prefix;

        API.country().success(function(data){

            reg_info.country_code = data[0].country_code;
            reg_info.country_name = data[0].country_name;
            reg_info.country_local_name = data[0].country_local_name;
            reg_info.site_name = data[0].site_name;
            reg_info.site_url1 = data[0].site_url1;
            reg_info.site_url2 = data[0].site_url2;
            reg_info.language_name = data[0].language_name;
            reg_info.language_code = data[0].language_code;
            reg_info.currency_symbol = data[0].currency_symbol;
            reg_info.currency_code = data[0].currency_code;
            reg_info.currency_crypto = data[0].currency_crypto;
            reg_info.currency_name = data[0].currency_name;
            reg_info.currency_name_plural = data[0].currency_name_plural;
            reg_info.currency_approximate_value = data[0].currency_approximate_value;
            reg_info.critical_value = data[0].critical_value;

        API.country_docs().success(function(data){

            reg_info.doc1_name = data[0].doc1_name;
            reg_info.doc1_required = data[0].doc1_required;
            reg_info.doc1_ispicture = data[0].doc1_ispicture;
            reg_info.doc1_format = data[0].doc1_format;
            reg_info.doc2_name = data[0].doc2_name;
            reg_info.doc2_required = data[0].doc2_required;
            reg_info.doc2_ispicture = data[0].doc2_ispicture;
            reg_info.doc2_format = data[0].doc2_format;
            reg_info.doc3_name = data[0].doc3_name;
            reg_info.doc3_required = data[0].doc3_required;
            reg_info.doc3_ispicture = data[0].doc3_ispicture;
            reg_info.doc3_format = data[0].doc3_format;
            reg_info.doc4_name = data[0].doc4_name;
            reg_info.doc4_required = data[0].doc4_required;
            reg_info.doc4_ispicture = data[0].doc4_ispicture;
            reg_info.doc4_format = data[0].doc4_format;
            reg_info.doc5_name = data[0].doc5_name;
            reg_info.doc5_required = data[0].doc5_required;
            reg_info.doc5_ispicture = data[0].doc5_ispicture;
            reg_info.doc5_format = data[0].doc5_format;

        API.banks_list().success(function(data){
            banks_info = data[0];

            reg_info.country_code = data[0].country_code;
            reg_info.bank_code = data[0].bank_code;
            reg_info.bank_name = data[0].bank_name;

            $('#user-reg').html(user_reg_template(reg_info));
            reloadsrc();

        });
        });
        });
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
