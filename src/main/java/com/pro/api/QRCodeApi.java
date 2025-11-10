package com.pro.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.pro.qrcode.QRCodeService;

@RestController
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
}