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
        "partner" -> c._12
      )
    })
    ))
  }

  def orders_list = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val orders_list_info = globals.engineModel.OrderList()
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
        "net_value" -> c._16,
        "comment" -> c._17,
        "email" -> c._18,
        "first_name" -> c._19,
        "middle_name" -> c._20,
        "last_name" -> c._21

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
        "ver5" -> c._17
      )
    })
    ))
  }

  def balance = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val balances = globals.engineModel.balance(Some(request.user.id), None, globals.country_currency_code, globals.country_currency_crypto)
    Ok(Json.toJson(balances.map({ c =>
      Json.obj(
        "currency" -> c._1,
        "amount" -> c._2._1.bigDecimal.toPlainString,
        "hold" -> c._2._2.bigDecimal.toPlainString,
        "is_fiat" -> c._2._3
      )
    })
    ))
  }

  /*
  def country_settings = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    Ok(Json.toJson(
      Json.obj(
        "decimal_separator" -> globals.country_decimal_separator,
        "minimum_value" -> globals.country_minimum_value.toString,
        "critical_value1" -> globals.country_critical_value1.toString,
        "critical_value2" -> globals.country_critical_value2.toString,
        "preferential_bank1_code" -> globals.country_preferential_bank1_code,
        "preferential_bank1_name" -> globals.country_preferential_bank1_name,
        "preferential_bank2_code" -> globals.country_preferential_bank2_code,
        "preferential_bank2_name" -> globals.country_preferential_bank2_name,
        "preferential_bank3_code" -> globals.country_preferential_bank3_code,
        "preferential_bank3_name" -> globals.country_preferential_bank3_name,
        "preferential_bank4_code" -> globals.country_preferential_bank4_code,
        "preferential_bank4_name" -> globals.country_preferential_bank4_name,
        "nominal_fee_withdrawal_preferential_bank" -> globals.country_nominal_fee_withdrawal_preferential_bank.toString,
        "nominal_fee_withdrawal_not_preferential_bank" -> globals.country_nominal_fee_withdrawal_not_preferential_bank.toString,
        "fees_global_percentage" -> globals.country_fees_global_percentage.toString,
        "fee_deposit_percent" -> globals.country_fee_deposit_percent.toString,
        "fee_withdrawal_percent" -> globals.country_fee_withdrawal_percent.toString,
        "fee_send_percent" -> globals.country_fee_send_percent.toString,
        "fee_tofiat_percent" -> globals.country_fee_tofiat_percent.toString
      )
    ))
  }
*/

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
    val partner = (request.request.body \ "partner").asOpt[String]
    val initial_value = (request.request.body \ "initial_value").asOpt[BigDecimal]
    val total_fee: BigDecimal = calculate_local_fee(order_type.get, initial_value.get) + calculate_global_fee(order_type.get, initial_value.get)
    val bank = (request.request.body \ "bank").asOpt[String]
    val agency = (request.request.body \ "agency").asOpt[String]
    val account = (request.request.body \ "account").asOpt[String]
    val doc1 = (request.request.body \ "doc1").asOpt[String]
    if (globals.userModel.create_order(request.user.id, globals.country_code, order_type, status, partner, globals.country_currency_code, initial_value, Option(total_fee), bank, agency, account, doc1)) {
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

    if (globals.userModel.update_order(order_id.get, status.get, net_value.get, comment.get, calculate_local_fee(order_type.get, net_value.get), calculate_global_fee(order_type.get, net_value.get))) {
      Ok(Json.obj())
    } else {
      BadRequest(Json.obj("message" -> Messages("messages.api.error.failedtoupdateorder")))
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

  def calculate_local_fee(order_type: String, initial_value: BigDecimal = 0): BigDecimal = {
    val percentage = (100 - globals.country_fees_global_percentage.asInstanceOf[Double]) * 0.01
    if (order_type == "D") {
      return initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "S") {
      return initial_value * globals.country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "DCS") {
      return initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "W") { // withdrawal to a preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "W.") { // withdrawal to a non preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + globals.country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "RFW") { // withdrawal to a preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "RFW.") { // withdrawal to a non preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + globals.country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "F") {
      return initial_value * globals.country_fee_tofiat_percent.asInstanceOf[Double] * 0.01 * percentage
    } else return 0
  }

  def calculate_global_fee(order_type: String, initial_value: BigDecimal = 0): BigDecimal = {
    val percentage = globals.country_fees_global_percentage.asInstanceOf[Double] * 0.01
    if (order_type == "D") {
      return initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "S") {
      return initial_value * globals.country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "DCS") {
      return initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "W" || order_type == "W.") {
      return initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "RFW" || order_type == "RFW.") {
      return initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage
    } else if (order_type == "F") {
      return initial_value * globals.country_fee_tofiat_percent.asInstanceOf[Double] * 0.01 * percentage
    } else return 0
  }
}