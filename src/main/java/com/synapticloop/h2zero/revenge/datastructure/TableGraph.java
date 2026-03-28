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
				// Ignore self-references and non-existent tables
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
	 * <p>The logic ensures that referenced tables are generated before the tables
	 * that reference them (Parent to Child).</p>
	 *
	 * @return the ordered list of tables
	 */
	public List<Table> generateOrder() {
		List<Table> orderedTables = new ArrayList<>();

		// 1. Process tables with no parents first (root nodes in the dependency tree)
		// These are tables that don't reference any other tables.
		for (TableNode node : nodes.values()) {
			if (!node.isOrdered() && node.getParents().isEmpty()) {
				processNode(node, orderedTables, new HashSet<>());
			}
		}

		// 2. Process any remaining tables (handles cycles or disjoint subgraphs)
		for (TableNode node : nodes.values()) {
			if (!node.isOrdered()) {
				processNode(node, orderedTables, new HashSet<>());
			}
		}

		return orderedTables;
	}

	/**
	 * <p>Recursively ensures that all parents of a node are added to the list
	 * before the node itself, and then recursively adds its children.</p>
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
			// We break the cycle by adding the node here and stopping parent traversal
			addNodeToOrderedList(node, orderedTables);
			return;
		}

		visiting.add(node);

		// Recursively ensure all parents (dependencies) are ordered first
		for (TableNode parent : node.getParents()) {
			processNode(parent, orderedTables, visiting);
		}

		// Once parents are handled, add this node
		addNodeToOrderedList(node, orderedTables);

		// Then recursively add all children (tables that reference this one)
		for (TableNode child : node.getChildren()) {
			processNode(child, orderedTables, visiting);
		}

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
}
