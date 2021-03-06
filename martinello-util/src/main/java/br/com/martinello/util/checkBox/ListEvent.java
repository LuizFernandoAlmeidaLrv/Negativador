package br.com.martinello.util.checkBox;

import java.util.List;
import br.com.martinello.util.checkBox.ListCheckModel;

/**
 * 
 * <P>
 * Copyright (C) 2010 Carlos Eduardo Leite de Andrade
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
public class ListEvent{

  private ListCheckModel source;
  private List<Object> values;
  private boolean valueIsAdjusting;

  public ListEvent(ListCheckModel source, List<Object> values,
	  boolean valueIsAdjusting) {
	this.source = source;
	this.values = values;
	this.valueIsAdjusting = valueIsAdjusting;
  }

  @Override
  public String toString() {
	return getClass().getName() + " " + isValueIsAdjusting() + " "
		+ getValues();
  }

  public ListCheckModel getSource() {
	return source;
  }

  public List<Object> getValues() {
	return values;
  }

  /**
   * Returns whether or not this is one in a series of multiple events, where
   * changes are still being made.
   * 
   * @return boolean
   */
  public boolean isValueIsAdjusting() {
	return valueIsAdjusting;
  }

}
