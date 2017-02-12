//
// Created by ALERT on 21.03.2016.
//

package controllers

import play.api.Play.current
import play.api.i18n.Lang
import play.api.mvc.Cookies

object ManualAutoControll {

  def GetManualAutoFromCookies(Cookies: Cookies): String = {
    var manualAuto = "M"
    if (Cookies.get("PLAY_MANUALAUTO").toString != "None") {
      manualAuto = Cookies.get("PLAY_MANUALAUTO").get.value.toString
      if (manualAuto != "M" && manualAuto != "A") {
        manualAuto = "M"
      }
    }
    return manualAuto
  }

  def GetManualAutoFromString(Cookies: String): String = {
    var manualAuto = "M"
    if (Cookies == "M" || Cookies == "A") {
      manualAuto = Cookies
    }
    return manualAuto
  }

}
