package me.jonah.chat.utils.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageSize<K, V> {
	
	private int element;
	private int perPage;
	private int elementconfig;
	private int maxpage;
	private int page;
	private Map<K, V> hash;
	
	public PageSize(Map<K, V> hash, int perPage) {
		this.hash = hash;
		this.perPage = perPage;
		try {
			this.maxpage = roundUp((double) hash.size() / (double) perPage);
		} catch (NullPointerException e) {
			this.maxpage = 1;
		}
	}
	
	public PageSize(Map<K, V> hash, int perPage, int page) {
		this.hash = hash;
		this.perPage = perPage;
		this.page = page;
		try {
			this.maxpage = roundUp((double) hash.size() / (double) perPage);
		} catch (NullPointerException e) {
			this.maxpage = 1;
		}
	}
	
	public int getPageSize() {
		return this.perPage;
	}
	
	public int getMaxPage() {
		return this.maxpage;
	}
	
	public Map<?, ?> getHashMap() {
		return this.hash;
	}
	
	public int getPage() {
		return this.page;
	}
	
	public void setPage(int page) {
		this.page = page;
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
	
	public boolean hasNextPage(boolean byRounding) {
		return byRounding ? ((this.page) + 1 <= this.maxpage) : !getContent(page + 1).isEmpty();
	}
	
	public boolean hasPreviousPage() {
		return !((this.page - 1) <= 0);
	}
	
	public boolean isValidPage() {
		// If page given is above 0
		return (this.page > 0 && this.page <= this.maxpage);
	}
	
	public Map<K, V> getContent(int page) {
		if (perPage <= 0) throw new IllegalArgumentException("Page cannot be negative");
		if (page > 1) {
			element = (page * perPage) - perPage;
			elementconfig = element + perPage;
		} else if (page <= 0) {
			throw new IllegalArgumentException("Page cannot be negative");
		} else {
			element = 0;
			elementconfig = perPage;
		}
		maxpage = hash.size() / perPage;
		Map<K, V> comhash = new HashMap<>();
		if (!hash.isEmpty()) {
			List<K> complete = new ArrayList<K>();
			for (K o : hash.keySet()) {
				complete.add(o);
			}
			for (int i = 0 ; i < hash.keySet().size() ; i++) {
				try {
					if (element <= i && i < elementconfig) comhash.put(complete.get(i), hash.get(complete.get(i)));
				} catch (IndexOutOfBoundsException e) {
					//
				}	
			}
		}
		return comhash;
	}
	
	public Map<K, V> getContent() {
		if (perPage <= 0) throw new IllegalArgumentException("Page cannot be negative");
		if (page > 1) {
			element = (page * perPage) - perPage;
			elementconfig = element + perPage;
		} else if (page <= 0) {
			throw new IllegalArgumentException("Page cannot be negative");
		} else {
			element = 0;
			elementconfig = perPage;
		}
		HashMap<K, V> comhash = new HashMap<K, V>();
		if (!hash.isEmpty()) {
			List<K> complete = new ArrayList<K>();
			for (K o : hash.keySet()) {
				complete.add(o);
			}
			for (int i = 0 ; i < hash.keySet().size() ; i++) {
				try {
					if (element <= i && i < elementconfig) comhash.put(complete.get(i), hash.get(complete.get(i)));
				} catch (IndexOutOfBoundsException e) {
					//
				}	
			}
		}
		return comhash;
	}
	
	/*
	public static void main(String[] args) throws NegativePageException {
		HashMap<Integer, Integer> ints = new HashMap<Integer, Integer>();
		for (int i = 0 ; i < 100 ; i++) {
			ints.put(i, i*i);
		}
		PageSize page = new PageSize(ints, 6);
		for (int i2 = 1 ; i2 < (page.getMaxPage() + 1) ; i2++) {
			page.setPage(i2);
			System.out.println(page.getContent().keySet() + " (" + page.hasPreviousPage() +  ") " + " (" + page.hasNextPage() + ") ");
		}
	}
	*/
}
