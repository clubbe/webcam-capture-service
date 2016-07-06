package com.zailab;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "webcam")
public class WebcamSettings {

	private String name;
	private int width;
	private int height;
	private double fps;
	private int socketport;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getFps() {
		return fps;
	}

	public void setFps(double fps) {
		this.fps = fps;
	}

	public int getSocketport() {
		return socketport;
	}

	public void setSocketport(int socketport) {
		this.socketport = socketport;
	}

	@Override
	public String toString() {
		return "WebcamSettings [name=" + name + ", width=" + width + ", height=" + height + ", fps=" + fps + ", socketport=" + socketport + "]";
	}
}
