package matrix_converter;

import image_processor.ImageData;

import java.util.ArrayList;
import java.util.List;

public class Normalizer {
	double max = 0;
	double min = 20000;
	
	public Normalizer(List<ImageData> dataListIn){
		normalize(dataListIn);
	}
	
	private List<ImageData> normalize(List<ImageData> dataIn){
		for (ImageData imageD: dataIn){
			List<Double> rgb = imageD.getRGB();
			for (double rgbValue: rgb){
				double value = Math.abs(rgbValue);
				if (value > max){
					max = value;
				}
				if (value < min){
					min = value;
				}
			}
		}
		
		System.out.println("Normalizing rgb values...");
		for (ImageData imageD: dataIn){
			List<Double> rgb = imageD.getRGB();
			List<Double> normalizedRGB = new ArrayList<Double>();
			for (double rgbValue: rgb){
				double value = (Math.abs(rgbValue) - min)/(max - min);
				normalizedRGB.add(value);
			}
			imageD.setRGB(normalizedRGB);
		}
		
		System.out.println("Features successfully scaled.");
		return null;
	}
}
