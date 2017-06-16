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
  val country = "country"
  /* Country Regional Settings */
  val country_code = Play.current.configuration.getString(country.concat(".country_code")).getOrElse("Not Set")
  val country_name = Play.current.configuration.getString(country.concat(".country_name")).getOrElse("Not Set")
  val country_system_name = Play.current.configuration.getString(country.concat(".country_system_name")).getOrElse("Direct-Pay")
  val country_site_name = Play.current.configuration.getString(country.concat(".country_site_name")).getOrElse("Not Set")
  val country_site_url1 = Play.current.configuration.getString(country.concat(".country_site_url1")).getOrElse("Not Set")
  val country_currency_symbol = Play.current.configuration.getString(country.concat(".country_currency_symbol")).getOrElse("Not Set")
  val country_currency_code = Play.current.configuration.getString(country.concat(".country_currency_code")).getOrElse("Not Set")
  val country_currency_crypto = Play.current.configuration.getString(country.concat(".country_currency_crypto")).getOrElse("Not Set")
  val country_currency_name = Play.current.configuration.getString(country.concat(".country_currency_name")).getOrElse("Not Set")
  val country_currency_name_plural = Play.current.configuration.getString(country.concat(".country_currency_name_plural")).getOrElse("Not Set")
  val country_decimal_separator = Play.current.configuration.getString(country.concat(".country_decimal_separator")).getOrElse("Not Set")
  val country_operations_organized = Play.current.configuration.getString(country.concat(".country_operations_organized")).getOrElse("Not Set")
  val country_iban = Play.current.configuration.getBoolean(country.concat(".country_iban")).getOrElse(false)
  val country_system_initial_crypto_capital = Play.current.configuration.getDouble(country.concat(".country_system_initial_crypto_capital")).getOrElse(0)
  val country_minimum_value = Play.current.configuration.getInt(country.concat(".country_minimum_value")).getOrElse(0)
  val country_critical_value1 = Play.current.configuration.getInt(country.concat(".country_critical_value1")).getOrElse(0)
  val country_critical_value2 = Play.current.configuration.getInt(country.concat(".country_critical_value2")).getOrElse(0)
  val country_minimum_difference = Play.current.configuration.getDouble(country.concat(".country_minimum_difference")).getOrElse(0.03)
  val country_partner1 = Play.current.configuration.getString(country.concat(".country_partner1")).getOrElse("Not Set")
  val country_partner1_url = Play.current.configuration.getString(country.concat(".country_partner1_url")).getOrElse("Not Set")
  val country_partner1_info = Play.current.configuration.getString(country.concat(".country_partner1_info")).getOrElse("Not Set")
  val country_partner1_account = Play.current.configuration.getString(country.concat(".country_partner1_account")).getOrElse("Not Set")
  val country_partner1_color = Play.current.configuration.getString(country.concat(".country_partner1_color")).getOrElse("Not Set")
  val country_partner1_picture = Play.current.configuration.getString(country.concat(".country_partner1_picture")).getOrElse("Not Set")
  val country_partner2 = Play.current.configuration.getString(country.concat(".country_partner2")).getOrElse("Not Set")
  val country_partner2_url = Play.current.configuration.getString(country.concat(".country_partner2_url")).getOrElse("Not Set")
  val country_partner2_info = Play.current.configuration.getString(country.concat(".country_partner2_info")).getOrElse("Not Set")
  val country_partner2_account = Play.current.configuration.getString(country.concat(".country_partner2_account")).getOrElse("Not Set")
  val country_partner2_color = Play.current.configuration.getString(country.concat(".country_partner2_color")).getOrElse("Not Set")
  val country_partner2_picture = Play.current.configuration.getString(country.concat(".country_partner2_picture")).getOrElse("Not Set")
  val country_doc1_name = Play.current.configuration.getString(country.concat(".country_doc1_name")).getOrElse("Not Set")
  val country_doc1_required = Play.current.configuration.getBoolean(country.concat(".country_doc1_required")).getOrElse(false)
  val country_doc1_ispicture = Play.current.configuration.getBoolean(country.concat(".country_doc1_ispicture")).getOrElse(false)
  val country_doc1_format = Play.current.configuration.getString(country.concat(".country_doc1_format")).getOrElse("")
  val country_doc2_name = Play.current.configuration.getString(country.concat(".country_doc2_name")).getOrElse("Not Set")
  val country_doc2_required = Play.current.configuration.getBoolean(country.concat(".country_doc2_required")).getOrElse(false)
  val country_doc2_ispicture = Play.current.configuration.getBoolean(country.concat(".country_doc2_ispicture")).getOrElse(false)
  val country_doc2_format = Play.current.configuration.getString(country.concat(".country_doc2_format")).getOrElse("")
  val country_doc3_name = Play.current.configuration.getString(country.concat(".country_doc3_name")).getOrElse("Not Set")
  val country_doc3_required = Play.current.configuration.getBoolean(country.concat(".country_doc3_required")).getOrElse(false)
  val country_doc3_ispicture = Play.current.configuration.getBoolean(country.concat(".country_doc3_ispicture")).getOrElse(false)
  val country_doc3_format = Play.current.configuration.getString(country.concat(".country_doc3_format")).getOrElse("")
  val country_doc4_name = Play.current.configuration.getString(country.concat(".country_doc4_name")).getOrElse("Not Set")
  val country_doc4_required = Play.current.configuration.getBoolean(country.concat(".country_doc4_required")).getOrElse(false)
  val country_doc4_ispicture = Play.current.configuration.getBoolean(country.concat(".country_doc4_ispicture")).getOrElse(false)
  val country_doc4_format = Play.current.configuration.getString(country.concat(".country_doc4_format")).getOrElse("")
  val country_doc5_name = Play.current.configuration.getString(country.concat(".country_doc5_name")).getOrElse("Not Set")
  val country_doc5_required = Play.current.configuration.getBoolean(country.concat(".country_doc5_required")).getOrElse(false)
  val country_doc5_ispicture = Play.current.configuration.getBoolean(country.concat(".country_doc5_ispicture")).getOrElse(false)
  val country_doc5_format = Play.current.configuration.getString(country.concat(".country_doc5_format")).getOrElse("")
  val country_preferential_bank1_code = Play.current.configuration.getString(country.concat(".country_preferential_bank1_code")).getOrElse("Not Set")
  val country_preferential_bank1_name = Play.current.configuration.getString(country.concat(".country_preferential_bank1_name")).getOrElse("Not Set")
  val country_preferential_bank1_agency = Play.current.configuration.getString(country.concat(".country_preferential_bank1_agency")).getOrElse("Not Set")
  val country_preferential_bank1_account = Play.current.configuration.getString(country.concat(".country_preferential_bank1_account")).getOrElse("Not Set")
  val country_preferential_bank1_reference = Play.current.configuration.getString(country.concat(".country_preferential_bank1_reference")).getOrElse("Not Set")
  val country_preferential_bank2_code = Play.current.configuration.getString(country.concat(".country_preferential_bank2_code")).getOrElse("Not Set")
  val country_preferential_bank2_name = Play.current.configuration.getString(country.concat(".country_preferential_bank2_name")).getOrElse("Not Set")
  val country_preferential_bank2_agency = Play.current.configuration.getString(country.concat(".country_preferential_bank2_agency")).getOrElse("Not Set")
  val country_preferential_bank2_account = Play.current.configuration.getString(country.concat(".country_preferential_bank2_account")).getOrElse("Not Set")
  val country_preferential_bank2_reference = Play.current.configuration.getString(country.concat(".country_preferential_bank2_reference")).getOrElse("Not Set")
  val country_preferential_bank3_code = Play.current.configuration.getString(country.concat(".country_preferential_bank3_code")).getOrElse("Not Set")
  val country_preferential_bank3_name = Play.current.configuration.getString(country.concat(".country_preferential_bank3_name")).getOrElse("Not Set")
  val country_preferential_bank3_agency = Play.current.configuration.getString(country.concat(".country_preferential_bank3_agency")).getOrElse("Not Set")
  val country_preferential_bank3_account = Play.current.configuration.getString(country.concat(".country_preferential_bank3_account")).getOrElse("Not Set")
  val country_preferential_bank3_reference = Play.current.configuration.getString(country.concat(".country_preferential_bank3_reference")).getOrElse("Not Set")
  val country_preferential_bank4_code = Play.current.configuration.getString(country.concat(".country_preferential_bank4_code")).getOrElse("Not Set")
  val country_preferential_bank4_name = Play.current.configuration.getString(country.concat(".country_preferential_bank4_name")).getOrElse("Not Set")
  val country_preferential_bank4_agency = Play.current.configuration.getString(country.concat(".country_preferential_bank4_agency")).getOrElse("Not Set")
  val country_preferential_bank4_account = Play.current.configuration.getString(country.concat(".country_preferential_bank4_account")).getOrElse("Not Set")
  val country_preferential_bank4_reference = Play.current.configuration.getString(country.concat(".country_preferential_bank4_reference")).getOrElse("Not Set")
  val country_bank1_code = Play.current.configuration.getString(country.concat(".country_bank1_code")).getOrElse("Not Set")
  val country_bank1_name = Play.current.configuration.getString(country.concat(".country_bank1_name")).getOrElse("Not Set")
  val country_bank2_code = Play.current.configuration.getString(country.concat(".country_bank2_code")).getOrElse("Not Set")
  val country_bank2_name = Play.current.configuration.getString(country.concat(".country_bank2_name")).getOrElse("Not Set")
  val country_bank3_code = Play.current.configuration.getString(country.concat(".country_bank3_code")).getOrElse("Not Set")
  val country_bank3_name = Play.current.configuration.getString(country.concat(".country_bank3_name")).getOrElse("Not Set")
  val country_bank4_code = Play.current.configuration.getString(country.concat(".country_bank4_code")).getOrElse("Not Set")
  val country_bank4_name = Play.current.configuration.getString(country.concat(".country_bank4_name")).getOrElse("Not Set")
  val country_bank5_code = Play.current.configuration.getString(country.concat(".country_bank5_code")).getOrElse("Not Set")
  val country_bank5_name = Play.current.configuration.getString(country.concat(".country_bank5_name")).getOrElse("Not Set")
  val country_bank6_code = Play.current.configuration.getString(country.concat(".country_bank6_code")).getOrElse("Not Set")
  val country_bank6_name = Play.current.configuration.getString(country.concat(".country_bank6_name")).getOrElse("Not Set")
  val country_bank7_code = Play.current.configuration.getString(country.concat(".country_bank7_code")).getOrElse("Not Set")
  val country_bank7_name = Play.current.configuration.getString(country.concat(".country_bank7_name")).getOrElse("Not Set")
  val country_bank8_code = Play.current.configuration.getString(country.concat(".country_bank8_code")).getOrElse("Not Set")
  val country_bank8_name = Play.current.configuration.getString(country.concat(".country_bank8_name")).getOrElse("Not Set")
  val country_bank9_code = Play.current.configuration.getString(country.concat(".country_bank9_code")).getOrElse("Not Set")
  val country_bank9_name = Play.current.configuration.getString(country.concat(".country_bank9_name")).getOrElse("Not Set")
  val country_bank10_code = Play.current.configuration.getString(country.concat(".country_bank10_code")).getOrElse("Not Set")
  val country_bank10_name = Play.current.configuration.getString(country.concat(".country_bank10_name")).getOrElse("Not Set")
  val country_bank11_code = Play.current.configuration.getString(country.concat(".country_bank11_code")).getOrElse("Not Set")
  val country_bank11_name = Play.current.configuration.getString(country.concat(".country_bank11_name")).getOrElse("Not Set")
  val country_bank12_code = Play.current.configuration.getString(country.concat(".country_bank12_code")).getOrElse("Not Set")
  val country_bank12_name = Play.current.configuration.getString(country.concat(".country_bank12_name")).getOrElse("Not Set")
  val country_bank13_code = Play.current.configuration.getString(country.concat(".country_bank13_code")).getOrElse("Not Set")
  val country_bank13_name = Play.current.configuration.getString(country.concat(".country_bank13_name")).getOrElse("Not Set")
  val country_bank14_code = Play.current.configuration.getString(country.concat(".country_bank14_code")).getOrElse("Not Set")
  val country_bank14_name = Play.current.configuration.getString(country.concat(".country_bank14_name")).getOrElse("Not Set")
  val country_bank15_code = Play.current.configuration.getString(country.concat(".country_bank15_code")).getOrElse("Not Set")
  val country_bank15_name = Play.current.configuration.getString(country.concat(".country_bank15_name")).getOrElse("Not Set")
  val country_bank16_code = Play.current.configuration.getString(country.concat(".country_bank16_code")).getOrElse("Not Set")
  val country_bank16_name = Play.current.configuration.getString(country.concat(".country_bank16_name")).getOrElse("Not Set")
  val country_bank17_code = Play.current.configuration.getString(country.concat(".country_bank17_code")).getOrElse("Not Set")
  val country_bank17_name = Play.current.configuration.getString(country.concat(".country_bank17_name")).getOrElse("Not Set")
  val country_bank18_code = Play.current.configuration.getString(country.concat(".country_bank18_code")).getOrElse("Not Set")
  val country_bank18_name = Play.current.configuration.getString(country.concat(".country_bank18_name")).getOrElse("Not Set")
  val country_bank19_code = Play.current.configuration.getString(country.concat(".country_bank19_code")).getOrElse("Not Set")
  val country_bank19_name = Play.current.configuration.getString(country.concat(".country_bank19_name")).getOrElse("Not Set")
  val country_bank20_code = Play.current.configuration.getString(country.concat(".country_bank20_code")).getOrElse("Not Set")
  val country_bank20_name = Play.current.configuration.getString(country.concat(".country_bank20_name")).getOrElse("Not Set")
  val country_local_administrator = Play.current.configuration.getString(country.concat(".country_local_administrator")).getOrElse("Not Set")
  val country_global_administrator = Play.current.configuration.getString(country.concat(".country_global_administrator")).getOrElse("Not Set")
  val country_nominal_fee_withdrawal_preferential_bank = Play.current.configuration.getDouble(country.concat(".country_nominal_fee_withdrawal_preferential_bank ")).getOrElse(0)
  val country_nominal_fee_withdrawal_not_preferential_bank = Play.current.configuration.getDouble(country.concat(".country_nominal_fee_withdrawal_not_preferential_bank")).getOrElse(0)
  val country_fees_global_percentage = Play.current.configuration.getDouble(country.concat(".country_fees_global_percentage")).getOrElse(0)
  val country_fee_deposit_percent = Play.current.configuration.getDouble(country.concat(".country_fee_deposit_percent")).getOrElse(0)
  val country_fee_withdrawal_percent = Play.current.configuration.getDouble(country.concat(".country_fee_withdrawal_percent")).getOrElse(0)
  val country_fee_send_percent = Play.current.configuration.getDouble(country.concat(".country_fee_send_percent")).getOrElse(0)

  val country_fee_tofiat_percent = if (country_operations_organized == "direct") { 0 } else { Play.current.configuration.getDouble(country.concat(".country_fee_tofiat_percent")).getOrElse(0) }

  val country_fee_tofiat_minimum_rate_percent = Play.current.configuration.getDouble(country.concat(".country_fee_tofiat_minimum_rate_percent")).getOrElse(0)
  val country_appearance_pic1 = Play.current.configuration.getString(country.concat(".country_appearance_pic1")).getOrElse("Not Set")
  val country_appearance_pic2 = Play.current.configuration.getString(country.concat(".country_appearance_pic2")).getOrElse("Not Set")
  val country_appearance_background_1 = Play.current.configuration.getString(country.concat(".country_appearance_background_1")).getOrElse("Not Set")
  val country_appearance_background_2 = Play.current.configuration.getString(country.concat(".country_appearance_background_2")).getOrElse("Not Set")
  val country_appearance_background_3 = Play.current.configuration.getString(country.concat(".country_appearance_background_3")).getOrElse("Not Set")
  val country_appearance_background_4 = Play.current.configuration.getString(country.concat(".country_appearance_background_4")).getOrElse("Not Set")
  val country_appearance_background_5 = Play.current.configuration.getString(country.concat(".country_appearance_background_5")).getOrElse("Not Set")
  val country_appearance_background_6 = Play.current.configuration.getString(country.concat(".country_appearance_background_6")).getOrElse("Not Set")
  val country_appearance_background_7 = Play.current.configuration.getString(country.concat(".country_appearance_background_7")).getOrElse("Not Set")
  val country_appearance_background_8 = Play.current.configuration.getString(country.concat(".country_appearance_background_8")).getOrElse("Not Set")
  val country_appearance_text_1 = Play.current.configuration.getString(country.concat(".country_appearance_text_1")).getOrElse("Not Set")
  val country_appearance_text_2 = Play.current.configuration.getString(country.concat(".country_appearance_text_2")).getOrElse("Not Set")
  val country_appearance_text_3 = Play.current.configuration.getString(country.concat(".country_appearance_text_3")).getOrElse("Not Set")
  val country_appearance_text_4 = Play.current.configuration.getString(country.concat(".country_appearance_text_4")).getOrElse("Not Set")
  val country_appearance_text_5 = Play.current.configuration.getString(country.concat(".country_appearance_text_5")).getOrElse("Not Set")
  val country_appearance_text_6 = Play.current.configuration.getString(country.concat(".country_appearance_text_6")).getOrElse("Not Set")
  val country_appearance_text_7 = Play.current.configuration.getString(country.concat(".country_appearance_text_7")).getOrElse("Not Set")

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

        insert into image (image_id, name) values (0, 'null');

        select currency_insert(1, 'BRL', 'br', 0, 0);
        select currency_insert(2, 'USD', 'us', 0, 0);

        insert into users(id, email) values (0, '');
        insert into balances (user_id, currency) select 0, currency from currencies;
        update balances set balance = 0, balance_c = ${country_system_initial_crypto_capital.asInstanceOf[Double]} where currency = 'BRL' and user_id = 0;
        update balances set balance = 0, balance_c = 70000 where currency = 'USD' and user_id = 0;

        select create_user($country_local_administrator, 'Fada00Fada', true, null, 'en', 'br', true, '');
        select create_user($country_global_administrator, 'aaa222', true, null, 'en', 'ru', true, '');

        select create_user($country_partner1_account, 'aaa222', true, null, 'en', 'fr', true, '');
        select create_user($country_partner2_account, 'aaa222', true, null, 'en', 'fr', true, '');

        select insert_as_admin('us', $country_global_administrator, 'admin_g1');
        select insert_as_admin('us', $country_local_administrator, 'admin_l1');
        select insert_as_admin('br', $country_global_administrator, 'admin_g1');
        select insert_as_admin('br', $country_local_administrator, 'admin_l1');
        select insert_as_admin('br', $country_local_administrator, 'admin_o1');

        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Marcelo', 'Simão', 'Boczko', '999.090.089-98', 'doc_pdf.pdf', 'doc_pdf.pdf', '(12)99324-0988', 'doc5', ${!globals.country_doc1_ispicture}, ${!globals.country_doc2_ispicture}, ${!globals.country_doc3_ispicture}, ${!globals.country_doc4_ispicture}, ${!globals.country_doc5_ispicture} from users where email=$country_local_administrator;
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Yura', '', 'Mitrofanov', '097.455.645-09', '140.png', 'doc_38.jpg', '(53)30823-098', 'doc5', false, false, false, true, false from users where email=$country_global_administrator;
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, $country_partner1, $country_partner1_url, $country_partner1_info, '', '', '', '', '', false, false, false, false, false from users where email=$country_partner1_account;
        insert into users_name_info (user_id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, $country_partner2, $country_partner1_url, $country_partner1_info, '', '', '', '', '', false, false, false, false, false from users where email=$country_partner2_account;

        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email=$country_local_administrator), '745', 'Agency B', 'Account B', 'Crypto-Trade.net', 'partner_account@gmail.com';
        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email=$country_global_administrator), '341', '8788-X', '677.789-9', 'Crypto-Trade.net', '';
        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email=$country_partner1_account), '', '', '', $country_partner1_url, $country_partner1_account;
        insert into users_connections (user_id, bank, agency, account, partner, partner_account) select (select id from users where email=$country_partner2_account), '', '', '', $country_partner2_url, $country_partner2_account;

        update balances set balance = 1000 where currency = 'BRL' and user_id = (select id from users where email=$country_local_administrator);;
        update balances set balance = 1000 where currency = 'BRL' and user_id = (select id from users where email=$country_global_administrator);;

        select create_user('a', 'a', true, null, 'en', 'us', false, '');
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

        commit;
        """.execute()
      })
    }
  } catch {

    // XXX: any kind of error in the SQL above will cause this cryptic exception:
    // org.postgresql.util.PSQLException: Cannot change transaction read-only property in the middle of a transaction.
    case error: Throwable => Logger.error(error.toString)
  }

  /*
        --select create_user($country_local_administrator, 'Fada00Fada', true, null, 'en', 'br', true, '');
        --insert into balances (user_id, currency) select (select id from users where email=$country_local_administrator), currency from currencies;;
        --insert into users_passwords (user_id, password) values ((select id from users where email=$country_local_administrator), crypt('qwe', gen_salt('bf', 8)));;

        --select create_user($country_global_administrator, 'qwerty123', true, null, 'en', 'ru', false, '');
        --insert into balances (user_id, currency) select (select id from users where email=$country_global_administrator), currency from currencies;;
        --insert into users_passwords (user_id, password) values ((select id from users where email=$country_global_administrator), crypt('qwe', gen_salt('bf', 8)));;



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


   */

  val userModel = new UserModel(masterDB)
  val logModel = new LogModel(masterDB)
  val engineModel = new EngineModel(masterDB)

  val userTrustModel = new UserTrustModel(masterDBTrusted)

  def calculate_local_fee(order_type: String, initial_value: BigDecimal = 0): BigDecimal = {
    val percentage = (100 - country_fees_global_percentage.asInstanceOf[Double]) * 0.01
    var low_value_fee = 0.0
    if (initial_value < country_minimum_value) {
      low_value_fee = country_minimum_value * 0.02
    }
    if (order_type == "D") {
      return initial_value * country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * percentage + low_value_fee
    } else if (order_type == "S") {
      return initial_value * country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "S.") {
      return initial_value * country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "DCS") {
      return initial_value * (country_fee_deposit_percent.asInstanceOf[Double] + country_fee_send_percent.asInstanceOf[Double]) * 0.01 * percentage + low_value_fee
    } else if (order_type == "W") { // withdrawal to a preferential bank
      return country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage + low_value_fee
    } else if (order_type == "W.") { // withdrawal to a non preferential bank
      return country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage + low_value_fee
    } else if (order_type == "RFW") { // withdrawal to a preferential bank
      return country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * (country_fee_withdrawal_percent.asInstanceOf[Double] + country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage + low_value_fee
    } else if (order_type == "RFW.") { // withdrawal to a non preferential bank
      return country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * (country_fee_withdrawal_percent.asInstanceOf[Double] + country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage + low_value_fee
    } else if (order_type == "F") {
      return initial_value * country_fee_tofiat_percent.asInstanceOf[Double] * 0.01 * percentage
    } else return 0
  }

  def calculate_global_fee(order_type: String, initial_value: BigDecimal = 0): BigDecimal = {
    val percentage = country_fees_global_percentage.asInstanceOf[Double] * 0.01
    if (order_type == "D") {
      return initial_value * country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "S") {
      return initial_value * country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "S.") {
      return initial_value * country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "DCS") {
      return initial_value * (country_fee_deposit_percent.asInstanceOf[Double] + country_fee_send_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "W" || order_type == "W.") {
      return initial_value * country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "RFW" || order_type == "RFW.") {
      return initial_value * (country_fee_withdrawal_percent.asInstanceOf[Double] + country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "F") {
      return initial_value * country_fee_tofiat_percent.asInstanceOf[Double] * 0.01 * percentage
    } else return 0
  }

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
