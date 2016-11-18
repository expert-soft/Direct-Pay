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
