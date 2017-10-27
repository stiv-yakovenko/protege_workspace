package org.protege.editor.owl.ui.editor;

import org.protege.editor.core.ui.util.InputVerificationStatusChangedListener;
import org.protege.editor.core.ui.util.VerifiedInputEditor;
import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class IRITextEditor implements OWLObjectEditor<IRI>, VerifiedInputEditor {
	private OWLObjectEditorHandler<IRI> handler;
	private JPanel editor;
	private JTextField iriTextField;
	private List<InputVerificationStatusChangedListener> inputVerificationListeners = new ArrayList<>();
	
	public IRITextEditor(OWLEditorKit editorKit) {
		createGui();
		setInitialIri(editorKit);
	}
	
	private void createGui() {
		editor = new JPanel();
		editor.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(12,12,0,12);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0;
		c.weighty = 0;
		editor.add(new JLabel("IRI:"), c);
		
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(12,0,0,12);
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		iriTextField = new JTextField();
		iriTextField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				for (InputVerificationStatusChangedListener listener : inputVerificationListeners) {
					listener.verifiedStatusChanged(getEditedObject() != null);
				}
			}

			public void keyPressed(KeyEvent e) {
			}
			
			public void keyReleased(KeyEvent e) {
			}
		});
		
		editor.add(iriTextField, c);

		editor.addHierarchyListener(e -> {
            if(editor.isShowing()) {
                iriTextField.requestFocus();
            }
        });
	}
	
	private void setInitialIri(OWLEditorKit editorKit) {
		OWLOntology ontology = editorKit.getOWLModelManager().getActiveOntology();
		OWLOntologyID ontologyId = ontology.getOntologyID();
		if (!ontologyId.isAnonymous()) {
			iriTextField.setText(ontologyId.getOntologyIRI().get().toString());
		}
	}

	@Nonnull
	public String getEditorTypeName() {
		return "IRI Editor";
	}

	public boolean canEdit(Object object) {
		return object instanceof IRI;
	}

	public boolean isMultiEditSupported() {
		return false;
	}
	
	public boolean isPreferred() {
		return false;
	}

	@Nonnull
	public JComponent getEditorComponent() {
		return editor;
	}

	public boolean setEditedObject(IRI editedObject) {
		if (editedObject != null) {
			iriTextField.setText(editedObject.toString());
		}
		return editedObject != null;
	}

	@Nullable
	public IRI getEditedObject() {
		IRI editedObject = null;
		try {
			editedObject = IRI.create(iriTextField.getText());
		}
		catch (RuntimeException e) {
		}
		return editedObject;
	}

	public Set<IRI> getEditedObjects() {
		IRI editedObject = getEditedObject();
		if (editedObject != null) {
			return Collections.singleton(editedObject);
		}
		else {
			return Collections.emptySet();
		}
	}
	
	public void addStatusChangedListener(InputVerificationStatusChangedListener listener) {
		inputVerificationListeners.add(listener);
	}
	
	public void removeStatusChangedListener(InputVerificationStatusChangedListener listener) {
		inputVerificationListeners.remove(listener);
	}

	public OWLObjectEditorHandler<IRI> getHandler() {
		return handler;
	}

	public void setHandler(OWLObjectEditorHandler<IRI> handler) {
		this.handler = handler;
	}

	public void clear() {
		iriTextField.setText("");
	}

	public void dispose() {
	}
}
