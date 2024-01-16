package nameable

import dotty.tools.dotc.plugins._

class Plugin extends StandardPlugin {
  val name: String = "nameable"

  override val description: String = "Name nameable things"

  def init(options: List[String]): List[PluginPhase] =
    new NameablePhase :: Nil
}
