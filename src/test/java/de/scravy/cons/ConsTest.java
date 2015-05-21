package de.scravy.cons;

import org.junit.Assert;
import org.junit.Test;

public class ConsTest {

  @Test
  public void head() {
    final Cons<Character> cons = Cons.singleton('c');
    Assert.assertEquals((Character) 'c', cons.getHead());
  }

  @Test
  public void tail() {
    final Cons<Character> cons = Cons.singleton('c');
    Assert.assertEquals(Cons.empty(), cons.getTail());
    Assert.assertEquals(
        Cons.fromArray(2, 3), Cons.fromArray(1, 2, 3).getTail());
  }

  @Test
  public void length() {
    Assert.assertEquals(1, Cons.singleton(1).getLength());
    Assert.assertEquals(5, Cons.fromArray(1, 2, 3, 4, 5).getLength());
  }

  @Test
  public void empty() {
    Assert.assertEquals(0, Cons.empty().getLength());
  }

  @Test
  public void equals() {
    final Cons<Character> cons1 = Cons.cons('a', Cons.singleton('c'));
    final Cons<Character> cons2 = Cons.cons('a',
        Cons.cons('c', Cons.<Character> empty()));
    Assert.assertEquals(cons1, cons2);
    Assert.assertEquals(
        Cons.fromArray(1, 2, 3, 4, 5),
        Cons.fromArray(1, 2, 3, 4, 5));
  }

  @Test
  public void hash() {
    final Cons<Character> cons1 = Cons.cons('a', Cons.singleton('c'));
    final Cons<Character> cons2 = Cons.cons('a',
        Cons.cons('c', Cons.<Character> empty()));
    Assert.assertEquals(cons1.hashCode(), cons2.hashCode());
    Assert.assertEquals(
        Cons.fromArray(1, 2, 3, 4, 5).hashCode(),
        Cons.fromArray(1, 2, 3, 4, 5).hashCode());
  }
}
