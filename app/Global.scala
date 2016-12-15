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
  val country_system_name = Play.current.configuration.getString("country.country_system_name").getOrElse("Not Set")
  val country_site_name = Play.current.configuration.getString("country.country_site_name").getOrElse("Not Set")
  val country_site_url1 = Play.current.configuration.getString("country.country_site_url1").getOrElse("Not Set")
  val country_currency_symbol = Play.current.configuration.getString("country.country_currency_symbol").getOrElse("Not Set")
  val country_currency_code = Play.current.configuration.getString("country.country_currency_code").getOrElse("Not Set")
  val country_currency_crypto = Play.current.configuration.getString("country.country_currency_crypto").getOrElse("Not Set")
  val country_currency_name = Play.current.configuration.getString("country.country_currency_name").getOrElse("Not Set")
  val country_currency_name_plural = Play.current.configuration.getString("country.country_currency_name_plural").getOrElse("Not Set")
  val country_critical_value1 = Play.current.configuration.getInt("country.country_critical_value1").getOrElse(0)
  val country_critical_value2 = Play.current.configuration.getInt("country.country_critical_value2").getOrElse(0)
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
  val country_global_administrator = Play.current.configuration.getString("country.country_global_administrator").getOrElse("Not Set")
  val country_local_administrator = Play.current.configuration.getString("country.country_local_administrator").getOrElse("Not Set")
  val country_fee_local_doc_verification = Play.current.configuration.getDouble("country.country_fee_local_doc_verification").getOrElse(0)
  val country_fee_global_deposit_percent = Play.current.configuration.getDouble("country.country_fee_global_deposit_percent").getOrElse(0)
  val country_fee_local_deposit_percent = Play.current.configuration.getDouble("country.country_fee_local_deposit_percent").getOrElse(0)
  val country_fee_local_deposit_nominal = Play.current.configuration.getDouble("country.country_fee_local_deposit_nominal").getOrElse(0)
  val country_fee_global_withdrawal_percent = Play.current.configuration.getDouble("country.country_fee_global_withdrawal_percent").getOrElse(0)
  val country_fee_local_withdrawal_percent = Play.current.configuration.getDouble("country.country_fee_local_withdrawal_percent").getOrElse(0)
  val country_fee_local_withdrawal_nominal_workbank = Play.current.configuration.getDouble("country.country_fee_local_withdrawal_nominal_workbank").getOrElse(0)
  val country_fee_local_withdrawal_nominal_notworkbank = Play.current.configuration.getDouble("country.country_fee_local_withdrawal_nominal_notworkbank").getOrElse(0)
  val country_fee_global_send_percent = Play.current.configuration.getDouble("country.country_fee_global_send").getOrElse(0)
  val country_fee_local_send = Play.current.configuration.getDouble("country.country_fee_local_send").getOrElse(0)
  val country_fee_global_tofiat = Play.current.configuration.getDouble("country.country_fee_global_tofiat").getOrElse(0)
  val country_fee_local_tofiat = Play.current.configuration.getDouble("country.country_fee_local_tofiat").getOrElse(0)
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
      delete from users_address_info;
      delete from users;
      delete from orders;

      select currency_insert('BRL',1);
      select currency_insert('BRLc',2);
      select currency_insert('EUR',3);
      select currency_insert('EURc',4);
      select currency_insert('USD',5);
      select currency_insert('USDc',6);

      insert into users(id, email) values (0, '');
      insert into balances (user_id, currency) select 0, currency from currencies;

      select create_user('mboczko@yahoo.com', 'Fada00Fada', true, null, 'en');
      select create_user('a2terminator@mail.ru', 'qwerty123', true, null, 'en');
      select create_user('test@hotmail.ru', 'pass01', true, null, 'ru');
      select create_user('test@gmail.com', 'pass02', true, null, 'en');
      select create_user('test@yahoo.com.br', 'pass03', true, null, 'br');
      select create_user('testru@gmail.ru', 'pass04', true, null, 'ru');

      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Marcelo', 'Boczko', 'Simao', 'Mr.' from users where email='mboczko@yahoo.com';
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Yura', 'Mitrofanov', '', 'Mr.' from users where email='a2terminator@mail.ru';
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Test', 'Test-Surname', 'Tes-middle', 'Mr.' from users where email='test@hotmail.ru';
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Test', 'Sur', '', 'Ms.' from users where email='test@gmail.com';
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'TestBR', 'sobrenome', '', 'Mr.' from users where email='test@yahoo.com.br';
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'TestRU', 'skovsk', '', 'Mr.' from users where email='testru@gmail.ru';


      insert into orders (order_id, user_id, country_id, user_email, type, creation) select 1, 852, 55, 'mboczko@yahoo.com', 'RFW', 1;
      insert into orders (order_id, user_id, country_id, user_email, type, creation) select 2, 852, 55, 'mboczko@yahoo.com', 'D', 2;
      insert into orders (order_id, user_id, country_id, user_email, type, creation) select 3, 881, 1, 'test@gmail.com', 'W', 3;
      insert into orders (order_id, user_id, country_id, user_email, type, creation) select 4, 881, 1, 'test@gmail.com', 'D', 5;

      commit;
      """.execute()

        // fee_global_owner, fee_local_owner, fee_global_deposit_percent, fee_local_deposit_percent, fee_local_deposit_nominal, fee_global_withdrawal_percent, fee_local_withdrawal_percent, fee_local_withdrawal_nominal, fee_global_send_percent, fee_local_send_percent, fee_global_tofiat_percent, fee_local_tofiat_percent, fee_local_doc_verification, appearance_pic1, appearance_pic2, appearance_color1, appearance_color2, appearance_color3, appearance_color4, appearance_color5
        // 'a2terminator@mail.ru', 'mboczko@yahoo.com', 0, 0, 0, 0.1, 0.1, 8.5, 0, 0, 0.05, 0.05, 0, './img/logo_br1.png', './img/logo_br2.png', '#883399', '#883399', '#883399', '#883399', '#883399';
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
