$(function(){

    var user_reg_template_one = Handlebars.compile($("#user-reg-one-template").html());

    function reload_reg(){
        API.user_name_info().success(function(data){
            reg_info = data[0];
            reg_info.first_name = data[0].first_name;
            reg_info.middle_name = data[0].middle_name;
            reg_info.last_name = data[0].last_name;
            reg_info.doc1 = data[0].doc1;
            reg_info.doc2 = data[0].doc2;
            reg_info.doc3 = data[0].doc3;
            reg_info.doc4 = data[0].doc4;
            reg_info.doc5 = data[0].doc5;
            reg_info.bank = data[0].bank;
            reg_info.agency = data[0].agency;
            reg_info.account = data[0].account;
            if(data[0].partner == $('#hidden_partner1').val()) {reg_info.partner1_selected = "selected"} else {reg_info.partner1_selected = ""}
            if(data[0].partner == $('#hidden_partner2').val()) {reg_info.partner2_selected = "selected"} else {reg_info.partner2_selected = ""}
            reg_info.partner_account = data[0].partner_account;

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

            $('.buttonPrevious').addClass('btn btn-default');
            $('.buttonNext').addClass('btn btn-default');
            $('.buttonFinish').addClass('btn btn-primary');
            $('.buttonFinish').addClass('triggers_submit');
        });

        $(document).ready(function() {
            $(":input").inputmask();
        });

        $(document).ready(function() {
            $('#banks').val($('#hidden_bank').val());
        });
    }
    reloadsrc();


    function submit_personal_info() {
        if ($('#first_name').val() != "")
        {   var first_name = $('#first_name').val();
            var middle_name = $('#middle_name').val();
            var last_name = $('#last_name').val();
            var email = $('#email').val();
            var doc1 = $('#doc1').val();
            var doc2 = $('#doc2').val();
            var doc3 = $('#doc3').val();
            var doc4 = $('#doc4').val();
            var doc5 = $('#doc5').val();
            var bank = $('#banks').val();
            var agency = $('#agency').val();
            var account = $('#account').val();
            var partner = $('#partner').val();
            var partner_account = $('#partner_account').val();
            var manualauto_mode = $('#manualauto_mode').prop('checked');
            API.update_personal_info(first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, bank, agency, account, partner, partner_account, manualauto_mode).success(function () {
                $.pnotify({
                    title: Messages("messages.api.success"),
                    text: Messages("messages.api.success.ordercreatedsuccessfully"),
                    styling: 'bootstrap',
                    type: 'success',
                    text_escape: true
                });
            })
        }
        else
            alert("not submitted");
    }

    $(document).ready(function () {    });

    $('.triggers_submit').live('click', function() {
        submit_personal_info()
    });
});






