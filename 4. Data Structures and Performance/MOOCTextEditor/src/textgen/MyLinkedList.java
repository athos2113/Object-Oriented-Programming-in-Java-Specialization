package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 *
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);

		head.next = tail;
		tail.prev = head;

	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element )
	{
		// TODO: Implement this method
		if(element == null){
			throw new NullPointerException("Null Element !");
		}

		LLNode<E> newNode = new LLNode<E>(element);
		tail.prev.next = newNode;
		newNode.prev = tail.prev;
		newNode.next = tail;
		tail.prev = newNode;
		size++;
		return true;
	}

	public void addFront(E element){
		LLNode<E> newNode = new LLNode<E>(element);
		newNode.next = head.next;
		newNode.prev = newNode.next.prev;
		newNode.next.prev = newNode;
		head.next = newNode;
		size++;
	}

	/** Get the element at position index
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
		// TODO: Implement this method.
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Wrong Index !");
		}

		LLNode<E> currNode = head;
		//while(index-- > 0){
		//	currNode = currNode.next;
		//}
		for(int i =0; i <= index; i++){
			currNode = currNode.next;
		}

		return currNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element )
	{
		// TODO: Implement this method
		if(element == null){
			throw new NullPointerException("Null Element !");
		}

		if((index < 0 || index >= size) && size != 0){
			throw new IndexOutOfBoundsException("Wrong Index !");
		}

		LLNode<E> prev = head;
		for(int i =0; i<index; i++){
			prev = prev.next;
		}

		LLNode<E> newNode = new LLNode<E>(element);
		newNode.next = prev.next;
		prev.next = newNode;
		newNode.next.prev = newNode;
		newNode.prev = prev;
		size++;

	}


	/** Return the size of the list */
	public int size()
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 *
	 */
	public E remove(int index)
	{
		// TODO: Implement this method
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException("Wrong Index !");
		}

		LLNode<E> currNode = head;
		for(int i = 0; i<=index; i++){
			currNode = currNode.next;
		}

		currNode.prev.next = currNode.next;
		currNode.next.prev = currNode.prev;
		size--;

		return currNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element)
	{
		// TODO: Implement this method
		if((index < 0 || index >= size) && size != 0){
			throw new IndexOutOfBoundsException("Wrong Index !");
		}
		if(element == null){
			throw new NullPointerException("Null Element !");
		}

		LLNode<E> currNode = head;
		for(int i=0; i<index; i++){
			currNode = currNode.next;
		}

		LLNode<E> oldNode = currNode;
		currNode.data = element;
		return oldNode.data;
	}
}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e)
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode() {
		this.prev = null;
		this.next = null;
	}

}
