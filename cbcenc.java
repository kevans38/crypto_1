public class cbcenc
{
	static int BIT_BLOCK_SIZE = 128;
	//need to divide by 8 because it will be in BYTES
	// so 128 bits / 8 = 16 bytes
	static int BLOCK_SIZE = BIT_BLOCK_SIZE/8;
	
	public static void main(String[] args) throws Exception
	{
		//use test_printing(byte[]) to print out a byte array
		byte[] key_data;
		byte[] input_data;
		byte[] iv_data;
		
		byte[][] input_blocks;

		byte[] cipher_text;
		
		byte[] temp_data = null;
		
		key_data = ctfuncs.key_file(args);
		input_data = ctfuncs.input_file(args);
		iv_data = ctfuncs.iv_file(args);
		
		ctfuncs.test_printing(iv_data);
		
		input_blocks = ctfuncs.make_blocks(input_data);
		
		input_blocks = ctfuncs.padding(input_blocks);
		
		//+1 for the IV at the front
		cipher_text = new byte[(input_blocks.length+1)*BLOCK_SIZE];
		
		cipher_text = ctfuncs.append_bytes(cipher_text, iv_data, 0);
		
		for ( int i = 0; i < input_blocks.length; i++ )
		{
			if ( i == 0 )
			{
				temp_data = ctfuncs.xor_bytes(input_blocks[i], iv_data);
			}
			else
			{
				temp_data = ctfuncs.xor_bytes(input_blocks[i], temp_data);
			}
			
			temp_data = ctfuncs.encrypt_data(temp_data, key_data);
			
			
			cipher_text = ctfuncs.append_bytes(cipher_text, temp_data, i+1);

		}
		
		ctfuncs.output_file(args, cipher_text );
	}
}
