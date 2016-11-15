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

package usertrust

import play.api.db.DB
import securesocial.core.Token
import java.sql.Timestamp
import play.api.Play.current
import org.joda.time.DateTime
import anorm._

class UserTrustModel(val db: String = "default") {
  def getTrustedActionRequests = DB.withConnection(db) { implicit c =>
    SQL"""select email, is_signup, language from trusted_action_requests"""().map(row =>
      (row[String]("email"), row[Boolean]("is_signup"), row[String]("language"))
    ).toList
  }

  def trustedActionFinish(email: String, is_signup: Boolean) = DB.withConnection(db) { implicit c =>
    SQL"""delete from trusted_action_requests where email = $email and is_signup = $is_signup""".execute
  }

  def saveToken(token: Token) = DB.withConnection(db) { implicit c =>
    SQL"""
    insert into tokens (token, email, creation, expiration, is_signup, language)
    values (${token.uuid}, ${token.email}, ${new Timestamp(token.creationTime.getMillis)}, ${new Timestamp(token.expirationTime.getMillis)}, ${token.isSignUp}, ${token.language})
    """.execute
  }
}
