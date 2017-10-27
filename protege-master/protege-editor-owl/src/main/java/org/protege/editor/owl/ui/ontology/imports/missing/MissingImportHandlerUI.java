package org.protege.editor.owl.ui.ontology.imports.missing;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.MissingImportHandler;
import org.protege.editor.owl.model.library.OntologyCatalogManager;
import org.protege.editor.owl.ui.UIHelper;
import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.entry.UriEntry;
import org.semanticweb.owlapi.model.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 31-Aug-2006<br><br>

 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class MissingImportHandlerUI implements MissingImportHandler {

    private final Logger logger = LoggerFactory.getLogger(MissingImportHandlerUI.class);

    private final OWLEditorKit owlEditorKit;


    public MissingImportHandlerUI(OWLEditorKit owlEditorKit) {
        this.owlEditorKit = owlEditorKit;
    }


    public IRI getDocumentIRI(IRI ontologyIRI) {
        FutureTask<IRI> futureTask = new FutureTask<>(() -> {
            int ret = JOptionPane.showConfirmDialog(null,
                    "<html><body>The system couldn't locate the ontology:<br><font color=\"blue\">" + ontologyIRI.toString() + "</font><br><br>" +

                            "Would you like to attempt to resolve the missing import?</body></html>",
                    "Resolve missing import?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (ret != JOptionPane.YES_OPTION) {
                return null;
            }
            UIHelper helper = new UIHelper(owlEditorKit);
            File file = helper.chooseOWLFile("Please select an ontology file");
            if (file == null) {
                return ontologyIRI;
            }
            updateActiveCatalog(ontologyIRI, file);
            return IRI.create(file);
        });

        SwingUtilities.invokeLater(futureTask);
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            logger.debug("Resolve import task interrupted");
            return null;
        } catch (ExecutionException e) {
            logger.error("An exception was thrown whilst the user was resolving a missing import: {}", e.getCause().getMessage());
            return null;
        }
    }
    
    private void updateActiveCatalog(IRI ontologyIRI, File file) {
        OntologyCatalogManager catalogManager = owlEditorKit.getOWLModelManager().getOntologyCatalogManager();
        XMLCatalog activeCatalog = catalogManager.getActiveCatalog();
        if (activeCatalog == null) {
            return;
        }
        URI relativeFile = CatalogUtilities.relativize(file.toURI(), activeCatalog);
        activeCatalog.addEntry(0, new UriEntry("User Entered Import Resolution", activeCatalog, ontologyIRI.toString(), relativeFile, null));
        File catalogLocation = new File(activeCatalog.getXmlBaseContext().getXmlBase());
        try {
            CatalogUtilities.save(activeCatalog, catalogLocation);
        }
        catch (IOException e) {
            logger.error("Could not save user supplied import redirection to catalog.", e);
        }
    }
}

