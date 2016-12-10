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
import play.api.i18n.{ Lang, MessagesApi, I18nSupport, Messages }

class APIv1 @Inject() (val messagesApi: MessagesApi) extends Controller with securesocial.core.SecureSocial with I18nSupport {
  // Json serializable case classes have implicit definitions in their companion objects

  import globals._

  def index = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    Ok(Json.obj())
  }

  def balance = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val balances = globals.engineModel.balance(Some(request.user.id), None)
    Ok(Json.toJson(balances.map({ c =>
      Json.obj(
        "currency" -> c._1,
        "amount" -> c._2._1.bigDecimal.toPlainString,
        "hold" -> c._2._2.bigDecimal.toPlainString
      )
    })
    ))
  }

  def user_name_info = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val user_info = globals.engineModel.UserNameINFO(Some(request.user.id))
    Ok(Json.toJson(user_info.map({ c =>
      Json.obj(
        "name" -> c._1,
        "surname" -> c._2,
        "middle_name" -> c._3,
        "prefix" -> c._4
      )
    })
    ))
  }

  def country = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val country_info = globals.engineModel.country()
    Ok(Json.toJson(country_info.map({ c =>
      Json.obj(
        "country_code" -> c._1,
        "country_name" -> c._2,
        "country_local_name" -> c._3,
        "site_name" -> c._4,
        "site_url1" -> c._5,
        "site_url2" -> c._6,
        "language_name" -> c._7,
        "language_code" -> c._8,
        "currency_symbol" -> c._9,
        "currency_code" -> c._10,
        "currency_crypto" -> c._11,
        "currency_name" -> c._12,
        "currency_name_plural" -> c._13,
        "currency_approximate_value" -> c._14.bigDecimal.toPlainString,
        "critical_value" -> c._15.bigDecimal.toPlainString

      /*
             "fee_global" -> c._16,
             "fee_local" -> c._17,
             "fee_global_deposit_percent" -> c._18,
             "fee_local_deposit_percent" -> c._19,
             "fee_local_deposit_nominal" -> c._20,
             "fee_global_withdrawal_percent" -> c._21,
             "fee_local_withdrawal_percent" -> c._22,
             "fee_local_withdrawal_nominal" -> c._23,
             "fee_global_send_percent" -> c._24,
             "fee_local_send_percent" -> c._25,
             "fee_global_tofiat_percent" -> c._26,
             "fee_local_tofiat_percent" -> c._27,
             "fee_local_doc_verification" -> c._28,

             "appearance_pic1" -> c._29,
             "appearance_pic2" -> c._30,
             "appearance_color1" -> c._31,
             "appearance_color2" -> c._32,
             "appearance_color3" -> c._33,
             "appearance_color4" -> c._34,
             "appearance_color5" -> c._35,

           */
      )
    })
    ))
  }

  def country_docs = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val country_docs = globals.engineModel.country_docs()
    Ok(Json.toJson(country_docs.map({ c =>
      Json.obj(
        "doc1_name" -> c._1,
        "doc1_required" -> c._2,
        "doc1_ispicture" -> c._3,
        "doc1_format" -> c._4,
        "doc2_name" -> c._5,
        "doc2_required" -> c._6,
        "doc2_ispicture" -> c._7,
        "doc2_format" -> c._8,
        "doc3_name" -> c._9,
        "doc3_required" -> c._10,
        "doc3_ispicture" -> c._11,
        "doc3_format" -> c._12,
        "doc4_name" -> c._13,
        "doc4_required" -> c._14,
        "doc4_ispicture" -> c._15,
        "doc4_format" -> c._16,
        "doc5_name" -> c._17,
        "doc5_required" -> c._18,
        "doc5_ispicture" -> c._19,
        "doc5_format" -> c._20
      )
    })
    ))
  }

  def banks_list = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val banks_list_info = globals.engineModel.BanksList()
    Ok(Json.toJson(banks_list_info.map({ c =>
      Json.obj(
        "country_code" -> c._1,
        "bank_code" -> c._2,
        "bank_name" -> c._3
      )
    })
    ))
  }

  def user_list = SecuredAction(ajaxCall = true)(parse.anyContent) { implicit request =>
    val user_list_info = globals.engineModel.UserList()
    Ok(Json.toJson(user_list_info.map({ c =>
      Json.obj(
        "id" -> c._1,
        "created" -> c._2,
        "email" -> c._3,
        "active" -> c._4,
        "name" -> c._5,
        "surname" -> c._6,
        "middle_name" -> c._7,
        "prefix" -> c._8
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
}
