package com.mindhub.techTreasure.controllers;

import com.itextpdf.awt.geom.Line2D;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;
import com.mindhub.techTreasure.dtos.PurchaseOrderApplicationDTO;
import com.mindhub.techTreasure.models.*;
import com.mindhub.techTreasure.repositories.OrderDetailRepository;
import com.mindhub.techTreasure.services.CustomerService;
import com.mindhub.techTreasure.services.ProductService;
import com.mindhub.techTreasure.services.PurchaseOrderService;
import com.mindhub.techTreasure.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PurchaseOrderController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Transactional
    @PostMapping("/new/buy")//despues de pago hacer peticion a esta ruta.
    public ResponseEntity<?> newBuy(@RequestBody PurchaseOrderApplicationDTO purchaseOrderApplicationDTO, Authentication authentication) {
        Customer customer = customerService.findByEmail(authentication.getName());
        System.out.println(purchaseOrderApplicationDTO.getShippingAddress());
        System.out.println(purchaseOrderApplicationDTO.getProductIds());
        PurchaseOrder purchaseOrder = new PurchaseOrder("", LocalDateTime.now(), OrderStatus.PENDING, 0.0, 0.0, "");

        purchaseOrderService.savePurchaseOrder(purchaseOrder);

        if (purchaseOrderApplicationDTO.getShippingAddress() == null) {
            return new ResponseEntity<>("Please add a shipping address", HttpStatus.FORBIDDEN);
        }
        double totalTax = 0.0;

        double totalAmountWithTax = 0.0;

        Map<Long, OrderDetail> productIdToOrderDetail = new HashMap<>();

        for (Long productId : purchaseOrderApplicationDTO.getProductIds()) {//enviar el numero del id del producto, cuantas veces lo agrege el usuario al carro. Por ejemplo si se añadieron 3 productos del mismo enviar 3 veces el id.
            Product product = productService.findProductById(productId);

            if (product != null) {
                OrderDetail orderDetail = productIdToOrderDetail.get(productId);

                if (orderDetail == null) {

                    orderDetail = new OrderDetail(1, product.getPrice(), product);

                    orderDetail.setPurchaseOrder(purchaseOrder);
                    orderDetailRepository.save(orderDetail);
                    purchaseOrder.getOrderDetail().add(orderDetail);
                    productIdToOrderDetail.put(productId, orderDetail);

                    product.setStock(product.getStock() - 1);

                    Review review = new Review();
                    reviewService.save(review);
                    product.addReview(review);
                    customer.addReview(review);

                } else {
                    int newQuantity = orderDetail.getQuantity() + 1;
                    orderDetail.setQuantity(newQuantity);
                }
                totalTax += product.getPrice() * 0.19;
                totalAmountWithTax += product.getPrice() + product.getPrice() * 0.19;
            }
        }
        purchaseOrder.setNumber(orderNumberGenerator());
        purchaseOrder.setTax(totalTax);
        purchaseOrder.setTotal(totalAmountWithTax);
        purchaseOrder.setShippingAddress(purchaseOrderApplicationDTO.getShippingAddress());
        customer.addOrder(purchaseOrder);
        customerService.save(customer);
        System.out.println(customer);

        byte[] pdfContent = pdfTicketGenerator(purchaseOrder);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "attachment; filename=ticket.pdf");
        ResponseEntity<byte[]> response = new ResponseEntity<>(pdfContent,headers,HttpStatus.OK);

        return response;
    }


    public String orderNumberGenerator() {
        String prefix = "ORDER N° ";
        int numberRandom = (int) (Math.random() * 1000000);
        String digits = String.format("%06d", numberRandom);
        return prefix + digits;
    }

    private byte[] pdfTicketGenerator(PurchaseOrder purchaseOrder) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            Image image = Image.getInstance("https://res.cloudinary.com/dryi0j55n/image/upload/v1701478283/tech/ixwfyx9sa42ei2mxjzwl.png");
            image.scaleToFit(200, 80);
            document.add(image);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            LocalTime currentTime = LocalTime.now();
            String formatTime = currentTime.getHour() + ":" + currentTime.getMinute();
            LocalDate localDate= LocalDate.now();
            String formatDate = localDate.getDayOfMonth() + "/" + localDate.getMonth() + "/" + localDate.getYear();

            document.add(new Paragraph("Purchase Ticket", titleFont));
            document.add(new Paragraph(purchaseOrder.getNumber()));
            document.add(new Paragraph("Date: " + formatDate));
            document.add(new Paragraph("Time: " + formatTime));
            document.add(new Paragraph("Name: " + purchaseOrder.getCustomer().getName() + " " +  purchaseOrder.getCustomer().getLastName()));
            document.add(new Paragraph("Email: " + purchaseOrder.getCustomer().getEmail()));
            document.add(new Paragraph("Shipping Address: " + purchaseOrder.getShippingAddress()));
            document.add(new Paragraph("Tel: " + purchaseOrder.getCustomer().getTelephone()));



            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String formattedAmount = "Total: USD" + decimalFormat.format(purchaseOrder.getTotal());
//            String formatedTax= "TAX: USD" + decimalFormat.format(purchaseOrder.getTax());
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Products Description", subtitleFont));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100f);
            float[] columnWidths = {3f, 2f, 2f,1f,2f};
            table.setWidths(columnWidths);

            PdfPCell header1 = new PdfPCell(new Phrase("PRODUCT NAME"));
            PdfPCell header2 = new PdfPCell(new Phrase("QUANTITY"));
            PdfPCell header3 = new PdfPCell(new Phrase("UNIT PRICE" + "(USD)"));
            PdfPCell header4 = new PdfPCell(new Phrase("TAX"));
            PdfPCell header5 = new PdfPCell(new Phrase("TOTAL" + "(USD)")); ///TOTAL CANTIDAD + IMPUESTOS

            header1.setPadding(4);
// Añadir encabezados a la tabla
            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(header4);
            table.addCell(header5);

            for (OrderDetail orderDetail : purchaseOrder.getOrderDetail()) {
                Product product = orderDetail.getProduct();
                PdfPCell cell1 = new PdfPCell(new Phrase(product.getName()));
                PdfPCell cell2 = new PdfPCell(new Phrase(String.valueOf(orderDetail.getQuantity())));
                PdfPCell cell3 = new PdfPCell(new Phrase(String.valueOf(product.getPrice())));
                PdfPCell cell4 = new PdfPCell(new Phrase(("19"+"%")));
                cell1.setPadding(6);

                // Obtener valores numéricos para el cálculo
                int quantity = orderDetail.getQuantity();
                double price = product.getPrice();

                // Realizar el cálculo fuera de las celdas
                double totalPrice = quantity * price * 1.19;
                String formattedTotal = decimalFormat.format(totalPrice);
                PdfPCell cell5 = new PdfPCell(new Phrase(String.valueOf(formattedTotal)));

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);

            }
            document.add(table);
//            document.add(new Paragraph(formatedTax));
            document.add(new Paragraph(formattedAmount, subtitleFont));
            document.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

}


