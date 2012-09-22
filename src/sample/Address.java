package sample;

public class Address implements java.io.Serializable {
	private String name;
	private String address;

	public Address(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public void setName(String name) {
		this.name =name;
	}

	public String getName() {
		return name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}
