// TxBits - An open source Bitcoin and crypto currency exchange
// Copyright (C) 2014-2015  Viktor Stanchev & Kirk Zathey
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package controllers.IAPI

import javax.inject.Inject

import controllers.Util
import play.api._
import play.api.i18n.I18nSupport
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._
import play.api.i18n.MessagesApi
import service.{ PGP, TOTPUrl }
import org.postgresql.util.PSQLException
import org.apache.commons.codec.binary.Base64.encodeBase64
import java.security.SecureRandom

import play.api.i18n.{ I18nSupport, Lang, Messages, MessagesApi }
import globals._
import org.joda.time.DateTime

import scala.collection.immutable.Range.Double

class APIv1 @Inject() (val messagesApi: MessagesApi) extends Controller with securesocial.core.SecureSocial with I18nSupport {
  // Json serializable case classes have implicit definitions in their companion objects

  import globals._

  def index = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    Ok(Json.obj())
  }

  def user_name_info = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val user_info = globals.engineModel.UserNameINFO(Some(request.user.id))
    Ok(Json.toJson(user_info.map({ c =>
      Json.obj(
        "first_name" -> c._1,
        "middle_name" -> c._2,
        "last_name" -> c._3,
        "doc1" -> c._4,
        "doc2" -> c._5,
        "doc3" -> c._6,
        "doc4" -> c._7,
        "doc5" -> c._8,
        "bank" -> c._9,
        "agency" -> c._10,
        "account" -> c._11,
        "partner" -> c._12,
        "partner_account" -> c._13
      )
    })
    ))
  }

  def get_bank_data = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val bank_data = globals.engineModel.GetBankData(Some(request.user.id))
    Ok(Json.toJson(bank_data.map({ c =>
      Json.obj(
        "bank" -> c._1,
        "agency" -> c._2,
        "account" -> c._3,
        "partner" -> c._4,
        "partner_account" -> c._5
      )
    })
    ))
  }

  def orders_list = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val search_criteria = (request.request.body \ "search_criteria").asOpt[String]
    val search_value = (request.request.body \ "search_value").asOpt[String]
    val orders_list_info = globals.engineModel.OrderList(Some(request.user.id), search_criteria, search_value)
    Ok(Json.toJson(orders_list_info.map({ c =>
      Json.obj(
        "order_id" -> c._1,
        "user_id" -> c._2,
        "country_id" -> c._3,
        "order_type" -> c._4,
        "status" -> c._5,
        "partner" -> c._6,
        "created" -> c._7,
        "currency" -> c._8,
        "initial_value" -> c._9,
        "total_fee" -> c._10,
        "doc1" -> c._11,
        "doc2" -> c._12,
        "bank" -> c._13,
        "agency" -> c._14,
        "account" -> c._15,
        // ###       "closed" ->
        "net_value" -> c._16,
        "comment" -> c._17,
        "image_id" -> c._18,
        "email" -> c._19,
        "first_name" -> c._20,
        "middle_name" -> c._21,
        "last_name" -> c._22
      // ###       "sum BRL" -> (calculated balance at each time)
      // ###       "sum crypto" -> (calculated balance at each time)
      )
    })
    ))
  }

  def users_list = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val users_list_info = globals.engineModel.UsersList()
    Ok(Json.toJson(users_list_info.map({ c =>
      Json.obj(
        "id" -> c._1,
        "created" -> c._2,
        "email" -> c._3,
        "active" -> c._4,
        "first_name" -> c._5,
        "middle_name" -> c._6,
        "last_name" -> c._7,
        "doc1" -> c._8,
        "doc2" -> c._9,
        "doc3" -> c._10,
        "doc4" -> c._11,
        "doc5" -> c._12,
        "ver1" -> c._13,
        "ver2" -> c._14,
        "ver3" -> c._15,
        "ver4" -> c._16,
        "ver5" -> c._17,
        "balance" -> c._18,
        "hold" -> c._19,
        "balance_c" -> c._20,
        "hold_c" -> c._21
      )
    })
    ))
  }

  def get_docs_info = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val docs_info = globals.engineModel.GetDocsInfo(request.user.id)
    Ok(Json.toJson(docs_info.map({ c =>
      Json.obj(
        "user_id" -> c._1,
        "doc1" -> c._2,
        "doc2" -> c._3,
        "doc3" -> c._4,
        "doc4" -> c._5,
        "doc5" -> c._6,
        "ver1" -> c._7,
        "ver2" -> c._8,
        "ver3" -> c._9,
        "ver4" -> c._10,
        "ver5" -> c._11,
        "pic1" -> c._12,
        "pic2" -> c._13,
        "pic3" -> c._14,
        "pic4" -> c._15,
        "pic5" -> c._16
      )
    })
    ))
  }

  def management_data = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val management_data_info = globals.engineModel.ManagementData(Some(request.user.id))
    Ok(Json.toJson(management_data_info.map({ c =>
      Json.obj(
        "country_code" -> c._1,
        "currency" -> c._2,
        "number_users" -> c._3,
        "fiat_funds" -> c._4,
        "crypto_funds" -> c._5,
        "system_balance" -> c._6,
        "number_pending_orders" -> c._7
      )
    })
    ))
  }

  def get_log_events = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val log_list_info = globals.logModel.getLoginEvents(request.user.id, None, None, None)
    Ok(Json.toJson(log_list_info.map({ c =>
      Json.obj(
        "id" -> c.id,
        "email" -> c.email.getOrElse("").toString,
        "ip" -> c.ip.getOrElse("").toString,
        "created" -> c.created.getOrElse(new DateTime(0).toString).toString,
        "type" -> c.typ.toString
      )
    })
    ))
  }

  def balance = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val balances = globals.engineModel.balance(Some(request.user.id), None, globals.country_currency_code)
    Ok(Json.toJson(balances.map({ c =>
      Json.obj(
        "currency" -> c._1,
        "amount" -> c._2._1.bigDecimal.toPlainString,
        "hold" -> c._2._2.bigDecimal.toPlainString,
        "amount_c" -> c._2._3.bigDecimal.toPlainString,
        "hold_c" -> c._2._4.bigDecimal.toPlainString
      )
    })
    ))
  }

  def get_admins = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val admins = globals.engineModel.GetAdmins(globals.country_code)
    Ok(Json.toJson(admins.map({ c =>
      Json.obj(
        "admin_g1" -> c._1,
        "admin_g2" -> c._2,
        "admin_l1" -> c._3,
        "admin_l2" -> c._4,
        "admin_o1" -> c._5,
        "admin_o2" -> c._6,
        "email_g1" -> c._7,
        "email_g2" -> c._8,
        "email_l1" -> c._9,
        "email_l2" -> c._10,
        "email_o1" -> c._11,
        "email_o2" -> c._12
      )
    })
    ))
  }

  def user = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    Ok(Json.toJson(request.user))
  }

  def turnOffTFA() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val tfa_code = (request.request.body \ "tfa_code").validate[String].get
    val password = (request.request.body \ "password").validate[String].get

    if (globals.userModel.turnOffTFA(request.user.id, tfa_code, password)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoturnofftwofactorauth")))
    }
  }

  def turnOnEmails() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    if (globals.userModel.turnOnEmails(request.user.id)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoaddtomailinglist")))
    }
  }

  def turnOffEmails() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    if (globals.userModel.turnOffEmails(request.user.id)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoremovefrommailinglist")))
    }
  }

  // This creates a secret, stores it and shows it to the user
  // Then the user has to verify that they know the secret by providing the code from it
  // and then they can turn on 2fa for login / withdrawal
  def genTOTPSecret() = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    if (!request.user.TFAEnabled) {
      val secret = globals.userModel.genTFASecret(request.user.id)
      if (secret.isEmpty) {
        BadRequest(Json.obj("message" -> Messages("messages.api.error.twofactorauthenticationisalready")))
      } else {
        Ok(Json.obj("secret" -> secret.get._1.toBase32, "otps" -> secret.get._2, "otpauth" -> TOTPUrl.totpSecretToUrl(request.user.email, secret.get._1)))
      }
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.twofactorauthenticationisalready")))
    }
  }

  def turnOnTFA() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val tfa_code = (request.request.body \ "tfa_code").validate[String].get
    val password = (request.request.body \ "password").validate[String].get

    if (globals.userModel.turnOnTFA(request.user.id, tfa_code, password)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoturnontwofactorauth")))
    }
  }

  def addPgp() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    (for (
      password <- (request.request.body \ "password").validate[String];
      pgp <- (request.request.body \ "pgp").validate[String];
      parsedKey = PGP.parsePublicKey(pgp);
      tfa_code = (request.request.body \ "tfa_code").asOpt[String]
    ) yield {
      if (parsedKey.isDefined) {
        if (globals.userModel.addPGP(request.user.id, password, tfa_code, parsedKey.get._2)) {
          Ok(Json.obj())
        } else {
          BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoaddpgpkey")))
        }
      } else {
        BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoaddpgpkeynovalid")))
      }
    }).getOrElse(
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoparseinput")))
    )
  }

  def removePgp() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val tfa_code = (request.request.body \ "tfa_code").asOpt[String]
    val password = (request.request.body \ "password").validate[String].get
    if (globals.userModel.removePGP(request.user.id, password, tfa_code)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoremovepgpkey")))
    }
  }

  def create_order = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val order_type = (request.request.body \ "order_type").asOpt[String]
    val status = (request.request.body \ "status").asOpt[String]
    var partner = (request.request.body \ "partner").asOpt[String]
    if (partner == "undefined")
      partner = globals.country_partner1_account.asInstanceOf[Option[String]] // this is the preferred partner ### should find another way in the future, specially for F orders
    val initial_value = (request.request.body \ "initial_value").asOpt[BigDecimal]
    val local_fee: BigDecimal = globals.calculate_local_fee(order_type.get, initial_value.get)
    val global_fee: BigDecimal = globals.calculate_global_fee(order_type.get, initial_value.get)
    val bank = (request.request.body \ "bank").asOpt[String]
    val agency = (request.request.body \ "agency").asOpt[String]
    val account = (request.request.body \ "account").asOpt[String]
    val doc1 = (request.request.body \ "doc1").asOpt[String]
    if (globals.userModel.create_order(request.user.id, globals.country_code, order_type, status, partner, globals.country_currency_code, initial_value, Option(local_fee), Option(global_fee), bank, agency, account, doc1)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtocreateorder")))
    }
  }

  def update_order = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val order_id = (request.request.body \ "order_id").validate[Long]
    val order_type = (request.request.body \ "order_type").validate[String]
    val status = (request.request.body \ "status").validate[String]
    val net_value = (request.request.body \ "net_value").validate[BigDecimal]
    val comment = (request.request.body \ "comment").validate[String]
    // This function updates orders, updates balance fiat, updates balance crypto, updates system balances (fees)

    if (globals.userModel.update_order(order_id.get, status.get, net_value.get, comment.get, globals.calculate_local_fee(order_type.get, net_value.get), globals.calculate_global_fee(order_type.get, net_value.get), request.user.id)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoupdateorder")))
    }
  }

  def update_personal_info() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val first_name = (request.request.body \ "first_name").asOpt[String]
    val middle_name = (request.request.body \ "middle_name").asOpt[String]
    val last_name = (request.request.body \ "last_name").asOpt[String]
    val doc1 = (request.request.body \ "doc1").asOpt[String]
    val doc2 = (request.request.body \ "doc2").asOpt[String]
    val doc3 = (request.request.body \ "doc3").asOpt[String]
    val doc4 = (request.request.body \ "doc4").asOpt[String]
    val doc5 = (request.request.body \ "doc5").asOpt[String]
    val bank = (request.request.body \ "bank").asOpt[String]
    val agency = (request.request.body \ "agency").asOpt[String]
    val account = (request.request.body \ "account").asOpt[String]
    val partner = (request.request.body \ "partner").asOpt[String]
    val partner_account = (request.request.body \ "partner_account").asOpt[String]
    val manualauto_mode = (request.request.body \ "manualauto_mode").asOpt[Boolean]
    //  ### need to update currentUser variable:  securesocial.core.SecureSocial.currentUser.partner = partner
    if (globals.userModel.update_personal_info(request.user.id, first_name, middle_name, last_name, doc1, doc2, doc3, doc4, doc5, bank, agency, account, partner, partner_account, manualauto_mode)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoremovepgpkey")))
    }
  }

  def update_bank_data() = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val bank = (request.request.body \ "bank").asOpt[String]
    val agency = (request.request.body \ "agency").asOpt[String]
    val account = (request.request.body \ "account").asOpt[String]
    if (globals.userModel.update_bank_data(request.user.id, bank, agency, account)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoremovepgpkey")))
    }
  }

  def change_manualauto = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val manualauto_mode = (request.request.body \ "manualauto_mode").asOpt[Boolean]
    if (globals.userModel.change_manualauto(request.user.id, manualauto_mode)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtochangemanualautomode")))
    }
  }

  def save_admins = SecuredAction(ajaxCall = true)(parse.json) { implicit request =>
    val country = (request.request.body \ "country").asOpt[String]
    val admin_g1 = (request.request.body \ "admin_g1").asOpt[String]
    val admin_g2 = (request.request.body \ "admin_g2").asOpt[String]
    val admin_l1 = (request.request.body \ "admin_l1").asOpt[String]
    val admin_l2 = (request.request.body \ "admin_l2").asOpt[String]
    val admin_o1 = (request.request.body \ "admin_o1").asOpt[String]
    val admin_o2 = (request.request.body \ "admin_o2").asOpt[String]
    if (globals.userModel.save_admins(country, admin_g1, admin_g2, admin_l1, admin_l2, admin_o1, admin_o2)) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtosaveadministrators")))
    }
  }

  def return_all_images = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val images = globals.engineModel.return_all_images(Some(request.user.id))
    Ok(Json.toJson(images.map({ c =>
      Json.obj(
        "image_id" -> c._1,
        "name" -> c._2
      )
    })
    ))
  }

}