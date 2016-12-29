$(function(){
    var users_list_template = Handlebars.compile($("#users-list-template").html());

    function reload(){
        API.users_list().success(function(data){
            for (var i = 0; i < data.length; i++) {

                data[i].id = data[i].id;
                data[i].created = data[i].created;
                data[i].email = data[i].email;
                data[i].active = data[i].active;
                data[i].name = data[i].name;
                data[i].surname = data[i].surname;
                data[i].middle_name = data[i].middle_name;
                data[i].doc1 = data[i].doc1;
                data[i].doc2 = data[i].doc2;
                data[i].doc3 = data[i].doc3;
                data[i].doc4 = data[i].doc4;
                data[i].doc5 = data[i].doc5;
                data[i].checked1 = "RRR";
                data[i].checked2 = "RRR";
                data[i].checked3 = "RRR";
                data[i].checked4 = "RRR";
                data[i].checked5 = "RRR";
            }
            $('#users-list').html(users_list_template(data));
        });
    }
    reload();
});
