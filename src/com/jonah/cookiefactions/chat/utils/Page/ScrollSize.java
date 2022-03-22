package me.jonah.chat.utils.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrollSize<K, V> {

	private int maxscroll;
	private int curscroll;
	private int each;
	private Map<K, V> hash;
	
	public ScrollSize(Map<K, V> hash, int curscroll, int each) {
		this.hash = hash;
		if ((curscroll - 1) < 0) throw new IllegalArgumentException("Current Scroll cannot <= 0");
		this.curscroll = curscroll - 1;
		this.each = each;
		this.maxscroll = roundUp((double) hash.size() / (double) each);
	}
	
	public int getMaxScroll() {
		return maxscroll;
	}
	
	public int getScroll() {
		return curscroll;
	}
	
	public int getEach() {
		return each;
	}
	
	public Map<K, V> getMap() {
		return hash;
	}
	
	public boolean isValidPage() {
		return (!getContent().isEmpty());
	}
	
	public boolean hasNextPage() {
		int sz = new ScrollSize(hash, (curscroll + 1), each).getContent().size();
		switch (sz) {
			case 1:
			case 0:
				return false;
			default:
				return true;
		}
	}
	
	public boolean hasPreviousPage() {
		return ((curscroll - 1) > -1);
	}
	
	public int roundUp(double db) {
		String[] rt = String.valueOf(db).split("\\.");
		try {
			if (rt[1] == "0" || rt[1] == "00") {
				return (Integer.valueOf(rt[0]));
			} else {
				return (Integer.valueOf(rt[0]) + 1);
			}
		} catch (IndexOutOfBoundsException e) {
			return Integer.valueOf(rt[0]);
		}
	}
	
	public Map<K, V> getContent() {
		List<K> list = new ArrayList<>();
		for (K o : hash.keySet()) {
			list.add(o);
		}
		int i = 0;
		while (i < curscroll) {
			i++;
		}
		HashMap<K, V> comhash = new HashMap<K, V>();
		for (int i2 = i ; i2 < (each + curscroll) ; i2++) {
			try {
				comhash.put(list.get(i2), hash.get(list.get(i2)));
			} catch (IndexOutOfBoundsException e) {}
		}
		return comhash;
	}
	
	public Map<K, V> getContent(int inp) {
		List<K> list = new ArrayList<K>();
		for (K o : hash.keySet()) {
			list.add(o);
		}
		int i = 0;
		while (i < curscroll) {
			i++;
		}
		HashMap<K, V> comhash = new HashMap<K, V>();
		for (int i2 = i ; i2 < (each + inp) ; i2++) {
			try {
				comhash.put(list.get(i2), hash.get(list.get(i2)));
			} catch (IndexOutOfBoundsException e) {}
		}
		return comhash;
	}
}
