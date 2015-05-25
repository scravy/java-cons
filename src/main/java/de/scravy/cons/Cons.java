package de.scravy.cons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import de.scravy.pair.Pair;
import de.scravy.pair.Pairs;

/**
 * A singly linked, persistent (immutable), homogeneous list.
 *
 * No method will mutate the list. The list is immutable, unmodifieable,
 * you-name-it.
 *
 * @author Julian Fleischer
 * @since 1.0.0
 *
 * @param <E>
 *          The Element type.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cons<E> implements Pair<E, Cons<E>>, Iterable<E>, List<E> {

  private static Cons<?> EMPTY = new Cons<Object>(null, null);

  private final E head;
  private final Cons<E> tail;

  /**
   * Creates a list with one element.
   *
   * @since 1.0.0
   * @param value
   *          The one element.
   * @param <E>
   *          The Element type.
   * @return A list with one element.
   */
  @SuppressWarnings("unchecked")
  public static <E> Cons<E> singleton(final E value) {
    return new Cons<E>(value, (Cons<E>) EMPTY);
  }

  /**
   * The empty list.
   *
   * @since 1.0.0
   * @param <E>
   *          The Element type.
   * @return An empty list.
   */
  @SuppressWarnings("unchecked")
  public static <E> Cons<E> empty() {
    return (Cons<E>) EMPTY;
  }

  /**
   * Create a list from an existing list and an element (push to the front of
   * the list, returns the new list).
   *
   * The complexity of this function is <code>O(1)</code>.
   *
   * @since 1.0.0
   * @param head
   *          The head for the new list.
   * @param tail
   *          The tail for the new list (the list to prepend to).
   * @param <E>
   *          The Element type.
   * @return The new list which consists of head and tail.
   */
  public static <E> Cons<E> cons(final E head, final Cons<E> tail) {
    if (tail == null) {
      throw new IllegalArgumentException();
    }
    return new Cons<E>(head, tail);
  }

  /**
   * Prepend an element to this list.
   *
   * The complexity of this function is <code>O(1)</code>.
   *
   * @param head
   * @return The new element with the element prepended.
   */
  public Cons<E> cons(final E head) {
    return cons(head, this);
  }

  /**
   * Create a list from an existing array.
   *
   * @since 1.0.0
   * @param array
   *          The array.
   * @param <E>
   *          The Element type.
   * @return The list created from that array.
   */
  @SafeVarargs
  public static <E> Cons<E> fromArray(final E... array) {
    Cons<E> current = Cons.empty();
    for (int i = array.length - 1; i >= 0; i -= 1) {
      current = cons(array[i], current);
    }
    return current;
  }

  /**
   * Check whether this list is empty.
   *
   * The complexity of this function is <code>O(1)</code>.
   *
   * @since 1.0.0
   * @return Whether this list is empty or not.
   */
  @Override
  public boolean isEmpty() {
    return this == EMPTY;
  }

  /**
   * Get the length of the list.
   *
   * The complexity of this function is <code>O(n)</code>.
   *
   * @since 1.0.0
   * @return The length of the list.
   */
  public int getLength() {
    int count = 0;
    Cons<E> current = this;
    while (current != EMPTY) {
      count += 1;
      current = current.tail;
    }
    return count;
  }

  /**
   * Transform this list into a Collections list.
   *
   * @since 1.0.0
   * @return The newly created list.
   */
  public List<E> toList() {
    final List<E> list = new ArrayList<>(getLength());
    return toCollection(list);
  }

  /**
   * Add the contents of this list to the given collection.
   *
   * @since 1.0.0
   * @param <C>
   *          The collection type.
   * @param collection
   *          The collection to add things to.
   * @return The (mutated) collection is returned.
   */
  public <C extends Collection<E>> C toCollection(final C collection) {
    Cons<E> current = this;
    while (current != EMPTY) {
      collection.add(current.head);
      current = current.tail;
    }
    return collection;
  }

  /**
   * Retrieve the element at the given index.
   *
   * @since 1.0.0
   * @param ix
   *          The index.
   * @return The element at the given index.
   * @throws NoSuchElementException
   *           Thrown if the list does not contain this many elements (iff
   *           <code>getLength() ≥ index</code>).
   */
  @Override
  public E get(final int ix) {
    Cons<E> current = this;
    int i = 0;
    while (i < ix && current != EMPTY) {
      current = current.tail;
      i += 1;
    }
    if (current == EMPTY) {
      throw new NoSuchElementException();
    }
    return current.head;
  }

  @Override
  public E getFirst() {
    return this.head;
  }

  @Override
  public Cons<E> getSecond() {
    return this.tail;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {

      Cons<E> current = Cons.this;

      @Override
      public boolean hasNext() {
        return this.current != EMPTY;
      }

      @Override
      public E next() {
        final E value = this.current.head;
        this.current = this.current.tail;
        return value;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public int size() {
    return getLength();
  }

  @Override
  public boolean contains(final Object o) {
    for (final E e : this) {
      if (e == o || (e != null && e.equals(o))) {
        return true;
      }
    }
    return false;
  }

  public boolean doesNotContain(final Object o) {
    for (final E e : this) {
      if (e == o || (e != null && e.equals(o))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Object[] toArray() {
    return toArray(new Object[getLength()]);
  }

  @Override
  public <T> T[] toArray(final T[] a) {
    return toArray(a, 0);
  }

  @SuppressWarnings("unchecked")
  public <T> T[] toArray(final T[] a, final int offset) {
    final int i = offset;
    for (final E e : this) {
      if (i + offset >= a.length) {
        return a;
      }
      a[i] = (T) e;
    }
    return a;
  }

  @Override
  public boolean add(final E e) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(final Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(final Collection<?> c) {
    final Set<E> set = new HashSet<E>(this);
    for (final Object e : c) {
      if (set.contains(e)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(final Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(final int index, final Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public E set(final int index, final E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(final int index, final E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public E remove(final int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int indexOf(final Object o) {
    int i = 0;
    for (final E e : this) {
      if (Objects.equals(e, o)) {
        return i;
      }
      i += 1;
    }
    return -1;
  }

  @Override
  public int lastIndexOf(final Object o) {
    int i = 0;
    int ix = -1;
    for (final E e : this) {
      if (Objects.equals(e, o)) {
        ix = i;
      }
      i += 1;
    }
    return ix;
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator(final int index) {
    throw new UnsupportedOperationException();
  }

  public Cons<E> take(final int n) {
    final Deque<E> stack = new LinkedList<>();
    int i = 0;
    for (final E e : this) {
      if (i >= n) {
        break;
      }
      stack.push(e);
      i += 1;
    }
    Cons<E> result = Cons.empty();
    while (!stack.isEmpty()) {
      result = result.cons(stack.pop());
    }
    return result;
  }

  public Cons<E> drop(final int n) {
    return subList(n);
  }

  public Cons<E> subList(final int ix) {
    Cons<E> current = this;
    int i = 0;
    while (i < ix && current != EMPTY) {
      current = current.tail;
      i += 1;
    }
    return current;
  }

  public Cons<E> reverse() {
    final Deque<E> stack = new LinkedList<>();
    for (final E e : this) {
      stack.push(e);
    }
    Cons<E> current = Cons.empty();
    while (!stack.isEmpty()) {
      current = current.cons(stack.pop());
    }
    return current;
  }

  @Override
  public Cons<E> subList(final int fromIndex, final int toIndex) {
    return drop(fromIndex).take(toIndex - fromIndex);
  }

  @Override
  public int hashCode() {
    return Pairs.hashCode(this);
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other instanceof Pair) {
      return Pairs.equals(this, other);
    }
    if (other == null) {
      return false;
    }
    if (getClass() != other.getClass()) {
      return false;
    }
    final Iterator<?> it1 = iterator();
    final Iterator<?> it2 = ((Cons<?>) other).iterator();
    while (it1.hasNext() && it2.hasNext()) {
      if (!Objects.equals(it1.next(), it2.next())) {
        return false;
      }
    }
    return !it1.hasNext() && !it2.hasNext();
  }
}
