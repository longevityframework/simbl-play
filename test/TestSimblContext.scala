import longevity.context.LongevityContext
import domain.SimblDomainModel

/** default container for all the simbl application components */
trait TestSimblContext {
  val longevityContext = LongevityContext(SimblDomainModel)
}

object TestSimblContext extends TestSimblContext
