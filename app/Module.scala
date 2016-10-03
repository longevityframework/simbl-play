import com.google.inject.AbstractModule

import com.google.inject.TypeLiteral
import com.google.inject.name.Names
import domain.Blog
import domain.BlogPost
import domain.SimblSubdomain
import domain.User
import longevity.context.LongevityContext
import longevity.persistence.Repo
import play.api.Configuration
import play.api.Environment

class Module(environment: Environment, configuration: Configuration) extends AbstractModule {

  private lazy val coreDomain = new SimblSubdomain
  private lazy val longevityContext = LongevityContext(coreDomain)
  private lazy val repoPool = longevityContext.repoPool

  lazy val blogRepo = repoPool[Blog]
  lazy val blogPostRepo = repoPool[BlogPost]
  lazy val userRepo = repoPool[User]

  override def configure() = {
    bind(new TypeLiteral[Repo[Blog    ]]{}).toInstance(blogRepo)
    bind(new TypeLiteral[Repo[BlogPost]]{}).toInstance(blogPostRepo)
    bind(new TypeLiteral[Repo[User    ]]{}).toInstance(userRepo)
  }

}
