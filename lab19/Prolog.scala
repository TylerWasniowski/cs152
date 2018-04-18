object Prolog {

  def error(s: String) = throw new RuntimeException(s)
  
  import Iterator.{empty, single};

  var showMatches = false
  var trace = false

// --- Terms ----------------------------------------------

  abstract class Term 
  {
    def subst(s: Subst): Term
    def apply(ts: Term*): Constructor = error("" + this + " cannot be applied")
    def :: (t: Term) = new ListConstructor(t, this)
  }

  case class Variable(name: String) extends Term 
  {
    assert(Character.isUpperCase(name.charAt(0)), name)
    def subst(s: Subst): Term = lookup(s, name) match {
      case Some(t) => t subst s
      case None => this
    }
    override def toString(): String = "'" + name
  }

  case class Constructor(name: String, ts: List[Term]) extends Term 
  {
    def subst(s: Subst): Term = Constructor(name, ts map (_.subst(s)))
    override def toString(): String = "'" + name + ts.mkString("(", ",", ")")
  }

  class ListConstructor(hd: Term, tl: Term) extends Constructor("::", List(hd, tl)) 
  {
    override def subst(s: Subst): Term = new ListConstructor(hd subst s, tl subst s)
    override def toString(): String = hd.toString() + " :: " + tl.toString()
  }

  class Constant(name: String) extends Constructor(name, List()) 
  {
    assert(!Character.isUpperCase(name.charAt(0)), name)
    override def subst(s: Subst): Term = this
    override def apply(ts: Term*): Constructor = new Constructor(name, ts.toList)
    override def toString(): String = "'" + name
  }

  implicit def symbol2term(s: Symbol): Term = 
    if (Character.isUpperCase(s.name.charAt(0))) Variable(s.name)
    else new Constant(s.name)

  def freeVars(t: Any): List[String] = t match {
    case Variable(name) => List(name)
    case Constructor(name, ts) => freeVars(ts)
    case And(q1, q2) => freeVars(List(q1, q2))
    case Apply(p, args) => freeVars(args)
    case Clause(hd, tl) => freeVars(List(hd, tl))
    case ts: List[Term] => (ts flatMap freeVars).distinct
    case _ => List()
  }

  private var count = 0
  def newVar(prefix: String): Variable = { 
    count = count + 1
    Variable(prefix + count) 
  }

// --- Substitutions ----------------------------------------
    
  case class Binding(name: String, term: Term) {
    term match { 
      case Variable(n) if (name == n) => error("bad binding") 
      case _ => 
    }
    override def toString() = "'" + name + " = " + term
  }

  type Subst = List[Binding]

  def lookup(s: Subst, name: String): Option[Term] = s match {
    case List() => None
    case b :: s1 => if (name == b.name) Some(b.term) else lookup(s1, name)
  }

  def rename(s: String) = Binding(s, newVar(s))

// --- Unification ------------------------------------------

  var indent = 0 
  def show(s: String) = { 
    if (showMatches) { 
      for (i <- 0 until indent) Console.print(" ")
      Console.println(s)
    }
  }
  
  def unify(x: Term, y: Term, s: Subst): Iterator[Subst] = {
    indent+=1
    show("unify " + x + " with " + y)
    val result = Pair(x, y) match {
      case Pair(Variable(a), Variable(b)) if (a == b) =>
        single(s)
      case Pair(Variable(a), _) => lookup(s, a) match {
        case Some(x1) => unify(x1, y, s)
        case None => if (freeVars(y) contains a) empty else single(Binding(a, y) :: s)
      }
      case Pair(_, Variable(b)) => lookup(s, b) match {
        case Some(y1) => unify(x, y1, s)
        case None => if (freeVars(x) contains b) empty else single(Binding(b, x) :: s)
      }
      case Pair(Constructor(a, ts), Constructor(b, us)) if (a == b) =>
        unify(ts, us, s)
      case Pair(x, y) => 
        if (x != y) empty else single(s) 
    }
    show("" + result.hasNext)
    indent -= 1
    result
  }

  def unify(xs: List[Term], ys: List[Term], s: Subst): Iterator[Subst] = Pair(xs, ys) match {
    case Pair(List(), List()) => 
      single(s)
    case Pair(x :: xs1, y :: ys1) =>
      for {
        s1 <- unify(x, y, s)
        s2 <- unify(xs1, ys1, s1)
      } yield s2
    case _ => 
      empty
  }

// --- Solutions ------------------------------------------

  var currentSolutions: Iterator[Subst] = Iterator.empty
  var currentFreeVars: List[String] = List()

  def more: String = {
    val r = nextSolution(currentSolutions, currentFreeVars contains)
    System.out.println(r)
    r
  }

  def nextSolution(solns: Iterator[Subst], p: String => Boolean): String = 
    if (solns.hasNext) {
      val s = solns.next;
      val proj = for (Binding(a, t) <- s if p(a)) yield "'" + a + " = " + t.subst(s)
      if (proj.isEmpty) "yes" else proj.mkString("[", ", ", "]")
    } else "no"

// --- Constraints ------------------------------------------

  abstract class Query {
    def subst(s: Subst): Query
    def solve(s: Subst): Iterator[Subst]
    def solve1(s: Subst): Iterator[Subst] =
      if (trace) {
        val Pair(ss1, ss2) = solve(s).duplicate
        System.out.println("solved " + this + " at " + s + " = " + nextSolution(ss1, s => true))
        ss2
      } else solve(s)
    def & (q: Query): Query = And(this, q)
    def ? : String = {
      currentFreeVars = freeVars(this)
      currentSolutions = solve(List())
      more
    }
    def ?[T](x: => T): T = { this?; x }
  }

  case object True extends Query {
    def subst(s: Subst): Query = this
    def solve(s: Subst): Iterator[Subst] = single(s)
  }

  case class And(q1: Query, q2: Query) extends Query {
    def subst(s: Subst): Query = And(q1 subst s, q2 subst s)
    def solve(s: Subst): Iterator[Subst] = 
      for (s1 <- q1 solve1 s; s2 <- q2 solve1 s1) yield s2
  }

  case class Apply(p: Predicate, arguments: List[Term]) extends Query {
    def subst(s: Subst): Apply = Apply(p, arguments map (_.subst(s)))
    def solve(s: Subst): Iterator[Subst] =
      for {
        clause <- p.clauses map (_.newInstance)
        s1 <- unify(clause.hd.arguments, arguments, s)
        s2 <- clause.tl solve1 s1
      } yield s2
    def :- (q: Query): Predicate = p.addClause(this, q)
    def ! : Unit = p.addClause(this, True)
    def ![T](x: => T): T = { this!; x }
  }

// --- Predicates --------------------------------------------

  case class Clause(hd: Apply, tl: Query) {
    def newInstance: Clause = {
      val s = freeVars(this) map rename
      Clause(hd subst s, tl subst s)
    }
  }

  class Linked[T] { var elem: T = _; var next: Linked[T] = _}
      
  class Predicate {
    private var initClauseRef = new Linked[Clause]
    private var lastClauseRef = initClauseRef

    def addClause(hd: Apply, tl: Query) = {
      lastClauseRef.next = new Linked
      lastClauseRef.next.elem = Clause(hd, tl)
      lastClauseRef = lastClauseRef.next
      this
    }

    def & (q: Query): Unit =
      lastClauseRef.elem = Clause(lastClauseRef.elem.hd, lastClauseRef.elem.tl & q)

    def clauses = new Iterator[Clause] {
      var current = initClauseRef
      def hasNext = current != lastClauseRef
      def next = { current = current.next; current.elem }
    }

    def apply(ts: Term*): Apply = Apply(this, ts.toList)
  }
}


object Main {
  def main(args : Array[String]) : Unit = {
	import Prolog._
    showMatches = true
	val member = new Predicate
    member('X, 'X::'Xs)!
    member('X, 'Y::'Ys) :- member('X, 'Ys)
    member('X, 'john :: 'mary :: 'nil)?
    more
    more
  }
}

