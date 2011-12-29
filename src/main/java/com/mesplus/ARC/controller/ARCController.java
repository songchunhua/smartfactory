package com.mesplus.ARC.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mesplus.ARC.dao.OriginalDao;
import com.mesplus.ARC.dao.TaskDao;

@Controller
public class ARCController {
	//private static final Logger logger = LoggerFactory.getLogger(CMNController.class);

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private OriginalDao originalDao;

	/*
	 * Archive
	 */
	
	//http://localhost:8080/smartfactory/module/ARC/data/taskList.json
	@RequestMapping(value = "module/ARC/data/taskList.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String,Object>> taskList(HttpServletRequest request, HttpServletResponse response) {
		return taskDao.getTaskList();
	}
	
	//http://localhost:8080/smartfactory/module/ARC/data/taskInfo.json?dbName=MES&taskId=LOTS
	@RequestMapping(value = "module/ARC/data/taskInfo.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String,Object> taskInfo(HttpServletRequest request, HttpServletResponse response) {
		String dbName = request.getParameter("dbName");
		String taskId = request.getParameter("taskId");
		
		Map<String,Object> infoMap = new LinkedHashMap<String, Object>();
		infoMap.put("taskBasic", taskDao.getTaskBasic(dbName, taskId));
		infoMap.put("taskMaster", taskDao.getTaskMaster(dbName, taskId));
		infoMap.put("taskMasterCondition", taskDao.getMasterCondition(dbName, taskId));
		infoMap.put("taskSlave", taskDao.getTaskSlave(dbName, taskId));
		
		return infoMap;
	}
	
	//http://localhost:8080/smartfactory/module/ARC/data/dbList.json
	@RequestMapping(value = "module/ARC/data/dbList.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String,Object>> dbList(HttpServletRequest request, HttpServletResponse response) {
		
		return taskDao.getDbList();
	}
	
	/*
	 * Orginal
	 */
	
	//http://localhost:8080/smartfactory/module/ARC/data/columnList.json?tableName=MRASRESHIS
	@RequestMapping(value = "module/ARC/data/columnList.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String,Object>> columnList(HttpServletRequest request, HttpServletResponse response) {
		String tableName = request.getParameter("tableName");
		
		return originalDao.getColumnList(tableName);
	}
	
	//http://localhost:8080/smartfactory/module/ARC/data/tableList.json
	@RequestMapping(value = "module/ARC/data/tableList.json", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String,Object>> tableList(HttpServletRequest request, HttpServletResponse response) {
		//String tableName = request.getParameter("owner");
		
		return originalDao.getTableList("MESMGR");
	}
	
	@RequestMapping(value = "module/ARC/data/taskCreate.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> taskCreate(HttpServletRequest request, HttpServletResponse response) {

		
		//TODO : request까지 받았어요~~~
		System.out.println("============================");
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()) {
			String name = (String) e.nextElement();
	      	String value = request.getParameter(name);
	      
	      	System.out.println(String.format("name = %s, value = %s ", name,value));
		}
		System.out.println("============================");
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("msg", "Consignment updated");
		
		return resultMap;
	}
	
}