import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.io.*;

public class ctfuncs
{
	
	static int BIT_BLOCK_SIZE = 128;
	//need to divide by 8 because it will be in BYTES
	// so 128 bits / 8 = 16 bytes
	static int BLOCK_SIZE = BIT_BLOCK_SIZE/8;
	
//TODO: ?...
	static int BUFFER = 12000;
	
//TODO: Used for testing. TURN TO FALSE BEFORE TURNING IN
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
	public static byte[] IVgen()
	{
		byte[] IV = new byte[BLOCK_SIZE];	
//TODO: not... actually random?...
		SecureRandom random = new SecureRandom();
		random.nextBytes(IV);

		return IV;
	}
		

	public static byte[] key_file ( String[] args ) throws Exception
	{
		byte[] data = null;
				
		for ( int i = 0; i < args.length; i++ )
		{
			//-k <key file>:  required, specifies a file storing a valid AES key as a hex encoded string
			if ( args[i].equals("-k") )
			{
				i++;
				
				try
				{
					data =  read_file(args[i]);
				} 
				catch (Exception e)
				{
					System.err.format("Exception occurred trying to read '%s'.", args[i]);
					e.printStackTrace();
					return null;
				}

			}

		}

		
		
		return data;
					
	}
	
	public static byte[] input_file ( String[] args ) throws Exception
	{
		byte[] data = null;
				
		for ( int i = 0; i < args.length; i++ )
		{
			//Tries to find input file
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

			}
			
		}
		
		return data;
					
	}
	
	public static void output_file ( String[] args, byte[] output ) throws Exception
	{
		
		for ( int i = 0; i < args.length; i++ )
		{
			//-o <output file>: required, specifies the path of the file where the resulting output is stored
			if ( args[i].equals("-o") )
			{
				i++;
				write_file( args[i], output );
				break;
			}
		}
		
		//put results in output file
					
	}
	
	public static byte[] iv_file ( String[] args )throws Exception
	{
		byte[] data = null;
		
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
			data = IVgen();
			return data;
		}
		
	}
	
	
	//need to make open/make file functions
	public static byte[] read_file(String filename) throws Exception
	{
		InputStream is = null;
				
		byte[] data = new byte[BUFFER];
		
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
	
	
	public static void write_file(String filename, byte[] data ) throws Exception
	{
		
			FileOutputStream stream = new FileOutputStream(filename);
			try 
			{	
					stream.write(data);
			}
			catch (Exception e)
			{
				System.err.format("Exception occurred trying to write '%s'.", filename);
				e.printStackTrace();
			}
			finally
			{
				stream.close();
			}
		
			
		}
		
	
	public static byte[][] make_blocks( byte[] data )
	{
		int data_size = data.length;
		
		byte[][] data_blocks = new byte[(data_size/BLOCK_SIZE)+1][BLOCK_SIZE];
		
		for( int i = 0; i < data_size; i++ ) 
		{
			data_blocks[i/BLOCK_SIZE][i%BLOCK_SIZE] = data[i];
		} 
		
		return data_blocks;
	}
	
	public static byte[] merge_blocks( byte[][] data )
	{
		int data_size = data.length;
		
		byte[] data_string = new byte[data_size];
		
		for( int i = 0; i < data_size; i++ ) 
		{
			data_string[i] = data[i/BLOCK_SIZE][i%BLOCK_SIZE];
		} 
		
		return data_string;
	}

}
