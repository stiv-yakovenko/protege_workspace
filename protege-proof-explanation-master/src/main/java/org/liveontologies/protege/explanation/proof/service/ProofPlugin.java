package org.liveontologies.protege.explanation.proof.service;

/*-
 * #%L
 * Protege Proof-Based Explanation
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2014 - 2016 Live Ontologies Project
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

import org.eclipse.core.runtime.IExtension;
import org.protege.editor.core.plugin.AbstractProtegePlugin;
import org.protege.editor.owl.OWLEditorKit;

public class ProofPlugin extends AbstractProtegePlugin<ProofService> {

	public static final String KEY = "org.liveontologies.protege.explanation.proof";
	public static final String ID = "service";
	public static final String NAME = "name";

	private final OWLEditorKit editorKit_;

	public ProofPlugin(OWLEditorKit editorKit, IExtension extension) {
		super(extension);
		this.editorKit_ = editorKit;
	}

	public String getName() {
		return getPluginProperty(NAME);
	}

	@Override
	public ProofService newInstance() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		return super.newInstance().setup(editorKit_, getId(), getName());
	}

}
