package image_processor;

import java.util.ArrayList;
import java.util.List;

public class ImageData {
	private List<Double> RGB = new ArrayList<Double>();
	private double isFace;
	
	public ImageData(List<Double> RGBIn, boolean isFaceIn){
		this.RGB = RGBIn;
		if(isFaceIn){
			this.isFace = 1.0;
		}
		else
			this.isFace = 0.0;
	}
	
	public List<Double> getRGB() {
		return RGB;
	}
	public void setRGB(List<Double> rGB) {
		RGB = rGB;
	}
	public double getIsFace() {
		return isFace;
	}
	public void setFace(double isFace) {
		this.isFace = isFace;
	}
}
