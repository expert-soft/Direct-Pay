$(function(){
    var users_list_template = Handlebars.compile($("#users-list-template").html());

    function reload(){
        API.users_list().success(function(data){
            var critical_value1 = $('#hidden_critical_value1').val();
            for (var i = 0; i < data.length; i++) {

                data[i].id = data[i].id;
                data[i].created = data[i].created;
                data[i].email = data[i].email;
                data[i].active = data[i].active;
                data[i].first_name = data[i].first_name;
                data[i].middle_name = data[i].middle_name;
                data[i].last_name = data[i].last_name;
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].doc3 = data[i].doc3;
                data[i].doc4 = data[i].doc4;
                data[i].doc5 = data[i].doc5;
                data[i].ver1 = data[i].ver1;
                data[i].ver2 = data[i].ver2;
                data[i].ver3 = data[i].ver3;
                data[i].ver4 = data[i].ver4;
                data[i].ver5 = data[i].ver5;
                data[i].balance = data[i].balance;
                data[i].balance_c = data[i].balance_c;
                if (data[i].doc1 != "") { if (data[i].ver1) { data[i].checked1 = "fa fa-check" } else { data[i].checked1 = "fa fa-alert" } } else {data[i].checked1 = ""};
                if (data[i].doc2 != "") { if (data[i].ver2) { data[i].checked2 = "fa fa-check" } else { data[i].checked2 = "fa fa-asterisk" } } else {data[i].checked2 = ""};
                if (data[i].doc3 != "") { if (data[i].ver3) { data[i].checked3 = "fa fa-check" } else { data[i].checked3 = "fa fa-asterisk" } } else {data[i].checked3 = ""};
                if (data[i].doc4 != "") { if (data[i].ver4) { data[i].checked4 = "fa fa-check" } else { data[i].checked4 = "fa fa-asterisk" } } else {data[i].checked4 = ""};
                if (data[i].doc5 != "") { if (data[i].ver5) { data[i].checked5 = "fa fa-check" } else { data[i].checked5 = "fa fa-asterisk" } } else {data[i].checked5 = ""};
                if (data[i].balance >= critical_value1 ) { data[i].class_fiat="bgn_green" }
                if (data[i].balance_c >= critical_value1 ) {data[i].class_crypto="bgn_green" }
            }
            $('#users-list').html(users_list_template(data));
        });
    }
    reload();


    $(document).ready(function () {
        $('.requestPopUp').live('click', function() {
            $('#image-holder').attr('src', '/assets/img/brUserDocs/' + $(this).attr('name'));
            var id_value = $(this).attr('id');
            if ($(this).attr('doc') == "1") $('#popUpPictureTitle').html($('#popUpPictureDoc1').val());
            if ($(this).attr('doc') == "2") $('#popUpPictureTitle').html($('#popUpPictureDoc2').val());
            if ($(this).attr('doc') == "3") $('#popUpPictureTitle').html($('#popUpPictureDoc3').val());
            if ($(this).attr('doc') == "4") $('#popUpPictureTitle').html($('#popUpPictureDoc4').val());
            if ($(this).attr('doc') == "5") $('#popUpPictureTitle').html($('#popUpPictureDoc5').val());
            $('#popUpPictureInfo1').html($(this).attr('user'));
            $('#popUpPictureInfo2').html($(this).attr('email'));
            if ($(this).attr('ver') == "true") {  // this doc is already verified
                $('#popUpPictureInfo3').html('Document already verified');
            }
            else {  // this doc is not yet verified
                $('#popUpPictureInfo3').html('Document not yet verified');
            }
//            alert($(this).attr('id'));
//            alert(event.target.id);
//            var id = $(this).attr('id');
//            if (id == "m1") {
//                //do your stuff here
//            }
        });
    });
});
