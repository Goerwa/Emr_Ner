package P1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MagicSquare  {
	public static boolean isLegalMagicSquare(String fileName) 
	{         
		File file=new File(fileName);  
		BufferedReader reader=null;
		Pattern p = Pattern.compile("^[0-9]*$");          
	    String temp =null;
		String str [] = null;
	    int [][] square = null; 
	    int line = 0;
	    try{  
            reader=new BufferedReader(new FileReader(file));  
            while((temp=reader.readLine())!=null)  
    	    {  
    	    		line++;
            	str = temp.split("\t");
            	//System.out.println(str.length);
    	    		if(line ==1 )
    	    		{
    	    			int n = str.length;
    	    			square = new int[n][n];		
    	    		}
    	    		for(int i=0;i<str.length;i++)
    	    		{
    	    			//Pattern p = Pattern.compile("^[0-9]$");
    	    			//System.out.println(str[i]);
    	    			Matcher m = p.matcher(str[i]);
    	    			if(m.matches() == false) {
    	    				System.out.println("it contains illegal numbers");
    	    				return false;
    	    			}
    	    		} 
    	    		for(int i=0;i<str.length;i++)
    	    		{
    	    			square[line-1][i] = Integer.valueOf(str[i]); 
    	    		}   	    		
    	    } 
    }     	    
	    catch(Exception e){  
	        e.printStackTrace();  
	    }  
	    finally{  
	        if(reader!=null){  
	            try{  
	                reader.close();  
	            }  
	            catch(Exception e){  
	                e.printStackTrace();  
	            }  
	        } 
	    } 
	    System.out.println(str.length);
	    System.out.println(line);
	    if(line !=  str.length) {
	    		System.out.println("the row and the columns are not the same");
	    		return false;
	    }
	    int flag = 0;
	    int flag1 = 0;
	    
	    for(int i=0;i<str.length;i++)
	    {
	    		flag += square[i][i];
	    		flag1 += square[i][str.length-1-i];
	    }
	    if(flag != flag1) {
	    		System.out.println("The values of diagonal are not equal");
	    		return false;
	    	}
	    for(int i=0;i<str.length;i++)
	    {
	    		int sum =0;
	    		for(int j=0;j<str.length;j++)
	    		{
	    			sum+=square[i][j];
	    		}
	    		if(sum != flag) {
	    			System.out.println("The values of rows are not equal");
	    			return false;
	    		}
	    }
	    for(int i=0;i<str.length;i++)
	    {
	    		int sum1 =0;
	    		for(int j=0;j<str.length;j++)
	    		{
	    			sum1+=square[i][j];
	    		}
	    		if(sum1 != flag){
	    			System.out.println("The values of columns are not equal");
	    			return false;
	    		}
	    } 
	    return true;
	}
	

	public static boolean generateMagicSquare(int n) throws IOException
	{
		   if(n<0 || n %2 == 0) {
			   System.out.println("The number entered is not valid");
			   return false;
		   }
		   int magic[][] = new int[n][n];   
		   int row = 0, col = n / 2, i, j, square = n * n;    
		   for (i = 1; i <= square; i++) 
		   {    
			   magic[row][col] = i;    
			   if (i % n == 0)     
				   row++;    
			   else 
			   {     
				   if (row == 0)     
					   row = n - 1;     
				   else      
					   row--;     
				   if (col == (n - 1))      
					   col = 0;     
				   else      col++;    
				   }   
			   }    
		   	for (i = 0; i < n; i++) 
		   	{    
		   		for (j = 0; j < n; j++)     
				   System.out.print(magic[i][j] + "\t");    
		   		System.out.println();   
		   	}
		   	FileWriter fw=new FileWriter(new File("src/P1/txt/6.txt"));
		    BufferedWriter  bw=new BufferedWriter(fw);
	    		for (i = 0; i < n; i++) 
	    		   {    
	    			   for (j = 0; j < n; j++)     
	    				  bw.write(magic[i][j]+"\t");
	    			   bw.write("\n");
	    		   }
	       bw.close();
	       fw.close();
		   return true; 
	} 
	
	public static void main(String[] args) throws IOException
	{
		System.out.println(isLegalMagicSquare("src/P1/txt/1.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/2.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/3.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/4.txt"));
		System.out.println(isLegalMagicSquare("src/P1/txt/5.txt"));
		System.out.println(generateMagicSquare(6));
		System.out.println(generateMagicSquare(-5));
		System.out.println(generateMagicSquare(5));
		//System.out.println(isLegalMagicSquare("src/P1/txt/6.txt"));
	}

}
