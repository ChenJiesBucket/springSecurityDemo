/**
 *
 */
package com.dcj.security.core.properties;

/**
 * @author zhailiang
 *
 */
public class ImageCodeProperties extends SmsCodeProperties {

	public ImageCodeProperties() {
		setLength(4);
	}

	private int width = 67;
	private int height = 23;
	private int expireIn = 60;

	@Override
	public int getExpireIn() {
		return expireIn;
	}

	@Override
	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

}
