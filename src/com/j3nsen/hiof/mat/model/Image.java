package com.j3nsen.hiof.mat.model;

public class Image {
	private int imageId;
	private String filePath;


	public Image(int imageId, String filePath) {
		this.imageId = imageId;
		this.filePath = filePath;
	}

	public int getImageId() {
		return this.imageId;
	}

	public String getFilePath() {
		return this.filePath;
	}
}
