

扩容问题？扩容考虑溢出问题

数据结构？数组

fail-fast？Iterator

```
综述：
（1）内部是一个数组结构
（2）数组结构的随机访问快，快在基于迭代器或者数组下标访问；随机增删慢，慢在每次随机增删均要移动数组
（3）线程不安全
（4）懒加载初始化，初始化大小默认为0，减少内存占用
（5）扩容，先检测容量溢出再1.5倍扩容，位移+原容量
（6）fail-fast基于Iterator迭代器，每次元素发生变化均改变modCount计数，对比判断
```

1、数组扩容使用了newCapacity = oldCapacity + (oldCapacity >> 1);，增强了对index溢出问题的检测

2、结构为数组结构，删除元素remove与指定位置add元素，均会造成数组复制移动元素

3、随机访问O(1)复杂度，add也是

4、使用Iterator迭代器，add，remove均改变modCount值，比较expectedModCount=modCount实现快速失败



初始化

懒初始化：指的是默认构造方法构造的集合类，占据尽可能少的内存空间

```java
1.6的无参构造方法（默认构造方法）构造的ArrayList的底层数组elementData大小（容量）默认为10；
1.7开始，无参构造方法构造的ArrayList的底层数组elementData大小默认为0
// 1.8
/**
 * Shared empty array instance used for default sized empty instances. We
 * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
 * first element is added.
 */
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
/**
 * Constructs an empty list with an initial capacity of ten.
 */
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}

// 1.8 带初始容量的构造函数
public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
        this.elementData = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
        this.elementData = EMPTY_ELEMENTDATA; // 重用空数组，一个小小的优化
    } else {
        throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
    }
}

// jdk 1.8
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();// 实际的元素占据的内存空间还是共享的
    if ((size = elementData.length) != 0) {
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        // replace with empty array.
        this.elementData = EMPTY_ELEMENTDATA;
    }
}

// 1.6
/** Constructs an empty list with an initial capacity of ten. */
public ArrayList() {
    this(10);
}
```

扩容

***ensureCapacity/ensureCapacityInternal***

这两个方法只是确保容量，不一定会扩容

1、对比1.6时候的扩容：int newCapacity = (oldCapacity * 3)/2 + 1;----int过早溢出问题

2、MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;----array header影响实际大小



（1）扩容包含自动扩容跟手动扩容

（2）先检测最小需要扩容的量是否溢出，在检测扩容后的大小是否溢出，进行1.5倍扩容

```java
// 手动扩容方法（可以外部调用，不过大多数情况都是List<?> = new ArrayList<>()，这样是调用不到这个方法的）
// 这个方法只是简单区别下list是不是通过 new ArrayList() 来创建的，这一点前面说了
// 如果是，则尝试最小扩容10个，不是则尝试扩容指定个，具体也是通过内部扩容方法完成容量确保
public void ensureCapacity(int minCapacity) {
    int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
        // any size if not default element table
        ? 0
        // larger than default for default empty table. It's already
        // supposed to be at default size.
        : DEFAULT_CAPACITY;
 
    if (minCapacity > minExpand) {
        ensureExplicitCapacity(minCapacity);
    }
}
 
// 下面是内部扩容相关的几个方法的代码
private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
 
    ensureExplicitCapacity(minCapacity);
}
 
private void ensureExplicitCapacity(int minCapacity) {
    modCount++;
 
    // overflow-conscious code 考虑int型溢出
    if (minCapacity - elementData.length > 0)// 检测出int型溢出
        grow(minCapacity);
}
 
private void grow(int minCapacity) {
    // overflow-conscious code 考虑int型溢出
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);// 1.5倍扩容
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)// MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}
 
private static int hugeCapacity(int minCapacity) {
    if (minCapacity < 0) // overflow int型溢出，直接报错
        throw new OutOfMemoryError();
    return (minCapacity > MAX_ARRAY_SIZE) ?
        Integer.MAX_VALUE :
        MAX_ARRAY_SIZE;
}
```

其他方法

```java
/**
 * Appends the specified element to the end of this list.
 *
 * @param e element to be appended to this list
 * @return <tt>true</tt> (as specified by {@link Collection#add})
 */
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
public void add(int index, E element) {
    rangeCheckForAdd(index);
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
public int indexOf(Object o) {
    if (o == null) {
        for (int i = 0; i < size; i++)
            if (elementData[i]==null)
                return i;
    } else {
        for (int i = 0; i < size; i++)
            if (o.equals(elementData[i]))
                return i;
    }
    return -1;
}
public Object clone() {
    try {
        ArrayList<?> v = (ArrayList<?>) super.clone();
        v.elementData = Arrays.copyOf(elementData, size);
        v.modCount = 0;
        return v;
    } catch (CloneNotSupportedException e) {
        // this shouldn't happen, since we are Cloneable
        throw new InternalError(e);
    }
}
public E remove(int index) {
    rangeCheck(index);// 检测index是否index >= size
    modCount++;
    E oldValue = elementData(index);
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    elementData[--size] = null; // clear to let GC do its work

    return oldValue;
}
public E set(int index, E element) {
    rangeCheck(index);
    E oldValue = elementData(index);
    elementData[index] = element;
    return oldValue;
}
```

Iterator--fail-fast

```java
public Iterator<E> iterator() {
    return new Itr();
}
/**
 * An optimized version of AbstractList.Itr
 */
private class Itr implements Iterator<E> {
    int cursor;       // index of next element to return
    int lastRet = -1; // index of last element returned; -1 if no such
    int expectedModCount = modCount;

    public boolean hasNext() {
        return cursor != size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        checkForComodification();
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i + 1;
        return (E) elementData[lastRet = i];
    }

    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        final int size = ArrayList.this.size;
        int i = cursor;
        if (i >= size) {
            return;
        }
        final Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length) {
            throw new ConcurrentModificationException();
        }
        while (i != size && modCount == expectedModCount) {
            consumer.accept((E) elementData[i++]);
        }
        // update once at end of iteration to reduce heap write traffic
        cursor = i;
        lastRet = i - 1;
        checkForComodification();
    }

    final void checkForComodification() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```