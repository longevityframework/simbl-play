package views

import domain.Email
import domain.User
import domain.Username

case class UserInfo(
  username: String,
  email: String,
  fullname: String) {

  def toUser = User(Username(username), Email(email), fullname, None)

  /** updates a [[User]] according to the information in this [[UserInfo]] */
  def mapUser(user: User) = user.copy(
    username = Username(username),
    email = Email(email),
    fullname = fullname)

}

object UserInfo {

  def fromUser(user: User): UserInfo =
    UserInfo(user.username.username, user.email.email, user.fullname)

}
