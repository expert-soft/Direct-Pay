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

package controllers

import javax.inject.Inject

import play.api._

import play.api.mvc._
import play.api.i18n.{ I18nSupport, Lang }
import play.api.Play.current
import play.api.i18n.MessagesApi
import play.i18n.Langs
import scala.language.postfixOps
import jsmessages.JsMessagesFactory

class Application @Inject() (jsMessagesFactory: JsMessagesFactory, val messagesApi: MessagesApi) extends Controller with securesocial.core.SecureSocial with I18nSupport {

  def index = UserAwareAction { implicit request =>
    Ok(views.html.content.index(request.user.isDefined))
  }

  def account = SecuredAction { implicit request =>
    Ok(views.html.exchange.account(request.user))
  }

  def user_settings = SecuredAction { implicit request =>
    Ok(views.html.exchange.user_settings(request.user))
  }

  def documents = SecuredAction { implicit request =>
    Ok(views.html.exchange.documents(request.user))
  }

  def management = SecuredAction { implicit request =>
    Ok(views.html.administrator.management(request.user))
  }

  def users_list = SecuredAction { implicit request =>
    Ok(views.html.administrator.users_list(request.user))
  }

  def orders_list = SecuredAction { implicit request =>
    Ok(views.html.administrator.orders_list(request.user))
  }

  def history = SecuredAction { implicit request =>
    Ok(views.html.exchange.history(request.user))
  }

  def deposit = SecuredAction { implicit request =>
    Ok(views.html.exchange.deposit(request.user))
  }

  def tocrypto = SecuredAction { implicit request =>
    Ok(views.html.exchange.tocrypto(request.user))
  }

  def send = SecuredAction { implicit request =>
    Ok(views.html.exchange.send(request.user))
  }

  def receive = SecuredAction { implicit request =>
    Ok(views.html.exchange.receive(request.user))
  }

  def tofiat = SecuredAction { implicit request =>
    Ok(views.html.exchange.tofiat(request.user))
  }

  def withdraw = SecuredAction { implicit request =>
    Ok(views.html.exchange.withdraw(request.user))
  }

  def about = SecuredAction { implicit request =>
    Ok(views.html.exchange.about(request.user))
  }

  def faq = SecuredAction { implicit request =>
    Ok(views.html.exchange.faq(request.user))
  }

  def contract = SecuredAction { implicit request =>
    Ok(views.html.exchange.contract(request.user))
  }

  def dashboard = SecuredAction { implicit request =>
    Ok(views.html.exchange.dashboard(request.user))
  }

  def chlang(lang: String) = UserAwareAction { implicit request =>
    if (request.user.isDefined) {
      globals.userModel.changeLanguage(request.user.get.id, lang)
    }
    Redirect(request.headers.get("referer").getOrElse("/")).withLang(Lang.get(lang).getOrElse(Lang.defaultLang))
  }

  def uploadDepositImage = SecuredAction(parse.multipartFormData) { implicit request =>
    var initial_value = 0.0
    var partner = ""
    var partner_account = ""
    var order_type = "DCS"
    var local_fee = 0.1
    var global_fee = 0.1
    request.body.files map {
      file =>
        val fileName = file.filename
        val contentType = file.contentType
        val user_id = request.user.id
        val position = file.key.indexOf("|")
        val position2 = file.key.substring(position + 1, file.key.length).indexOf("|") + position + 1
        // ### need to treat string to double exceptions. If not double value, reject order creation
        //  = try { Some(s.toDouble) } catch { case _ => None }
        initial_value = try { ((file.key.substring(0, position)).replace(globals.country_decimal_separator, ".")).toDouble } catch { case _ => 0.0 }
        if (initial_value != 0.0) {
          partner = file.key.substring(position + 1, position2)
          partner_account = file.key.substring(position2 + 1, file.key.length)
          val image_id = controllers.Image.saveImage(file.ref.file.getAbsolutePath, fileName, user_id)
          if (partner == "undefined") {
            order_type = "D"
            partner = ""
            partner_account = ""
            /*            local_fee = initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * (100 - globals.country_fees_global_percentage.asInstanceOf[Double]) * 0.01
            global_fee = initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * (100 - globals.country_fees_global_percentage.asInstanceOf[Double]) * 0.01
          } else {
            local_fee = initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * globals.country_fees_global_percentage.asInstanceOf[Double] * 0.01
            global_fee = initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * globals.country_fees_global_percentage.asInstanceOf[Double] * 0.01
*/
          }
          local_fee = calculate_local_fee(order_type, initial_value).toDouble
          global_fee = calculate_global_fee(order_type, initial_value).toDouble
          val success = globals.userModel.create_order_with_picture(request.user.id, globals.country_code, order_type, "Op", partner, globals.country_currency_code, initial_value, local_fee, global_fee, "", "", partner_account, fileName, image_id)
        }
    }
    Ok(views.html.exchange.dashboard(request.user))
  }

  def uploadWithdrawImage = SecuredAction(parse.multipartFormData) { implicit request =>
    var processed_value = 0.0
    var comment = ""
    var OK_Ch_Rj = ""
    var order_type = "ooo"
    var order_id = 0L
    var local_fee = 0.1
    var global_fee = 0.1
    request.body.files map {
      file =>
        val fileName = file.filename
        val contentType = file.contentType
        val user_id = request.user.id
        val position = file.key.indexOf("|")
        val position2 = file.key.substring(position + 1, file.key.length).indexOf("|") + position + 1
        val position3 = file.key.substring(position2 + 1, file.key.length).indexOf("|") + position2 + 1
        val position4 = file.key.substring(position3 + 1, file.key.length).indexOf("|") + position3 + 1
        // ### need to treat string to double exceptions. If not double value, reject order creation
        //  = try { Some(s.toDouble) } catch { case _ => None }
        processed_value = try { ((file.key.substring(0, position)).replace(globals.country_decimal_separator, ".")).toDouble } catch { case _ => 0.0 }

        comment = file.key.substring(position + 1, position2)
        OK_Ch_Rj = file.key.substring(position2 + 1, position3)
        order_type = file.key.substring(position3 + 1, position4)
        order_id = (file.key.substring(position4 + 1, file.key.length)).toLong
        val image_id = controllers.Image.saveImage(file.ref.file.getAbsolutePath, fileName, user_id)

        if (processed_value != 0.0) {
          local_fee = calculate_local_fee(order_type, processed_value).toDouble
          global_fee = calculate_global_fee(order_type, processed_value).toDouble
        } else {
          local_fee = 0;
          global_fee = 0;
        }
        if (processed_value != 0.0 || OK_Ch_Rj == "Rj") {
          val success = globals.userModel.update_order_with_picture(order_id, order_type, OK_Ch_Rj, processed_value, local_fee, global_fee, comment, image_id, request.user.id)
        }
    }
    Ok(views.html.exchange.dashboard(request.user))
  }

  def uploadDocImage = SecuredAction(parse.multipartFormData) { implicit request =>
    request.body.files map {
      file =>
        val fileName = file.filename
        val contentType = file.contentType
        val docNumber = file.key
        val user_id = request.user.id
        val image_id = controllers.Image.saveImage(file.ref.file.getAbsolutePath, fileName, user_id)
        var success = globals.userModel.create_order_with_picture(user_id, globals.country_code, "V", "Op", docNumber, globals.country_currency_code, 0, 0, 0, "", "", "", fileName, image_id)
        success = globals.userModel.update_user_doc(user_id, docNumber, image_id, fileName)
    }
    Ok(views.html.exchange.dashboard(request.user))
  }

  def getimage(image_id_s: String) = Action {
    val MimeType = "image/png"
    try {
      val imageData: Array[Byte] = controllers.Image.getImage(image_id_s.toLong)
      Ok(imageData).as(MimeType)
    } catch {
      case e: IllegalArgumentException =>
        BadRequest("Couldn’t generate image. Error: " + e.getMessage)
    }
  }

  val messages = jsMessagesFactory.all

  val jsMessages = Action { implicit request =>
    Ok(messages(Some("window.Messages")))
  }

  def calculate_local_fee(order_type: String, initial_value: BigDecimal = 0): BigDecimal = {
    val percentage = (100 - globals.country_fees_global_percentage.asInstanceOf[Double]) * 0.01
    var low_value_fee = 0.0
    if (initial_value < globals.country_minimum_value) {
      low_value_fee = globals.country_minimum_value * 0.02
    }
    if (order_type == "D") {
      return initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * percentage + low_value_fee
    } else if (order_type == "S") {
      return initial_value * globals.country_fee_send_percent.asInstanceOf[Double] * 0.01 * percentage
    } else if (order_type == "DCS") {
      return initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * percentage + low_value_fee
    } else if (order_type == "W") { // withdrawal to a preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage + low_value_fee
    } else if (order_type == "W.") { // withdrawal to a non preferential bank
      return globals.country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * globals.country_fee_withdrawal_percent.asInstanceOf[Double] * 0.01 * percentage + low_value_fee
    } else if (order_type == "RFW") { // withdrawal to a preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage + low_value_fee
    } else if (order_type == "RFW.") { // withdrawal to a non preferential bank
      return globals.country_nominal_fee_withdrawal_preferential_bank.asInstanceOf[Double] + globals.country_nominal_fee_withdrawal_not_preferential_bank.asInstanceOf[Double] + initial_value * (globals.country_fee_withdrawal_percent.asInstanceOf[Double] + globals.country_fee_tofiat_percent.asInstanceOf[Double]) * 0.01 * percentage + low_value_fee
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
