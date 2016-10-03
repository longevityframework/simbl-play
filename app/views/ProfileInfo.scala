package views

import domain.Markdown
import domain.Uri
import domain.UserProfile

case class ProfileInfo(
  tagline: String,
  imageUri: String,
  description: String) {

  def toProfile = UserProfile(tagline, Uri(imageUri), Markdown(description))

}

object ProfileInfo {

  def fromProfile(profile: UserProfile): ProfileInfo =
    ProfileInfo(profile.tagline, profile.imageUri.uri, profile.description.markdown)

}
