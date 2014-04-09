package com.j3nsen.hiof.mat.model;

public class Product {
	private String productCode;
	private String barcodeFormat;
	private String producer;
	private String name;

	public Product(String productCode, String barcodeFormat, String producer, String name) {
		this.productCode = productCode;
		this.barcodeFormat = barcodeFormat;
		this.producer = producer;
		this.name = name;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public String getBarcodeFormat() {
		return this.barcodeFormat;
	}

	public String getProducer() {
		return this.producer;
	}

	public String getName() {
		return this.name;
	}
}