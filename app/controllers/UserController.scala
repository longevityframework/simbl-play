package controllers

import akka.actor.ActorSystem
import com.google.inject.name.Named
import domain.SimblDomainModel
import domain.User
import domain.Username
import javax.inject._
import longevity.exceptions.persistence.DuplicateKeyValException
import longevity.persistence.PState
import longevity.persistence.Repo
import play.api._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import views.ProfileInfo
import views.UserInfo
import play.api.libs.json._

@Singleton
class UserController @Inject() (
  actorSystem: ActorSystem,
  private val repo: Repo[Future, SimblDomainModel])(
  implicit exec: ExecutionContext)
extends Controller {

  implicit val userReads = Json.reads[UserInfo]
  implicit val userWrites = Json.writes[UserInfo]
  implicit val profileReads = Json.reads[ProfileInfo]
  implicit val profileWrites = Json.writes[ProfileInfo]

  def createUser() = Action.async(BodyParsers.parse.json) { request =>
    val userInfo: UserInfo = request.body.validate[UserInfo].get
    val okResult =for {
      created <- repo.create(userInfo.toUser)
    } yield {
      Ok(Json.toJson(UserInfo.fromUser(created.get)))
    }
    okResult.recover {
      case e: DuplicateKeyValException[_, _] => handleDuplicateKeyVal(e, userInfo)
    }
  }

  def retrieveUser(username: String) = Action.async {
    for {
      retrieved <- repo.retrieve[User](Username(username))
    } yield {
      retrieved match {
        case Some(userState) => Ok(Json.toJson(UserInfo.fromUser(userState.get)))
        case None => NotFound
      }
    }
  }

  def updateUser(username: String) = Action.async(BodyParsers.parse.json) { request =>
    val userInfo: UserInfo = request.body.validate[UserInfo].get
    val okResult = for {
      retrieved <- repo.retrieveOne[User](Username(username))
      modified = retrieved.map(userInfo.mapUser)
      updated <- repo.update(modified)
    } yield {
      Ok(Json.toJson(UserInfo.fromUser(updated.get)))
    }
    okResult.recover {
      case e: DuplicateKeyValException[_, _] => handleDuplicateKeyVal(e, userInfo)
      case e: NoSuchElementException => NotFound
    }
  }

  def deleteUser(username: String) = Action.async {
    val okResult = for {
      retrieved <- repo.retrieveOne[User](Username(username))
      deleted <- repo.delete(retrieved)
    } yield {
      Ok(Json.toJson(UserInfo.fromUser(deleted.get)))
    }
    okResult.recover {
      case e: NoSuchElementException => NotFound
    }
  }

  def retrieveAllUsers() = Action.async {
    def stateToInfo(state: PState[User]) = UserInfo.fromUser(state.get)
    def usersToUserInfos(users: Seq[PState[User]]) = users.map(stateToInfo)
    import User.queryDsl._
    repo.queryToVector(filterAll).map(usersToUserInfos).map(infos => Ok(Json.toJson(infos)))
  }

  def retrieveUserProfile(username: String) = Action.async {
    for {
      retrieved <- repo.retrieve[User](Username(username))
    } yield {
      retrieved.flatMap(_.get.profile) match {
        case Some(profile) => Ok(Json.toJson(ProfileInfo.fromProfile(profile)))
        case None => NotFound
      }
    }
  }

  def updateUserProfile(username: String) = Action.async(BodyParsers.parse.json) { request =>
    val info: ProfileInfo = request.body.validate[ProfileInfo].get
    val okResult = for {
      retrieved <- repo.retrieveOne[User](Username(username))
      modified = retrieved.map(_.updateProfile(info.toProfile))
      updated <- repo.update(modified)
    } yield {
      val profile = updated.get.profile.get
      Ok(Json.toJson(ProfileInfo.fromProfile(profile)))
    }
    okResult.recover {
      case e: NoSuchElementException => NotFound
    }
  }

  def deleteUserProfile(username: String) = Action.async {
    val okResult = for {
      retrieved <- repo.retrieveOne[User](Username(username))
      modified = retrieved.map(_.deleteProfile)
      updated <- repo.update(modified)
    } yield {
      retrieved.get.profile match {
        case Some(profile) => Ok(Json.toJson(ProfileInfo.fromProfile(profile)))
        case None => Ok
      }
    }
    okResult.recover {
      case e: NoSuchElementException => NotFound
    }
  }

  private def handleDuplicateKeyVal(e: DuplicateKeyValException[_, _], info: UserInfo) = {
    e.key.prop match {
      case User.props.username =>
        Conflict(s"user with username ${info.username} already exists")
      case User.props.email =>
        Conflict(s"user with email ${info.email} already exists")
    }
  }

}
