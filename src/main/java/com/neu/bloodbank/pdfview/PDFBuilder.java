package com.neu.bloodbank.pdfview;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 
 * @author neera
 *
 */
public class PDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Object[]> bbstock = (List<Object[]>) model.get("bbstocks");

		doc.add(new Paragraph("Blood Stock Statement"));

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 2.0f, 2.0f});
		table.setSpacingBefore(10);

		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

	
		PdfPCell mycell = new PdfPCell();
		mycell.setBackgroundColor(BaseColor.GRAY);
		mycell.setPadding(2);

		mycell.setPhrase(new Phrase("Blood Type", font));
		table.addCell(mycell);

		mycell.setPhrase(new Phrase("Blood Amount", font));
		table.addCell(mycell);


		// write table row data
		for (Object obj[] : bbstock) {
			table.addCell(String.valueOf(obj[0]));
			table.addCell(String.valueOf(obj[1]));
		
		
		}

		doc.add(table);

	}
}