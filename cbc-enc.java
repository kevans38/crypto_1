public class Main
{
	public static void main(String[] args) throws Exception
	{
		byte[] key_data;
		byte[] input_data;
		byte[] iv_data;
		
		
		key_data = ctfuncs.key_file(args);
		input_data = ctfuncs.input_file(args);
		iv_data = ctfuncs.iv_file(args);
		
		
		//encrypt
		//getBytes returns an array of bytes
		byte[] ct = ctfuncs.encrypt_data(test.getBytes());
		//System.out.println(new String(ct));
		
		

		
		ctfuncs.output_file(args, "output");
	}
}
