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

import org.protege.editor.owl.ui.action.ProtegeOWLAction;

public class ToolsMenu3 extends ProtegeOWLAction {

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	public void actionPerformed(ActionEvent event) {
		StringBuilder message = new StringBuilder(
				"This example menu item is under the Tools menu, but displayed in a separate category from the other example menu items.\n");
		message.append("The active ontology has ");
		message.append(getOWLModelManager().getActiveOntology().getClassesInSignature().size());
		message.append(" classes.");
		JOptionPane.showMessageDialog(getOWLWorkspace(), message.toString());	
	}
}
