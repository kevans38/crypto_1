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
	
	//putting into AES to encrypt
	static byte[] encrypt_data(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
	IllegalBlockSizeException, BadPaddingException
	{
		/*SecretKeySpec(byte[] key, String algorithm)
		Constructs a secret key from the given byte array.*/
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

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
				
		byte[] data;
		
		try
		{
			is = new FileInputStream(filename);
		    
			File file = new File(filename);
			
			data = new byte[(int) file.length()];
			
			is.read(data);

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
		int data_size = data.length * BLOCK_SIZE;
		
		byte[] data_string = new byte[data_size];
		
		for( int i = 0; i < data_size; i++ ) 
		{
			data_string[i] = data[i/BLOCK_SIZE][i%BLOCK_SIZE];
		} 
		
		return data_string;
	}
	
	public static byte[][] padding( byte[][] data )
	{
		int data_size = data.length * BLOCK_SIZE;
		
		byte padding = 1;
		
		for( int i = 0; i < data_size; i++ ) 
		{
			if ( data[i/BLOCK_SIZE][i%BLOCK_SIZE] == 0 )
			{
				data[i/BLOCK_SIZE][i%BLOCK_SIZE] = padding;
				padding++;
			}
		} 
		
		return data;
	}
	
	//TODO: an unpadding function
	
	
	public static byte[] append_bytes( byte[] to_data, byte[] from_data, int to_data_starting_byte )
	{
		for ( int i = 0; i < from_data.length; i++ )
		{
			to_data[(to_data_starting_byte*BLOCK_SIZE)+i] = from_data[i];
		}
		
		return to_data;
	}
	
	public static byte[] xor_bytes( byte[] data1, byte[] data2 )
	{
		byte[] xor_data = new byte[BLOCK_SIZE];
		
		for( int i = 0; i < BLOCK_SIZE; i++ )
		{
			xor_data[i] = (byte) (data1[i] ^ data2[i]);
		}
		
		return xor_data;
	}
	
	public static void test_printing( byte[] data )
	{
		for(byte b:data) {
	         
	        // convert byte to character
	        char c = (char)b;
	        
	        // prints character
	        System.out.print(c);
	     }
		
		System.out.println();;
	}
	
	
}
