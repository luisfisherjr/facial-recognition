package image_processor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageParser {
	
	private boolean isFace;
	private File[] images;
	private List<ImageData> imageDataList = new ArrayList<ImageData>();
	public ImageParser(String filePathIn, boolean isFaceIn){
		
		this.isFace = isFaceIn;
		
		File file = new File(filePathIn);
		System.out.println("Now processing images under: " + filePathIn + "...");
		
		images = file.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith("jpg");
			}
		});
		
		for (File f: images){
			processImage(f.getAbsolutePath());
		}
		
		System.out.println("Image processing complete.");
		
	}
	
	public void processImage(String pathIn){
		
		try {
			BufferedImage imgOriginal = ImageIO.read(new File(pathIn));
			BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.createGraphics();
			g.drawImage(imgOriginal, 0, 0, 200, 200, null);
			g.dispose();

			int[][] fullPixels = new int [img.getHeight()] [img.getWidth()];
			List<Integer> result = new ArrayList<Integer>();
			for (int row = 0; row < img.getHeight(); row ++){
				for (int col = 0; col < img.getWidth(); col++){
					fullPixels[row][col] = img.getRGB(col, row);
					int value = img.getRGB(col, row);
					result.add(value);

				}
			}
			
			ImageData imageD = new ImageData(result, isFace);
			imageDataList.add(imageD);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ImageData> getImageDataList() {
		return imageDataList;
	}

	public void setImageDataList(List<ImageData> imageDataList) {
		this.imageDataList = imageDataList;
	}
}
