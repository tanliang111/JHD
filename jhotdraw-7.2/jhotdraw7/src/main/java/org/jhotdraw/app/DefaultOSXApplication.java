/*
 * @(#)DefaultOSXApplication.java  1.2  2007-12-25
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.app;

import ch.randelshofer.quaqua.*;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.util.*;
import org.jhotdraw.util.prefs.*;
import java.util.*;
import java.util.prefs.*;
import java.awt.event.*;
import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import org.jhotdraw.app.action.*;

/**
 * A DefaultOSXApplication can handle the life cycle of multiple document 
 * windows each being presented in a JFrame of its own. The application
 * provides all the functionality needed to work with the document, such as a
 * screen menu bar, tool bars and palette windows.
 * <p>
 * The life cycle of the application is tied to the application, which is
 * represented by the first menu in the screen menu bar.
 * <p>
 * DefaultOSXApplication is designed for Mac OS X. It will not work on other
 * platforms.
 * <p>
 * The screen menu bar has the following standard menus:
 * <pre>
 * "Application-Name" File Edit Window Help
 * </pre>
 * The first menu, is the <b>application menu</b>. It has the following standard
 * menu items. DefaultOSXApplication wires the menu items to the action objects
 * specified in brackets. The preferences menu item is only displayed,
 * if the application has an action with PreferencesAction.ID. The other menu
 * items are always displayed. Menu items without action wiring are generated by
 * Mac OS X and can not be changed.
 * <pre>
 *  About "Application-Name" (AboutAction.ID)
 *  -
 *  Preferences... (PreferencesAction.ID)
 *  -
 *  Services
 *  -
 *  Hide "Application-Name"
 *  Hide Others
 *  Show All
 *  -
 *  Quit "Application-Name" (ExitAction.ID)
 * </pre>
 *
 * The <b>file menu</b> has the following standard menu items.
 * DefaultOSXApplication wires the menu items to the action objects
 * specified in brackets. If the application hasn't an action with the
 * specified ID, the menu item is not displayed. Menu items without action
 * wiring are generated by this class, and can be changed by subclasses.
 * <pre>
 *  New (NewAction.ID)
 *  Open... (OpenAction.ID)
 *  Open Recent >
 *  -
 *  Close (CloseAction.ID)
 *  Save (SaveAction.ID)
 *  Save As... (SaveAsAction.ID)
 *  Save All
 *  Revert to Saved (RevertToSavedAction.ID)
 *  -
 *  Page Setup... (PrintPageSetupAction.ID)
 *  Print... (PrintAction.ID)
 * </pre>
 *
 * The <b>edit menu</b> has the following standard menu items.
 * DefaultOSXApplication wires the menu items to the action objects
 * specified in brackets. If the application hasn't an action with the
 * specified ID, the menu item is not displayed. Menu items without action
 * wiring are generated by this class, and can be changed by subclasses.
 * <pre>
 *  Undo (UndoAction.ID)
 *  Redo (RedoAction.ID)
 *  -
 *  Cut (CutAction.ID)
 *  Copy (CopyAction.ID)
 *  Paste (PasteAction.ID)
 *  Delete (DeleteAction.ID)
 *  Select All (SelectAllAction.ID)
 * </pre>
 *
 * @author Werner Randelshofer
 * @version 1.2 2007-12-25 Added method updateViewTitle. 
 * <br>1.1 2007-01-11 Removed method addStandardActionsTo.
 * <br>1.0.1 2007-01-02 Floating palettes disappear now if the application
 * looses the focus.
 * 1.0 October 4, 2005 Created.
 */
public class DefaultOSXApplication extends AbstractApplication {

    private OSXPaletteHandler paletteHandler;
    private Preferences prefs;
    private LinkedList<Action> paletteActions;

    /** Creates a new instance. */
    public DefaultOSXApplication() {
    }

    @Override
    public void init() {
        ResourceBundleUtil.putPropertyNameModifier("os", "mac", "default");
        super.init();

        prefs = Preferences.userNodeForPackage((getModel() == null) ? getClass() : getModel().getClass());
        initLookAndFeel();
        paletteHandler = new OSXPaletteHandler(this);

        initLabels();
        initApplicationActions();
        getModel().initApplication(this);
        paletteActions = new LinkedList<Action>();
        initPalettes(paletteActions);
        initScreenMenuBar();
    }

    @Override
    public void launch(String[] args) {
        System.setProperty("apple.awt.graphics.UseQuartz", "false");
        super.launch(args);
    }

    @Override
    public void configure(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.macos.useScreenMenuBar", "true");
    }

    protected void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initApplicationActions() {
        ApplicationModel mo = getModel();
        mo.putAction(AboutAction.ID, new AboutAction(this));
        mo.putAction(ExitAction.ID, new ExitAction(this));
        mo.putAction(OSXDropOnDockAction.ID, new OSXDropOnDockAction(this));

        mo.putAction(NewAction.ID, new NewAction(this));
        mo.putAction(OpenAction.ID, new OpenAction(this));
        mo.putAction(ClearRecentFilesAction.ID, new ClearRecentFilesAction(this));
        mo.putAction(SaveAction.ID, new SaveAction(this));
        mo.putAction(SaveAsAction.ID, new SaveAsAction(this));
        mo.putAction(PrintAction.ID, new PrintAction(this));
        mo.putAction(CloseAction.ID, new CloseAction(this));

        mo.putAction(UndoAction.ID, new UndoAction(this));
        mo.putAction(RedoAction.ID, new RedoAction(this));
        mo.putAction(CutAction.ID, new CutAction());
        mo.putAction(CopyAction.ID, new CopyAction());
        mo.putAction(PasteAction.ID, new PasteAction());
        mo.putAction(DeleteAction.ID, new DeleteAction());
        mo.putAction(DuplicateAction.ID, new DuplicateAction());
        mo.putAction(SelectAllAction.ID, new SelectAllAction());

        mo.putAction(MaximizeAction.ID, new MaximizeAction(this));
        mo.putAction(MinimizeAction.ID, new MinimizeAction(this));
    }

    protected void initViewActions(View p) {
        p.putAction(FocusAction.ID, new FocusAction(p));
    }

    @Override
    public void addPalette(Window palette) {
        paletteHandler.addPalette(palette);
    }

    @Override
    public void removePalette(Window palette) {
        paletteHandler.removePalette(palette);
    }

    @Override
    public void addWindow(Window window, final View p) {
        if (window instanceof JFrame) {
            ((JFrame) window).setJMenuBar(createMenuBar(p));
        } else if (window instanceof JDialog) {
            // ((JDialog) window).setJMenuBar(createMenuBar(null));
        }

        paletteHandler.add(window, p);
    }

    @Override
    public void removeWindow(Window window) {
        paletteHandler.remove(window);
    }

    public void show(final View p) {
        if (!p.isShowing()) {
            p.setShowing(true);
            final JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            f.setPreferredSize(new Dimension(400, 400));
            updateViewTitle(p, f);

            PreferencesUtil.installFramePrefsHandler(prefs, "view", f);
            Point loc = f.getLocation();
            boolean moved;
            do {
                moved = false;
                for (Iterator i = views().iterator(); i.hasNext();) {
                    View aView = (View) i.next();
                    if (aView != p && aView.isShowing() &&
                            SwingUtilities.getWindowAncestor(aView.getComponent()).
                            getLocation().equals(loc)) {
                        loc.x += 22;
                        loc.y += 22;
                        moved = true;
                        break;
                    }
                }
            } while (moved);
            f.setLocation(loc);


            f.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(final WindowEvent evt) {
                    setActiveView(p);
                    getModel().getAction(CloseAction.ID).actionPerformed(
                            new ActionEvent(f, ActionEvent.ACTION_PERFORMED,
                            "windowClosing"));
                }

                @Override
                public void windowClosed(final WindowEvent evt) {
                    if (p == getActiveView()) {
                        setActiveView(null);
                    }
                    p.stop();
                }

                @Override
                public void windowActivated(WindowEvent evt) {
                    setActiveView(p);
                }
            });

            p.addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    String name = evt.getPropertyName();
                    if (name.equals(View.HAS_UNSAVED_CHANGES_PROPERTY)) {
                        f.getRootPane().putClientProperty("windowModified", new Boolean(p.hasUnsavedChanges()));
                    } else if (name.equals(View.FILE_PROPERTY)) {
                        updateViewTitle(p, f);
                    }
                }
            });

            //f.setJMenuBar(createMenuBar(p));
            //paletteHandler.add(f, p);
            addWindow(f, p);

            f.getContentPane().add(p.getComponent());
            f.setVisible(true);
            p.start();
        }
    }

    /**
     * Updates the title of a view and displays it in the given frame.
     * 
     * @param p The view.
     * @param f The frame.
     */
    protected void updateViewTitle(View p, JFrame f) {
        String title;
        File file = p.getFile();
        if (file == null) {
            title = labels.getString("unnamedFile");
        } else {
            title = file.getName();
        }
        p.setTitle(labels.getFormatted("frame.title", title, getName(), p.getMultipleOpenId()));
        f.setTitle(p.getTitle());

        // Adds a proxy icon for the file to the title bar
        // See http://developer.apple.com/technotes/tn2007/tn2196.html#WINDOW_DOCUMENTFILE
        f.getRootPane().putClientProperty("Window.documentFile", file);
    }

    public void hide(View p) {
        if (p.isShowing()) {
            JFrame f = (JFrame) SwingUtilities.getWindowAncestor(p.getComponent());
            f.setVisible(false);
            f.remove(p.getComponent());
            //paletteHandler.remove(f, p);
            removeWindow(f);
            f.dispose();
        }
    }

    /**
     * Creates a menu bar.
     *
     * @param p The view for which the menu bar is created. This may be
     * <code>null</code> if the menu bar is attached to an application
     * component, such as the screen menu bar or a floating palette window.
     */
    protected JMenuBar createMenuBar(View p) {
        JMenuBar mb = new JMenuBar();
        mb.add(createFileMenu(p));
        for (JMenu mm : getModel().createMenus(this, p)) {
            mb.add(mm);
        }

        // Determine the index of the help menu, if one has been provided
        // Merge the help menu if one has been provided by the application model,
        // otherwise just add it.
        String helpMenuText = labels.getString("help.text");
        int index = mb.getComponentCount();
        for (int i = 0, n = mb.getComponentCount(); i < n; i++) {
            JMenu m = (JMenu) mb.getComponent(i);
            if (m.getText() != null && m.getText().equals(helpMenuText)) {
                index = i;
                break;
            }
        }
        mb.add(createWindowMenu(p), index);
        return mb;
    }

    protected JMenu createWindowMenu(final View p) {
        ApplicationModel model = getModel();

        JMenu m;
        JMenuItem mi;

        m = new JMenu();
        final JMenu windowMenu = m;
        labels.configureMenu(m, "window");
        addViewWindowMenuItems(m, p);
        m.addSeparator();
        for (View pr : views()) {
            if (pr.getAction(FocusAction.ID) != null) {
                windowMenu.add(pr.getAction(FocusAction.ID));
            }
        }
        if (paletteActions.size() > 0) {
            m.addSeparator();
            for (Action a : paletteActions) {
                JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(a);
                Actions.configureJCheckBoxMenuItem(cbmi, a);
                cbmi.setIcon(null);
                m.add(cbmi);
            }
        }

        addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name == "viewCount" || name == "paletteCount") {
                    if (p == null || views().contains(p)) {
                        JMenu m = windowMenu;
                        m.removeAll();
                        addViewWindowMenuItems(m, p);
                        m.addSeparator();
                        for (Iterator i = views().iterator(); i.hasNext();) {
                            View pr = (View) i.next();
                            if (pr.getAction(FocusAction.ID) != null) {
                                m.add(pr.getAction(FocusAction.ID));
                            }
                        }
                        if (paletteActions.size() > 0) {
                            m.addSeparator();
                            for (Action a : paletteActions) {
                                JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(a);
                                Actions.configureJCheckBoxMenuItem(cbmi, a);
                                cbmi.setIcon(null);
                                m.add(cbmi);
                            }
                        }
                    } else {
                        removePropertyChangeListener(this);
                    }
                }
            }
        });

        return m;
    }

    protected void addViewWindowMenuItems(JMenu m, View p) {
        JMenuItem mi;

        ApplicationModel model = getModel();
        mi = m.add(model.getAction(MinimizeAction.ID));
        mi.setIcon(null);
        mi = m.add(model.getAction(MaximizeAction.ID));
        mi.setIcon(null);
    }

    protected void updateOpenRecentMenu(JMenu openRecentMenu) {
        if (openRecentMenu.getItemCount() > 0) {
            JMenuItem clearRecentFilesItem = (JMenuItem) openRecentMenu.getItem(
                    openRecentMenu.getItemCount() - 1);
            openRecentMenu.removeAll();
            for (File f : recentFiles()) {
                openRecentMenu.add(new OpenRecentAction(DefaultOSXApplication.this, f));
            }
            if (recentFiles().size() > 0) {
                openRecentMenu.addSeparator();
            }
            openRecentMenu.add(clearRecentFilesItem);
        }
    }

    protected JMenu createFileMenu(View p) {
        //ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        ApplicationModel model = getModel();

        JMenu m;
        JMenuItem mi;
        final JMenu openRecentMenu;

        m = new JMenu();
        labels.configureMenu(m, "file");
        mi = m.add(model.getAction(NewAction.ID));
        mi.setIcon(null);
        mi = m.add(model.getAction(OpenAction.ID));
        mi.setIcon(null);
        if (model.getAction(OpenDirectoryAction.ID) != null) {
            mi = m.add(model.getAction(OpenDirectoryAction.ID));
            mi.setIcon(null);
        }
        openRecentMenu = new JMenu();
        labels.configureMenu(openRecentMenu, "file.openRecent");
        openRecentMenu.setIcon(null);
        openRecentMenu.add(model.getAction(ClearRecentFilesAction.ID));
        updateOpenRecentMenu(openRecentMenu);
        m.add(openRecentMenu);
        m.addSeparator();
        mi = m.add(model.getAction(CloseAction.ID));
        mi.setIcon(null);
        mi = m.add(model.getAction(SaveAction.ID));
        mi.setIcon(null);
        mi = m.add(model.getAction(SaveAsAction.ID));
        mi.setIcon(null);
        if (model.getAction(ExportAction.ID) != null) {
            mi = m.add(model.getAction(ExportAction.ID));
            mi.setIcon(null);
        }
        if (model.getAction(PrintAction.ID) != null) {
            m.addSeparator();
            mi = m.add(model.getAction(PrintAction.ID));
            mi.setIcon(null);
        }

        addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name == "recentFiles") {
                    updateOpenRecentMenu(openRecentMenu);
                }
            }
        });

        return m;
    }

    protected void initScreenMenuBar() {
        ApplicationModel model = getModel();
        net.roydesign.app.Application mrjapp = net.roydesign.app.Application.getInstance();
        mrjapp.setFramelessJMenuBar(createMenuBar(null));
        paletteHandler.add(SwingUtilities.getWindowAncestor(mrjapp.getFramelessJMenuBar()), null);
        mrjapp.getAboutJMenuItem().setAction(model.getAction(AboutAction.ID));
        mrjapp.getQuitJMenuItem().setAction(model.getAction(ExitAction.ID));
        mrjapp.addOpenDocumentListener(model.getAction(OSXDropOnDockAction.ID));
    }

    protected void initPalettes(final LinkedList<Action> paletteActions) {
        SwingUtilities.invokeLater(new Worker() {

            public Object construct() {
                LinkedList<JFrame> palettes = new LinkedList<JFrame>();
                LinkedList<JToolBar> toolBars = new LinkedList<JToolBar>(getModel().createToolBars(DefaultOSXApplication.this, null));

                int i = 0;
                int x = 0;
                for (JToolBar tb : toolBars) {
                    i++;
                    tb.setFloatable(false);
                    tb.setOrientation(JToolBar.VERTICAL);
                    tb.setFocusable(false);

                    JFrame d = new JFrame();

                    // Note: Client properties must be set before heavy-weight
                    // peers are created
                    d.getRootPane().putClientProperty("Window.style", "small");
                    d.getRootPane().putClientProperty("Quaqua.RootPane.isVertical", Boolean.FALSE);
                    d.getRootPane().putClientProperty("Quaqua.RootPane.isPalette", Boolean.TRUE);

                    d.setFocusable(false);
                    d.setResizable(false);
                    d.getContentPane().setLayout(new BorderLayout());
                    d.getContentPane().add(tb, BorderLayout.CENTER);
                    d.setAlwaysOnTop(true);
                    d.setUndecorated(true);
                    d.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
                    d.getRootPane().setFont(
                            new Font("Lucida Grande", Font.PLAIN, 11));

                    d.setJMenuBar(createMenuBar(null));

                    d.pack();
                    d.setFocusableWindowState(false);
                    PreferencesUtil.installPalettePrefsHandler(prefs, "toolbar." + i, d, x);
                    x += d.getWidth();

                    paletteActions.add(new OSXTogglePaletteAction(DefaultOSXApplication.this, d, tb.getName()));
                    palettes.add(d);
                }
                return palettes;

            }

            public void finished(Object result) {
                @SuppressWarnings("unchecked")
                LinkedList<JFrame> palettes = (LinkedList<JFrame>) result;
                if (palettes != null) {
                    for (JFrame p : palettes) {
                        addPalette(p);
                    }
                    firePropertyChange("paletteCount", 0, palettes.size());
                }
            }
        });
    }

    public boolean isSharingToolsAmongViews() {
        return true;
    }

    public Component getComponent() {
        return null;
    }
}
