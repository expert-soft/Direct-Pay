
$(function() {
    var data_variable = Handlebars.compile($("#script-template").html());
    function show_bank_data(){
        API.get_bank_data().success(function(data){
            $('#banks').val(data[0].bank);
            $('#agency').val(data[0].agency);
            $('#account').val( data[0].account);

            $('#script-position').html(data_variable(reg_info));

            $('#banks').val($('#hidden_bank').val());
        });
    }
    show_bank_data();



    function submit_bankdata() {
        var bank = $('#banks').val();
        var agency = $('#agency').val();
        var account = $('#account').val();
        if (bank == "00" || agency == "" || account == "")
            alert("Provided data will be updated but withdrawals can not be done if information is uncomplete. ###");
        API.update_bank_data(bank, agency, account).success(function () {
            $.pnotify({
                title: Messages("messages.api.success"),
                text: Messages("messages.api.success.bankinfosavedsuccessfully"),
                styling: 'bootstrap',
                type: 'success',
                text_escape: true
            });
        })
    }

    $(document).ready(function () {
        $('#banks').val($('#hidden_bank').val());
        fillMessages();
    });
    $('.triggers_submit').click(function () {submit_bankdata()});

});

