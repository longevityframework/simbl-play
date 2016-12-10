package domain

import longevity.model.annotations.keyVal

@keyVal[User] case class Username(username: String)
