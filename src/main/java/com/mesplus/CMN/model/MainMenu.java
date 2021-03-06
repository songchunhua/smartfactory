package com.mesplus.CMN.model;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 메뉴에 정보를 정의하는 모델
 * @author Jinho
 * @since 1.0
 */
public class MainMenu {
	private String func_name;
	private String func_desc;
	private String func_type_flag;
	private String func_group;
	private String assembly_file;
	private String assembly_name;
	private String short_cut;
	private String icon_index;
	private String disp_level;
	private String separator;
	private String add_tool_bar;
	
	private List<MainMenu> children;
	
	/**
	 * child를 children(List<MainMenu>)에 저장한다.
	 * @param child 트리에 추가할 객체정보
	 */
	public void add(MainMenu child) {
		if(children == null)
			children = new LinkedList<MainMenu>();
		children.add(child);
	}
	
	public static List<MainMenu> buildHierarchy(List<MainMenu> menus) {
		Stack<MainMenu> parents = new Stack<MainMenu>();
		MainMenu root = new MainMenu();
		parents.push(root);
		
		for(MainMenu menu : menus) {
			MainMenu parent = null;
			
			try {
				parent = parents.peek();
			} catch(EmptyStackException e) {
			}

			if(menu.func_type_flag.startsWith("M")) {
				while(parent != root) {
					int compared = menu.disp_level.length() - parent.disp_level.length();
					if(compared == 0) {
						try {
							MainMenu last = parents.pop();
							parents.peek().add(last);
						} catch(EmptyStackException e) {
						}
						break;
					} else if(compared < 0){
						try {
							MainMenu last = parents.pop();
							parent = parents.peek();
							parent.add(last);
						} catch(EmptyStackException e) {
						}
						continue;
					} else {
						break;
					}
				}
				
				parents.push(menu);
			} else {
				//case menu.func_type_flag == "F"
				if(parent != null)
					parent.add(menu);
			}
		}
		
		return parents.firstElement().children;
	}
	
	public boolean isLeaf() {
		return func_type_flag.equals("F");
	}

	public String getText() {
		return func_desc;
	}

	public List<MainMenu> getChildren() {
		return children;
	}

	/**
	 * Function 아이디를 반환
	 * @return Function 아이디
	 */
	public String getFunc_name() {
		return func_name;
	}
	
	/**
	 * Function 아이디를 저장
	 * @param func_name Function 아이디
	 */
	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}
	
	/**
	 * Function 설명을 반환
	 * @return Function 설명
	 */
	public String getFunc_desc() {
		return func_desc;
	}
	
	/**
	 * Function 설명을 저장
	 * @param func_desc Function 설명
	 */
	public void setFunc_desc(String func_desc) {
		this.func_desc = func_desc;
	}
	
	public String getFunc_type_flag() {
		return func_type_flag;
	}
	public void setFunc_type_flag(String func_type_flag) {
		this.func_type_flag = func_type_flag;
	}
	public String getFunc_group() {
		return func_group;
	}
	public void setFunc_group(String func_group) {
		this.func_group = func_group;
	}
	public String getAssembly_file() {
		return assembly_file;
	}
	public void setAssembly_file(String assembly_file) {
		this.assembly_file = assembly_file;
	}
	public String getAssembly_name() {
		return assembly_name;
	}
	public void setAssembly_name(String assembly_name) {
		this.assembly_name = assembly_name;
	}
	public String getShort_cut() {
		return short_cut;
	}
	public void setShort_cut(String short_cut) {
		this.short_cut = short_cut;
	}
	public String getIcon_index() {
		return icon_index;
	}
	public void setIcon_index(String icon_index) {
		this.icon_index = icon_index;
	}
	public String getDisp_level() {
		return disp_level;
	}
	public void setDisp_level(String disp_level) {
		this.disp_level = disp_level;
	}
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public String getAdd_tool_bar() {
		return add_tool_bar;
	}
	public void setAdd_tool_bar(String add_tool_bar) {
		this.add_tool_bar = add_tool_bar;
	}
}
