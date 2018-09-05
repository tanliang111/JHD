package CH.ifa.draw.test.util;

import junit.framework.TestCase;
// JUnitDoclet begin import
import CH.ifa.draw.util.PaletteLayout;

import java.awt.*;
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
* TestCase PaletteLayoutTest is generated by
* JUnitDoclet to hold the tests for PaletteLayout.
* @see CH.ifa.draw.util.PaletteLayout
*/
// JUnitDoclet end javadoc_class
public class PaletteLayoutTest
// JUnitDoclet begin extends_implements
extends TestCase
// JUnitDoclet end extends_implements
{
  // JUnitDoclet begin class
  // instance variables, helper methods, ... put them in this marker
  CH.ifa.draw.util.PaletteLayout palettelayout = null;
  // JUnitDoclet end class
  
  /**
  * Constructor PaletteLayoutTest is
  * basically calling the inherited constructor to
  * initiate the TestCase for use by the Framework.
  */
  public PaletteLayoutTest(String name) {
    // JUnitDoclet begin method PaletteLayoutTest
    super(name);
    // JUnitDoclet end method PaletteLayoutTest
  }
  
  /**
  * Factory method for instances of the class to be tested.
  */
  public CH.ifa.draw.util.PaletteLayout createInstance() throws Exception {
    // JUnitDoclet begin method testcase.createInstance
    return new CH.ifa.draw.util.PaletteLayout(10, new Point(5, 5));
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
    palettelayout = createInstance();
    // JUnitDoclet end method testcase.setUp
  }
  
  /**
  * Method tearDown is overwriting the framework method to
  * clean up after each single test of this TestCase.
  * It's called from the JUnit framework only.
  */
  protected void tearDown() throws Exception {
    // JUnitDoclet begin method testcase.tearDown
    palettelayout = null;
    super.tearDown();
    // JUnitDoclet end method testcase.tearDown
  }
  
  // JUnitDoclet begin javadoc_method addLayoutComponent()
  /**
  * Method testAddLayoutComponent is testing addLayoutComponent
  * @see CH.ifa.draw.util.PaletteLayout#addLayoutComponent(java.lang.String, java.awt.Component)
  */
  // JUnitDoclet end javadoc_method addLayoutComponent()
  public void testAddLayoutComponent() throws Exception {
    // JUnitDoclet begin method addLayoutComponent
    // JUnitDoclet end method addLayoutComponent
  }
  
  // JUnitDoclet begin javadoc_method removeLayoutComponent()
  /**
  * Method testRemoveLayoutComponent is testing removeLayoutComponent
  * @see CH.ifa.draw.util.PaletteLayout#removeLayoutComponent(java.awt.Component)
  */
  // JUnitDoclet end javadoc_method removeLayoutComponent()
  public void testRemoveLayoutComponent() throws Exception {
    // JUnitDoclet begin method removeLayoutComponent
    // JUnitDoclet end method removeLayoutComponent
  }
  
  // JUnitDoclet begin javadoc_method preferredLayoutSize()
  /**
  * Method testPreferredLayoutSize is testing preferredLayoutSize
  * @see CH.ifa.draw.util.PaletteLayout#preferredLayoutSize(java.awt.Container)
  */
  // JUnitDoclet end javadoc_method preferredLayoutSize()
  public void testPreferredLayoutSize() throws Exception {
    // JUnitDoclet begin method preferredLayoutSize
    // JUnitDoclet end method preferredLayoutSize
  }
  
  // JUnitDoclet begin javadoc_method minimumLayoutSize()
  /**
  * Method testMinimumLayoutSize is testing minimumLayoutSize
  * @see CH.ifa.draw.util.PaletteLayout#minimumLayoutSize(java.awt.Container)
  */
  // JUnitDoclet end javadoc_method minimumLayoutSize()
  public void testMinimumLayoutSize() throws Exception {
    // JUnitDoclet begin method minimumLayoutSize
    // JUnitDoclet end method minimumLayoutSize
  }
  
  // JUnitDoclet begin javadoc_method layoutContainer()
  /**
  * Method testLayoutContainer is testing layoutContainer
  * @see CH.ifa.draw.util.PaletteLayout#layoutContainer(java.awt.Container)
  */
  // JUnitDoclet end javadoc_method layoutContainer()
  public void testLayoutContainer() throws Exception {
    // JUnitDoclet begin method layoutContainer
    // JUnitDoclet end method layoutContainer
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
  
  /**
  * Method to execute the TestCase from command line
  * using JUnit's textui.TestRunner .
  */
  public static void main(String[] args) {
    // JUnitDoclet begin method testcase.main
    junit.textui.TestRunner.run(PaletteLayoutTest.class);
    // JUnitDoclet end method testcase.main
  }
}
