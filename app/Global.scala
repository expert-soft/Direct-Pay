import play.api.db.DB
import play.api.i18n.{ MessagesApi, I18nSupport }
import play.api.Play.current
import models._
import play.api._
import play.libs.Akka
import service.txbitsUserService
import usertrust.{ UserTrustModel, UserTrustService }
import anorm._

package object globals {

  /* Country Regional Settings */
  val country_code = Play.current.configuration.getString("country.country_code").getOrElse("Not Set")
  val country_name = Play.current.configuration.getString("country.country_name").getOrElse("Not Set")
  val country_system_name = Play.current.configuration.getString("country.country_system_name").getOrElse("Direct-Pay")
  val country_site_name = Play.current.configuration.getString("country.country_site_name").getOrElse("Not Set")
  val country_site_url1 = Play.current.configuration.getString("country.country_site_url1").getOrElse("Not Set")
  val country_currency_symbol = Play.current.configuration.getString("country.country_currency_symbol").getOrElse("Not Set")
  val country_currency_code = Play.current.configuration.getString("country.country_currency_code").getOrElse("Not Set")
  val country_currency_crypto = Play.current.configuration.getString("country.country_currency_crypto").getOrElse("Not Set")
  val country_currency_name = Play.current.configuration.getString("country.country_currency_name").getOrElse("Not Set")
  val country_currency_name_plural = Play.current.configuration.getString("country.country_currency_name_plural").getOrElse("Not Set")
  val country_decimal_separator = Play.current.configuration.getString("country.country_decimal_separator").getOrElse("Not Set")
  val country_operations_organized = Play.current.configuration.getString("country.country_operations_organized").getOrElse("Not Set")
  val country_iban = Play.current.configuration.getString("country.country_iban").getOrElse(false)
  val country_minimum_value = Play.current.configuration.getInt("country.country_minimum_value").getOrElse(0)
  val country_critical_value1 = Play.current.configuration.getInt("country.country_critical_value1").getOrElse(0)
  val country_critical_value2 = Play.current.configuration.getInt("country.country_critical_value2").getOrElse(0)
  val country_partner1 = Play.current.configuration.getString("country.country_partner1").getOrElse("Not Set")
  val country_partner1_url = Play.current.configuration.getString("country.country_partner1_url").getOrElse("Not Set")
  val country_partner1_info = Play.current.configuration.getString("country.country_partner1_info").getOrElse("Not Set")
  val country_partner1_color = Play.current.configuration.getString("country.country_partner1_color").getOrElse("Not Set")
  val country_partner1_picture = Play.current.configuration.getString("country.country_partner1_picture").getOrElse("Not Set")
  val country_partner2 = Play.current.configuration.getString("country.country_partner2").getOrElse("Not Set")
  val country_partner2_url = Play.current.configuration.getString("country.country_partner2_url").getOrElse("Not Set")
  val country_partner2_info = Play.current.configuration.getString("country.country_partner2_info").getOrElse("Not Set")
  val country_partner2_color = Play.current.configuration.getString("country.country_partner2_color").getOrElse("Not Set")
  val country_partner2_picture = Play.current.configuration.getString("country.country_partner2_picture").getOrElse("Not Set")
  val country_doc1_name = Play.current.configuration.getString("country.country_doc1_name").getOrElse("Not Set")
  val country_doc1_required = Play.current.configuration.getBoolean("country.country_doc1_required").getOrElse(false)
  val country_doc1_ispicture = Play.current.configuration.getBoolean("country.country_doc1_ispicture").getOrElse(false)
  val country_doc1_format = Play.current.configuration.getString("country.country_doc1_format").getOrElse("")
  val country_doc2_name = Play.current.configuration.getString("country.country_doc2_name").getOrElse("Not Set")
  val country_doc2_required = Play.current.configuration.getBoolean("country.country_doc2_required").getOrElse(false)
  val country_doc2_ispicture = Play.current.configuration.getBoolean("country.country_doc2_ispicture").getOrElse(false)
  val country_doc2_format = Play.current.configuration.getString("country.country_doc2_format").getOrElse("")
  val country_doc3_name = Play.current.configuration.getString("country.country_doc3_name").getOrElse("Not Set")
  val country_doc3_required = Play.current.configuration.getBoolean("country.country_doc3_required").getOrElse(false)
  val country_doc3_ispicture = Play.current.configuration.getBoolean("country.country_doc3_ispicture").getOrElse(false)
  val country_doc3_format = Play.current.configuration.getString("country.country_doc3_format").getOrElse("")
  val country_doc4_name = Play.current.configuration.getString("country.country_doc4_name").getOrElse("Not Set")
  val country_doc4_required = Play.current.configuration.getBoolean("country.country_doc4_required").getOrElse(false)
  val country_doc4_ispicture = Play.current.configuration.getBoolean("country.country_doc4_ispicture").getOrElse(false)
  val country_doc4_format = Play.current.configuration.getString("country.country_doc4_format").getOrElse("")
  val country_doc5_name = Play.current.configuration.getString("country.country_doc5_name").getOrElse("Not Set")
  val country_doc5_required = Play.current.configuration.getBoolean("country.country_doc5_required").getOrElse(false)
  val country_doc5_ispicture = Play.current.configuration.getBoolean("country.country_doc5_ispicture").getOrElse(false)
  val country_doc5_format = Play.current.configuration.getString("country.country_doc5_format").getOrElse("")
  val country_preferential_bank1_code = Play.current.configuration.getString("country.country_preferential_bank1_code").getOrElse("Not Set")
  val country_preferential_bank1_name = Play.current.configuration.getString("country.country_preferential_bank1_name").getOrElse("Not Set")
  val country_preferential_bank1_agency = Play.current.configuration.getString("country.country_preferential_bank1_agency").getOrElse("Not Set")
  val country_preferential_bank1_account = Play.current.configuration.getString("country.country_preferential_bank1_account").getOrElse("Not Set")
  val country_preferential_bank1_reference = Play.current.configuration.getString("country.country_preferential_bank1_reference").getOrElse("Not Set")
  val country_preferential_bank2_code = Play.current.configuration.getString("country.country_preferential_bank2_code").getOrElse("Not Set")
  val country_preferential_bank2_name = Play.current.configuration.getString("country.country_preferential_bank2_name").getOrElse("Not Set")
  val country_preferential_bank2_agency = Play.current.configuration.getString("country.country_preferential_bank2_agency").getOrElse("Not Set")
  val country_preferential_bank2_account = Play.current.configuration.getString("country.country_preferential_bank2_account").getOrElse("Not Set")
  val country_preferential_bank2_reference = Play.current.configuration.getString("country.country_preferential_bank2_reference").getOrElse("Not Set")
  val country_preferential_bank3_code = Play.current.configuration.getString("country.country_preferential_bank3_code").getOrElse("Not Set")
  val country_preferential_bank3_name = Play.current.configuration.getString("country.country_preferential_bank3_name").getOrElse("Not Set")
  val country_preferential_bank3_agency = Play.current.configuration.getString("country.country_preferential_bank3_agency").getOrElse("Not Set")
  val country_preferential_bank3_account = Play.current.configuration.getString("country.country_preferential_bank3_account").getOrElse("Not Set")
  val country_preferential_bank3_reference = Play.current.configuration.getString("country.country_preferential_bank3_reference").getOrElse("Not Set")
  val country_preferential_bank4_code = Play.current.configuration.getString("country.country_preferential_bank4_code").getOrElse("Not Set")
  val country_preferential_bank4_name = Play.current.configuration.getString("country.country_preferential_bank4_name").getOrElse("Not Set")
  val country_preferential_bank4_agency = Play.current.configuration.getString("country.country_preferential_bank4_agency").getOrElse("Not Set")
  val country_preferential_bank4_account = Play.current.configuration.getString("country.country_preferential_bank4_account").getOrElse("Not Set")
  val country_preferential_bank4_reference = Play.current.configuration.getString("country.country_preferential_bank4_reference").getOrElse("Not Set")
  val country_bank1_code = Play.current.configuration.getString("country.country_bank1_code").getOrElse("Not Set")
  val country_bank1_name = Play.current.configuration.getString("country.country_bank1_name").getOrElse("Not Set")
  val country_bank2_code = Play.current.configuration.getString("country.country_bank2_code").getOrElse("Not Set")
  val country_bank2_name = Play.current.configuration.getString("country.country_bank2_name").getOrElse("Not Set")
  val country_bank3_code = Play.current.configuration.getString("country.country_bank3_code").getOrElse("Not Set")
  val country_bank3_name = Play.current.configuration.getString("country.country_bank3_name").getOrElse("Not Set")
  val country_bank4_code = Play.current.configuration.getString("country.country_bank4_code").getOrElse("Not Set")
  val country_bank4_name = Play.current.configuration.getString("country.country_bank4_name").getOrElse("Not Set")
  val country_bank5_code = Play.current.configuration.getString("country.country_bank5_code").getOrElse("Not Set")
  val country_bank5_name = Play.current.configuration.getString("country.country_bank5_name").getOrElse("Not Set")
  val country_bank6_code = Play.current.configuration.getString("country.country_bank6_code").getOrElse("Not Set")
  val country_bank6_name = Play.current.configuration.getString("country.country_bank6_name").getOrElse("Not Set")
  val country_bank7_code = Play.current.configuration.getString("country.country_bank7_code").getOrElse("Not Set")
  val country_bank7_name = Play.current.configuration.getString("country.country_bank7_name").getOrElse("Not Set")
  val country_bank8_code = Play.current.configuration.getString("country.country_bank8_code").getOrElse("Not Set")
  val country_bank8_name = Play.current.configuration.getString("country.country_bank8_name").getOrElse("Not Set")
  val country_bank9_code = Play.current.configuration.getString("country.country_bank9_code").getOrElse("Not Set")
  val country_bank9_name = Play.current.configuration.getString("country.country_bank9_name").getOrElse("Not Set")
  val country_bank10_code = Play.current.configuration.getString("country.country_bank10_code").getOrElse("Not Set")
  val country_bank10_name = Play.current.configuration.getString("country.country_bank10_name").getOrElse("Not Set")
  val country_bank11_code = Play.current.configuration.getString("country.country_bank11_code").getOrElse("Not Set")
  val country_bank11_name = Play.current.configuration.getString("country.country_bank11_name").getOrElse("Not Set")
  val country_bank12_code = Play.current.configuration.getString("country.country_bank12_code").getOrElse("Not Set")
  val country_bank12_name = Play.current.configuration.getString("country.country_bank12_name").getOrElse("Not Set")
  val country_bank13_code = Play.current.configuration.getString("country.country_bank13_code").getOrElse("Not Set")
  val country_bank13_name = Play.current.configuration.getString("country.country_bank13_name").getOrElse("Not Set")
  val country_bank14_code = Play.current.configuration.getString("country.country_bank14_code").getOrElse("Not Set")
  val country_bank14_name = Play.current.configuration.getString("country.country_bank14_name").getOrElse("Not Set")
  val country_bank15_code = Play.current.configuration.getString("country.country_bank15_code").getOrElse("Not Set")
  val country_bank15_name = Play.current.configuration.getString("country.country_bank15_name").getOrElse("Not Set")
  val country_bank16_code = Play.current.configuration.getString("country.country_bank16_code").getOrElse("Not Set")
  val country_bank16_name = Play.current.configuration.getString("country.country_bank16_name").getOrElse("Not Set")
  val country_bank17_code = Play.current.configuration.getString("country.country_bank17_code").getOrElse("Not Set")
  val country_bank17_name = Play.current.configuration.getString("country.country_bank17_name").getOrElse("Not Set")
  val country_bank18_code = Play.current.configuration.getString("country.country_bank18_code").getOrElse("Not Set")
  val country_bank18_name = Play.current.configuration.getString("country.country_bank18_name").getOrElse("Not Set")
  val country_bank19_code = Play.current.configuration.getString("country.country_bank19_code").getOrElse("Not Set")
  val country_bank19_name = Play.current.configuration.getString("country.country_bank19_name").getOrElse("Not Set")
  val country_bank20_code = Play.current.configuration.getString("country.country_bank20_code").getOrElse("Not Set")
  val country_bank20_name = Play.current.configuration.getString("country.country_bank20_name").getOrElse("Not Set")
  val country_local_administrator = Play.current.configuration.getString("country.country_local_administrator").getOrElse("Not Set")
  val country_global_administrator = Play.current.configuration.getString("country.country_global_administrator").getOrElse("Not Set")
  val country_initial_crypto_capital = Play.current.configuration.getDouble("country.country_initial_crypto_capital").getOrElse(0)
  val country_nominal_fee_withdrawal_preferential_bank = Play.current.configuration.getDouble("country.country_nominal_fee_withdrawal_preferential_bank ").getOrElse(0)
  val country_nominal_fee_withdrawal_not_preferential_bank = Play.current.configuration.getDouble("country.country_nominal_fee_withdrawal_not_preferential_bank").getOrElse(0)
  val country_fees_global_percentage = Play.current.configuration.getDouble("country.country_fees_global_percentage").getOrElse(0)
  val country_fee_deposit_percent = Play.current.configuration.getDouble("country.country_fee_deposit_percent").getOrElse(0)
  val country_fee_withdrawal_percent = Play.current.configuration.getDouble("country.country_fee_withdrawal_percent").getOrElse(0)
  val country_fee_send_percent = Play.current.configuration.getDouble("country.country_fee_send_percent").getOrElse(0)
  val country_fee_tofiat_percent = Play.current.configuration.getDouble("country.country_fee_tofiat_percent").getOrElse(0)
  val country_fee_tofiat_minimum_rate_percent = Play.current.configuration.getDouble("country.country_fee_tofiat_minimum_rate_percent").getOrElse(0)
  val country_appearance_pic1 = Play.current.configuration.getString("country.country_appearance_pic1").getOrElse("Not Set")
  val country_appearance_pic2 = Play.current.configuration.getString("country.country_appearance_pic2").getOrElse("Not Set")
  val country_appearance_background_1 = Play.current.configuration.getString("country.country_appearance_background_1").getOrElse("Not Set")
  val country_appearance_background_2 = Play.current.configuration.getString("country.country_appearance_background_2").getOrElse("Not Set")
  val country_appearance_background_3 = Play.current.configuration.getString("country.country_appearance_background_3").getOrElse("Not Set")
  val country_appearance_background_4 = Play.current.configuration.getString("country.country_appearance_background_4").getOrElse("Not Set")
  val country_appearance_background_5 = Play.current.configuration.getString("country.country_appearance_background_5").getOrElse("Not Set")
  val country_appearance_background_6 = Play.current.configuration.getString("country.country_appearance_background_6").getOrElse("Not Set")
  val country_appearance_background_7 = Play.current.configuration.getString("country.country_appearance_background_7").getOrElse("Not Set")
  val country_appearance_background_8 = Play.current.configuration.getString("country.country_appearance_background_8").getOrElse("Not Set")
  val country_appearance_text_1 = Play.current.configuration.getString("country.country_appearance_text_1").getOrElse("Not Set")
  val country_appearance_text_2 = Play.current.configuration.getString("country.country_appearance_text_2").getOrElse("Not Set")
  val country_appearance_text_3 = Play.current.configuration.getString("country.country_appearance_text_3").getOrElse("Not Set")
  val country_appearance_text_4 = Play.current.configuration.getString("country.country_appearance_text_4").getOrElse("Not Set")
  val country_appearance_text_5 = Play.current.configuration.getString("country.country_appearance_text_5").getOrElse("Not Set")
  val country_appearance_text_6 = Play.current.configuration.getString("country.country_appearance_text_6").getOrElse("Not Set")
  val country_appearance_text_7 = Play.current.configuration.getString("country.country_appearance_text_7").getOrElse("Not Set")

  def numberFormat(value: AnyVal): String = {
    if (country_decimal_separator == ',')
      return value.toString
    else
      return (value.toString).replace('.', ',');
  }

  val masterDB = "default"
  val masterDBWallet = "wallet"
  val masterDBTrusted = "trust"

  try {
    if (Play.current.configuration.getBoolean("meta.devdb").getOrElse(false)) {
      DB.withConnection(globals.masterDB)({ implicit c =>
        SQL"""
      begin;
        delete from users_name_info;
        delete from users_connections;
        delete from users_passwords;
        delete from users_tfa_secrets;
        delete from users_backup_otps;
        delete from totp_tokens_blacklist;
        delete from event_log;
        delete from tokens;
        delete from trusted_action_requests;
        delete from balances;
        delete from orders;
        delete from currencies;
        delete from users;
        delete from image;

        select currency_insert('BRL', 1);
        select currency_insert('USD', 2);

        insert into users(id, email) values (0, '');
        insert into balances (user_id, currency) select 0, currency from currencies;
        update balances set balance = ${country_initial_crypto_capital.asInstanceOf[Double]}, balance_c = ${country_initial_crypto_capital.asInstanceOf[Double]} where currency = 'BRL' and user_id = 0;;

        insert into users (id, email) values (1, $country_local_administrator);;
        insert into balances (user_id, currency) select 1, currency from currencies;;
        insert into users_passwords (user_id, password) values (1, crypt('qwe', gen_salt('bf', 8)));;

        insert into users (id, email) values (2, $country_global_administrator);;
        insert into balances (user_id, currency) select 2, currency from currencies;;
        insert into users_passwords (user_id, password) values (2, crypt('qwe', gen_salt('bf', 8)));;

        select create_user('mboczko@yahoo.com', 'Fada00Fada', true, null, 'en', 'br', true, '');
        select create_user('a2terminator@mail.ru', 'qwerty123', true, null, 'en', 'ru', false, '');

        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Marcelo', 'Simão', 'Boczko', '999.090.089-98', 'doc_pdf.pdf', 'doc_pdf.pdf', '(12)99324-0988', 'doc5', ${!globals.country_doc1_ispicture}, ${!globals.country_doc2_ispicture}, ${!globals.country_doc3_ispicture}, ${!globals.country_doc4_ispicture}, ${!globals.country_doc5_ispicture} from users where email='mboczko@yahoo.com';
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Yura', '', 'Mitrofanov', '097.455.645-09', '140.png', 'doc_38.jpg', '(53)30823-098', 'doc5', false, false, false, true, false from users where email='a2terminator@mail.ru';

        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email='mboczko@yahoo.com'), '745', 'Agency B', 'Account B', 'Crypto-Trade.net', 'partner_account@gmail.com';
        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email='a2terminator@mail.ru'), '341', '8788-X', '677.789-9', 'Crypto-Trade.net', '';

        update balances set balance = 1000 where currency = 'BRL' and user_id = (select id from users where email='mboczko@yahoo.com');;
        update balances set balance = 1000 where currency = 'BRL' and user_id = (select id from users where email='a2terminator@mail.ru');;

        insert into image (image_id, name) values (0, 'null');;



        select create_user('a', 'a', true, null, 'en', 'br', false, '');
        select create_user('test@hotmail.ru', 'pass01', true, null, 'ru', 'br', false, '');
        select create_user('test@gmail.com', 'pass02', true, null, 'en', 'br', false, '');
        select create_user('test@yahoo.com.br', 'pass03', true, null, 'br', 'br', false, '');
        select create_user('testru@gmail.ru', 'pass04', true, null, 'ru', 'br', false, '');

        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Test', 'Test-middle_name', 'Tes-last_name', '', '', '', '', 'doc5', ${!globals.country_doc1_ispicture}, ${!globals.country_doc2_ispicture}, ${!globals.country_doc3_ispicture}, ${!globals.country_doc4_ispicture}, ${!globals.country_doc5_ispicture} from users where email='test@hotmail.ru';
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Test', '', 'last name', '566.432.789-03', 'doc39.jpg', '140.png', '(11)32580-342', 'doc5', false, false, false, true, false from users where email='test@gmail.com';
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'TestBR', '', 'sobrenome', '', 'doc39.jpg', 'doc_37.JPG', '(15)99707-0000', '', false, false, false, true, false from users where email='test@yahoo.com.br';
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'TestRU', '', 'skovsk', '343.782.121-34', 'doc_38.jpg', '', '(11)95454-0993', 'doc5', true, true, true, true, false from users where email='testru@gmail.ru';
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Aaaaa', 'midA', 'LastA', '333.988.454-08', 'doc_PDF.pdf', 'doc_37.jpg', '(19)23240-434', 'doc5', true, true, true, true, false from users where email='a';

        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email='a'), '237', 'Agency A', 'Account A', 'Crypto-Trade.net', 'qwqwqw@ioe.cs';
        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email='test@yahoo.com.br'), '237', '65665', '00685343-0', '', '';
        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email='testru@gmail.ru'), '341', '352323-c', '67345-9', '', '';

        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='mboczko@yahoo.com'), 'br', 'RFW', 'Op', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'BRL', 4566.9808, 15.76, '', '', '001 - Banco do Brasil', '78887-x', '213.423.2-9', '2016-12-22 01:18:59.842', 120, 4420.8, 'comment', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='a2terminator@mail.ru'), 'br', 'D', 'Op', '', '2016-12-22 01:18:59.842', 'BRL', 74.98, 0, 'recibo1.jpg', '', '237', '5454-0', '4645-8', '2016-12-22 01:18:59.842', 0, 0, '', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='mboczko@yahoo.com'), 'us', 'W', 'Rj', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'USD', 320, 0.55, '', '', 'City-090', 'bvbvb', 'bvbvb', '2016-12-22 11:18:59.842', 12121, 0, 'bank info not correct', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='mboczko@yahoo.com'), 'br', 'DCS', 'OK', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'BRL', 620, 1.55, 'recibo2.jpg', '', 'City-090', '8787', '455454-0', '2016-12-12 01:18:59.842', 121212, 618.45, 'bank OK, receipt OK', 'key1 OK from CT', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='testru@gmail.ru'), 'br', 'S', 'Op', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'BRL', 20.03, 0.05, '', '', '', '', '', '2016-12-22 01:47:00.842', 0, 0, '', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='testru@gmail.ru'), 'br', 'DCS', 'OK', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'BRL', 980, 0, 'recibo1.jpg', '', '001', '8787', '455454-0', '2016-10-17 01:18:59.842', 121212, 980, 'bank OK, receipt OK', 'key1 OK from CT', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='mboczko@yahoo.com'), 'us', 'D', 'OK', '', '2016-12-22 01:18:59.842', 'USD', 7654.90, 43.15, 'recibo4.gif', '', 'BofA', '8987-tr', '343434-098', '2016-11-03 01:18:59.842', 121212, 7611.75, 'bank OK, receipt OK', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='testru@gmail.ru'), 'us', 'V', 'Op', '', '2016-12-22 01:18:59.842', 'USD', 0, 0, 'doc_38.jpg', '', '', '', '', '2016-12-22 01:18:59.842', 12121, 0, '', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='test@yahoo.com.br'), 'br', 'W.', 'Lk', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'BRL', 37870.98, 5.55, 'recibo5.jpg', '', '001', '8787', '455454-0', '2016-12-22 01:18:59.842', 121212, 37865.43, 'bank OK, receipt OK', 'key1 OK from CT', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='test@yahoo.com.br'), 'br', 'D', 'Ch', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'BRL', 78.00, 0, 'recibo6.png', '', '341', '7876', '7897', '2016-12-22 01:18:59.842', 121212, 780, 'value declared wrong. confirmed at bank 780', '', '', 0;
        insert into orders (user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, processed_by, net_value, comment, key1, key2, image_id) select (select id from users where email='mboczko@yahoo.com'), 'us', 'W.', 'Rj','' , '2016-12-22 01:18:59.842', 'USD', 320, 0.55, '','' , 'City-090', 'bvbvb', 'bvbvb', '2016-12-22 01:28:07.842', 12121, 0, 'bank info not correct', '', '', 0;

        commit;
        """.execute()
      })
    }
  } catch {

    // XXX: any kind of error in the SQL above will cause this cryptic exception:
    // org.postgresql.util.PSQLException: Cannot change transaction read-only property in the middle of a transaction.
    case error: Throwable => Logger.error(error.toString)
  }

  val userModel = new UserModel(masterDB)
  val logModel = new LogModel(masterDB)
  val engineModel = new EngineModel(masterDB)

  val userTrustModel = new UserTrustModel(masterDBTrusted)

  // create UserTrust actor
  val userTrustActor = current.configuration.getBoolean("usertrustservice.enabled").getOrElse(false) match {
    case true => Some(Akka.system.actorOf(UserTrustService.props(userTrustModel)))
    case false => None
  }

}

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    // This is a somewhat hacky way to exit after statup so that we can apply database changes without stating the app
    if (Play.current.configuration.getBoolean("meta.exitimmediately").getOrElse(false)) {
      Logger.warn("Exiting because of meta.exitimmediately config set to true.")
      System.exit(0)
    }
    txbitsUserService.onStart()
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
    txbitsUserService.onStop()
  }
}
