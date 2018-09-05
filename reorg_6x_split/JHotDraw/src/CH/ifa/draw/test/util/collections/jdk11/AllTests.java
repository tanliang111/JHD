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
package CH.ifa.draw.test.util.collections.jdk11;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author <a href="mailto:mtnygard@charter.net">Michael T. Nygard</a>
 * @version $Revision: 227 $
 */
public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(AllTests.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for CH.ifa.draw.test.util.collections.jdk11");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(CollectionsFactoryJDK11Test.class));
		suite.addTest(new TestSuite(IteratorWrapperTest.class));
		suite.addTest(new TestSuite(ListWrapperTest.class));
		suite.addTest(new TestSuite(MapWrapperTest.class));
		suite.addTest(new TestSuite(SetWrapperTest.class));
		//$JUnit-END$
		return suite;
	}
}
