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

  def users_list = SecuredAction { implicit request =>
    Ok(views.html.administrator.users_list(request.user))
  }

  def orders_list = SecuredAction { implicit request =>
    Ok(views.html.administrator.orders_list(request.user))
  }

  def order_details = SecuredAction { implicit request =>
    Ok(views.html.administrator.order_details(request.user))
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

  def orderdetails = SecuredAction { implicit request =>
    Ok(views.html.administrator.order_details(request.user))
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
        // need to treat string to double exceptions. If not double value, reject order creation
        initial_value = (file.key.substring(0, position)).toDouble
        partner = file.key.substring(position + 1, position2)
        partner_account = file.key.substring(position2 + 1, file.key.length)
        val image_id = controllers.Image.saveImage(file.ref.file.getAbsolutePath, fileName, user_id)
        if (partner == "undefined") {
          order_type = "D"
          partner = ""
          partner_account = ""
          local_fee = initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * (100 - globals.country_fees_global_percentage.asInstanceOf[Double]) * 0.01
          global_fee = initial_value * globals.country_fee_deposit_percent.asInstanceOf[Double] * 0.01 * (100 - globals.country_fees_global_percentage.asInstanceOf[Double]) * 0.01
        } else {
          local_fee = initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * globals.country_fees_global_percentage.asInstanceOf[Double] * 0.01
          global_fee = initial_value * (globals.country_fee_deposit_percent.asInstanceOf[Double] + globals.country_fee_send_percent.asInstanceOf[Double]) * 0.01 * globals.country_fees_global_percentage.asInstanceOf[Double] * 0.01
        }
        val success = globals.userModel.create_order_with_picture(request.user.id, globals.country_code, order_type, "Op", partner, globals.country_currency_code, initial_value, local_fee, global_fee, "", "", partner_account, fileName)
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
        controllers.Image.saveImage(file.ref.file.getAbsolutePath, fileName, user_id)
        val success = globals.userModel.create_order_with_picture(request.user.id, globals.country_code, "V", "Op", docNumber, globals.country_currency_code, 0, 0, 0, "", "", "", fileName)
    }
    Ok(views.html.exchange.dashboard(request.user))
  }

  def getimage(name: String) = Action {
    val MimeType = "image/png"
    try {
      val imageData: Array[Byte] = controllers.Image.getImage(name)
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
}

object ManualAuto {

}