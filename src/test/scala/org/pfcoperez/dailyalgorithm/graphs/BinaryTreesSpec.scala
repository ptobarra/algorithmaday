package org.pfcoperez.dailyalgorithm.graphs

import org.pfcoperez.dailyalgorithm.Graphs.BinaryTrees._

import org.scalatest._


class BinaryTreesSpec extends FlatSpec with Matchers {

  import RawBinaryTree._

  val input = Seq(3, 2, 5, 1, 6, 0)

  val btree: BinaryTree[Int] = ((Empty: BinaryTree[Int]) /: input) {
    (tree, element) => insert(tree)(element)
  }


  "BinaryTrees" should "be able to find the elements on them inserted" in {

    toList(btree) should be (input sorted)

  }

  it should "provide the right minimum and maximum values" in {

    min(btree) shouldBe Some(input.min)
    max(btree) shouldBe Some(input.max)

  }

  it should "be able to delete items" in {

    val orderedInput = input sorted

    val trees_deleted = input.scan(btree -> Set.empty) {
      case ((tree: BinaryTree[Int @ unchecked], deleted: Set[Int @ unchecked]), toDelete: Int) =>
        val newTree = delete(tree)(toDelete)
        (newTree, deleted + toDelete)
    }

    trees_deleted foreach { case (tree: BinaryTree[Int @ unchecked], deleted: Set[Int @ unchecked]) =>
      val expected = orderedInput filterNot (deleted.contains)
      toList(tree) should be (expected)
    }

  }

}
