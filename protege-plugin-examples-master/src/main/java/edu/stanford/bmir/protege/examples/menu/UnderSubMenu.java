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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;

import org.liveontologies.owlapi.proof.OWLProver;
import org.liveontologies.puli.Inference;
import org.liveontologies.puli.Proof;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.elk.owlapi.ElkProver;
import org.semanticweb.elk.owlapi.ElkProverFactory;
import org.semanticweb.elk.owlapi.ElkReasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class UnderSubMenu extends ProtegeOWLAction {

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	private static <C> void unwindProofs(Proof<C> inferences, C entailment) {
		// Start recursive unwinding
		LinkedList<C> toDo = new LinkedList<C>();
		Set<C> done = new HashSet<C>();

		toDo.add(entailment);
		done.add(entailment);

		for (;;) {
			C next = toDo.poll();

			if (next == null) {
				break;
			}

			for (Inference<C> inf : inferences.getInferences(next)) {
				System.out.println(inf);
				// Recursively unwind premise inferences
				for (C premise : inf.getPremises()) {

					if (done.add(premise)) {
						toDo.addFirst(premise);
					}
				}

				// Uncomment if only interested in one inference per derived expression (that is sufficient to reconstruct one proof)
				//break;
			}
		}
	}

	private static OWLAxiom getEntailment() {
		// Let's pick some class subsumption we want to explain
		OWLDataFactory factory = OWLManager.getOWLDataFactory();

		OWLClass subsumee = factory.getOWLClass(IRI.create("http://www.semanticweb.org/denis/ontologies/2017/2/untitled-ontology-195#A"));
		OWLClass subsumer = factory.getOWLClass(IRI.create("http://www.semanticweb.org/denis/ontologies/2017/2/untitled-ontology-195#C"));

		return factory.getOWLSubClassOfAxiom(subsumee, subsumer);
	}


	public void actionPerformed(ActionEvent arg0) {
		OWLReasoner reasoner = getOWLModelManager().getOWLReasonerManager()
				.getCurrentReasoner();
		ElkProver elkProver=null;
		OWLOntology activeOntology = getOWLModelManager().getActiveOntology();
		if (reasoner instanceof ElkReasoner) {
			elkProver = new ElkProver((ElkReasoner) reasoner);
		} else {
			ElkProverFactory factory = new ElkProverFactory();
			elkProver = factory
					.createReasoner(activeOntology);
		}
		System.out.println("elkProver=");
		System.out.println(elkProver);

		OWLProver prover = (OWLProver)elkProver;
		prover.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		OWLAxiom entailment = getEntailment();
		Proof<OWLAxiom> inferences = prover.getProof(entailment);
		unwindProofs(inferences, entailment);
		System.out.println("DONE");
	}
}
