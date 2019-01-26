package application;

import java.util.Date;

public class Order {
	private String sender;
	private String name;
	private String dir;
	private float volume;
	private String StringReceived1;
	private String StringReceived;
	private int price;
	private float total;
	

	public Order(String sender, String name, String dir, float volume, String format, String format2, int price,
			int total) {
		this.sender = sender;
		this.name = name;
		this.dir = dir;
		this.volume = volume;
		this.StringReceived = format;
		this.price = price;
		this.total = total;
		this.StringReceived1=format2;
	}

	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume; 
	}
	public String getStringReceived() {
		return StringReceived;
	}
	public void setStringReceived(String StringReceived) {
		this.StringReceived = StringReceived;
	}
	public String getStringReceived1() {
		return StringReceived1;
	}
	public void setStringReceived1(String StringReceived) {
		this.StringReceived1 = StringReceived;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total=(volume*price);
	}

	@Override
	public String toString() {
		return sender + ";" + name + ";" + dir + ";" + volume + ";"
				+ StringReceived1 + ";" + StringReceived + ";" + price + ";" + total;
	}	
	
}
