/*-
 * #%L
 * OWL API Proof Extension
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
package org.liveontologies.owlapi.proof;

import org.liveontologies.puli.DynamicProof;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;

/**
 * An {@link OWLReasoner} that can provide proofs for the entailed axioms.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * 
 * @author Yevgeny Kazakov
 */
public interface OWLProver extends OWLReasoner {

	/**
	 * @param entailment
	 * @return the inferences using which the entailment can be derived from the
	 *         axioms in the ontology
	 * @throws UnsupportedEntailmentTypeException
	 *             if the proof cannot be provided for the given entailment type
	 */
	public DynamicProof<OWLAxiom> getProof(OWLAxiom entailment)
			throws UnsupportedEntailmentTypeException;

}
