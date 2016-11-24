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

      select currency_insert('Real',1);
      select currency_insert('BRL-crypto',1);
      select currency_insert('Euro',1);
      select currency_insert('crypto-EUR',1);
      select currency_insert('US dollar',1);
      select currency_insert('US-eletron',1);

      insert into users(id, email) values (0, '');
      insert into balances (user_id, currency) select 0, currency from currencies;
      select create_user('mboczko@yahoo.com', 'Fada00Fada', true, null, 'en');
      select create_user('a2terminator@mail.ru', 'qwerty123', true, null, 'en');
      select create_user('test@hotmail.ru', 'pass01', true, null, 'ru');
      select create_user('test@gmail.com', 'pass02', true, null, 'en');
      select create_user('test@yahoo.com.br', 'pass03', true, null, 'br');
      select create_user('testru@gmail.ru', 'pass04', true, null, 'ru');

      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Marcelo', 'Boczko', '', 'Mr.' from users where email='mboczko@yahoo.com'
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Yura', 'Mitrofanov', '', 'Mr.' from users where email='a2terminator@mail.ru'
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Test', 'Test-Surname', 'Tes-middle', 'Mr.' from users where email='test@hotmail.ru'
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'Test', 'Sur', '', 'Ms.' from users where email='test@gmail.com'
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'TestBR', 'sobrenome', '', 'Mr.' from users where email='test@yahoo.com.br'
      insert into users_name_info (user_id, name, surname, middle_name, prefix) select id, 'TestRU', 'skovsky', '', 'Mr.' from users where email='testru@gmail.ru'
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
