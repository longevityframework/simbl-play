package domain

import longevity.subdomain.annotations.persistent
import longevity.subdomain.query.Query
import org.joda.time.DateTime

@persistent(
  keySet = Set(partitionKey(props.uri)),
  indexSet = Set(index(props.blog, props.postDate)))
case class BlogPost(
  uri: BlogPostUri,
  title: String,
  slug: Option[Markdown] = None,
  content: Markdown,
  labels: Set[String] = Set(),
  postDate: DateTime,
  blog: BlogUri,
  authors: Set[Username])

object BlogPost {

  object queries {
    import com.github.nscala_time.time.Implicits._
    import BlogPost.queryDsl._
    import BlogPost.props._
    def recentPosts(blogUri: BlogUri): Query[BlogPost] =
      blog eqs blogUri and postDate gt DateTime.now - 1.week
  }

}
