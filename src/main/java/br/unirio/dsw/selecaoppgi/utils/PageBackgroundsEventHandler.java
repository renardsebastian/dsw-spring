package br.unirio.dsw.selecaoppgi.utils;

import static br.unirio.dsw.selecaoppgi.utils.PdfBuilder.IMAGE;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Admin
 */
public class PageBackgroundsEventHandler implements IEventHandler {

    @Override
    public void handleEvent(Event event) {
    	String bgUrl = "";
    	
    	try {
    		bgUrl = new ClassPathResource("/img/bg-relatorio.jpg").getFile().getAbsolutePath();    		
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	System.out.println(bgUrl);
    	
        try {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();

            PdfCanvas canvas = new PdfCanvas(page);
            Rectangle rect = page.getPageSize();
            
            ImageData image = ImageDataFactory.create(bgUrl);
            canvas.saveState();
            PdfExtGState state = new PdfExtGState();
            state.setFillOpacity(0.07f);
            canvas.setExtGState(state);
            canvas.addImage(image, 0, 200, rect.getWidth(), false);
            canvas.restoreState();
        } catch (MalformedURLException ex) {
            Logger.getLogger(PageBackgroundsEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

