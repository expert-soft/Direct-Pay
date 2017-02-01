/**
 * Created by Admin on 30/01/2017.
 */

$(function() {

    function submit_send() {
        var country_id = 55;
        var order_type = "S";
        var status = "Op";
        var partner = "Crypto-Trade.net";
        API.create_order(country_id, order_type, status, partner).success(function () {
            $.pnotify({
                title: Messages("java.api.messages.account.twofactorauthentication"),
                text: Messages("java.api.messages.account.twofactorauthenticationturnedon"),
                styling: 'bootstrap',
                type: 'success',
                text_escape: true
            });
        })
    }



    $(document).ready(function () {

    });

    $('.triggers_submit').click(function () {submit_send()});
});
