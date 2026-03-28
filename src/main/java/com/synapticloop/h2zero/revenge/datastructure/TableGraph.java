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

import java.util.*;

/**
 * <p>Manages a directed graph of database tables and their foreign key relationships.</p>
 *
 * <p>The graph is used to determine the optimal generation order for tables, ensuring
 * that referenced tables are defined before the tables that reference them.</p>
 */
public class TableGraph {
	private final Map<String, TableNode> nodes = new LinkedHashMap<>();
	private boolean foundCircularDependency = false;

	/**
	 * <p>Adds a list of tables to the graph and establishes their relationships.</p>
	 *
	 * @param tables the list of tables to add
	 */
	public void addTables(List<Table> tables) {
		// First pass: create all nodes
		for (Table table : tables) {
			nodes.put(table.getName().toLowerCase(), new TableNode(table));
		}

		// Second pass: establish relationships
		for (TableNode node : nodes.values()) {
			Set<String> referencedTableNames = node.getTable().getReferencedTableNames();
			for (String referencedName : referencedTableNames) {
				TableNode parent = nodes.get(referencedName.toLowerCase());
				// A parent is a table that this node references.
				// This node is a child of the parent.
				if (parent != null && parent != node) {
					node.addParent(parent);
					parent.addChild(node);
				}
			}
		}
	}

	/**
	 * <p>Generates the ordered list of tables based on the graph's relationships.</p>
	 *
	 * <p>The logic ensures that referenced tables (dependencies) are ALWAYS defined
	 * before the tables that reference them.</p>
	 *
	 * @return the ordered list of tables
	 */
	public List<Table> generateOrder() {
		List<Table> orderedTables = new ArrayList<>();
		
		// Reset ordered status for all nodes to be safe
		for (TableNode node : nodes.values()) {
			node.setOrdered(false);
		}

		// Iterate through all nodes. The processNode method recursively ensures
		// that all dependencies are handled before adding the node itself.
		for (TableNode node : nodes.values()) {
			if (!node.isOrdered()) {
				processNode(node, orderedTables, new LinkedHashSet<>());
			}
		}

		return orderedTables;
	}

	/**
	 * <p>Recursively ensures that all parents (referenced tables) of a node are added
	 * to the list before the node itself.</p>
	 *
	 * @param node the node to process
	 * @param orderedTables the list to add tables to
	 * @param visiting a set of nodes currently being visited in the current recursion stack (for cycle detection)
	 */
	private void processNode(TableNode node, List<Table> orderedTables, Set<TableNode> visiting) {
		if (node.isOrdered()) {
			return;
		}

		// Cycle detection: if we are already visiting this node, we have a circular dependency
		if (visiting.contains(node)) {
			// Circular dependency found. We break it by just returning, allowing the 
			// caller to move on to the next dependency.
			System.err.printf("[   WARN ] Circular dependency detected involving table: %s. Skipping this path to break the cycle.%n", node.getName());
			foundCircularDependency = true;
			return;
		}

		visiting.add(node);

		// EVERY parent (table referenced by this one) MUST be defined first.
		List<TableNode> parents = new ArrayList<>(node.getParents());
		for (TableNode parent : parents) {
			if (!parent.isOrdered()) {
				processNode(parent, orderedTables, visiting);
			}
		}

		// NOW, and ONLY NOW, can we add this node to the list.
		addNodeToOrderedList(node, orderedTables);

		visiting.remove(node);
	}

	/**
	 * <p>Adds a node's table to the ordered list and marks the node as ordered.</p>
	 *
	 * @param node the node to add
	 * @param orderedTables the list to add the table to
	 */
	private void addNodeToOrderedList(TableNode node, List<Table> orderedTables) {
		if (!node.isOrdered()) {
			node.setOrdered(true);
			orderedTables.add(node.getTable());
			System.out.printf("[   INFO ] Ordered table: %s%n", node.getName());
		}
	}

	/**
	 * <p>Returns whether any circular dependencies were found during the ordering process.</p>
	 *
	 * @return true if a circular dependency was found, false otherwise
	 */
	public boolean hasFoundCircularDependency() {
		return foundCircularDependency;
	}
}
