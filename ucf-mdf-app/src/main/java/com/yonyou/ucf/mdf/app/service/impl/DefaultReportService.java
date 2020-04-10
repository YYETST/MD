package com.yonyou.ucf.mdf.app.service.impl;

import com.yonyou.ucf.mdd.common.model.Pager;
import com.yonyou.ucf.mdd.common.report.model.StaticReport;
import com.yonyou.ucf.mdd.ext.bill.biz.BillBiz; //TODO BillBiz 不是mdd里面的
import com.yonyou.ucf.mdd.ext.bill.dto.BillDataDto;
import com.yonyou.ucf.mdd.ext.dao.sql.SqlHelper;
import com.yonyou.ucf.mdd.ext.meta.model.ReportResult;
import com.yonyou.ucf.mdd.ext.poi.model.ExcelExportData;
import com.yonyou.ucf.mdd.ext.report.utils.ReportUtils;
import com.yonyou.ucf.mdf.app.service.IReportBaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("mddDefaultReportService")
public class DefaultReportService implements IReportBaseService {
	
	@Override
	public Pager queryByPage(BillDataDto bill) throws Exception {
		Pager pager= BillBiz.queryByPage(bill);
		ReportUtils.clearTempReport();
		return pager;
	}
	@Override
	public Map detail(BillDataDto bill) throws Exception {
		return BillBiz.detail(bill);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List querytree(BillDataDto bill) throws Exception {
		return BillBiz.querytree(bill);
	}
	@Override
	public StaticReport publish(BillDataDto bill) throws Exception {
		return BillBiz.publish(bill);
	}

	@Override
	public StaticReport getStaticReport(Long id) throws Exception {
		return ReportUtils.getStaticReport(id);
	}
 
	@Override
	public ExcelExportData export(BillDataDto bill) throws Exception {
		return BillBiz.export(bill);
	}

	@Override
	public Object getRefData(BillDataDto bill) throws Exception {
		return BillBiz.getRefData(bill);
	}
	@Override
	public Map<String, Object> getTemplateStructure(Map<String, Object> params) throws Exception {
		return BillBiz.getTemplateStructure(params);
	}

	@Override
	public String getPrintData(String billno, List<Object> ids,String code,String condition) throws Exception {
		return BillBiz.getPrintData(billno, ids,code,condition);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter)
			throws Exception {
		return SqlHelper.selectList(statement, parameter);//TODO 数据库修改
	}
	@Override
	public Map movefirst(BillDataDto bill) throws Exception {
		return BillBiz.movefirst(bill);
	}
	@Override
	public Map movelast(BillDataDto bill) throws Exception {
		return BillBiz.movelast(bill);
	}
	@Override
	public Map movenext(BillDataDto bill) throws Exception {
		return BillBiz.movenext(bill);
	}
	@Override
	public Map moveprev(BillDataDto bill) throws Exception {
		return BillBiz.moveprev(bill);
	}
	
	@Override
	public ReportResult query(Map<String,Object> param)
			throws Exception {
		return BillBiz.query(param);
	}
}
