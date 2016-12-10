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
import securesocial.core.Token
import play.api.libs.json.Json
import java.sql.Timestamp

class EngineModel(val db: String = "default") {

  // privileged apis

  // regular apis

  def balance(uid: Option[Long], apiKey: Option[String]) = DB.withConnection(db) { implicit c =>
    SQL"""select * from balance($uid, $apiKey)"""().map(row =>
      row[String]("currency") -> (row[BigDecimal]("amount"), row[BigDecimal]("hold"))
    ).toMap
  }

  def UserNameINFO(uid: Option[Long]) = DB.withConnection(db) { implicit c =>
    SQL"""select * from get_user_name_info($uid)"""().map(row => (
      row[String]("name"),
      row[String]("surname"),
      row[String]("middle_name"),
      row[String]("prefix")
    )).toList
  }

  def country() = DB.withConnection(db) { implicit c =>
    SQL"""select * from country"""().map(row => (
      row[String]("country_code"),
      row[String]("country_name"),
      row[String]("country_local_name"),
      row[String]("site_name"),
      row[String]("site_url1"),
      row[String]("site_url2"),
      row[String]("language_name"),
      row[String]("language_code"),
      row[String]("currency_symbol"),
      row[String]("currency_code"),
      row[String]("currency_crypto"),
      row[String]("currency_name"),
      row[String]("currency_name_plural"),
      row[BigDecimal]("currency_approximate_value"),
      row[BigDecimal]("critical_value")
    )).toList
  }

  def country_docs() = DB.withConnection(db) { implicit c =>
    SQL"""select * from country_docs"""().map(row => (
      row[String]("doc1_name"),
      row[Boolean]("doc1_required"),
      row[Boolean]("doc1_ispicture"),
      row[String]("doc1_format"),
      row[String]("doc2_name"),
      row[Boolean]("doc2_required"),
      row[Boolean]("doc2_ispicture"),
      row[String]("doc2_format"),
      row[String]("doc3_name"),
      row[Boolean]("doc3_required"),
      row[Boolean]("doc3_ispicture"),
      row[String]("doc3_format"),
      row[String]("doc4_name"),
      row[Boolean]("doc4_required"),
      row[Boolean]("doc4_ispicture"),
      row[String]("doc4_format"),
      row[String]("doc5_name"),
      row[Boolean]("doc5_required"),
      row[Boolean]("doc5_ispicture"),
      row[String]("doc5_format")

    )).toList
  }

  def BanksList() = DB.withConnection(db) { implicit c =>
    SQL"""select * from banks()"""().map(row => (
      row[String]("country_code"),
      row[String]("bank_code"),
      row[String]("bank_name")

    )).toList
  }

  def UserList() = DB.withConnection(db) { implicit c =>
    SQL"""select * from get_user_list()"""().map(row => (
      row[Long]("id"),
      row[DateTime]("created"),
      row[String]("email"),
      row[Boolean]("active"),
      row[Option[String]]("name").getOrElse("N/A"),
      row[Option[String]]("surname").getOrElse("N/A"),
      row[Option[String]]("middle_name").getOrElse("N/A"),
      row[Option[String]]("prefix").getOrElse("N/A")

    )).toList
  }

}
