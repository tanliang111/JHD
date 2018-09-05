/*
 * @(#)GroupCommand.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	� by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.figures;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.util.*;

import java.util.List;

/**
 * Command to group the selection into a GroupFigure.
 *
 * @see GroupFigure
 *
 * @version <$CURRENT_VERSION$>
 */
public  class GroupCommand extends AbstractCommand {

   /**
	 * Constructs a group command.
	 * @param name the command name
	 * @param newDrawingEditor the DrawingEditor which manages the views
	 */
	public GroupCommand(String name, DrawingEditor newDrawingEditor) {
		super(name, newDrawingEditor);
	}

	public void execute() {
		super.execute();
		setUndoActivity(createUndoActivity());
		getUndoActivity().setAffectedFigures(view().selection());
		((GroupCommand.UndoActivity)getUndoActivity()).groupFigures();
		view().drawing().update();
	}

	public boolean isExecutableWithView() {
		return view().selectionCount() > 1;
	}

	/**
	 * Factory method for undo activity
	 */
	protected Undoable createUndoActivity() {
		return new GroupCommand.UndoActivity(view());
	}

	public static class UndoActivity extends UndoableAdapter {
		public UndoActivity(DrawingView newDrawingView) {
			super(newDrawingView);
			setUndoable(true);
			setRedoable(true);
		}

		public boolean undo() {
			if (!super.undo()) {
				return false;
			}

			getDrawingView().clearSelection();

			// orphan group figure(s)
			getDrawingView().drawing().orphanAll(getAffectedFigures());
			//this tool is now responsible for the release or readd of the figures it removed
			//!!!dnoyeb!!!

			// create a new collection with the grouped figures as elements
			List affectedFigures = CollectionsFactory.current().createList();

			FigureEnumeration fe = getAffectedFigures();
			while (fe.hasNextFigure()) {
				Figure currentFigure = fe.nextFigure();
				//figures can not be in 2 containers at same time.
				//copy figures for later use
				if(currentFigure instanceof CompositeFigure){
					FigureEnumeration feToRemove = currentFigure.figures();
					FigureEnumeration feToAdd = currentFigure.figures();
					FigureEnumeration feToSelect = currentFigure.figures();
					FigureEnumeration groupedFigures = currentFigure.figures();
					
					//remove figures from currentFigure
					((CompositeFigure)currentFigure).orphanAll( feToRemove );
					// add contained figures to drawing
					getDrawingView().drawing().addAll(feToAdd);
					//select figures
					getDrawingView().addToSelectionAll(feToSelect);
					//set new affected figures
					while (groupedFigures.hasNextFigure()) {
						affectedFigures.add(groupedFigures.nextFigure());
					}
				}
				setAffectedFigures(new FigureEnumerator(affectedFigures));
			}
			return true;
		}

		public boolean redo() {
			// do not call execute directly as the selection might has changed
			if (isRedoable()) {
				groupFigures();
				return true;
			}

			return false;
		}

		public void groupFigures() {
			getDrawingView().drawing().orphanAll(getAffectedFigures());
			//this tool is now responsible for the release or readd of the figures it removed
			//!!!dnoyeb!!!
			getDrawingView().clearSelection();

			// add new group figure instead
			GroupFigure group = new GroupFigure();
			group.addAll(getAffectedFigures());

			Figure figure = getDrawingView().drawing().add(group);
			getDrawingView().addToSelection(figure);

			// create a new collection with the new group figure as element
			List affectedFigures = CollectionsFactory.current().createList();
			affectedFigures.add(figure);
			setAffectedFigures(new FigureEnumerator(affectedFigures));
		}
	}
}
