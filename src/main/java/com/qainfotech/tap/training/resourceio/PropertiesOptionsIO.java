package com.qainfotech.tap.training.resourceio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class PropertiesOptionsIO {

	/**
	 * 
	 * @param optionKey String
	 * @return Object
	 */
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