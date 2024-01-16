package nameable

case class Nameable(value: String) {
  var name: String = ""
  override def toString: String = s"Nameable($value, $name)"
}

object Nameable {
  var i = 0L
  def name[A <: Nameable](name: String)(a: => A): A = {
    i += 1
    val n = name + s"_$i"
    val res = a
    res.name = n
    res
  }
}
