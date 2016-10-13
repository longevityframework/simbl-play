package domain

import longevity.subdomain.KeyVal

case class BlogPostUri(uri: Uri) extends KeyVal[BlogPost, BlogPostUri]
