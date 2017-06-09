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

        user_name_info: APIWrap(function() {
            return $.get(iapi_prefix+'user_name_info', 'json');
        }),

        get_bank_data: APIWrap(function() {
            return $.get(iapi_prefix+'get_bank_data', 'json');
        }),

        users_list: APIWrap(function() {
            return $.get(iapi_prefix+'users_list', 'json');
        }),

        get_docs_info: APIWrap(function() {
            return $.get(iapi_prefix+'get_docs_info', 'json');
        }),

        management_data: APIWrap(function() {
            return $.get(iapi_prefix+'management_data', 'json');
        }),

        get_log_events: APIWrap(function() {
            return $.get(iapi_prefix+'get_log_events', 'json');
        }),

        balance: APIWrap(function() {
            return $.get(iapi_prefix+'balance', 'json');
        }),

        get_admins: APIWrap(function() {
            return $.get(iapi_prefix+'get_admins', 'json');
        }),

        get_all_img: APIWrap(function() {
            return $.get(iapi_prefix+'get_all_img', 'json');
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
        }),

        create_order: APIWrap(function(order_type, status, partner, initial_value, bank, agency, account, doc1) {
            return $.ajax(iapi_prefix+'create_order', {
                type: 'POST',
                data: JSON.stringify({order_type: order_type, status: status, partner: partner, initial_value: initial_value, bank: bank, agency: agency, account: account, doc1: doc1}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        update_order: APIWrap(function(order_id, order_type, status, net_value, comment) {
            return $.ajax(iapi_prefix+'update_order', {
                type: 'POST',
                data: JSON.stringify({order_id: order_id, order_type: order_type, status: status, net_value: net_value, comment: comment}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        update_personal_info: APIWrap(function(first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, bank, agency, account, partner, partner_account, manualauto_mode) {
            return $.ajax(iapi_prefix+'update_personal_info', {
                type: 'POST',
                data: JSON.stringify({first_name: first_name, middle_name: middle_name, last_name: last_name, doc1: doc1, doc2: doc2, doc3: doc3, doc4: doc4, doc5: doc5, bank: bank, agency: agency, account: account, partner: partner, partner_account: partner_account, manualauto_mode: manualauto_mode}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        update_bank_data: APIWrap(function(bank, agency, account) {
            return $.ajax(iapi_prefix+'update_bank_data', {
                type: 'POST',
                data: JSON.stringify({bank: bank, agency: agency, account: account}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        change_manualauto: APIWrap(function(manualauto_mode) {
            return $.ajax(iapi_prefix+'change_manualauto', {
                type: 'POST',
                data: JSON.stringify({manualauto_mode: manualauto_mode}),
                dataType: 'json',
                contentType: 'application/json'
            });
        }),

        orders_list: APIWrap(function(search_criteria, search_value) {
            return $.ajax(iapi_prefix+'orders_list', {
                type: 'POST',
                data: JSON.stringify({search_criteria: search_criteria, search_value: search_value}),
                dataType: 'json',
                contentType: 'application/json'
            });
        })



    };
})();
