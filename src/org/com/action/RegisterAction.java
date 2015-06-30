package org.com.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.com.dao.SickDao;
import org.com.model.Sick;
import org.com.util.DbUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport implements
		ServletRequestAware {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request;
	private Sick sick;
	private String msg;
	DbUtil dbUtil = new DbUtil();
	SickDao sickDao = new SickDao();

	@Override
	public String execute() throws Exception {
		// 链接connection
		Connection con = null;

		try {
			con = dbUtil.getCon();
			boolean flag = sickDao.getSickByName(con,sick.getSickName());
			if(!flag){
				int num =  sickDao.sickAdd(con, sick);
				if(num>0){
					ActionContext.getContext().getSession().put("sick", sick);
					msg="";
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		msg= "该用户名已存在。";
		return INPUT;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Sick getSick() {
		return sick;
	}

	public void setSick(Sick sick) {
		this.sick = sick;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
