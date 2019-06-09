package me.kerdo.shootr.net;

public class kPacket {
	private String type;
	private transient int currentMetaIndex = 0;
	private String[] metadata;
	
	public kPacket(String type, int metadataSize) {
		this.type = type;
		metadata = new String[metadataSize];
	}
	
	public void add(String data) {
		if(currentMetaIndex >= metadata.length)
			return;
		metadata[currentMetaIndex] = data;
		currentMetaIndex++;
	}
	
	public void set(int index, String data) {
		if(index >= metadata.length)
			return;
		metadata[index] = data;
	}
	
	public String getType() {
		return type;
	}
	
	public String[] getMetadata() {
		return metadata;
	}
}