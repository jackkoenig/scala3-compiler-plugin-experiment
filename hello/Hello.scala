import nameable.Nameable

object Hello {
  def main(args: Array[String]): Unit = {

    val y = Nameable("hi")
    println(y)

    val zz = Nameable("bye")
    println(zz)
  }
}
