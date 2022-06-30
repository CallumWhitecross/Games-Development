package game2D;
import java.io.*;

public class FadeFilterStream extends FilterInputStream {
	
	public FadeFilterStream (InputStream in)
	{
		super(in);
	}
	
	public short getSample(byte[] buffer, int position)
	{
		return (short) (((buffer[position+1] & 0xff) << 8) | (buffer[position] & 0xff));
	}

	public void setSample(byte[] buffer, int position, short sample)
	{
		buffer[position] = (byte)(sample & 0xFF);
		buffer[position+1] = (byte)((sample >> 8) & 0xFF);
	}
	
	public int read(byte [] sample, int offset, int length) throws IOException
	{
		int bytesRead = super.read(sample,offset,length);
		float change = 2.0f * (1.0f / (float)bytesRead);
		float volume = 1.0f;
		short amp=0;
		// Loop through the sample 2 bytes at a time
		for (int p=0; p<bytesRead; p = p + 2)
		{
			amp = getSample(sample,p);
			amp = (short)((float)amp * volume);
			setSample(sample,p,amp);
			volume = volume - change;
		}
		return length;
	}
}
