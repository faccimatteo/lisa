package it.unive.lisa.analysis.string.stringgraph.exception;

/**
 * Exception thrown when a bad string graph is built.
 */
public class WrongBuildStringGraphException extends RuntimeException {

	/**
	 * {@link WrongBuildStringGraphException}'s constructor.
	 * 
	 * @param message cause of bad string graph.
	 */
	public WrongBuildStringGraphException(String message) {
		super(message);
	}
}
