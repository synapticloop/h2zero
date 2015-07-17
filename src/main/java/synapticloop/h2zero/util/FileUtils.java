package synapticloop.h2zero.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	private FileUtils() {}
	/**
	 * Copy a file from one destination to another
	 *
	 * @param source - the path to the source file
	 * @param destination - the path to the destination file
	 */
	public static void copyFile(String source, String destination) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			File sourceFile = new File(source);
			File destinationFile = new File(destination);
			inputStream = new FileInputStream(sourceFile);

			outputStream = new FileOutputStream(destinationFile);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = inputStream.read(buffer)) > 0){
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();

		} catch(FileNotFoundException fnfex){
			System.out.println("File not found, message was: " + fnfex.getMessage());
		} catch(IOException ioex){
			System.out.println("IO Exception, message was: " + ioex.getMessage());
		} finally {
			if(null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
					// do nothing
				}
			}
			try {
				if(null != outputStream) {
					outputStream.close();
				}
			} catch (IOException ignored) {
				// do nothing
			}
		}
	}

	/**
	 * Copy a resource from the classpath to the file system
	 *
	 * @param classpathSource  the classpath source
	 * @param destination the destination on the file system
	 */
	public static void copyResourceToFile(String classpathSource, String destination) {
		InputStream resourceAsStream = null;
		OutputStream outputStream = null;
		try {
			resourceAsStream = FileUtils.class.getResourceAsStream(classpathSource);
			File destinationFile = new File(destination);

			outputStream = new FileOutputStream(destinationFile);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = resourceAsStream.read(buffer)) > 0){
				outputStream.write(buffer, 0, length);
			}
			outputStream.flush();

		} catch(FileNotFoundException fnfex){
			System.out.println("File not found, message was: " + fnfex.getMessage());
		} catch(IOException ioex){
			System.out.println("IO Exception, message was: " + ioex.getMessage());
		} finally {
			if(null != resourceAsStream) {
				try {
					resourceAsStream.close();
				} catch (IOException ignored) {
					// do nothing
				}
			}
			try {
				if(null != outputStream) {
					outputStream.close();
				}
			} catch (IOException ignored) {
				// do nothing
			}
		}
	}
}
