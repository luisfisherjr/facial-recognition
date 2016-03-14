package image_processor;

import java.util.ArrayList;
import java.util.List;

public class ImageData {
	private List<Integer> RGB = new ArrayList<Integer>();
	private boolean isFace;
	
	public ImageData(List<Integer> RGBIn, boolean isFaceIn){
		this.RGB = RGBIn;
		this.isFace = isFaceIn;
	}
	
	public List<Integer> getRGB() {
		return RGB;
	}
	public void setRGB(List<Integer> rGB) {
		RGB = rGB;
	}
	public boolean isFace() {
		return isFace;
	}
	public void setFace(boolean isFace) {
		this.isFace = isFace;
	}
}
