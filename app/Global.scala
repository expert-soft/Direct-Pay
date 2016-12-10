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
      delete from country;
      delete from country_docs;
      delete from banks;

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

      insert into country (country_id, country_code, country_name, country_local_name, site_name, site_url1, site_url2, language_name, language_code, currency_symbol, currency_code, currency_crypto, currency_name, currency_name_plural, currency_approximate_value, critical_value) select 1, 'us', 'United States', 'United States', 'Direct Pay', 'www.localsite.com', 'www.altsite.com', 'English', 'en', 'U&', 'USD', 'USD-cry', 'dollar', 'dollars', 1, 5000;
      insert into country (country_id, country_code, country_name, country_local_name, site_name, site_url1, site_url2, language_name, language_code, currency_symbol, currency_code, currency_crypto, currency_name, currency_name_plural, currency_approximate_value, critical_value) select 55, 'br', 'Brazil', 'Brasil', 'Direct Pay', 'www.localsite.com.br', 'www.altsite.com.br', 'Portugues', 'pt', 'U&', 'BRL', 'BRL-cry', 'real', 'reais', 0.3, 5000;

      insert into country_docs (country_id, doc1_name, doc1_required, doc1_ispicture, doc1_format, doc2_name, doc2_required, doc2_ispicture, doc2_format, doc3_name, doc3_required, doc3_ispicture, doc3_format, doc4_name, doc4_required, doc4_ispicture, doc4_format, doc5_name, doc5_required, doc5_ispicture, doc5_format) select 1, 'ID / Passport', true, true, '', 'Address Prove', true, true, '', 'Social Security', true, false, '', 'Phone', true, false, '(999) 99999-9999', 'doc5', false, false, '';
      insert into country_docs (country_id, doc1_name, doc1_required, doc1_ispicture, doc1_format, doc2_name, doc2_required, doc2_ispicture, doc2_format, doc3_name, doc3_required, doc3_ispicture, doc3_format, doc4_name, doc4_required, doc4_ispicture, doc4_format, doc5_name, doc5_required, doc5_ispicture, doc5_format) select 55, 'RG / Passport', true, true, '', 'Address Prove', true, true, '', 'CPF', true, false, '999.999.999-99', 'Phone', true, false, '(99) 99999-9999', 'doc5', false, false, '';

      insert into banks (bank_id, country_id, country_code, bank_code, bank_name) select 1, 55, 'br', '001', 'Banco do Brasil';
      insert into banks (bank_id, country_id, country_code, bank_code, bank_name) select 2, 55, 'br', '237', 'Bradesco';
      insert into banks (bank_id, country_id, country_code, bank_code, bank_name) select 3, 1, 'us', 'BoA21', 'Bank of America';
      insert into banks (bank_id, country_id, country_code, bank_code, bank_name) select 4, 1, 'us', 'LB02', 'Lehmann Brothers';

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
