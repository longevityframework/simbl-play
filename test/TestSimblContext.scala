import longevity.context.LongevityContext
import domain.SimblSubdomain

/** default container for all the simbl application components */
trait TestSimblContext {
  val longevityContext = LongevityContext(SimblSubdomain)
}

object TestSimblContext extends TestSimblContext
