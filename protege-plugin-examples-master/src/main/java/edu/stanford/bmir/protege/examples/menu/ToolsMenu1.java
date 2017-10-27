package edu.stanford.bmir.protege.examples.menu;

/*-
 * #%L
 * ELK Reasoner Protege Plug-in
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2017 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.protege.editor.owl.model.selection.OWLSelectionModel;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;

public class ToolsMenu1 extends ProtegeOWLAction {

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	public void actionPerformed(ActionEvent event) {
		StringBuilder message = new StringBuilder("This example menu item is under the Tools menu.\n");
		message.append("The currently selected class is ");
		org.semanticweb.owlapi.model.OWLClass lastSelectedClass = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();
		message.append(lastSelectedClass);
		message.append(".");
		JOptionPane.showMessageDialog(getOWLWorkspace(), message.toString());	
	}
}
