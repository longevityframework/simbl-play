import longevity.context.LongevityContext
import domain.SimblSubdomain

/** default container for all the simbl application components */
trait TestSimblContext {
  val coreDomain = new SimblSubdomain
  val longevityContext = LongevityContext(coreDomain)
}

object TestSimblContext extends TestSimblContext
