import com.google.inject.AbstractModule

import com.google.inject.TypeLiteral
import com.google.inject.name.Names
import domain.SimblDomainModel
import longevity.context.LongevityContext
import longevity.persistence.Repo
import play.api.Configuration
import play.api.Environment
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Module(environment: Environment, configuration: Configuration) extends AbstractModule {

  private lazy val longevityContext = LongevityContext[Future, SimblDomainModel]()
  lazy val repo = longevityContext.repo

  override def configure() = {
    bind(new TypeLiteral[Repo[Future, SimblDomainModel]]{}).toInstance(repo)
  }

}
