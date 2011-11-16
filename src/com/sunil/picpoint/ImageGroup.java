package com.sunil.picpoint;


public class ImageGroup {
	private String img1_name, img2_name;
	private int img1_id, img2_id;
	private int img1_sound_id, img2_sound_id;
	
	public ImageGroup(String image1_name, int image1_id, int image1_sound_id, 
			String image2_name, int image2_id, int image2_sound_id) {
		img1_name = image1_name;
		img2_name = image2_name;
		img1_id = image1_id;
		img2_id = image2_id;	
		img1_sound_id = image1_sound_id;
		img2_sound_id = image2_sound_id;	
		}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("image 1: ").append(img1_name);
		sb.append("/n").append(img2_name);
		return sb.toString();
	}

	public int[] getImageIds() {
		int[] ids = {img1_id, img2_id};
		return ids;
	}
	

	public int[] getImageSoundIds() {
		int[] ids = {img1_sound_id, img2_sound_id};
		return ids;
	}
	public String[] getImageNames() {
		String[] names = {img1_name, img2_name};
		return names;
	}
	
}
