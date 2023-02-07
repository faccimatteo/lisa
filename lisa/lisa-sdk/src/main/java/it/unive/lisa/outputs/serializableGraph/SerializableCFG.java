package it.unive.lisa.outputs.serializableGraph;

import it.unive.lisa.analysis.CFGWithAnalysisResults;
import it.unive.lisa.program.cfg.CFG;
import it.unive.lisa.program.cfg.edge.Edge;
import it.unive.lisa.program.cfg.statement.NaryExpression;
import it.unive.lisa.program.cfg.statement.Statement;
import it.unive.lisa.program.cfg.statement.UnaryStatement;
import it.unive.lisa.util.datastructures.graph.GraphVisitor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class to build {@link SerializableGraph}s from {@link CFG}s.
 * 
 * @author <a href="mailto:luca.negrini@unive.it">Luca Negrini</a>
 */
public class SerializableCFG {

	private SerializableCFG() {
	}

	/**
	 * Builds a {@link SerializableGraph} starting from the given {@link CFG},
	 * with no extra descriptions for the statements.
	 * 
	 * @param source the source cfg
	 *
	 * @return the serializable version of that cfg
	 */
	public static SerializableGraph fromCFG(CFG source) {
		return fromCFG(source, null);
	}

	/**
	 * Builds a {@link SerializableGraph} starting from the given {@link CFG},
	 * using the given function to generate extra descriptions for each
	 * statement.
	 * 
	 * @param source               the source cfg
	 * @param descriptionGenerator the function that can generate descriptions
	 *                                 from statements
	 *
	 * @return the serializable version of that cfg
	 */
	public static SerializableGraph fromCFG(CFG source, Function<Statement, SerializableValue> descriptionGenerator) {
		String name = source.getDescriptor().getFullSignatureWithParNames();
		String desc;
		if (source instanceof CFGWithAnalysisResults<?, ?, ?, ?>)
			desc = ((CFGWithAnalysisResults<?, ?, ?, ?>) source).getId();
		else
			desc = null;

		SortedSet<SerializableNode> nodes = new TreeSet<>();
		SortedSet<SerializableNodeDescription> descrs = new TreeSet<>();
		SortedSet<SerializableEdge> edges = new TreeSet<>();

		for (Statement node : source.getNodes()) {
			Map<Statement, List<Statement>> inners = new IdentityHashMap<>();
			node.accept(new InnerNodeExtractor(), inners);
			for (Statement inner : inners.keySet())
				addNode(nodes, descrs, inner, inners.getOrDefault(inner, Collections.emptyList()),
						descriptionGenerator);
			addNode(nodes, descrs, node, inners.getOrDefault(node, Collections.emptyList()), descriptionGenerator);
		}

		for (Statement src : source.getNodes())
			for (Statement dest : source.followersOf(src))
				for (Edge edge : source.getEdgesConnecting(src, dest))
					edges.add(new SerializableEdge(src.getOffset(), dest.getOffset(), edge.getClass().getSimpleName()));

		return new SerializableGraph(name, desc, nodes, edges, descrs);
	}

	private static void addNode(
			SortedSet<SerializableNode> nodes,
			SortedSet<SerializableNodeDescription> descrs,
			Statement node,
			List<Statement> inners,
			Function<Statement, SerializableValue> descriptionGenerator) {
		List<Integer> innerIds = inners.stream().map(st -> st.getOffset()).collect(Collectors.toList());
		SerializableNode n = new SerializableNode(node.getOffset(), innerIds, node.toString());
		nodes.add(n);
		if (descriptionGenerator != null) {
			SerializableValue value = descriptionGenerator.apply(node);
			if (value != null)
				descrs.add(new SerializableNodeDescription(node.getOffset(), value));
		}
	}

	private static class InnerNodeExtractor
			implements GraphVisitor<CFG, Statement, Edge, Map<Statement, List<Statement>>> {

		@Override
		public boolean visit(Map<Statement, List<Statement>> tool, CFG graph) {
			return false;
		}

		@Override
		public boolean visit(Map<Statement, List<Statement>> tool, CFG graph, Statement node) {
			List<Statement> inners = tool.computeIfAbsent(node, st -> new LinkedList<>());
			if (node instanceof UnaryStatement)
				// TODO should we have an nary statement? or an interface that
				// marks statements with sub-statements?
				inners.add(((UnaryStatement) node).getExpression());
			else if (node instanceof NaryExpression)
				inners.addAll(Arrays.asList(((NaryExpression) node).getSubExpressions()));
			return true;
		}

		@Override
		public boolean visit(Map<Statement, List<Statement>> tool, CFG graph, Edge edge) {
			return false;
		}
	}
}
