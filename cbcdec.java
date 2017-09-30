public class cbcdec{
	public static void main(String[] args) throws Exception{

		byte[] key_data;
		byte[] input_data;
		byte[] iv_data;
		byte[][] input_blocks;
		
		int input_size;
		
		//open files from cmd line args
		key_data = ctfuncs.key_file(args);
		input_data = ctfuncs.input_file(args);
		input_size = input_data.length;
		
		iv_data = ctfuncs.iv_file(args);
		
		//break message apart into blocks
		input_blocks = ctfuncs.make_blocks(input_data, input_size);
				
		//get IV at front and the rest are cipher text blocks
		ctfuncs.test_printing(input_blocks[1]);
		
		
		//send c1 block through AES
			//XOR c1 with IV to get m1
		
		//send c2 through 
			//xor c2 with c1 to get m2
			//loop
		
	}
}
