package domain

import longevity.subdomain.KeyVal

case class BlogUri(uri: Uri) extends KeyVal[Blog, BlogUri]
