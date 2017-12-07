package br.unirio.dsw.selecaoppgi.service.relatorio;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import br.unirio.dsw.selecaoppgi.utils.PageBackgroundsEventHandler;


public class GeradorRelatorio {	
	
	String arquivoHomologados = "relatorio-homologados-original.pdf";
	String arquivoHomologadosRecurso = "relatorio-homologados-recurso.pdf";
	
	
	public GeradorRelatorio(){
		 
	}
	
	/*
	 *  Gera relatório de inscrições homologadas, em pdf, recebendo as listas de aprovados, reprovados e suas justificativas
	 */
	public String relatorioInscricoesHomologadas(List<String> candidatosAprovados, List<String> candidatosReprovados, List<String> justificativas){	
		String titleAprovados = "Inscrições homologadas";
		String titleReprovados = "Inscrições não homologadas";
		String subtitleAprovados = "Candidatos com pedidos de inscrição aceitos após entrega dos documentos";
		String subtitleReprovados = "Candidatos com pedidos de inscrição rejeitados após entrega dos documentos";
		Document doc = null;
		
		try {
			String path = new ClassPathResource("/downloads/" + arquivoHomologados).getFile().getAbsolutePath();
			doc = initializeDocument(path);
	        
	        // Define a fote utilizada
	        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
	        
	        // Escreve o título e o subtítulo da section
	        doc.add(new Paragraph("\n"));
	        doc.add(new Paragraph(titleAprovados).setFont(font).setFontSize(16));	        
	        doc.add(new Paragraph(subtitleAprovados).setFont(font).setFontSize(12));
	        
	        
	        criaTabela(doc, "#", "Nome do candidato", new Float(1), new Float(15), candidatosAprovados);
	        doc.add(new Paragraph("\n"));	        
	        
	        // Escreve o título e o subtítulo da section
	        doc.add(new Paragraph("\n"));
	        doc.add(new Paragraph(titleReprovados).setFont(font).setFontSize(16));	        
	        doc.add(new Paragraph(subtitleReprovados).setFont(font).setFontSize(12));
	        criaTabela(doc, "#", "Nome do candidato", "Justificativa", new Float(1), new Float(6), new Float(9), candidatosReprovados, justificativas);
	        doc.add(new Paragraph("\n"));	  
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Close document
        doc.close();
		
		return this.arquivoHomologados;
	}
	
	/*
	 *  Gera relatório de inscrições homologadas após recurso
	 */
	public String relatorioInscricoesHomologadasAposRecurso(List<String> candidatosAprovados, List<String> candidatosReprovados, List<String> justificativas){
		String titleAprovados = "Inscrições homologadas";
		String titleReprovados = "Inscrições não homologadas";
		String subtitleAprovados = "Candidatos com pedidos de inscrição aceitos após entrega dos documentos";
		String subtitleReprovados = "Candidatos com pedidos de inscrição rejeitados após entrega dos documentos";
		Document doc = null;
		
		try {
			String path = new ClassPathResource("/downloads/" + arquivoHomologadosRecurso).getFile().getAbsolutePath();
			doc = initializeDocument(path);
	        
	        // Define a fote utilizada
	        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
	        
	        // Escreve o título e o subtítulo da section
	        doc.add(new Paragraph("\n"));
	        doc.add(new Paragraph(titleAprovados).setFont(font).setFontSize(16));	        
	        doc.add(new Paragraph(subtitleAprovados).setFont(font).setFontSize(12));
	        
	        
	        criaTabela(doc, "#", "Nome do candidato", new Float(1), new Float(15), candidatosAprovados);
	        doc.add(new Paragraph("\n"));	        
	        
	        // Escreve o título e o subtítulo da section
	        doc.add(new Paragraph("\n"));
	        doc.add(new Paragraph(titleReprovados).setFont(font).setFontSize(16));	        
	        doc.add(new Paragraph(subtitleReprovados).setFont(font).setFontSize(12));
	        criaTabela(doc, "#", "Nome do candidato", "Justificativa", new Float(1), new Float(6), new Float(9), candidatosReprovados, justificativas);
	        doc.add(new Paragraph("\n"));	  
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Close document
        doc.close();
		
		return this.arquivoHomologadosRecurso;
	}
	

    public Document initializeDocument(String path){
    	PdfDocument pdf;
    	Document doc = null;    	
        PdfWriter writer;
		try {
			writer = new PdfWriter(path);
			pdf = new PdfDocument(writer);
			pdf.addEventHandler(PdfDocumentEvent.START_PAGE, new PageBackgroundsEventHandler());
			PageSize pageSize = new PageSize(PageSize.A4);
			doc = new Document(pdf, pageSize);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return doc; 
    }
           
    public void criaTabela(Document doc, String headerCell1, String headerCell2, Float tamCol1, Float tamCol2, List<String> listaColuna1) {
        // Create Table
        float[] columnWidths = { tamCol1, tamCol2 };
        
         Table table = new Table(columnWidths);
         table.setWidthPercent(100);
        
        Cell[] header = new Cell[] {
            new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell1).setPadding(5),
            new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell2).setPadding(5)
        };
        for (Cell hfCell : header) {
            table.addHeaderCell(hfCell);
        }
        
        //lista.size()
        for (int i = 0; i<listaColuna1.size() ; i++) {
            table.addCell(String.valueOf(i + 1));
            table.addCell(listaColuna1.get(i));
        }

        doc.add(table);
    }
    
    public void criaTabela(Document doc, String headerCell1, String headerCell2, String headerCell3, Float tamCol1, Float tamCol2, Float tamCol3, List<String> listaColuna1, List<String> listaColuna2) {
        // Create Table
    	float[] columnWidths = { tamCol1, tamCol2, tamCol3 };
         Table table = new Table(columnWidths);
         table.setWidthPercent(100);
        
        Cell[] header = new Cell[] {
        		new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell1).setPadding(5),
                new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell2).setPadding(5),
                new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell3).setPadding(5)
        };
        for (Cell hfCell : header) {
            table.addHeaderCell(hfCell);
        }
        
        //lista.size()
        for (int i = 0; i<listaColuna1.size() ; i++) {
            table.addCell(String.valueOf(i + 1));
            table.addCell(listaColuna1.get(i));            
            table.addCell(listaColuna2.get(i));
        }

        doc.add(table);
    }
    
    public void criaTabela(Document doc, String headerCell1, String headerCell2, String headerCell3, String headerCell4, Float tamCol1, Float tamCol2, Float tamCol3, Float tamCol4, List<String> listaColuna1, List<String> listaColuna2, List<String> listaColuna3) {
        // Create Table
        float[] columnWidths = { tamCol1, tamCol2, tamCol3, tamCol4 };
         Table table = new Table(columnWidths);
         table.setWidthPercent(100);
        
        Cell[] header = new Cell[] {
        		new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell1).setPadding(5),
                new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell2).setPadding(5),
                new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell3).setPadding(5),
                new Cell().setBackgroundColor(new DeviceGray(0.75f)).add(headerCell3).setPadding(5)
        };
        for (Cell hfCell : header) {
            table.addHeaderCell(hfCell);
        }
        
        //lista.size()
        for (int i = 0; i<100; i++) {
            table.addCell(String.valueOf(i + 1));           
            table.addCell(listaColuna1.get(i));            
            table.addCell(listaColuna2.get(i));
            table.addCell(listaColuna3.get(i));
        }

        doc.add(table);
    }
	
}
