package it.unive.lisa.analysis.string.stringgraph;

import it.unive.lisa.analysis.SemanticDomain;
import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.nonrelational.value.BaseNonRelationalValueDomain;
import it.unive.lisa.analysis.representation.DomainRepresentation;
import it.unive.lisa.analysis.representation.StringRepresentation;
import it.unive.lisa.program.cfg.ProgramPoint;
import it.unive.lisa.symbolic.value.Constant;
import it.unive.lisa.symbolic.value.operator.binary.BinaryOperator;
import it.unive.lisa.symbolic.value.operator.binary.StringConcat;
import it.unive.lisa.symbolic.value.operator.binary.StringContains;
import it.unive.lisa.symbolic.value.operator.ternary.StringSubstring;
import it.unive.lisa.symbolic.value.operator.ternary.TernaryOperator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.unive.lisa.analysis.string.stringgraph.StringGraph.checkPartialOrder;

/**
 * StringGraphDomain: abstract domain used to represent strings
 */
public class StringGraphDomain extends BaseNonRelationalValueDomain<StringGraphDomain> {

    private final StringGraph stringGraph;

    /**
     * Constructor that build a string graph domain from a {@code StringGraph}
     * @param stringGraph string graph needed to build {@code StringGraphDomain}
     */
    public StringGraphDomain(StringGraph stringGraph) {
        this.stringGraph = stringGraph;
    }

    @Override
    public StringGraphDomain evalNonNullConstant(Constant constant, ProgramPoint pp) throws SemanticException {
        if(constant.getValue() instanceof String) {
            String constantWithoutApex = ((String)constant.getValue()).replace("\"","");
            return new StringGraphDomain(new StringGraph(constantWithoutApex));
        }
        return super.evalNonNullConstant(constant, pp);
    }

    @Override
    public StringGraphDomain evalTernaryExpression(TernaryOperator operator, StringGraphDomain left, StringGraphDomain middle, StringGraphDomain right, ProgramPoint pp) throws SemanticException {
        if (operator instanceof StringSubstring){
            return new StringGraphDomain(left.stringGraph.substring(middle.stringGraph.getBound(), right.stringGraph.getBound()));
        }
        return new StringGraphDomain(StringGraph.buildMAX());
    }

    @Override
    public SemanticDomain.Satisfiability satisfiesBinaryExpression(BinaryOperator operator, StringGraphDomain left, StringGraphDomain right, ProgramPoint pp) throws SemanticException {
        if (operator instanceof StringContains) {
            return left.stringGraph.contains(right.stringGraph.getCharacter());
        }
        return SemanticDomain.Satisfiability.UNKNOWN;
    }



    @Override
    public StringGraphDomain evalBinaryExpression(BinaryOperator operator, StringGraphDomain left,
                                                     StringGraphDomain right,
                                                     ProgramPoint pp) {

        if (operator instanceof StringConcat) {
            StringGraph stringGraph = StringGraph.buildCONCAT(left.stringGraph, right.stringGraph);
            stringGraph.compact();
            stringGraph.normalize();
            return new StringGraphDomain(stringGraph);
        }
        return new StringGraphDomain(StringGraph.buildMAX());
    }

    @Override
    public StringGraphDomain lubAux(StringGraphDomain other) {
        StringGraph lubGraph = new StringGraph(StringGraph.NodeType.OR, new ArrayList<>(List.of(this.stringGraph, other.stringGraph)), null);
        lubGraph.compact();
        lubGraph.normalize();
        return new StringGraphDomain(lubGraph);
    }

    @Override
    public StringGraphDomain wideningAux(StringGraphDomain other) {

        // other --> gn
        // this --> go
        if (checkPartialOrder(this.stringGraph, other.stringGraph, new ArrayList<>())) {
            return this;
        } else {
            return this.widen(this.lubAux(other));
        }
    }

    /**
     * Auxiliary widening algorithm applying the {@code cycleInductionRule} and the {@code replacementRule}.
     * @param other {@code StringGraphDomain} to compare during the widening application process.
     * @return {@code StringGraphDomain} obtained when no rules can be applied anymore.
     */
    public StringGraphDomain widen(StringGraphDomain other) {
        // other --> gn
        // this --> go
        StringGraph gResCI = this.stringGraph.cycleInductionRule(other.stringGraph);
        StringGraph gResCR = this.stringGraph.replacementRule(other.stringGraph);

        if (Objects.nonNull(gResCI)) return this.widen(this.lubAux(new StringGraphDomain(gResCI)));
        else if (Objects.nonNull(gResCR)) return this.widen(this.lubAux(new StringGraphDomain(gResCR)));
        else
            return other;
    }

    @Override
    public boolean lessOrEqualAux(StringGraphDomain other) {
        List<Pair<StringGraph, StringGraph>> edges = new ArrayList<>();
        return checkPartialOrder(this.stringGraph, other.stringGraph, edges);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof StringGraphDomain) return this.stringGraph.equals(((StringGraphDomain) obj).stringGraph);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringGraph);
    }

    @Override
    public DomainRepresentation representation() {
        return new StringRepresentation(stringGraph);
    }

    @Override
    public StringGraphDomain top() {
        return new StringGraphDomain(StringGraph.buildMAX());
    }

    @Override
    public StringGraphDomain bottom() {
        return new StringGraphDomain(StringGraph.buildEMPTY());
    }

    @Override
    public boolean isTop() {
        if (Objects.nonNull(this.stringGraph))
            return this.stringGraph.getLabel() == StringGraph.NodeType.MAX;
        return true;
    }

    @Override
    public boolean isBottom() {
        if (Objects.nonNull(this.stringGraph))
            return this.stringGraph.getLabel() == StringGraph.NodeType.EMPTY;
        return true;
    }
}
