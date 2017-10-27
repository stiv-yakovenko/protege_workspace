package org.liveontologies.protege.explanation.proof.preferences;

/*-
 * #%L
 * Protege Proof-Based Explanation
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2014 - 2017 Live Ontologies Project
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
import org.protege.editor.core.editorkit.EditorKit;
import org.protege.editor.core.plugin.AbstractPluginLoader;

public class ProofPreferencesPanelPluginLoader extends AbstractPluginLoader<ProofPreferencesPanelPlugin> {
	private final EditorKit kit;
	
	private static final String ID = "preferences";
	private static final String KEY = "org.liveontologies.protege.explanation.proof";

	public ProofPreferencesPanelPluginLoader(EditorKit kit) {
		super(KEY, ID);
		this.kit = kit;
	}

	@Override
	protected ProofPreferencesPanelPlugin createInstance(IExtension extension) {
		return new ProofPreferencesPanelPlugin(kit, extension);
	}
}
