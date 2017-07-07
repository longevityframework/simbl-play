import domain.SimblDomainModel
import longevity.context.LongevityContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** default container for all the simbl application components */
trait TestSimblContext {
  val longevityContext = LongevityContext[Future, SimblDomainModel]()
}

object TestSimblContext extends TestSimblContext
