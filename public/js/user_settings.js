$(function(){

    var user_reg_template_one = Handlebars.compile($("#user-reg-one-template").html());

    function reload_reg(){
        API.user_name_info().success(function(data){
            reg_info = data[0];
            reg_info.name = data[0].name;
            reg_info.surname = data[0].surname;
            reg_info.middle_name = data[0].middle_name;
            reg_info.doc1 = data[0].doc1;
            reg_info.doc2 = data[0].doc2;
            reg_info.doc3 = data[0].doc3;
            reg_info.doc4 = data[0].doc4;
            reg_info.doc5 = data[0].doc5;
            reg_info.bank = data[0].bank;
            reg_info.agency = data[0].agency;
            reg_info.account = data[0].account;
            if(data[0].automatic) {reg_info.automatic = "checked"} else {reg_info.automatic = ""}
            if(data[0].partner == $('#hidden_partner1').val()) {reg_info.partner1_selected = "selected"} else {reg_info.partner1_selected = ""}

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

            $('.buttonNext').addClass('btn btn-primary');
            $('.buttonPrevious').addClass('btn btn-default');
            $('.buttonFinish').addClass('btn btn-success');
        });

        $(document).ready(function() {
            $(":input").inputmask();
        });

        $(document).ready(function() {
            $('#banks').val($('#hidden_bank').val());
        });
    }
    reloadsrc();

});