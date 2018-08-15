import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Test
{
	
	public static void main(String[] args)
	{
		long startTime = System.nanoTime();
		Test obj = new Test();
		obj.parse();
		long endTime = System.nanoTime();
		System.out.println("Time elapsed: "+((endTime-startTime)/1000000)+" milliseconds");
	}
	
	public void parse()
	{
		ClassLoader loader = getClass().getClassLoader();
		URL jsonURL = loader.getResource("case_inbound.json");

		JSONParser parser = new JSONParser();
		try {
				Object obj = parser.parse(new FileReader(new File(jsonURL.toURI())));
		       JsonFactory factory = new JsonFactory();
		       ObjectMapper mapper = new ObjectMapper(factory);
		       JsonNode rootNode;
		       ArrayNode arrayNode;
		       Iterator<Map.Entry<String,JsonNode>> fieldsIterator;
		       int workorderCount= 0;
		       int timeTrackerCount= 0;
		       
		       rootNode= mapper.readTree(obj.toString());  
		       fieldsIterator= rootNode.fields();
		       while (fieldsIterator.hasNext()) 
		       {
		           Map.Entry<String,JsonNode> field;
		           field= fieldsIterator.next();
		           if(field.getKey().toString().equalsIgnoreCase("workorders"))
		           {
		        	   rootNode= mapper.readTree(field.getValue().toString());
		        	   arrayNode = (ArrayNode) rootNode;
		        	   for(JsonNode node: arrayNode)
		        	   {
		        		   workorderCount++;
		        		   fieldsIterator= node.fields();
			        	   while(fieldsIterator.hasNext())
			        	   {
			        		   field = fieldsIterator.next();
			        		   if(field.getKey().toString().equalsIgnoreCase("timeTracker"))
			        		   {
			        			   rootNode= mapper.readTree(field.getValue().toString());
			        			   arrayNode = (ArrayNode) rootNode;
					        	   for(JsonNode jsonNode: arrayNode)
					        	   {
					        		   timeTrackerCount++;
					        		   fieldsIterator= jsonNode.fields();
						        	   while(fieldsIterator.hasNext())
						        	   {
						        		   field = fieldsIterator.next();
						        		   if(field.getKey().toString().equalsIgnoreCase("laborEndTime"))
						        		   {
						        			   System.out.println("Workorder number= "+workorderCount+" "+
						        		   "TimeTracker Number= "+timeTrackerCount+" "+"Labor End Time= "+
						        					   field.getValue());
						        			   break;
						        		   }
						        	   }
					        	   }
					        	   break;
						        }
			        		   
			        		  }
			        	   }
		        	   break;
		        	   }
		        	   
		           }
		          
		       }
		catch(IOException | ParseException | URISyntaxException e)
		{
			System.out.println("Exception in parse(): "+e);
			e.printStackTrace();
		}
	}

	
}
