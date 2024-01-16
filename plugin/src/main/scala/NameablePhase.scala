package nameable

import dotty.tools.dotc._

import plugins._

import core._
import Types._
import Contexts._
import Symbols._
import Flags._
import SymDenotations._

import Decorators._
import ast.Trees._
import ast.tpd
import StdNames.nme
import Names._
import Constants.Constant

import scala.language.implicitConversions


class NameablePhase extends PluginPhase {
  import tpd._

  val phaseName = "NameablePhase"

  override val runsAfter = Set(transform.Pickler.name)
  override val runsBefore = Set(transform.Inlining.name)

  private var nameableType: Type = _
  private var nameableObj: Symbol = _
  private var doName: Symbol = _

  override def prepareForUnit(tree: Tree)(using Context): Context = {
    nameableType = requiredClassRef("nameable.Nameable")
    nameableObj = requiredModule("nameable.Nameable")
    doName = nameableObj.requiredMethod("name")
    ctx
  }

  override def transformValDef(tree: ValDef)(using Context): Tree = {
    // println(s"Inspecting ${tree.name}\n  ${tree}\n  ${show(tree)}")
    val sym = tree.symbol

    if (tree.rhs.isEmpty || sym.isOneOf(Synthetic | Deferred | Accessor)) {
      return tree
    }
    
    if (tree.tpe <:< nameableType) {
      val n = Literal(Constant(tree.name.mangledString))
      val res = tpd.ref(nameableObj).select(doName).appliedToType(tree.rhs.tpe).appliedTo(n).appliedTo(tree.rhs)
      
      tpd.ValDef(tree.symbol.asTerm, res, inferred = true)
    } else {
      tree
    }
  }
}

