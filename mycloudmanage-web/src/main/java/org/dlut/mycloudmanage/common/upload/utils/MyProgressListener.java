package org.dlut.mycloudmanage.common.upload.utils;

import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Service;

public class MyProgressListener implements ProgressListener {
	private double megaBytes = -1;
	private HttpSession session;
	public MyProgressListener(HttpServletRequest request) {
		session=request.getSession();
	}
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
//		System.out.println("already read"+pBytesRead);
//		System.out.println("total"+pContentLength);
             int percent = (int) (((float)pBytesRead / (float)pContentLength) * 100);
             session.setAttribute("percent", percent+"");
       //      System.out.println("percent-----------"+percent);
         }
	}

