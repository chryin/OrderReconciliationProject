package Project2StubFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderReader {
	
    public List<ClientOrder> readClientOrders(String filePath) {
        List<ClientOrder> clientOrders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            
            int lineNum = 1; // counter for reading through each line
            
            while ((line = br.readLine()) != null) {
            	
            	lineNum++;
                String[] parts = line.split(",");
                
                if(parts.length < 5) 
                {
                	System.out.println("Line malformed at line number: " + lineNum + line);
                }
                String id = parts[0].trim();
                String symbol = parts[1].trim();
                String side = parts[2].trim();
                int quantity = Integer.parseInt(parts[3].trim());
                int price = Integer.parseInt(parts[4].trim());
                
                if(quantity <= 0)
                {
                
                	throw new InvalidTradeException("Client order " + id + 
                	"quantity is negative or 0 = " + quantity);
                
                }
                
                if(price <= 0)
                {
                	
                		throw new InvalidTradeException("Client order" + id + "price is negative or 0");
                }

                ClientOrder order = new ClientOrder(id, symbol, side, quantity, price);
                clientOrders.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading client orders file: " + e.getMessage());
        }

        return clientOrders;
    }

    public List<ChildOrder> readChildOrders(String filePath) {
        List<ChildOrder> childOrders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
            	
            	lineNum++;
                String[] parts = line.split(",");
                
                if(parts.length < 6) 
                {
                	System.out.println("Line is malformed expected 6 columns): " 
                + lineNum + line);
                }
                
               try {
                String childId = parts[0];
                String parentId = parts[1];
                String symbol = parts[2];
                String side = parts[3];
                int quantity = Integer.parseInt(parts[4]);
                int price = Integer.parseInt(parts[5]);
                int filledQuantity = Integer.parseInt(parts[6]);
                
                if(quantity <= 0)
                {
                	throw new InvalidTradeException("Child Order" + childId + "has negative quantity" + 
                	filledQuantity);
                }
                
                if(price <= 0)
                {
                	throw new InvalidTradeException("Child Order" + childId + "has a negative price"
                	+ price);
                }
                
                if(filledQuantity < 0)
                {
                	throw new InvalidTradeException("Child Order " + 
                	childId + "has negative filled quantity" + filledQuantity);
                }
                
               }
                
                catch(NumberFormatException nfe)
                {
                	System.out.println("Invalid Number Format: " + lineNum + line);
                }
                
                catch(InvalidTradeException ite)
               {
                	System.out.println("Rejected child order line" +  lineNum + ite.getMessage());
               }

                ChildOrder child = new ChildOrder(parentId, symbol, side, quantity, price, filledQuantity);
                childOrders.add(child);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading child orders file: " + e.getMessage());
        }

        return childOrders;
    }
}





//catch exceptions for each of the values.
