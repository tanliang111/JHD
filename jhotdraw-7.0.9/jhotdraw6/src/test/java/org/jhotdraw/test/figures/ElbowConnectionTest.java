/*
 * @(#)Test.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */
package org.jhotdraw.test.figures;

import org.jhotdraw.figures.ElbowConnection;
import junit.framework.TestCase;
// JUnitDoclet begin import
// JUnitDoclet end import

/*
 * Generated by JUnitDoclet, a tool provided by
 * ObjectFab GmbH under LGPL.
 * Please see www.junitdoclet.org, www.gnu.org
 * and www.objectfab.de for informations about
 * the tool, the licence and the authors.
 */

// JUnitDoclet begin javadoc_class
/**
 * TestCase ElbowConnectionTest is generated by
 * JUnitDoclet to hold the tests for ElbowConnection.
 * @see org.jhotdraw.figures.ElbowConnection
 */
// JUnitDoclet end javadoc_class
public class ElbowConnectionTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
	// JUnitDoclet begin class
	// instance variables, helper methods, ... put them in this marker
	private ElbowConnection elbowconnection;
	// JUnitDoclet end class

	/**
	 * Constructor ElbowConnectionTest is
	 * basically calling the inherited constructor to
	 * initiate the TestCase for use by the Framework.
	 */
	public ElbowConnectionTest(String name) {
		// JUnitDoclet begin method ElbowConnectionTest
		super(name);
		// JUnitDoclet end method ElbowConnectionTest
	}

	/**
	 * Factory method for instances of the class to be tested.
	 */
	public ElbowConnection createInstance() throws Exception {
		// JUnitDoclet begin method testcase.createInstance
		return new ElbowConnection();
		// JUnitDoclet end method testcase.createInstance
	}

	/**
	 * Method setUp is overwriting the framework method to
	 * prepare an instance of this TestCase for a single test.
	 * It's called from the JUnit framework only.
	 */
	protected void setUp() throws Exception {
		// JUnitDoclet begin method testcase.setUp
		super.setUp();
		elbowconnection = createInstance();
		// JUnitDoclet end method testcase.setUp
	}

	/**
	 * Method tearDown is overwriting the framework method to
	 * clean up after each single test of this TestCase.
	 * It's called from the JUnit framework only.
	 */
	protected void tearDown() throws Exception {
		// JUnitDoclet begin method testcase.tearDown
		elbowconnection = null;
		super.tearDown();
		// JUnitDoclet end method testcase.tearDown
	}

	// JUnitDoclet begin javadoc_method updateConnection()
	/**
	 * Method testUpdateConnection is testing updateConnection
	 * @see org.jhotdraw.figures.ElbowConnection#updateConnection()
	 */
	// JUnitDoclet end javadoc_method updateConnection()
	public void testUpdateConnection() throws Exception {
		// JUnitDoclet begin method updateConnection
		// JUnitDoclet end method updateConnection
	}

	// JUnitDoclet begin javadoc_method layoutConnection()
	/**
	 * Method testLayoutConnection is testing layoutConnection
	 * @see org.jhotdraw.figures.ElbowConnection#layoutConnection()
	 */
	// JUnitDoclet end javadoc_method layoutConnection()
	public void testLayoutConnection() throws Exception {
		// JUnitDoclet begin method layoutConnection
		// JUnitDoclet end method layoutConnection
	}

	// JUnitDoclet begin javadoc_method handles()
	/**
	 * Method testHandles is testing handles
	 * @see org.jhotdraw.figures.ElbowConnection#handles()
	 */
	// JUnitDoclet end javadoc_method handles()
	public void testHandles() throws Exception {
		// JUnitDoclet begin method handles
		// JUnitDoclet end method handles
	}

	// JUnitDoclet begin javadoc_method connectedTextLocator()
	/**
	 * Method testConnectedTextLocator is testing connectedTextLocator
	 * @see org.jhotdraw.figures.ElbowConnection#connectedTextLocator(org.jhotdraw.framework.Figure)
	 */
	// JUnitDoclet end javadoc_method connectedTextLocator()
	public void testConnectedTextLocator() throws Exception {
		// JUnitDoclet begin method connectedTextLocator
		// JUnitDoclet end method connectedTextLocator
	}

	// JUnitDoclet begin javadoc_method testVault
	/**
	 * JUnitDoclet moves marker to this method, if there is not match
	 * for them in the regenerated code and if the marker is not empty.
	 * This way, no test gets lost when regenerating after renaming.
	 * <b>Method testVault is supposed to be empty.</b>
	 */
	// JUnitDoclet end javadoc_method testVault
	public void testVault() throws Exception {
		// JUnitDoclet begin method testcase.testVault
		// JUnitDoclet end method testcase.testVault
	}

}
