package domain

import longevity.subdomain.KeyVal

case class Username(username: String)
extends KeyVal[User, Username](User.keys.username)
