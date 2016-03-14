package image_processor;

import java.util.List;


public class ImageDemo {
	public static void main( String[] args )
    {
		//This is an example of how to use ImageParser & ImageData
		
		//Enter the images directory
		String filePath = "C:/Users/MyImages/not faces";
		
		//Depending on whether the file path contains faces or not, supplies a boolean value. Set true if your director contains images of faces.
		ImageParser parser = new ImageParser(filePath, false);
		
		//In ImageParser, all images are rescaled to 200x200. Only .jpg will be accepted.
		
		//Returns a list of ImageData
		List<ImageData> imageDataList = parser.getImageDataList();
		
		//Note* Each ImageData contains a list of RGB values as integers. There's 40000 integers in each list for a 200x200 size image.
		for (ImageData data: imageDataList){
			
			//if you're curious with the image's RGB values, go ahead and run this.
			/*
			List<Integer> values = data.getRGB();
			for (int value: values){
				System.out.println(value);
			}
			*/
			System.out.println("Does this image contain a face? " + data.isFace());
		}
    }
}
