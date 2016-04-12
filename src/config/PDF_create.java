/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Desktop;
import java.io.File;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.PageSize;
import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
/**
 *
 * @author Familia Aparicio
 */
public class PDF_create {
    
    public String ruta,codigoreserva;    
    private Image imagen; 
    database db = new database();
    
    public PDF_create(String codreserva){
        codigoreserva=codreserva;
        ruta="Reserva_Vuelo.pdf";
        try{
            createPdf();
        }
        catch(Exception e){
            System.out.println("Error en crearcion de PDF");
        }
    }
    
    public void createPdf() throws DocumentException, IOException {
        String nombre="",salida="",llegada="",origen="",destino="",iduser="",idvuelo="";
        try{
            imagen= Image.getInstance("logo.png");
            imagen.scaleAbsolute(100,28);
        }
        catch(Exception e){
            System.out.println("Error con imagen. . . "+e);
        }
        
        try{
            db.conectar();
            String sql="SELECT usuarios_idusuarios,vuelos_idvuelos FROM usuarios_has_vuelos WHERE codigo='"+codigoreserva+"'";
            ResultSet rs = db.query(sql);
            rs.first();
            iduser=rs.getString("usuarios_idusuarios");
            idvuelo=rs.getString("vuelos_idvuelos");            
            
            String sql1="SELECT nombre FROM usuarios WHERE idusuarios='"+iduser+"'";
            ResultSet rs1 = db.query(sql1);
            rs1.first();
            nombre=rs1.getString("nombre");

            String sql2="SELECT vuelos.hora_inicio,vuelos.hora_fin,ruta.origen,ruta.destino FROM vuelos,ruta WHERE vuelos.idvuelos='"+idvuelo+"' AND vuelos.ruta_idruta=ruta.idruta";
            ResultSet rs2= db.query(sql2);
            rs2.first();
            salida=rs2.getString("hora_inicio");
            llegada=rs2.getString("hora_fin");
            origen=rs2.getString("origen");
            destino=rs2.getString("destino");
            db.desconectar();
            
        }
        catch(Exception e){
            System.out.println("Error obteniendo informacion. . . "+e);
        }
        Document document = new Document(PageSize.LETTER);
        document.setMargins(10, 10, 260, 290);
        PdfWriter.getInstance(document, new FileOutputStream(ruta));     
        document.open();
        
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPCell imagencell = new PdfPCell(imagen);
        imagencell.setFixedHeight(40);
        imagencell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(imagencell);        
        table.addCell(new Paragraph("RESERVA DE VUELO"));
        
        PdfPCell separacion = new PdfPCell();
        separacion.setBorder(com.itextpdf.text.Rectangle.BOTTOM);
        separacion.setColspan(2);
        table.addCell(separacion);
        
        table.addCell(" ");
        table.addCell(" ");  
        
        PdfPCell cellnombre = new PdfPCell(new Paragraph("Nombre: "+nombre));
        cellnombre.setBorder(PdfPCell.NO_BORDER);
        cellnombre.setColspan(2);
        table.addCell(cellnombre);
        
        table.addCell(" ");
        table.addCell(" ");  
        
        table.addCell("Código de reserva: "+codigoreserva);
        table.addCell("");
        
        table.addCell(" ");
        table.addCell(" ");
        
        table.addCell("Salida: "+salida);
        table.addCell("Llegada: "+llegada);
        
        table.addCell(" ");
        table.addCell(" ");
        
        table.addCell("Origen: "+origen);
        table.addCell("Destino: "+destino);
        
        table.addCell(" ");
        table.addCell(" ");
        
        PdfPCell cellnota = new PdfPCell(new Paragraph("ATENCION: FALTA REALIZAR CHECK-IN 24 HORAS ANTES DEL VIAJE."));
        cellnota.setBorder(PdfPCell.NO_BORDER);
        cellnota.setColspan(2);
        table.addCell(cellnota);
        
        document.add(table);
        
        document.close();        
        abrir();
    }
    private void abrir() {
        try {
            File path = new File (ruta);            
            Desktop.getDesktop().open(path);                         
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
    
}
