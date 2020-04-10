package com.yonyou.ucf.mdf.app.service;

import com.yonyou.ucf.mdd.common.model.Pager;
import com.yonyou.ucf.mdd.common.report.model.StaticReport;
import com.yonyou.ucf.mdd.ext.bill.dto.BillDataDto;
import com.yonyou.ucf.mdd.ext.meta.model.ReportResult;
import com.yonyou.ucf.mdd.ext.poi.model.ExcelExportData;

import java.util.List;
import java.util.Map;


public interface IReportBaseService {
	public ReportResult query(Map<String, Object> param) throws Exception;
	public Pager queryByPage(BillDataDto bill) throws Exception;
	public List querytree(BillDataDto bill) throws Exception;
	public Map detail(BillDataDto bill) throws Exception;
	public Map movefirst(BillDataDto bill) throws Exception;

	public Map movelast(BillDataDto bill) throws Exception;

	public Map movenext(BillDataDto bill) throws Exception;

	public Map moveprev(BillDataDto bill) throws Exception;

	public StaticReport publish(BillDataDto bill) throws Exception;
	public StaticReport getStaticReport(Long id) throws Exception;
	public ExcelExportData export(BillDataDto bill) throws Exception;

	public Object getRefData(BillDataDto bill) throws Exception;
	//获取打印预览的数据
	public String getPrintData(String billno, List<Object> ids, String code, String condition) throws Exception;
	//获取打印模版数据结构
	public  Map<String,Object> getTemplateStructure(Map<String, Object> params) throws Exception ;

	public <E> List<E> selectList(String statement, Object parameter)
            throws Exception;
	
}
