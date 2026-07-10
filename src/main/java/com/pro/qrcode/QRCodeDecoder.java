package com.pro.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeDecoder {

	public static String decode(File file) throws Exception {

		BufferedImage image = ImageIO.read(file);

		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

		Result result = new MultiFormatReader().decode(bitmap);

		return result.getText();
	}

//	public static void main(String[] args) throws Exception {
//		String texto = decode(new File("qrcode.png"));
//		System.out.println(texto);
//	}
}