import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.*;
import java.io.*;

public class ctfuncs
{
	//need to divide by 8 because it will be in BYTES
	// so 128 bits / 8 = 16 bytes
	static int BLOCK_SIZE = 128/8;
	
	//Used for testing. TURN TO FALSE BEFORE TURNING IN
	static boolean PRINTING = false;


	
	
	//putting into AES to encrypt
	static byte[] encrypt_data(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
	IllegalBlockSizeException, BadPaddingException{
		String key = "bad8deadcafef00d";

		/*SecretKeySpec(byte[] key, String algorithm)
		Constructs a secret key from the given byte array.*/
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

		//provide details for mode and padding scheme
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

		//init(int opmode, Key key) Initializes this cipher mode with a key.
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		// public final byte[] doFinal(byte[] input)
		byte[] ct = cipher.doFinal(data);
        	return ct;
   	}
	
	//generate IV with random()
	public static void IVgen(LinkedList<Integer> IVrand){
		Random rand = new Random();
		
		for(int i = 0; i < 128; i++){
			//each bit
			int r = rand.nextInt(15)+1;//15 is max number in a bit,1 is min
			IVrand.add(r);
		}
	
	}
		

	public static byte[] key_file ( String[] args ) throws Exception
	{
		byte[] data = new byte[BLOCK_SIZE];
		
		for ( int i = 0; i < args.length; i++ )
		{
			//-k <key file>:  required, specifies a file storing a valid AES key as a hex encoded string
			if ( args[i].equals("-k") )
			{
				i++;
				
				try
				{
					data = read_file(args[i]);
				} 
				catch (Exception e)
				{
					System.err.format("Exception occurred trying to read '%s'.", args[i]);
					e.printStackTrace();
					return null;
				}

				
				break;
			}
		}
		
		
		return data;
					
	}
	
	public static byte[] input_file ( String[] args ) throws Exception
	{
		byte[] data = new byte[BLOCK_SIZE];
		
		for ( int i = 0; i < args.length; i++ )
		{
			//looks for the input file
			if ( args[i].equals("-i") )
			{
				i++;
				
				try
				{
					data = read_file(args[i]);
				} 
				catch (Exception e)
				{
					System.err.format("Exception occurred trying to read '%s'.", args[i]);
					e.printStackTrace();
					return null;
				}

				
				break;
			}
		}
		
		
		return data;
	}
	
	public static void output_file ( String[] args, String output )
	{
		
		for ( int i = 0; i < args.length; i++ )
		{
			//-o <output file>: required, specifies the path of the file where the resulting output is stored
			if ( args[i].equals("-o") )
			{
				System.out.println( args[i+1] );
				//make output file
				i++;
				break;
			}
		}
		
		//put results in output file
					
	}
	
	public static byte[] iv_file ( String[] args )throws Exception
	{
		byte[] data = new byte[BLOCK_SIZE];
		
		boolean iv_found = false;
		
		for ( int i = 0; i < args.length; i++ )
		{
			//looks for the iv file
			if ( args[i].equals("-v") )
			{
				i++;
				
				try
				{
					data = read_file(args[i]);
				} 
				catch (Exception e)
				{
					System.err.format("Exception occurred trying to read '%s'.", args[i]);
					e.printStackTrace();
					return null;
				}

				
				break;
			}
		}
		
		
		if( iv_found )
		{
			return data;
		}
		else
		{
//TODO:			//make IV generator here
			return data;
		}
		
	}
	
	
	//need to make open/make file functions
	public static byte[] read_file(String filename) throws Exception
	{
		InputStream is = null;
				
		byte[] data = new byte[BLOCK_SIZE];
		
		try
		{
			is = new FileInputStream(filename);
		    
			is.read(data);

if (PRINTING)
{
	for(byte b:data) {
         
        // convert byte to character
        char c = (char)b;
        
        // prints character
        System.out.print(c);
     }
}
			return data;
			
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
		finally
		{
			 if(is!=null)
			 {
				 is.close();
			 }
		}
	}
	
	
	
		
		
}
