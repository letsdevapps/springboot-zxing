package com.pro.qrcode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Service
public class QRCodeService {

	public byte[] generateQRCode(String text) throws WriterException, IOException {
		int size = 250; // Tamanho do QR Code

		// Definindo os parâmetros de configuração do QR Code
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		hints.put(EncodeHintType.MARGIN, 1); // Definindo a margem

		// Gerando o QR code
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix matrix = writer.encode(
				text, 
				BarcodeFormat.QR_CODE, 
				size, 
				size, 
				hints
				);

		// Criando a imagem a partir da matriz
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				image.setRGB(x, y, matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}

		// Convertendo a imagem para um array de bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", baos);
		return baos.toByteArray();
	}
	
	//trying new method
	public static byte[] generateQRCode(String text, int width, int height) throws Exception {
		Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix matrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }
}