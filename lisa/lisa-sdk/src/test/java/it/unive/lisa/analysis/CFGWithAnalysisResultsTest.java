package it.unive.lisa.analysis;

import it.unive.lisa.*;
import it.unive.lisa.analysis.lattices.ExpressionSet;
import it.unive.lisa.analysis.symbols.SymbolAliasing;
import it.unive.lisa.program.ClassUnit;
import it.unive.lisa.program.Program;
import it.unive.lisa.program.SourceCodeLocation;
import it.unive.lisa.program.cfg.CFG;
import it.unive.lisa.program.cfg.CodeMemberDescriptor;
import it.unive.lisa.program.cfg.statement.Statement;
import it.unive.lisa.program.cfg.statement.VariableRef;
import it.unive.lisa.program.cfg.statement.call.Call.CallType;
import it.unive.lisa.program.cfg.statement.call.OpenCall;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CFGWithAnalysisResultsTest {

	private static final ClassUnit unit = new ClassUnit(new SourceCodeLocation("unknown", 0, 0),
			new Program(new TestLanguageFeatures(), new TestTypeSystem()), "Testing", false);

	@Test
	public void testIssue189() throws SemanticException {
		SourceCodeLocation unknown = new SourceCodeLocation("unknown", 0, 0);
		CFG cfg = new CFG(new CodeMemberDescriptor(unknown, unit, false, "emptyIf"));
		VariableRef x = new VariableRef(cfg, unknown, "x");
		OpenCall y = new OpenCall(cfg, unknown, CallType.STATIC, "bar", "foo", x);
		cfg.addNode(y, true);

		AnalysisState<TestAbstractState, TestHeapDomain,
				TestValueDomain,
				TestTypeDomain> state = new AnalysisState<>(
						new TestAbstractState(),
						new ExpressionSet<>(), new SymbolAliasing());

		Map<Statement, AnalysisState<TestAbstractState,
				TestHeapDomain, TestValueDomain, TestTypeDomain>> entries = Map.of(y, state);
		Map<Statement, AnalysisState<TestAbstractState,
				TestHeapDomain, TestValueDomain, TestTypeDomain>> results = Map.of(y, state, x, state);

		CFGWithAnalysisResults<TestAbstractState, TestHeapDomain,
				TestValueDomain, TestTypeDomain> res = new CFGWithAnalysisResults<>(cfg, state, entries, results);

		assertEquals(state, res.getAnalysisStateAfter(y));
		assertEquals(state, res.getAnalysisStateBefore(y));
		assertEquals(state, res.getAnalysisStateAfter(x));
		assertEquals(state, res.getAnalysisStateBefore(x));
	}
}
