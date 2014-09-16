/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rtp_streaming;

/**
 *
 * @author atwork
 */
import java.io.*;

public class VideoStream {

  FileInputStream fis; //video file
  int frame_nb; //current frame nb
  String filename;

  //-----------------------------------
  //constructor
  //-----------------------------------
  public VideoStream(String filenames) throws Exception{

    //init variables
    filename = filenames;
    frame_nb = 0;
  }

  //-----------------------------------
  // getnextframe
  //returns the next frame as an array of byte and the size of the frame
  //-----------------------------------
  public int getnextframe(byte[] frame) throws Exception
  {
    int length = 0,i=0;
    String length_string;
    byte[] frame_length = new byte[4];
    fis = new FileInputStream(filename);
    //read current frame length
    fis.read(frame_length,0,4);
	
    //transform frame_length to integer
    length_string = new String(frame_length);
    length = Integer.parseInt(length_string);
	
    i=(fis.read(frame,0,length));
    fis.close();
    return i;
  }
}
