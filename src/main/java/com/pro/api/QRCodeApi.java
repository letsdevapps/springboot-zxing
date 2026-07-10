package com.pro.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.pro.qrcode.QRCodeDecoder;
import com.pro.qrcode.QRCodeService;

@RestController
@RequestMapping("/qrcode")
public class QRCodeApi {

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping("/generate-qrcode")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String text) throws IOException, WriterException {
        byte[] qrCode = qrCodeService.generateQRCode(text);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(qrCode);
    }

    //trying new method
    @GetMapping(value = "/generate-qrcode-2", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generate(@RequestParam String text) throws Exception {
        byte[] image = QRCodeService.generateQRCode(text, 300, 300);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
    
    @GetMapping("/decode-qrcode")
    public ResponseEntity<String> decodeQRCode() throws Exception {
    	byte[] bytes = QRCodeService.generateQRCode("http://www.google.com", 300, 300);
    	BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
    	
    	Path tempDir = Paths.get("tmp");
    	Files.createDirectories(tempDir);

//    	File file = tempDir.resolve("qrcode.png").toFile();
//    	ImageIO.write(image, "png", file);
//    	String decodedText = QRCodeDecoder.decode(file);

    	File tempFile = File.createTempFile(
    			"qrcode-",
    			".png",
    			tempDir.toFile()
    			);

	    try {
	        ImageIO.write(image, "png", tempFile);

	        String decodedText = QRCodeDecoder.decode(tempFile);

	        return ResponseEntity.ok(decodedText);

	    } finally {
	        Files.deleteIfExists(tempFile.toPath());
	    }
    }
    
//    @GetMapping("/decode-qrcode-tempfile")
    public ResponseEntity<String> decodeQRCodeTempFile() throws Exception {
    	byte[] bytes = QRCodeService.generateQRCode("http://www.google.com", 300, 300);
    	BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

    	// Warning Caution, Will create a file in the OS /tmp
    	// Use this only on Production
    	
    	File tempFile = File.createTempFile("qrcode-", ".png");
    	ImageIO.write(image, "png", tempFile);

    	String decodedText = QRCodeDecoder.decode(tempFile);
    	
    	tempFile.deleteOnExit();
    	
    	return ResponseEntity.ok(decodedText);
    }
    
    @PostMapping("/decode")
    public String decode(@RequestParam MultipartFile file) throws Exception {
        BufferedImage image = ImageIO.read(file.getInputStream());

        BinaryBitmap bitmap = new BinaryBitmap(
            new HybridBinarizer(
                new BufferedImageLuminanceSource(image)
            )
        );

        Result result = new MultiFormatReader().decode(bitmap);

        return result.getText();
    }
}