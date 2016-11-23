//
// Created by ALERT on 21.03.2016.
//

package controllers

import play.api.Play.current
import play.api.i18n.Lang
import play.api.mvc.{ Cookies }

object LangControll {

  def GetLangFromCookies(Cookies: Cookies): String = {
    var lang = Lang.defaultLang.language

    if (Cookies.get("PLAY_LANG").toString != "None") {
      lang = Lang.get(Cookies.get("PLAY_LANG").get.value.toString).get.language
      if (Lang.availables.exists(_.language == lang) == false) {
        lang = Lang.defaultLang.language
      }
    }
    return lang
  }

  def GetLangFromString(Cookies: String): String = {
    var lang = Lang.defaultLang.language
    if (Lang.availables.exists(_.language == Cookies) == true) {
      lang = Cookies
    }
    return lang
  }

}
