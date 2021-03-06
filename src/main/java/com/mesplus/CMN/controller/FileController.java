package com.mesplus.CMN.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mesplus.CMN.dao.FileDao;


/**
 * 파일의 업로드, 다운로드 기능을 관리하는 컨트롤러
 * @author Jinho
 * @since 1.0
 */
@Controller
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileDao fileDao;

    private static final String DESTINATION_DIR_PATH = "files";
    private static String realPath;
    
    /**
     * 파일을 업로드한다.
	 * <ul>
	 * 	<li>접속 주소: module/CMN/file_upload</li>
	 *  <li>접속 방법: POST</li>
	 * </ul>
     * @param request GET/POST로 전송받은 실제파일 정보
     * @param response GET/POST로 전송할 성공여부
     * @throws FileNotFoundException 파일을 찾지 못해서 발생한 에러정보
     * @throws IOException 업드로 실패 에러정보
     * @return <code>success: true</code> 업로드 성공 <code>success: false</code> 업로드 실패
     */
    @RequestMapping(value = "module/CMN/file_upload", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		request.setCharacterEncoding("utf-8");
    	} catch(Exception e) {
            logger.error(this.getClass().getName() + "cannot set character encoding to 'UTF-8': " + e.getMessage());
    	}

    	realPath = request.getServletContext().getRealPath(DESTINATION_DIR_PATH) + "/";
    	
    	logger.info("Realpath:" + realPath);
    	
        PrintWriter writer = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            writer = response.getWriter();
        } catch (IOException ex) {
            logger.error(this.getClass().getName() + "has thrown an exception: " + ex.getMessage());
        }

        try {
            String filename = URLDecoder.decode(request.getHeader("X-File-Name"), "UTF-8");
        	logger.info("Filename:" + filename);

        	is = request.getInputStream();
            fos = new FileOutputStream(new File(realPath + filename));
            IOUtils.copy(is, fos);
            response.setStatus(HttpServletResponse.SC_OK);
            writer.print("{success: true}");
        } catch (FileNotFoundException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print("{success: false}");
            logger.error(this.getClass().getName() + "has thrown an exception: " + ex.getMessage());
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print("{success: false}");
            logger.error(this.getClass().getName() + "has thrown an exception: " + ex.getMessage());
        } finally {
            try {
                fos.close();
                is.close();
            } catch (IOException ignored) {
            }
        }

        writer.flush();
        writer.close();
		
		return null;
	}

    /**
     * 파일을 다운로드한다.
	 * <ul>
	 * 	<li>접속 주소: module/CMN/file_download</li>
	 *  <li>접속 방법: POST</li>
	 * </ul>
     * @param request GET/POST로 전송받은 실제파일 정보
     * @param response GET/POST로 전송할 성공여부
     * @exception Exception 다운로드 실패 에러정보
     */
    @RequestMapping(value = "module/CMN/file_download", method = RequestMethod.POST)
	public void download(HttpServletRequest request, HttpServletResponse response) {
    	try {
//        	request.setCharacterEncoding("utf-8");
//        	response.setCharacterEncoding("utf-8");

        	realPath = request.getServletContext().getRealPath(DESTINATION_DIR_PATH) + "/";
        	
        	logger.info("Realpath:" + realPath);
        	
        	String filename = request.getParameter("fileId");
        	logger.info("Filename : " + filename);
        	
        	File file = new File(realPath + filename);
    		
        	FileInputStream is = new FileInputStream(realPath + filename);
        	
        	response.setContentType("application/octet-stream");
    		String encoded = new String(filename.getBytes(), response.getCharacterEncoding());
    		
    		response.setHeader("Content-Disposition","attachment;filename=\"" + encoded + "\"");
        	
        	response.setContentLength((int)file.length());
        	IOUtils.copy(is, response.getOutputStream());
    	} catch(Exception e) {
    		logger.error(e.getMessage());
    		// At this time ignore. I'll throw specific exception to handle error case.
    	}
    }

    /**
     * 파일을 다운로드한다.
	 * <ul>
	 * 	<li>접속 주소: module/CMN/file_download</li>
	 *  <li>접속 방법: GET</li>
	 * </ul>
     * @param request GET/POST로 전송받은 실제파일 정보
     * @param response GET/POST로 전송할 성공여부
     * @exception Exception 다운로드 실패 에러정보
     */
    @RequestMapping(value = "module/CMN/file_download", method = RequestMethod.GET)
	public void view(HttpServletRequest request, HttpServletResponse response) {
    	try {
        	request.setCharacterEncoding("utf-8");
        	response.setCharacterEncoding("utf-8");

        	realPath = request.getServletContext().getRealPath(DESTINATION_DIR_PATH) + "/";
        	
        	logger.info("Realpath:" + realPath);
        	
        	String filename = request.getParameter("fileId");
        	logger.info("Filename : " + filename);

        	File file = new File(realPath + filename);
    		
        	String mimetype = request.getServletContext().getMimeType(filename);
        	FileInputStream is = new FileInputStream(realPath + filename);
        	
        	response.setContentType(mimetype);
        	
        	response.setContentLength((int)file.length());
        	IOUtils.copy(is, response.getOutputStream());
    	} catch(Exception e) {
    		logger.error(e.getMessage());
    		// At this time ignore. I'll throw specific exception to handle error case.
    	}
    }
}
