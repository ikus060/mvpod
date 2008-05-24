package net.homeip.entreprisesmd.mvconv.gui.options;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ComboCustomViewer extends AbstractListViewer {
	
	/**
	 * Custom element that been add.
	 */
	private Object customSelection;

	/**
	 * This viewer's list control if this viewer is instantiated with a combo
	 * control; otherwise <code>null</code>.
	 * 
	 * @see #ComboViewer(Combo)
	 */
	private Combo combo;

	/**
	 * This viewer's list control if this viewer is instantiated with a CCombo
	 * control; otherwise <code>null</code>.
	 * 
	 * @see #ComboViewer(CCombo)
	 * @since 3.3
	 */
	private CCombo ccombo;

	/**
	 * Creates a combo viewer on a newly-created combo control under the given
	 * parent. The viewer has no input, no content provider, a default label
	 * provider, no sorter, and no filters.
	 * 
	 * @param parent
	 *            the parent control
	 */
	public ComboCustomViewer(Composite parent) {
		this(parent, SWT.READ_ONLY | SWT.BORDER);
	}

	/**
	 * Creates a combo viewer on a newly-created combo control under the given
	 * parent. The combo control is created using the given SWT style bits. The
	 * viewer has no input, no content provider, a default label provider, no
	 * sorter, and no filters.
	 * 
	 * @param parent
	 *            the parent control
	 * @param style
	 *            the SWT style bits
	 */
	public ComboCustomViewer(Composite parent, int style) {
		this(new Combo(parent, style));
	}

	/**
	 * Creates a combo viewer on the given combo control. The viewer has no
	 * input, no content provider, a default label provider, no sorter, and no
	 * filters.
	 * 
	 * @param list
	 *            the combo control
	 */
	public ComboCustomViewer(Combo list) {
		this.combo = list;
		hookControl(list);
	}

	/**
	 * Creates a combo viewer on the given CCombo control. The viewer has no
	 * input, no content provider, a default label provider, no sorter, and no
	 * filters.
	 * 
	 * @param list
	 *            the CCombo control
	 * @since 3.3
	 */
	public ComboCustomViewer(CCombo list) {
		this.ccombo = list;
		hookControl(list);
	}

	protected void listAdd(String string, int index) {
		if (combo == null) {
			ccombo.add(string, index);
		} else {
			combo.add(string, index);
		}
	}

	protected void listSetItem(int index, String string) {
		if (combo == null) {
			ccombo.setItem(index, string);
		} else {
			combo.setItem(index, string);
		}
	}

	protected int[] listGetSelectionIndices() {
		if (combo == null) {
			return new int[] { ccombo.getSelectionIndex() };
		} else {
			return new int[] { combo.getSelectionIndex() };
		}
	}

	protected int listGetItemCount() {
		if (combo == null) {
			return ccombo.getItemCount();
		} else {
			return combo.getItemCount();
		}
	}

	protected void listSetItems(String[] labels) {
		if (combo == null) {
			ccombo.setItems(labels);
		} else {
			combo.setItems(labels);
		}
	}

	protected void listRemoveAll() {
		if (combo == null) {
			ccombo.removeAll();
		} else {
			combo.removeAll();
		}
	}

	protected void listRemove(int index) {
		if (combo == null) {
			ccombo.remove(index);
		} else {
			combo.remove(index);
		}
	}

	/*
	 * (non-Javadoc) Method declared on Viewer.
	 */
	public Control getControl() {
		if (combo == null) {
			return ccombo;
		} else {
			return combo;
		}
	}

	/**
	 * Returns this list viewer's list control. If the viewer was not created on
	 * a CCombo control, some kind of unchecked exception is thrown.
	 * 
	 * @return the list control
	 * @since 3.3
	 */
	public CCombo getCCombo() {
		Assert.isNotNull(ccombo);
		return ccombo;
	}

	/**
	 * Returns this list viewer's list control. If the viewer was not created on
	 * a Combo control, some kind of unchecked exception is thrown.
	 * 
	 * @return the list control
	 */
	public Combo getCombo() {
		Assert.isNotNull(combo);
		return combo;
	}

	/*
	 * Do nothing -- combos only display the selected element, so there is no
	 * way we can ensure that the given element is visible without changing the
	 * selection. Method defined on StructuredViewer.
	 */
	public void reveal(Object element) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listSetSelection(int[])
	 */
	protected void listSetSelection(int[] ixs) {
		if (combo == null) {
			for (int idx = 0; idx < ixs.length; idx++) {
				ccombo.select(ixs[idx]);
			}
		} else {
			for (int idx = 0; idx < ixs.length; idx++) {
				combo.select(ixs[idx]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listDeselectAll()
	 */
	protected void listDeselectAll() {
		if (combo == null) {
			ccombo.deselectAll();
			ccombo.clearSelection();
		} else {
			combo.deselectAll();
			combo.clearSelection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listShowSelection()
	 */
	protected void listShowSelection() {
	}

	/**
	 * Parlays the given list of selected elements into selections on this
	 * viewer's control.
	 * <p>
	 * This implementation ensure to set the selection to the given element. If
	 * the element doesn't exist, it's created.
	 * </p>
	 * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(org.eclipse.jface.viewers.ISelection,
	 *      boolean)
	 */
	protected void setSelectionToWidget(ISelection selection, boolean reveal) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			List wantedSelection = strucSelection.toList();
			setSelectionToWidget(wantedSelection, reveal);

			List curSelection = ((IStructuredSelection) this.getSelection())
					.toList();

			if (wantedSelection.size() == 1
					&& !wantedSelection.equals(curSelection)) {
				if (customSelection != null) {
					this.remove(customSelection);
				}

				Object element = wantedSelection.get(0);
				customSelection = element;
				this.add(customSelection);

				super.setSelection(new StructuredSelection(customSelection));

			}

		} else {
			setSelectionToWidget((List) null, reveal);
		}

	}

}
