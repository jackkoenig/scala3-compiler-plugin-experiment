# Scala 3 Compiler Plugin Experiment

Insert `Nameable.name(<name>)(<rhs>)` around the RHS of vals of type Nameable.

A Scala 3 compiler plugin to count the most frequently called methods in a program.

## Use

```
$ sbt
> plugin / publishLocal ; nameable / publishLocal; hello / run
```
