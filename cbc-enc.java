public class Main
{
	public static void main(String[] args) throws Exception
	{
		byte[] key_data;
		byte[] input_data;
		byte[] iv_data;
		
		byte[][] key_blocks;
		byte[][] input_blocks;
		byte[][] iv_blocks;

		
//TODO: array is an example. Need to remove
		byte[] output_data;
		
		key_data = ctfuncs.key_file(args);
		input_data = ctfuncs.input_file(args);
		iv_data = ctfuncs.iv_file(args);
	
		input_blocks = ctfuncs.make_blocks(input_data);
		
		output_data = ctfuncs.merge_blocks(input_blocks);
		
		ctfuncs.output_file(args, output_data );
	}
}
