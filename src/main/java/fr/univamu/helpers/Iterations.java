package fr.univamu.helpers;

import fr.univamu.graph.rootedtrees.RootedTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Iterations {

  public static Iterator<Integer> range(int fromIncluded, int toExcluded) {
    return new Iterator<Integer>() {
      private int current;

      @Override
      public boolean hasNext() {
        return current < toExcluded;
      }

      @Override
      public Integer next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return current++;
      }
    };
  }


  public static <S,T> Iterator<T> map(Iterator<S> iterator, Function<S,T> f) {
    return new Iterator<T>() {

      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public T next() {
        return f.apply(iterator.next());
      }
    };
  }

  public static <T> Stream<T> toStream(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }

  public static <T> Iterable<T> empty() {
    return () -> new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public T next() {
        throw new NoSuchElementException();
      }
    };
  }

  public static <T> Iterable<T> single(T element) {
    return () -> new Iterator<T> () {
      private boolean hasNext = true;
      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        hasNext = false;
        return element;
      }
    };
  }

  public static <T> Iterable<T> append(Iterable<T> iter1, Iterable<T> iter2) {
    return concat(List.of(iter1, iter2));
  }

  public static <T> Iterable<T> concat(Iterable<? extends Iterable<T>> iterables) {
    return () -> new Iterator<T>() {

      private Iterator<? extends Iterable<T>> futureIterators = iterables.iterator();
      private Iterator<T> currentIterator;

      { prepareNext(); }

      private void prepareNext() {
        while (currentIterator == null
            || !currentIterator.hasNext() && futureIterators.hasNext()) {
          currentIterator = futureIterators.next().iterator();
        }
      }

      @Override
      public boolean hasNext() {
        return currentIterator.hasNext();
      }

      @Override
      public T next() {
        if (!currentIterator.hasNext()) {
          throw new NoSuchElementException();
        }
        T value = currentIterator.next();
        prepareNext();
        return value;
      }
    };
  }

  public static <T> Iterable<T> reverse(Iterable<T> bfsIterable) {
    List<T> list = new ArrayList<>();
    bfsIterable.forEach(list::add);
    return () -> new Iterator<T>() {
      private int index = list.size()-1;
      @Override
      public boolean hasNext() {
        return index >= 0;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return list.get(index--);
      }
    };
  }
}
