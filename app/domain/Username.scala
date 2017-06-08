package domain

import longevity.model.annotations.keyVal

@keyVal[SimblDomainModel, User] case class Username(username: String)
