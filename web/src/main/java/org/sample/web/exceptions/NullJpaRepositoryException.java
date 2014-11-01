/**
 * 
 */
package org.sample.web.exceptions;

/**
 * @author mpostelnicu
 *
 */
public class NullJpaRepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NullJpaRepositoryException() {
		super("jpaRepository is null! Please set the jpaRepository in your constructor");
	}

}
