package it.unive.lisa.analysis.string.stringgrapdomain;

import it.unive.lisa.analysis.SemanticDomain;
import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import it.unive.lisa.symbolic.value.operator.binary.StringContains;
import org.junit.Test;

import java.util.List;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.NodeType.*;
import static org.junit.Assert.assertEquals;

public class SatisfiesBinaryExpressionTest {

    @Test
    public void whenStringIsContained_thenChecksSatisfied() throws SemanticException {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("o");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        SemanticDomain.Satisfiability satisfiability = sgd1.satisfiesBinaryExpression(StringContains.INSTANCE, sgd1, sgd2, null);

        assertEquals(SemanticDomain.Satisfiability.SATISFIED, satisfiability);

    }

    @Test
    public void whenStringIsNotContained_thenChecksNotSatisfied() throws SemanticException {
        StringGraph s1 = new StringGraph("hello");
        StringGraph s2 = new StringGraph("a");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        SemanticDomain.Satisfiability satisfiability = sgd1.satisfiesBinaryExpression(StringContains.INSTANCE, sgd1, sgd2, null);

        assertEquals(SemanticDomain.Satisfiability.NOT_SATISFIED, satisfiability);

    }

    @Test
    public void whenEMPTYString_thenChecksNotSatisfied() throws SemanticException {
        StringGraph s1 = new StringGraph(EMPTY);
        StringGraph s2 = new StringGraph("o");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        SemanticDomain.Satisfiability satisfiability = sgd1.satisfiesBinaryExpression(StringContains.INSTANCE, sgd1, sgd2, null);

        assertEquals(SemanticDomain.Satisfiability.NOT_SATISFIED, satisfiability);

    }

    @Test
    public void whenMAXString_thenChecksSatisfied() throws SemanticException {
        StringGraph s1 = new StringGraph(MAX);
        StringGraph s2 = new StringGraph("o");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        SemanticDomain.Satisfiability satisfiability = sgd1.satisfiesBinaryExpression(StringContains.INSTANCE, sgd1, sgd2, null);

        assertEquals(SemanticDomain.Satisfiability.SATISFIED, satisfiability);

    }

    @Test
    public void whenORStringGraph_thenChecksNotSatisfied() throws SemanticException {
        StringGraph s1 = new StringGraph(OR, List.of(new StringGraph("hello")), null);
        StringGraph s2 = new StringGraph("o");

        StringGraphDomain sgd1 = new StringGraphDomain(s1);
        StringGraphDomain sgd2 = new StringGraphDomain(s2);

        SemanticDomain.Satisfiability satisfiability = sgd1.satisfiesBinaryExpression(StringContains.INSTANCE, sgd1, sgd2, null);

        assertEquals(SemanticDomain.Satisfiability.UNKNOWN, satisfiability);

    }
}
