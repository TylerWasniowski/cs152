class Table[-K,+V](entries: List[(K,V)] = List()) { // TODO: Type variance
  def get[A <: K](k: A): Option[V] =
    entries.find(x => x._1 == k) match {
      case Some(x) => Some(x._2)
      case None => None
    }
  // TODO: Implementation
  // TODO: The put method doesn't quite work. Make the appropriate
  // changes.
  def put[M >: V](k: K, v: M): Table[K, M] = new Table((k, v) :: entries)
}
