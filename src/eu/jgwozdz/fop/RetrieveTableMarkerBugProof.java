package eu.jgwozdz.fop;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class RetrieveTableMarkerBugProof {

    public static void main(String[] args) throws IOException, FOPException, TransformerException, URISyntaxException {
        FopFactory fopFactory = FopFactory.newInstance(new URI("."));

        URL input = RetrieveTableMarkerBugProof.class.getResource("markers.fo.xml");
        Path outputPath = Paths.get("result.pdf");
        Logger.getAnonymousLogger().info("reading from " + input);
        Logger.getAnonymousLogger().info("writing to " + outputPath.toAbsolutePath());

        try (
        InputStream in = input.openStream();
        OutputStream out = Files.newOutputStream(outputPath)) {

            Source src = new StreamSource(in);

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            Result res = new SAXResult(fop.getDefaultHandler());

            TransformerFactory.newInstance().newTransformer().transform(src, res);
        }


        Desktop.getDesktop().open(outputPath.toFile());
    }
}