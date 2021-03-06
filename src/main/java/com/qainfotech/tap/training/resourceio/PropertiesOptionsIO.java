package com.qainfotech.tap.training.resourceio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 * @return
	 * @throws IOException
	 */
public class PropertiesOptionsIO{
	 Properties prop ;
	 InputStream in;
	    public Object getOptionValue(String optionKey) throws IOException {
	    
	  return prop.getProperty(optionKey);
	  
	        //throw new UnsupportedOperationException("Not implemented.");
	    }

	    public void addOption(String optionKey, String optionValue) throws IOException {
	     prop = new Properties();
	  in = getClass().getResourceAsStream("/options.properties");
	  prop.load(in);
	  prop.setProperty(optionKey, optionValue);
	  prop.store(new FileOutputStream("D:\\eclipse\\Assignment4\\src\\main\\resources\\options.properties"), "Written");
	
	    
	    }
	}