package br.com.martinello.util.checkBox;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import br.com.martinello.util.checkBox.PriorityComboBoxModel;



/**
 * Copyright (C) 2010-2011 Carlos Eduardo Leite de Andrade
 * <P>
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <P>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <P>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <A
 * HREF="www.gnu.org/licenses/">www.gnu.org/licenses/</A>
 * <P>
 * For more information, contact: <A HREF="www.japura.org">www.japura.org</A>
 * <P>
 * 
 * @author Carlos Eduardo Leite de Andrade
 */
public abstract class DefaultPriorityComboBoxModel extends AbstractListModel implements
	PriorityComboBoxModel{

  private static final long serialVersionUID = 3463014910762087126L;
  private List<Object> items;
  private List<Object> priorityItems;
  private Object selectedItem;
  private int priorityItemsCapacity;
  private int minimalSizeForPriority;
  private boolean increasePriorityOnSelection;

  public DefaultPriorityComboBoxModel(Object[] objects) {
	this();
	addAll(objects);
  }

  public DefaultPriorityComboBoxModel(List<?> objects) {
	this();
	addAll(objects);
  }

  public DefaultPriorityComboBoxModel() {
	items = new ArrayList<Object>();
	priorityItems = new ArrayList<Object>();
	priorityItemsCapacity = 3;
	setMinimalSizeForPriority(10);
	increasePriorityOnSelection = true;
  }

  private void addAll(Object... objects) {
	for (Object obj : objects) {
	  items.add(obj);
	}
  }

  public void addElement(Object item, boolean priorityItem) {
	if (items.contains(item) == false) {
	  items.add(item);
	  int i = items.size() - 1;

	  if (priorityItem) {
		addToPriority(item);
	  }

	  fireContentsChanged(this, i, i);
	  if (selectedItem == null) {
		selectedItem = item;
	  }
	}
  }

  public PriorityComboBoxListener[] getPriorityComboBoxListeners() {
	return (PriorityComboBoxListener[]) listenerList
		.getListeners(PriorityComboBoxListener.class);
  }

  public void addPriorityComboBoxListener(PriorityComboBoxListener listener) {
	listenerList.add(PriorityComboBoxListener.class, listener);
  }

  public void removePriorityComboBoxListener(PriorityComboBoxListener listener) {
	listenerList.remove(PriorityComboBoxListener.class, listener);
  }

  protected void firePriorityComboBoxListeners() {
	PriorityComboBoxEvent event = new PriorityComboBoxEvent(this);
	PriorityComboBoxListener[] array = getPriorityComboBoxListeners();
	for (PriorityComboBoxListener listener : array) {
	  listener.priorityChanged(event);
	}
  }

  public List<Object> getPriorityItems() {
	return new ArrayList<Object>(priorityItems);
  }

  public int getPriorityItemsCapacity() {
	return priorityItemsCapacity;
  }

  public void setPriorityItemsCapacity(int capacity) {
	priorityItemsCapacity = Math.max(1, capacity);
	while (priorityItems.size() > getPriorityItemsCapacity()) {
	  int i = priorityItems.size() - 1;
	  priorityItems.remove(i);
	  fireIntervalRemoved(this, i, i);
	}
	firePriorityComboBoxListeners();
  }

  public Object getSelectedItem() {
	return selectedItem;
  }

  public void setSelectedItem(Object anItem) {
	if (items.contains(anItem)) {
	  selectedItem = anItem;
	  if (isIncreasePriorityOnSelection() && anItem != null) {
		addToPriority(anItem);
	  }
	}
  }

  @Override
  public Object getElementAt(int index) {
	if (index < 0 || index >= getSize())
	  return null;

	if (isPriorityAvailable()) {
	  if (index < priorityItems.size()) {
		return priorityItems.get(index);
	  }

	  index -= priorityItems.size();
	}
	return items.get(index);
  }

  @Override
  public int getSize() {
	if (isPriorityAvailable()) {
	  return items.size() + priorityItems.size();
	}
	return items.size();
  }

  public void addToPriority(Object obj) {
	if (priorityItems.contains(obj)) {
	  int index = priorityItems.indexOf(obj);
	  if (index > 0) {
		priorityItems.remove(index);
		priorityItems.add(index - 1, obj);
	  }
	} else {
	  if (priorityItems.size() == getPriorityItemsCapacity()) {
		priorityItems.remove(priorityItems.size() - 1);
	  }
	  priorityItems.add(obj);
	}
	if (isPriorityAvailable()) {
	  int index = priorityItems.indexOf(obj);
	  fireContentsChanged(this, index, index);
	}
	firePriorityComboBoxListeners();
  }

  public void removeFromPriority(Object obj) {
	if (priorityItems.contains(obj)) {
	  int index = priorityItems.indexOf(obj);
	  boolean fire = isPriorityAvailable();
	  priorityItems.remove(index);
	  if (fire) {
		fireContentsChanged(this, index, index);
	  }
	}
  }

  public void addElement(Object item) {
	addElement(item, false);
  }

  public void insertElementAt(Object item, int index) {
	if (items.contains(item) == false) {
	  if (isPriorityAvailable()) {
		index -= priorityItems.size();
	  }
	  if (index >= 0 && index <= items.size()) {
		items.add(index, item);
	  }
	}
  }

  public void removeElement(Object obj) {
	if (obj == null)
	  return;

	priorityItems.remove(obj);
	int index = items.indexOf(obj);
	if (items.remove(obj)) {
	  int i = index;
	  if (isPriorityAvailable()) {
		i += getPriorityItemsSize();
	  }
	  fireIntervalRemoved(this, i, i);

	  if (priorityItems.remove(obj)) {
		firePriorityComboBoxListeners();
	  }
	}
	if (selectedItem.equals(obj)) {
	  if (getSize() > 0) {
		selectedItem = getElementAt(0);
	  } else {
		selectedItem = null;
	  }
	}
  }

  public void removeElementAt(int index) {
	Object obj = getElementAt(index);
	removeElement(obj);
  }

  public boolean isIncreasePriorityOnSelection() {
	return increasePriorityOnSelection;
  }

  public void setIncreasePriorityOnSelection(boolean enable) {
	this.increasePriorityOnSelection = enable;
  }

  public int getPriorityItemsSize() {
	return priorityItems.size();
  }

  public void clearPriorities() {
	priorityItems.clear();
	firePriorityComboBoxListeners();
  }

  public int getMinimalSizeForPriority() {
	return minimalSizeForPriority;
  }

  public boolean isPriorityAvailable() {
	if (items.size() >= getMinimalSizeForPriority()) {
	  return true;
	}
	return false;
  }

  public void setMinimalSizeForPriority(int size) {
	minimalSizeForPriority = Math.max(1, size);
  }

  public boolean isPriorityItem(Object obj) {
	return priorityItems.contains(obj);
  }

}
