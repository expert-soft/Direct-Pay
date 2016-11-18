$(function(){

    var user_info_template = Handlebars.compile($("#user-info-template").html());

    function reload(){
        API.user_name_info().success(function(data){
            user_name_info = JSON.parse(data);

            user_name_info.name = user_name_info.name;
            user_name_info.surname = user_name_info.surname;
            user_name_info.middle_name = user_name_info.middle_name;
            user_name_info.prefix = user_name_info.prefix;

                $('#user-info').html(user_info_template(user_name_info));
            });
    }
    reload();
});
