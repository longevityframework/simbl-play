package domain

import longevity.subdomain.KeyVal

case class Email(email: String)
extends KeyVal[User, Email](User.keys.email)