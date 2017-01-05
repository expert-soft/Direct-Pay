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
  val country_critical_value1 = Play.current.configuration.getInt("country.country_critical_value1").getOrElse(0)
  val country_critical_value2 = Play.current.configuration.getInt("country.country_critical_value2").getOrElse(0)
  val country_partner1 = Play.current.configuration.getString("country.country_partner1").getOrElse("Not Set")
  val country_partner1_url = Play.current.configuration.getString("country.country_partner1_url").getOrElse("Not Set")
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
  val country_working_bank1_name = Play.current.configuration.getString("country.country_working_bank1_name").getOrElse("Not Set")
  val country_working_bank1_agency = Play.current.configuration.getString("country.country_working_bank1_agency").getOrElse("Not Set")
  val country_working_bank1_account = Play.current.configuration.getString("country.country_working_bank1_account").getOrElse("Not Set")
  val country_working_bank1_reference = Play.current.configuration.getString("country.country_working_bank1_reference").getOrElse("Not Set")
  val country_working_bank2_name = Play.current.configuration.getString("country.country_working_bank2_name").getOrElse("Not Set")
  val country_working_bank2_agency = Play.current.configuration.getString("country.country_working_bank2_agency").getOrElse("Not Set")
  val country_working_bank2_account = Play.current.configuration.getString("country.country_working_bank2_account").getOrElse("Not Set")
  val country_working_bank2_reference = Play.current.configuration.getString("country.country_working_bank2_reference").getOrElse("Not Set")
  val country_working_bank3_name = Play.current.configuration.getString("country.country_working_bank3_name").getOrElse("Not Set")
  val country_working_bank3_agency = Play.current.configuration.getString("country.country_working_bank3_agency").getOrElse("Not Set")
  val country_working_bank3_account = Play.current.configuration.getString("country.country_working_bank3_account").getOrElse("Not Set")
  val country_working_bank3_reference = Play.current.configuration.getString("country.country_working_bank3_reference").getOrElse("Not Set")
  val country_working_bank4_name = Play.current.configuration.getString("country.country_working_bank4_name").getOrElse("Not Set")
  val country_working_bank4_agency = Play.current.configuration.getString("country.country_working_bank4_agency").getOrElse("Not Set")
  val country_working_bank4_account = Play.current.configuration.getString("country.country_working_bank4_account").getOrElse("Not Set")
  val country_working_bank4_reference = Play.current.configuration.getString("country.country_working_bank4_reference").getOrElse("Not Set")
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
  val country_fees_global_percentage = Play.current.configuration.getDouble("country.country_fees_global_percentage").getOrElse(0)
  val country_nominal_fee_doc_verification = Play.current.configuration.getDouble("country.country_fee_local_doc_verification").getOrElse(0)
  val country_nominal_fee_withdrawal_workbank = Play.current.configuration.getDouble("country.country_nominal_fee_withdrawal_workbank ").getOrElse(0)
  val country_nominal_fee_withdrawal_notworkbank = Play.current.configuration.getDouble("country.country_nominal_fee_withdrawal_notworkbank").getOrElse(0)
  val country_fee_deposit_percent = Play.current.configuration.getDouble("country.country_fee_deposit_percent").getOrElse(0)
  val country_fee_withdrawal_percent = Play.current.configuration.getDouble("country.country_fee_withdrawal_percent").getOrElse(0)
  val country_fee_send_percent = Play.current.configuration.getDouble("country.country_fee_send_percent").getOrElse(0)
  val country_fee_tofiat_percent = Play.current.configuration.getDouble("country.country_fee_tofiat_percent").getOrElse(0)
  val country_appearance_pic1 = Play.current.configuration.getString("country.appearance_pic1").getOrElse("Not Set")
  val country_appearance_pic2 = Play.current.configuration.getString("country.appearance_pic2").getOrElse("Not Set")
  val country_appearance_color1 = Play.current.configuration.getString("country.appearance_color1").getOrElse("Not Set")
  val country_appearance_color2 = Play.current.configuration.getString("country.appearance_color2").getOrElse("Not Set")
  val country_appearance_color3 = Play.current.configuration.getString("country.appearance_color3").getOrElse("Not Set")
  val country_appearance_color4 = Play.current.configuration.getString("country.appearance_color4").getOrElse("Not Set")
  val country_appearance_color5 = Play.current.configuration.getString("country.appearance_color5").getOrElse("Not Set")

  val masterDB = "default"
  val masterDBWallet = "wallet"
  val masterDBTrusted = "trust"

  try {
    if (Play.current.configuration.getBoolean("meta.devdb").getOrElse(false)) {
      DB.withConnection(globals.masterDB)({ implicit c =>
        SQL"""
      begin;
      delete from users_passwords;
      delete from users_tfa_secrets;
      delete from users_backup_otps;
      delete from totp_tokens_blacklist;
      delete from event_log;
      delete from tokens;
      delete from trusted_action_requests;
      delete from balances;
      delete from currencies;
      delete from users_name_info;
      delete from users_connections;
      delete from users;
      delete from orders;

      select currency_insert('BRL', 1, true);
      select currency_insert('BRL-crypto', 2, false);
      select currency_insert('EUR', 3, true);
      select currency_insert('EUR-crypto', 4, false);
      select currency_insert('USD', 5, true);
      select currency_insert('USD-crypto', 6, false);

      insert into users(id, email) values (0, '');
      insert into balances (user_id, currency) select 0, currency from currencies;

      select create_user('a', 'a', true, null, 'en');
      select create_user('mboczko@yahoo.com', 'Fada00Fada', true, null, 'en');
      select create_user('a2terminator@mail.ru', 'qwerty123', true, null, 'en');
      select create_user('test@hotmail.ru', 'pass01', true, null, 'ru');
      select create_user('test@gmail.com', 'pass02', true, null, 'en');
      select create_user('test@yahoo.com.br', 'pass03', true, null, 'br');
      select create_user('testru@gmail.ru', 'pass04', true, null, 'ru');

      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Marcelo', 'Boczko', 'Simão', 'doc1', 'doc2', 'doc3', 'doc4', 'doc5', true, true, true, true, false from users where email='mboczko@yahoo.com';
      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Yura', 'Mitrofanov', '', 'doc1', 'doc2', 'doc3', 'doc4', 'doc5', false, false, false, true, false from users where email='a2terminator@mail.ru';
      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Test', 'Test-Surname', 'Tes-middle', '', '', '', '', '', false, false, false, false, false from users where email='test@hotmail.ru';
      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Test', 'Sur', '', 'doc1', 'doc2', 'doc3', 'doc4', 'doc5', false, false, false, true, false from users where email='test@gmail.com';
      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'TestBR', 'sobrenome', '', '', 'doc2', 'doc3', 'doc4', '', false, false, false, true, false from users where email='test@yahoo.com.br';
      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'TestRU', 'skovsk', '', 'doc1', 'doc2', '', 'doc4', 'doc5', true, true, true, true, false from users where email='testru@gmail.ru';
      insert into users_name_info (user_id, name, surname, middle_name, doc1, doc2, doc3, doc4, doc5, ver1, ver2, ver3, ver4, ver5) select id, 'Aaaaa', 'SurA', 'MidA', 'doc1', 'doc2', 'doc3', 'doc4', 'doc5', true, true, true, true, false from users where email='a';

      insert into users_connections (user_id, bank, agency, account, automatic, partner) select (select id from users where email='a'), '237', 'Agency A', 'Account A', true, 'Crypto-Trade.net';
      insert into users_connections (user_id, bank, agency, account, automatic, partner) select (select id from users where email='mboczko@yahoo.com'), '001', 'Agency B', 'Account B', false, 'Crypto-Trade.net';
      insert into users_connections (user_id, bank, agency, account, automatic, partner) select (select id from users where email='a2terminator@mail.ru'), '341', '8788-X', '677.789-9', false, 'Crypto-Trade.net';
      insert into users_connections (user_id, bank, agency, account, automatic, partner) select (select id from users where email='test@yahoo.com.br'), '237', '65665', '00685343-0', true, '';
      insert into users_connections (user_id, bank, agency, account, automatic, partner) select (select id from users where email='testru@gmail.ru'), '341', '352323-c', '67345-9', false, '';

      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 1, (select id from users where email='mboczko@yahoo.com'), 55, 'RFW', 'Op', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'Rsif', 4566.9808, 15.76, '', '', '001 - Banco do Brasil', '78887-x', '213.423.2-9', '2016-12-22 01:18:59.842', 0, 0, '', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 2, (select id from users where email='a2terminator@mail.ru'), 55, 'D', 'Op', '', '2016-12-22 01:18:59.842', 'Rsif', 74.98, 0, './receipts/902354643533_dec-2016_2', '', '237', '5454-0', '4645-8', '2016-12-22 01:18:59.842', 0, 0, '', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 3, (select id from users where email='mboczko@yahoo.com'), 1, 'W', 'Rj', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'USD', 320, 0.55, '', '', 'City-090', 'bvbvb', 'bvbvb', '2016-12-22 01:18:59.842', 12121, 0, 'bank info not correct', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 4, (select id from users where email='mboczko@yahoo.com'), 1, 'DCS', 'OK', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'USD', 620, 1.55, './receipts/9023724243_dec-2016_1', '', 'City-090', '8787', '455454-0', '2016-12-22 01:18:59.842', 121212, 618.45, 'bank OK, receipt OK', 'key1 OK from CT', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 5, (select id from users where email='testru@gmail.ru'), 55, 'S', 'Op', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'Rsif', 20.03, 0.05, '', '', '', '', '', '', 0, 0, '', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 6, (select id from users where email='testru@gmail.ru'), 55, 'DCS', 'OK', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'Rsif', 980, 0, './receipts/9023724243_dec-2016_1', '', '001', '8787', '455454-0', '2016-12-22 01:18:59.842', 121212, 980, 'bank OK, receipt OK', 'key1 OK from CT', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 7, (select id from users where email='mboczko@yahoo.com'), 1, 'D', 'OK', '', '2016-12-22 01:18:59.842', 'USD', 7654.90, 43.15, './receipts/9023724243_dec-2016_1', '', 'BofA', '8987-tr', '343434-098', '2016-12-22 01:18:59.842', 121212, 7611.75, 'bank OK, receipt OK', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 8, (select id from users where email='testru@gmail.ru'), 1, 'V', 'Op', '', '2016-12-22 01:18:59.842', 'USD', 0, 0, './docs/doc1_9023724243', '', '', '', '', '2016-12-22 01:18:59.842', 12121, 0, '', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 9, (select id from users where email='test@yahoo.com.br'), 55, 'DCS', 'OK', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'Rsif', 37870.98, 5.55, './receipts/9023724243_dec-2016_1', '', '001', '8787', '455454-0', '2016-12-22 01:18:59.842', 121212, 37865.43, 'bank OK, receipt OK', 'key1 OK from CT', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 10, (select id from users where email='test@yahoo.com.br'), 55, 'D', 'Ch', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'Rsif', 78.00, 0, './receipts/9023724243_dec-2016_1', '', '341', '7876', '7897', '2016-12-22 01:18:59.842', 121212, 780, 'value declared wrong. confirmed at bank 780', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 11, (select id from users where email='a'), 55, 'RFW', 'Op', 'Crypto-Trade.net', '2016-12-22 01:18:59.842', 'Rsif', 566.1011, 5.98, './withdrawals/67564544_dec-2016_doc1', '', '001 - Banco do Brasil', '78887-x', '213.423.2-9', '2016-12-22 01:18:59.842', 0, 0, '', '', '';
      insert into orders (order_id, user_id, country_id, order_type, status, partner, created, currency, initial_value, total_fee, doc1, doc2, bank, agency, account, closed, closed_by, closed_value, comment, key1, key2) select 12, (select id from users where email='a'), 55, 'D', 'Op', '', '2016-12-22 01:18:59.842', 'Rsif', 813.05, 0, './receipts/902354643533_dec-2016_2', '', '237', '', '', '', 0, 0, '', '', '';

      commit;
      """.execute()
      })
    }
  } catch {
    /*



*/
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
