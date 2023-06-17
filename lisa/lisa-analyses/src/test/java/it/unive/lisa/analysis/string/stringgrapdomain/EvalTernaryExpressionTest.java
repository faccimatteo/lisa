package it.unive.lisa.analysis.string.stringgrapdomain;

import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.string.stringgraph.StringGraph;
import it.unive.lisa.analysis.string.stringgraph.StringGraphDomain;
import it.unive.lisa.symbolic.value.operator.ternary.StringReplace;
import it.unive.lisa.symbolic.value.operator.ternary.StringSubstring;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class EvalTernaryExpressionTest {

    @Test
    public void whenStringSubstringIsApplied_thenCheckSubStringIsApplied() throws SemanticException {
        StringGraph left = new StringGraph("ternaryexpression");
        StringGraph lowerBound = new StringGraph("7");
        StringGraph upperBound = new StringGraph("17");
        StringGraphDomain sgd = new StringGraphDomain(left);
        StringGraphDomain lbSgd = new StringGraphDomain(lowerBound);
        StringGraphDomain ubSgd = new StringGraphDomain(upperBound);
        StringGraphDomain subString = sgd.evalTernaryExpression(StringSubstring.INSTANCE, sgd, lbSgd, ubSgd, null);

        assertEquals("CONCAT[ e x p r e s s i o n]",subString.getStringGraph().toString());
    }

    @Test
    public void whenStringSubstringIsAppliedWithIncorrectBound_thenCheckMAXIsReturned() throws SemanticException {
        StringGraph left = new StringGraph("ternaryexpression");
        StringGraph lowerBound = new StringGraph("-1");
        StringGraph upperBound = new StringGraph("200");
        StringGraphDomain sgd = new StringGraphDomain(left);
        StringGraphDomain lbSgd = new StringGraphDomain(lowerBound);
        StringGraphDomain ubSgd = new StringGraphDomain(upperBound);
        StringGraphDomain subString = sgd.evalTernaryExpression(StringSubstring.INSTANCE, sgd, lbSgd, ubSgd, null);

        assertEquals("MAX",subString.getStringGraph().toString());
    }

    @Test
    public void whenStringSubstringIsAppliedToNonSimpleChild_thenCheckMAXIsReturned() throws SemanticException {
        StringGraph left = new StringGraph("leftternaryexpression");
        StringGraph right = new StringGraph("rightternaryexpression");
        StringGraph middle = new StringGraph(StringGraph.NodeType.OR, List.of(left, right), null);
        StringGraph lowerBound = new StringGraph("-1");
        StringGraph upperBound = new StringGraph("200");
        StringGraphDomain sgd = new StringGraphDomain(middle);
        StringGraphDomain lbSgd = new StringGraphDomain(lowerBound);
        StringGraphDomain ubSgd = new StringGraphDomain(upperBound);
        StringGraphDomain subString = sgd.evalTernaryExpression(StringSubstring.INSTANCE, sgd, lbSgd, ubSgd, null);

        assertEquals("MAX",subString.getStringGraph().toString());
    }

    @Test
    public void whenStringReplaceIsApplied_thenCheckMAXIsReturned() throws SemanticException {
        StringGraph left = new StringGraph("ternaryexpression");
        StringGraph lowerBound = new StringGraph("-1");
        StringGraph upperBound = new StringGraph("200");
        StringGraphDomain sgd = new StringGraphDomain(left);
        StringGraphDomain lbSgd = new StringGraphDomain(lowerBound);
        StringGraphDomain ubSgd = new StringGraphDomain(upperBound);
        StringGraphDomain subString = sgd.evalTernaryExpression(StringReplace.INSTANCE, sgd, lbSgd, ubSgd, null);

        assertEquals("MAX",subString.getStringGraph().toString());
    }
}
