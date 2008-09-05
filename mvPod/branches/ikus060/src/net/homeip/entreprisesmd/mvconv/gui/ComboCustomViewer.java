package net.homeip.entreprisesmd.mvconv.gui;

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
	Object customSelection;

	/**
	 * This viewer's list control if this viewer is instantiated with a combo
	 * control; otherwise <code>null</code>.
	 * 
	 * @see #ComboViewer(Combo)
	 */
	Combo combo;

	/**
	 * This viewer's list control if this viewer is instantiated with a CCombo
	 * control; otherwise <code>null</code>.
	 * 
	 * @see #ComboViewer(CCombo)
	 * @since 3.3
	 */
	CCombo ccombo;

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
		if (this.combo == null) {
			this.ccombo.add(string, index);
		} else {
			this.combo.add(string, index);
		}
	}

	protected void listSetItem(int index, String string) {
		if (this.combo == null) {
			this.ccombo.setItem(index, string);
		} else {
			this.combo.setItem(index, string);
		}
	}

	protected int[] listGetSelectionIndices() {
		if (this.combo == null) {
			return new int[] { this.ccombo.getSelectionIndex() };
		}
		return new int[] { this.combo.getSelectionIndex() };
	}

	protected int listGetItemCount() {
		if (this.combo == null) {
			return this.ccombo.getItemCount();
		}
		return this.combo.getItemCount();
	}

	protected void listSetItems(String[] labels) {
		if (this.combo == null) {
			this.ccombo.setItems(labels);
		} else {
			this.combo.setItems(labels);
		}
	}

	protected void listRemoveAll() {
		if (this.combo == null) {
			this.ccombo.removeAll();
		} else {
			this.combo.removeAll();
		}
	}

	protected void listRemove(int index) {
		if (this.combo == null) {
			this.ccombo.remove(index);
		} else {
			this.combo.remove(index);
		}
	}

	/*
	 * (non-Javadoc) Method declared on Viewer.
	 */
	public Control getControl() {
		if (this.combo == null) {
			return this.ccombo;
		}
		return this.combo;
	}

	/**
	 * Returns this list viewer's list control. If the viewer was not created on
	 * a CCombo control, some kind of unchecked exception is thrown.
	 * 
	 * @return the list control
	 * @since 3.3
	 */
	public CCombo getCCombo() {
		Assert.isNotNull(this.ccombo);
		return this.ccombo;
	}

	/**
	 * Returns this list viewer's list control. If the viewer was not created on
	 * a Combo control, some kind of unchecked exception is thrown.
	 * 
	 * @return the list control
	 */
	public Combo getCombo() {
		Assert.isNotNull(this.combo);
		return this.combo;
	}

	/*
	 * Do nothing -- combos only display the selected element, so there is no
	 * way we can ensure that the given element is visible without changing the
	 * selection. Method defined on StructuredViewer.
	 */
	public void reveal(Object element) {
		// Empty block
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listSetSelection(int[])
	 */
	protected void listSetSelection(int[] ixs) {
		if (this.combo == null) {
			for (int idx = 0; idx < ixs.length; idx++) {
				this.ccombo.select(ixs[idx]);
			}
		} else {
			for (int idx = 0; idx < ixs.length; idx++) {
				this.combo.select(ixs[idx]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listDeselectAll()
	 */
	protected void listDeselectAll() {
		if (this.combo == null) {
			this.ccombo.deselectAll();
			this.ccombo.clearSelection();
		} else {
			this.combo.deselectAll();
			this.combo.clearSelection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listShowSelection()
	 */
	protected void listShowSelection() {
		// Available for sub-class
	}

	/**
	 * Parlays the given list of selected elements into selections on this
	 * viewer's control.
	 * <p>
	 * This implementation ensure to set the selection to the given element. If
	 * the element doesn't exist, it's created.
	 * </p>
	 * 
	 * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(org.eclipse.jface.viewers.ISelection,
	 *      boolean)
	 */
	protected void setSelectionToWidget(ISelection selection, boolean reveal) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			List<?> wantedSelection = strucSelection.toList();
			setSelectionToWidget(wantedSelection, reveal);

			List<?> curSelection = ((IStructuredSelection) this.getSelection())
					.toList();

			if (wantedSelection.size() == 1
					&& !wantedSelection.equals(curSelection)) {
				if (this.customSelection != null) {
					this.remove(this.customSelection);
				}

				Object element = wantedSelection.get(0);
				this.customSelection = element;
				this.add(this.customSelection);

				super
						.setSelection(new StructuredSelection(
								this.customSelection));

			}

		} else {
			setSelectionToWidget((List<?>) null, reveal);
		}

	}

}
