package image_processor;

import java.util.ArrayList;
import java.util.List;

public class ImageData {
	private List<Integer> RGB = new ArrayList<Integer>();
	private double isFace;
	
	public ImageData(List<Integer> RGBIn, boolean isFaceIn){
		this.RGB = RGBIn;
		if(isFaceIn){
			this.isFace = 1.0;
		}
		else
			this.isFace = 0.0;
	}
	
	public List<Integer> getRGB() {
		return RGB;
	}
	public void setRGB(List<Integer> rGB) {
		RGB = rGB;
	}
	public double isFace() {
		return isFace;
	}
	public void setFace(double isFace) {
		this.isFace = isFace;
	}
}
