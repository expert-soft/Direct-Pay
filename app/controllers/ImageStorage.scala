/**
 * Created by ALERT on 25.09.2016.
 */

package controllers

import java.io.{ BufferedInputStream, ByteArrayOutputStream, FileInputStream }
import java.awt.image.BufferedImage

import anorm._
import anorm.SqlParser._
import anorm.~
import play.api.Play.current
import play.api.db.DB
import scala.language.postfixOps

object Image {

  val db: String = "default"

  def getImage(name: String): Array[Byte] = {
    getImageFromDb(name)._3
  }

  def getImageFromDb(name: String) = DB.withConnection(db) { implicit c =>
    play.api.cache.Cache.getOrElse("%s.images".format(name)) {
      SQL""" SELECT id, name, data  FROM image where name =${name}"""().map(row => (
        row[Int]("id"),
        row[String]("name"),
        row[Array[Byte]]("data"))).head
    }
  }

  def saveImage(fullPath: String, fileName: String) = {
    val bis = new BufferedInputStream(new FileInputStream(fullPath))
    val bArray = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
    saveImageToDb(fileName, bArray)
  }

  def saveImageToDb(fileName: String, file: Array[Byte]) = DB.withConnection(db) { implicit c =>
    SQL""" INSERT INTO image(name, data) VALUES ($fileName, $file); """.execute()
  }
}
