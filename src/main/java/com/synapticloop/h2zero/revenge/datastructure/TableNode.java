package com.synapticloop.h2zero.revenge.datastructure;

/*
 * Copyright (c) 2013-2026 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import com.synapticloop.h2zero.revenge.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents a node in the table dependency graph.</p>
 *
 * <p>A node wraps a Table and maintains references to its parents (tables it references)
 * and its children (tables that reference it).</p>
 */
public class TableNode {
	private final Table table;
	private final List<TableNode> children = new ArrayList<>();
	private final List<TableNode> parents = new ArrayList<>();
	private boolean isOrdered = false;

	/**
	 * <p>Constructs a new TableNode for the given table.</p>
	 *
	 * @param table the table to wrap
	 */
	public TableNode(Table table) {
		this.table = table;
	}

	/**
	 * <p>Adds a child node to this table. A child is a table that has a foreign key
	 * referencing this table.</p>
	 *
	 * @param child the referencing table node
	 */
	public void addChild(TableNode child) {
		if (!children.contains(child)) {
			children.add(child);
		}
	}

	/**
	 * <p>Adds a parent node to this table. A parent is a table that this table
	 * references via a foreign key.</p>
	 *
	 * @param parent the referenced table node
	 */
	public void addParent(TableNode parent) {
		if (!parents.contains(parent)) {
			parents.add(parent);
		}
	}

	/**
	 * <p>Gets the wrapped table.</p>
	 *
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * <p>Gets the name of the wrapped table.</p>
	 *
	 * @return the table name
	 */
	public String getName() {
		return table.getName();
	}

	/**
	 * <p>Gets the list of child nodes (tables that reference this one).</p>
	 *
	 * @return the list of children
	 */
	public List<TableNode> getChildren() {
		return children;
	}

	/**
	 * <p>Gets the list of parent nodes (tables this one references).</p>
	 *
	 * @return the list of parents
	 */
	public List<TableNode> getParents() {
		return parents;
	}

	/**
	 * <p>Checks if this node has already been added to the ordered list.</p>
	 *
	 * @return true if ordered, false otherwise
	 */
	public boolean isOrdered() {
		return isOrdered;
	}

	/**
	 * <p>Sets the ordered status of this node.</p>
	 *
	 * @param ordered the ordered status
	 */
	public void setOrdered(boolean ordered) {
		isOrdered = ordered;
	}
}
