/**
 * Created by ALERT on 25.09.2016.
 */

package controllers

import java.io.{ BufferedInputStream, ByteArrayOutputStream, FileInputStream }
import java.awt.image.BufferedImage

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB

import scala.language.postfixOps

object Image {

  val db: String = "default"

  def getImage(image_id: Long): Array[Byte] = {
    getImageFromDb(image_id)._3
  }

  def getImageFromDb(image_id: Long) = DB.withConnection(db) { implicit c =>
    {
      SQL""" SELECT image_id, name, data FROM image where image_id =${image_id}"""().map(row => (
        row[Int]("image_id"),
        row[String]("name"),
        row[Array[Byte]]("data"))).head
    }
  }

  def saveImage(fullPath: String, fileName: String, user_id: Long) = {
    val bis = new BufferedInputStream(new FileInputStream(fullPath))
    val bArray = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
    val fullname = Romanization.normalize(fileName).replace(" ", "-")
    insert_user_image(fullname, bArray)
  }

  def saveImageToDb(fileName: String, file: Array[Byte]) = DB.withConnection(db) { implicit c =>
    SQL""" INSERT INTO image(name, data) VALUES ($fileName, $file); """.execute()
  }

  def insert_user_image(fileName: String, file: Array[Byte]) = DB.withConnection(db) { implicit c =>
    SQL"""
     select insert_user_image as success from insert_user_image($fileName, $file)
    """().map(row =>
      row[Long]("success")
    ).head
  }
}

