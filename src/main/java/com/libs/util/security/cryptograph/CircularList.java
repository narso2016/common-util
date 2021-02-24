package com.libs.util.security.cryptograph;

class CircularList {

	Object[] list;
	int mask;
	int head;
	int tail;

	CircularList(int size) {
		int powerOf2 = 0x1;;
		while (powerOf2 < size) powerOf2 = powerOf2 << 2;
		list = new Object[powerOf2];
		mask = powerOf2 - 1;
	}

	public int getCapacity() {
		return list.length;
	}

	public synchronized Object getHead() {
		Object v = null;
		if (head != tail) {
			v = list[head];
			head = (head + 1) & mask;
		}
		return v;
	}

	public synchronized void enqueue(Object o) {
		int newTail = (tail + 1) & mask;
		if (head != newTail) {
			list[tail] = o;
			tail = newTail;
		}
	}

}
