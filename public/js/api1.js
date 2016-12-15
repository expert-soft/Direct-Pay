var API;
(function(){
    var iapi_prefix = '/iapi/1/';
    var api_prefix = '/api/1/';
    var limit = 20;

    // This creates a simple paginated function that makes calls to `path`
    function paginated(path) {
        return function(before, last_id) {
            return $.get(iapi_prefix+path, {before: before, limit: limit, last_id: last_id});
        };
    }

    // This adds an error handler to API calls to show the error in the UI
    var APIWrap = function(fn) {
        return function () {
            return fn.apply(this, arguments).error( function (res) {
                var err_text = '';
                try {
                    err_text = JSON.parse(res.responseText).message
                } catch (e) {
                    err_text = res.responseText;
                }
                $.pnotify({
                    title: Messages("java.api.messages.trade.apierror"),
                    text: err_text,
                    styling: 'bootstrap',
                    type: 'error',
                    text_escape: true
                });
            });
        };
    };
    API = {

        balance: APIWrap(function() {
            return $.get(iapi_prefix+'balance', 'json');
        }),

        user_name_info: APIWrap(function() {
            return $.get(iapi_prefix+'user_name_info', 'json');
        }),

        orders_list: APIWrap(function() {
            return $.get(iapi_prefix+'orders_list', 'json');
        }),

        user_list: APIWrap(function() {
            return $.get(iapi_prefix+'user_list', 'json');
        }),

        user: APIWrap(function() {
            return $.get(iapi_prefix+'user', 'json');
        }),

        turnoff_tfa: APIWrap(function(code, password) {
            return $.ajax(iapi_prefix+'turnoff_tfa', {
                type: 'POST',
                data: JSON.stringify({tfa_code: code, password: password}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        turnoff_emails: APIWrap(function() {
            return $.ajax(iapi_prefix+'turnoff_emails', {
                type: 'POST',
                data: "{}",
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        turnon_emails: APIWrap(function() {
            return $.ajax(iapi_prefix+'turnon_emails', {
                type: 'POST',
                data: "{}",
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        gen_totp_secret: APIWrap(function() {
            return $.post(iapi_prefix+'gen_totp_secret', 'json');
        }),

        turnon_tfa: APIWrap(function(code, password) {
            return $.ajax(iapi_prefix+'turnon_tfa', {
                type: 'POST',
                data: JSON.stringify({tfa_code: code, password: password}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        add_pgp: APIWrap(function(password, code, pgp) {
            return $.ajax(iapi_prefix+'add_pgp', {
                type: 'POST',
                data: JSON.stringify({tfa_code: code, password: password, pgp: pgp}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        remove_pgp: APIWrap(function(password, code) {
            return $.ajax(iapi_prefix+'remove_pgp', {
                type: 'POST',
                data: JSON.stringify({tfa_code: code, password: password}),
                dataType: 'json',
                contentType: 'application/json'
            });
        })
    };
})();
