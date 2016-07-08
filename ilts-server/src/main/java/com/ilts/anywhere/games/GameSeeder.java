package com.ilts.anywhere.games;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

public class GameSeeder {
	
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	public Marshaller getMarshaller() {
		return marshaller;
	}
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	public Object convertFromXml(String xmlfile) throws IOException {
		
		FileInputStream is = null;
		try {
			is = new FileInputStream(xmlfile);
			Object obj = getUnmarshaller().unmarshal(new StreamSource(is));
			System.out.println(obj);
			return obj;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		
	}

}
