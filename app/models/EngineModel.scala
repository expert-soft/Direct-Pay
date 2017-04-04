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

package models

import anorm._
import play.api.db.DB
import play.api.Play.current
import anorm.SqlParser._
import org.joda.time.DateTime
import securesocial.core.{ SocialUser, Token }
import play.api.libs.json.Json
import java.sql.Timestamp

class EngineModel(val db: String = "default") {

  // privileged apis

  // regular apis

  def UserNameINFO(uid: Option[Long]) = DB.withConnection(db) { implicit c =>
    SQL"""select * from get_user_name_info($uid)"""().map(row => (
      row[String]("first_name"),
      row[String]("middle_name"),
      row[String]("last_name"),
      row[Option[String]]("doc1").getOrElse("N/A"),
      row[Option[String]]("doc2").getOrElse("N/A"),
      row[Option[String]]("doc3").getOrElse("N/A"),
      row[Option[String]]("doc4").getOrElse("N/A"),
      row[Option[String]]("doc5").getOrElse("N/A"),
      row[Option[String]]("bank").getOrElse("N/A"),
      row[Option[String]]("agency").getOrElse("N/A"),
      row[Option[String]]("account").getOrElse("N/A"),
      row[Option[String]]("partner").getOrElse("N/A"),
      row[Option[String]]("partner_account").getOrElse("N/A")
    )).toList
  }

  def UsersList() = DB.withConnection(db) { implicit c =>
    SQL"""select * from get_users_list()"""().map(row => (
      row[Long]("id"),
      row[DateTime]("created"),
      row[String]("email"),
      row[Boolean]("active"),
      row[Option[String]]("first_name").getOrElse("N/A"),
      row[Option[String]]("middle_name").getOrElse("N/A"),
      row[Option[String]]("last_name").getOrElse("N/A"),
      row[Option[String]]("doc1").getOrElse("N/A"),
      row[Option[String]]("doc2").getOrElse("N/A"),
      row[Option[String]]("doc3").getOrElse("N/A"),
      row[Option[String]]("doc4").getOrElse("N/A"),
      row[Option[String]]("doc5").getOrElse("N/A"),
      row[Option[Boolean]]("ver1").getOrElse(false),
      row[Option[Boolean]]("ver2").getOrElse(false),
      row[Option[Boolean]]("ver3").getOrElse(false),
      row[Option[Boolean]]("ver4").getOrElse(false),
      row[Option[Boolean]]("ver5").getOrElse(false)

    )).toList
  }

  def OrderList() = DB.withConnection(db) { implicit c =>
    SQL"""select * from get_orders_list()"""().map(row => ( //See 2.sql at lines 787 and 848
      row[Long]("order_id"),
      row[Long]("user_id"),
      row[String]("country_id"),
      row[String]("order_type"),
      row[String]("status"),
      row[Option[String]]("partner").getOrElse("N/A"),
      row[DateTime]("created"),
      row[String]("currency"),
      row[BigDecimal]("initial_value"),
      row[BigDecimal]("total_fee"),
      row[Option[String]]("doc1").getOrElse("N/A"),
      row[Option[String]]("doc2").getOrElse("N/A"),
      row[Option[String]]("bank").getOrElse("N/A"),
      row[Option[String]]("agency").getOrElse("N/A"),
      row[Option[String]]("account").getOrElse("N/A"),
      row[BigDecimal]("net_value"),
      row[Option[String]]("comment").getOrElse("N/A"),
      row[Option[String]]("email").getOrElse("N/A"),
      row[Option[String]]("first_name").getOrElse("N/A"),
      row[Option[String]]("middle_name").getOrElse("N/A"),
      row[Option[String]]("last_name").getOrElse("N/A")
    )).toList
  }

  def balance(uid: Option[Long], apiKey: Option[String], currency_name: String) = DB.withConnection(db) { implicit c =>
    SQL"""select * from balance($uid, $apiKey, $currency_name)"""().map(row =>
      row[String]("currency") -> (
        row[BigDecimal]("amount"),
        row[BigDecimal]("hold"),
        row[BigDecimal]("amount_c"),
        row[BigDecimal]("hold_c")
      )).toMap
  }

  def get_all_image(uid: Option[Long]) = DB.withConnection(db) { implicit c =>
    SQL"""SELECT id, name  FROM public.image;"""().map(row => (
      row[Long]("id"),
      row[String]("name")
    )).toList
  }

}